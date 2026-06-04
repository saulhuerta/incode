package com.incode.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonRawValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "request_logs")
public class RequestLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "verification_id", nullable = false)
	private String verificationId;

	@JsonRawValue // To avoid (\n, \")
	@Lob // In case json is big
	@Column(name = "response_json", columnDefinition = "LONGTEXT")
	private String responseJson;

	@Column(name = "created")
	private LocalDateTime created;

	public RequestLog() {
	}

	public RequestLog(String verificationId, String responseJson) {
		this.verificationId = verificationId;
		this.responseJson = responseJson;
		this.created = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVerificationId() {
		return verificationId;
	}

	public void setVerificationId(String verificationId) {
		this.verificationId = verificationId;
	}

	public String getResponseJson() {
		return responseJson;
	}

	public void setResponseJson(String responseJson) {
		this.responseJson = responseJson;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
}
