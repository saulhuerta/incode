package com.incode.dto;

//This DTO maps free_service_companies-1.json
public record FreeServiceCompanyDTO(String cin, String name, String registration_date, String address,
		boolean is_active) {
}
