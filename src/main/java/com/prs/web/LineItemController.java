package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.LineItem;
import com.prs.business.Request;
import com.prs.db.LineItemRepo;
import com.prs.db.RequestRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/line-items")
public class LineItemController {
	
	@Autowired
	private LineItemRepo lineItemRepo;
	
	@Autowired
	private RequestRepo requestRepo;
	

	@GetMapping("/")
	public List<LineItem> getAll() {
		return lineItemRepo.findAll();
	}

	@GetMapping("/{id}")
	public LineItem getById(@PathVariable int id) {
		return lineItemRepo.findById(id).get();
	}
	
	@PostMapping("/")
	public LineItem addLineItem(@RequestBody LineItem lineItem) {
		lineItemRepo.save(lineItem);
		recalculateLineItemValue(lineItem);
		return lineItem;
	}
	
	private void recalculateLineItemValue(LineItem lineItem) {
		Request r = lineItem.getRequest();
		List<LineItem> lineItem1 = 
				lineItemRepo.findAllByRequestId(r.getId());
		double newTotal = 0.0;
		for (LineItem lineCollect:lineItem1) {
			newTotal += lineCollect.getQuantity()*lineCollect.getProduct().getPrice();
		}
		
		r.setTotal(newTotal);
		requestRepo.save(r);
		
	}
	
	@GetMapping("/lines-for-pr/{id}")
	public List<LineItem> allLineItems(@PathVariable int id) {
		// select * from line-items where request id = {id}
		return lineItemRepo.findAllByRequestId(id);
	}
	
	@PutMapping("/") 
	public LineItem update(@RequestBody LineItem lineItem) {
		lineItemRepo.save(lineItem);
		recalculateLineItemValue(lineItem);
		return lineItem;
	}
	
	@DeleteMapping("/{id}") 
	public LineItem delete(@PathVariable int id) {
		Optional<LineItem> lineItem = lineItemRepo.findById(id);
		if (lineItem.isPresent()) {
			lineItemRepo.delete(lineItem.get());
			recalculateLineItemValue(lineItem.get().getRequest());
		}
		else {
			System.out.println("Delete Error - lineItem not found for id: "+id);
		}
		return lineItem.get();
	}
	

	

}
