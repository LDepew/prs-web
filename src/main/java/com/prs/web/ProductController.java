package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prs.business.Product;
import com.prs.business.User;
import com.prs.db.ProductRepo;

	
	@CrossOrigin
	@RestController
	@RequestMapping("/api/products")
	public class ProductController {
		
		@Autowired
		private ProductRepo productRepo;

		@GetMapping("/")
		public List<Product> getAll() {
			return productRepo.findAll();
		}

		@GetMapping("/{id}")
		public Product getById(@PathVariable int id) {
			return productRepo.findById(id).get();
		}
		
		@PostMapping("/") 
		public Product create(@RequestBody Product product) {
			return productRepo.save(product);
		}
		
		@PutMapping("/") 
		public Product update(@RequestBody Product product) {
			return productRepo.save(product);
		}
		
//		@DeleteMapping("/{id}") 
//		public Product delete(@PathVariable int id) {
//			Optional<Product> product = productRepo.findById(id);
//			if (product.isPresent()) {
//				productRepo.delete(product.get());
//			}
//			else {
//				System.out.println("Delete Error - product not found for id: "+id);
//			}
//			return Product.getId();
//		}
//		


	}
