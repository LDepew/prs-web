package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.Request;
import com.prs.db.RequestRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
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
	
	@PutMapping("/") 
	public Request update(@RequestBody Request request) {
		return requestRepo.save(request);
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

}
