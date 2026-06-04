package com.incode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incode.model.RequestLog;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
	List<RequestLog> findTop50ByVerificationIdOrderByCreatedDesc(String verificationId);
}
