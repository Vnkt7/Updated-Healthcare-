package org.eureka.registry.controllers;

import java.util.Date;
import java.util.List;

import org.eureka.registry.entities.Enrollee;
import org.eureka.registry.services.EnrolleeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enrollee")
public class EnrolleeController {

	@Autowired
	private EnrolleeService enrolleeService;
	
	@GetMapping("/all")
	public ResponseEntity<List<Enrollee>> getEnrollees() {
		List<Enrollee> enrollees = enrolleeService.getEnrollees();
		return new ResponseEntity<List<Enrollee>>(enrollees, HttpStatus.OK);
	}
	
	@GetMapping("/v1")
	public ResponseEntity<Enrollee> getEnrolleeByIdV1(@RequestParam("enrolleeId") long enrolleeId) {
		Enrollee enrollee = enrolleeService.getEnrolleeByIdV1(enrolleeId);
		return new ResponseEntity<Enrollee>(enrollee, HttpStatus.OK);
	}
	
	@GetMapping("/v2")
	public ResponseEntity<Enrollee> getEnrolleeByIdV2(@RequestParam("enrolleeId") long enrolleeId) {
		Enrollee enrollee = enrolleeService.getEnrolleeByIdV2(enrolleeId);
		return new ResponseEntity<Enrollee>(enrollee, HttpStatus.OK);
	}
	
	@PostMapping("/v1")
	public ResponseEntity<Long> saveEnrollee(@RequestParam("name") String name, 
			@RequestParam("activationStatus") boolean activationStatus, 
			@RequestParam("birthDate") Date birthDate,
			@RequestParam(name = "phoneNumber", required = false) String phoneNumber) {
		Long enrolleeId = enrolleeService.saveEnrolleeV1(name, activationStatus, birthDate, phoneNumber);
		return new ResponseEntity<Long>(enrolleeId, HttpStatus.CREATED);
	}
	
	@PostMapping("/v2")
	public ResponseEntity<Long> saveEnrolleeV2(@RequestParam("name") String name, 
			@RequestParam("activationStatus") boolean activationStatus, 
			@RequestParam("birthDate") Date birthDate,
			@RequestParam(name = "phoneNumber", required = false) String phoneNumber) {
		Long enrolleeId = enrolleeService.saveEnrolleeV2(name, activationStatus, birthDate, phoneNumber);
		return new ResponseEntity<Long>(enrolleeId, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Integer> updateEnrollee(@RequestParam("enrolleeId") long enrolleeId,
			@RequestParam(name = "name", required = false) String name, 
			@RequestParam(name = "activationStatus", required = false) boolean activationStatus, 
			@RequestParam(name = "birthDate", required = false) Date birthDate,
			@RequestParam(name = "phoneNumber", required = false) String phoneNumber) {
		int updatedEntitiesCount = enrolleeService.updateEnrollee(enrolleeId, name, activationStatus, birthDate, phoneNumber);
		return new ResponseEntity<Integer>(updatedEntitiesCount, HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteEnrollee(@RequestParam("enrolleeId") long enrolleeId) {
		enrolleeService.deleteEnrollee(enrolleeId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PatchMapping("/add")
	public ResponseEntity<Enrollee> addDependentToEnrollee(@RequestParam("enrolleeId") long enrolleeId,
			@RequestParam(name = "name") String name, 
			@RequestParam(name = "birthDate") Date birthDate) {
		Enrollee enrollee = enrolleeService.addDependentToEnrollee(enrolleeId, name, birthDate);
		return new ResponseEntity<Enrollee>(enrollee, HttpStatus.OK);
	}
	
	@PatchMapping("/remove")
	public ResponseEntity<Enrollee> removeDependentFromEnrollee(@RequestParam("enrolleeId") long enrolleeId,
			@RequestParam(name = "dependentId") long dependentId) {
		Enrollee enrollee = enrolleeService.removeDependentFromEnrollee(enrolleeId, dependentId);
		return new ResponseEntity<Enrollee>(enrollee, HttpStatus.OK);
	}
	
	@PatchMapping("/update")
	public ResponseEntity<Enrollee> updateDependentFromEnrollee(@RequestParam("enrolleeId") long enrolleeId,
			@RequestParam(name = "dependentId") long dependentId,
			@RequestParam(name = "name", required = false) String name, 
			@RequestParam(name = "birthDate", required = false) Date birthDate) {
		Enrollee enrollee = enrolleeService.updateDependentFromEnrollee(enrolleeId, dependentId, name, birthDate);
		return new ResponseEntity<Enrollee>(enrollee, HttpStatus.OK);
	}
	
}
