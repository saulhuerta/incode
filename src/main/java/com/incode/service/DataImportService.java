package com.incode.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.incode.dto.FreeServiceCompanyDTO;
import com.incode.dto.PremiumServiceCompanyDTO;

import jakarta.annotation.PostConstruct;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
public class DataImportService {
	private List<FreeServiceCompanyDTO> freeCompanies;
	private List<PremiumServiceCompanyDTO> premiumCompanies;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	public void init() throws IOException {
		// Load files
		freeCompanies = objectMapper.readValue(new ClassPathResource("free_service_companies-1.json").getInputStream(),
				new TypeReference<List<FreeServiceCompanyDTO>>() {
				});

		premiumCompanies = objectMapper.readValue(
				new ClassPathResource("premium_service_companies-1.json").getInputStream(),
				new TypeReference<List<PremiumServiceCompanyDTO>>() {
				});
	}

	public List<FreeServiceCompanyDTO> getFreeCompanies() {
		return freeCompanies;
	}

	public List<PremiumServiceCompanyDTO> getPremiumCompanies() {
		return premiumCompanies;
	}
}
