package com.incode.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incode.dto.CompanyResponseDTO;
import com.incode.dto.FreeServiceCompanyDTO;
import com.incode.dto.PremiumServiceCompanyDTO;
import com.incode.dto.UnifiedResponseDTO;
import com.incode.model.RequestLog;
import com.incode.model.VerificationSource;
import com.incode.repository.RequestLogRepository;

@Service
public class CompanyService {

	private final RestTemplate restTemplate;
	private final RequestLogRepository requestLogRepository;
	private final ObjectMapper objectMapper;

	private final String FREE_URL = "http://localhost:8080/free-third-party?query=";
	private final String PREMIUM_URL = "http://localhost:8080/premium-third-party?query=";

	private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

	public CompanyService(RestTemplate restTemplate, RequestLogRepository requestLogRepository,
			ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
		this.requestLogRepository = requestLogRepository;
		this.objectMapper = objectMapper;
	}

	public UnifiedResponseDTO processVerification(String verificationId, String query) {
		List<CompanyResponseDTO> finalResults = new ArrayList<>();
		VerificationSource usedSource = VerificationSource.NONE;

		try {
			// Trying with the FREE Service
			logger.info("[{}] - Calling to FREE service...", verificationId);

			ResponseEntity<FreeServiceCompanyDTO[]> response = restTemplate.getForEntity(FREE_URL + query,
					FreeServiceCompanyDTO[].class);

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null
					&& response.getBody().length > 0) {

				finalResults = Arrays.stream(response.getBody()).filter(FreeServiceCompanyDTO::is_active)
						.map(f -> new CompanyResponseDTO(f.cin(), f.name(), f.registration_date(), f.address()))
						.collect(Collectors.toList());

				usedSource = VerificationSource.FREE;

			}
		} catch (HttpServerErrorException.ServiceUnavailable e) {
			logger.error("[{}] - FREE Service is down (503). Fallback to PREMIUM", verificationId);
		} catch (Exception e) {
			logger.error("[{}] - ERROR in FREE Service \n", verificationId, e.getMessage());
		}

		// Fallback: If free service is down or no results
		if (finalResults.isEmpty()) {
			try {

				logger.info("[{}] - Calling to PREMIUM service...", verificationId);
				ResponseEntity<PremiumServiceCompanyDTO[]> response = restTemplate.getForEntity(PREMIUM_URL + query,
						PremiumServiceCompanyDTO[].class);

				if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {

					finalResults = Arrays.stream(response.getBody()).filter(PremiumServiceCompanyDTO::isActive)
							.map(p -> new CompanyResponseDTO(p.companyIdentificationNumber(), p.companyName(),
									p.registrationDate(), p.fullAddress()))
							.collect(Collectors.toList());

					usedSource = VerificationSource.PREMIUM;
				}

			} catch (Exception e) {

				logger.info("[{}] - Query: '{}' | Source: {} | Error: PREMIUM service not available", verificationId,
						query, usedSource);

				UnifiedResponseDTO response = new UnifiedResponseDTO(verificationId, query, usedSource, null, List.of(),
						"Services not available");

				String jsonContent = null;
				try {
					jsonContent = objectMapper.writeValueAsString(response);
					RequestLog log = new RequestLog(verificationId, jsonContent);
					requestLogRepository.save(log);
				} catch (JsonProcessingException ex) {
					logger.error("Error parsing the response to json", ex);
				}

				return response;
			}
		}

		// Prepare final results
		if (finalResults.isEmpty()) {

			return new UnifiedResponseDTO(verificationId, query, usedSource, null, List.of(),
					"No companies were found");
		}

		CompanyResponseDTO primary = finalResults.get(0); // First matching
		List<CompanyResponseDTO> others = finalResults.size() > 1 ? finalResults.subList(1, finalResults.size())
				: List.of();

		UnifiedResponseDTO response = new UnifiedResponseDTO(verificationId, query, usedSource, primary, others,
				"Source: " + usedSource);

		String jsonContent = null;
		try {
			jsonContent = objectMapper.writeValueAsString(response);
			RequestLog log = new RequestLog(verificationId, jsonContent);
			requestLogRepository.save(log);
		} catch (JsonProcessingException e) {
			logger.error("Error parsing the response to json", e);
		}

		return response;
	}
}
