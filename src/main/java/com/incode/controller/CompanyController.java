package com.incode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incode.dto.UnifiedResponseDTO;
import com.incode.service.CompanyService;

import tools.jackson.databind.ObjectMapper;

@RestController
public class CompanyController {

	private final CompanyService companyService;

	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	private ObjectMapper objectMapper = new ObjectMapper();

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping("/backend-service")
	public UnifiedResponseDTO getVerification(@RequestParam String verificationId, @RequestParam String query) {

		logger.info("[{}] - Query [{}]", verificationId, query);

		UnifiedResponseDTO response = companyService.processVerification(verificationId, query);

		String json = objectMapper.writeValueAsString(response);

		logger.info("[{}] - [{}]", verificationId, json);

		return response;
	}

}
