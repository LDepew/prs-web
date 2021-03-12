package com.prs.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.LineItem;
import com.prs.business.Request;
import com.prs.db.RequestRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestController {
	
	@Autowired
	private RequestRepo requestRepo;

	@GetMapping("/")
	public List<Request> getAll() {
		return requestRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Request> getById(@PathVariable int id) {
		return requestRepo.findById(id);
	}

	@GetMapping("/list-review/{id}")
	public List<Request> getReview(@PathVariable int id){
		List<Request> requestList = requestRepo.findAll();
		List<Request> reviewList = new ArrayList<>();
		for (Request request: requestList) {
			if (request.getStatus().equalsIgnoreCase("Review") && request.getUser().getId() != id) {
				reviewList.add(request);
			}
		}
		return reviewList;
	}
	
	@PostMapping("/")
	public Request create(@RequestBody Request request) {
		request.setStatus("New");
		request.setTotal(0);
		LocalDateTime dateSubmitted = LocalDateTime.now();
		request.setSubmittedDate(dateSubmitted);
		request.setReasonForRejection(null);
		return requestRepo.save(request);
	}
	
	
	@PutMapping("/submit-review")
	public Request submitReview(@RequestBody Request request) {
		if (request.getTotal() <= 50) {
			request.setStatus("Approved");
		}
		else {
			request.setStatus("Review");
		}
		LocalDateTime timeSubmitted = LocalDateTime.now();
		request.setSubmittedDate(timeSubmitted);
		requestRepo.save(request);
		return request;	
	}
	
	
	@DeleteMapping("/{id}") 
	public Request delete(@PathVariable int id) {
		Optional<Request> request = requestRepo.findById(id);
		if (request.isPresent()) {
			requestRepo.delete(request.get());
		}
		else {
			System.out.println("Delete Error - request not found for id: "+id);
		}
		return request.get();
	}
	
	@PutMapping("/approve")
	public Request approve(@RequestBody Request request) {
		request.setStatus("Approved");
		return request;
	}
	
	@PutMapping("/reject")
	public Request reject(@RequestBody Request request) {
		request.setStatus("Rejected");
		return request;
	}

}
