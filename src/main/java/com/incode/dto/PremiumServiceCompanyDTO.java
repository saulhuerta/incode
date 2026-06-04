package com.incode.dto;

//This DTO maps premium_service_companies-1.json
public record PremiumServiceCompanyDTO(String companyIdentificationNumber, String companyName, String registrationDate,
		String fullAddress, boolean isActive) {
}
