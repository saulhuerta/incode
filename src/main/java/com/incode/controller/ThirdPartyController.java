package com.incode.controller;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incode.dto.FreeServiceCompanyDTO;
import com.incode.dto.PremiumServiceCompanyDTO;
import com.incode.service.DataImportService;

@RestController
public class ThirdPartyController {

	private final DataImportService dataLoaderService;
	private final Random random = new Random();

	private static final Logger logger = LoggerFactory.getLogger(ThirdPartyController.class);

	public ThirdPartyController(DataImportService dataLoaderService) {
		this.dataLoaderService = dataLoaderService;
	}

	@GetMapping("/free-third-party")
	public ResponseEntity<List<FreeServiceCompanyDTO>> getFreeData(@RequestParam String query) {

		// Simulate 40% of error --> 503
		if (random.nextInt(100) < 40) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}

		List<FreeServiceCompanyDTO> filtered = dataLoaderService.getFreeCompanies().stream()
				.filter(c -> c.cin().contains(query)) // Filter by Company Identification Number
				.toList();

		logger.info("---> FREE Service");

		return ResponseEntity.ok(filtered);
	}

	@GetMapping("/premium-third-party")
	public ResponseEntity<List<PremiumServiceCompanyDTO>> getPremiumData(@RequestParam String query) {
		// Simulate 10% of error --> 503
		if (random.nextInt(100) < 10) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}

		List<PremiumServiceCompanyDTO> filtered = dataLoaderService.getPremiumCompanies().stream()
				.filter(c -> c.companyIdentificationNumber().contains(query)) // Filter by Company Identification Number
				.toList();

		logger.info("---> PREMIUM Service");

		return ResponseEntity.ok(filtered);
	}
}
