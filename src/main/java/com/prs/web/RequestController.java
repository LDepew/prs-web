package com.prs.web;

import java.time.LocalDateTime;
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
	public Request getById(@PathVariable int id) {
		return requestRepo.findById(id).get();
	}
	
	@PostMapping("/") 
	public Request create(@RequestBody Request request) {
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
	
//	@GetMapping("/list-review/{id}")
//	public List<Request> listReview(@PathVariable int id)
//	//id pass as variable is the signed in userId
//	//Call custom method which gets all requests in 'Review' status and userID
//	List<Request> requestList = requestRepo.findAll();
//	List<Request> reviewable = newArrayList<>();
//	
//	//!= id
//	//Return List<Request>
	
	
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

}
