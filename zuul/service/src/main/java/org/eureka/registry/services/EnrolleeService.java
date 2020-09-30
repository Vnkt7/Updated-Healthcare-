package org.eureka.registry.services;

import java.util.Date;
import java.util.List;

import org.eureka.registry.entities.Enrollee;

public interface EnrolleeService {

	public List<Enrollee> getEnrollees();

	public Enrollee getEnrolleeByIdV1(long enrolleeId);

	public Long saveEnrolleeV1(String name, boolean activationStatus, Date birthDate, String phoneNumber);
	
	public Enrollee getEnrolleeByIdV2(long enrolleeId);

	public Long saveEnrolleeV2(String name, boolean activationStatus, Date birthDate, String phoneNumber);

	public int updateEnrollee(long enrolleeId, String name, Boolean activationStatus, Date birthDate,
			String phoneNumber);

	public void deleteEnrollee(long enrolleeId);

	public Enrollee addDependentToEnrollee(long enrolleeId, String name, Date birthDate);

	public Enrollee removeDependentFromEnrollee(long enrolleeId, long dependentId);

	public Enrollee updateDependentFromEnrollee(long enrolleeId, long dependentId, String name, Date birthDate);
}
