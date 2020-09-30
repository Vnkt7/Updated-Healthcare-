package org.eureka.registry.dao;

import java.util.Date;
import java.util.List;

import org.eureka.registry.entities.Enrollee;

public interface EnrolleeDao {

	public List<Enrollee> getEnrollees();

	public Enrollee getEnrolleeById(long enrolleeId);

	public Long saveEnrollee(String name, boolean activationStatus, Date birthDate, String phoneNumber);

	public int updateEnrollee(long enrolleeId, String name, Boolean activationStatus, Date birthDate,
			String phoneNumber);

	public void deleteEnrollee(long enrolleeId);

	public Enrollee addDependentToEnrollee(long enrolleeId, String name, Date birthDate);

	public Enrollee removeDependentFromEnrollee(long enrolleeId, long dependentId);

	public Enrollee updateDependentFromEnrollee(long enrolleeId, long dependentId, String name, Date birthDate);
}
