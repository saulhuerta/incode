package com.incode.dto;

import java.util.List;

import com.incode.model.VerificationSource;

public record UnifiedResponseDTO(String verificationId, String query, VerificationSource source,
		CompanyResponseDTO result, List<CompanyResponseDTO> otherResults, String message) {

}
