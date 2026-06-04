package com.incode.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.incode.model.RequestLog;
import com.incode.repository.RequestLogRepository;

@Controller
@RequestMapping("/logs")
public class RequestController {

	private final RequestLogRepository logRepository;

	public RequestController(RequestLogRepository logRepository) {
		this.logRepository = logRepository;
	}

	// RESTful Endpoint
	@GetMapping("/api/request/{verificationId}")
	@ResponseBody
	public List<RequestLog> getLogsApi(@PathVariable String verificationId) {
		return logRepository.findTop50ByVerificationIdOrderByCreatedDesc(verificationId);
	}

	// UI Endpoint
	@GetMapping("/view")
	public String viewLogs(@RequestParam(required = false) String verificationId, Model model) {

		System.out.println("Verification Id: " + verificationId);

		if (verificationId != null && !verificationId.isEmpty()) {
			List<RequestLog> results = logRepository.findTop50ByVerificationIdOrderByCreatedDesc(verificationId);

			System.out.println("Results: " + results);

			model.addAttribute("logs", results);
			model.addAttribute("lastVId", verificationId);
		} else {
			model.addAttribute("logs", new ArrayList<>());
		}
		return "log-view";
	}

}
