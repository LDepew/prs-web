package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.LineItem;
import com.prs.db.LineItemRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/lineItems")
public class LineItemController {
	
	@Autowired
	private LineItemRepo lineItemRepo;

	@GetMapping("/")
	public List<LineItem> getAll() {
		return lineItemRepo.findAll();
	}

	@GetMapping("/{id}")
	public LineItem getById(@PathVariable int id) {
		return lineItemRepo.findById(id).get();
	}
	
	@PostMapping("/") 
	public LineItem create(@RequestBody LineItem lineItem) {
		return lineItemRepo.save(lineItem);
	}
	
	@PutMapping("/") 
	public LineItem update(@RequestBody LineItem lineItem) {
		return lineItemRepo.save(lineItem);
	}
	
	@DeleteMapping("/{id}") 
	public LineItem delete(@PathVariable int id) {
		Optional<LineItem> lineItem = lineItemRepo.findById(id);
		if (lineItem.isPresent()) {
			lineItemRepo.delete(lineItem.get());
		}
		else {
			System.out.println("Delete Error - lineItem not found for id: "+id);
		}
		return lineItem.get();
	}
	

	

}
