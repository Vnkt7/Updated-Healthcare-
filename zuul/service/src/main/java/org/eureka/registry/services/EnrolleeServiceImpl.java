package org.eureka.registry.services;

import java.util.Date;
import java.util.List;

import org.eureka.registry.dao.EnrolleeDao;
import org.eureka.registry.entities.Enrollee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrolleeServiceImpl implements EnrolleeService {

	@Autowired
	private EnrolleeDao enrolleeDao;

	@Override
	public List<Enrollee> getEnrollees() {
		return enrolleeDao.getEnrollees();
	}

	@Override
	public Enrollee getEnrolleeByIdV1(long enrolleeId) {
		return enrolleeDao.getEnrolleeById(enrolleeId);
	}

	@Override
	public Long saveEnrolleeV1(String name, boolean activationStatus, Date birthDate, String phoneNumber) {
		return enrolleeDao.saveEnrollee(name, activationStatus, birthDate, phoneNumber);
	}
	
	@Override
	public Enrollee getEnrolleeByIdV2(long enrolleeId) {
		//TODO Implement version 2 specific logic
		return enrolleeDao.getEnrolleeById(enrolleeId);
	}

	@Override
	public Long saveEnrolleeV2(String name, boolean activationStatus, Date birthDate, String phoneNumber) {
		//TODO Implement version 2 specific logic
		return enrolleeDao.saveEnrollee(name, activationStatus, birthDate, phoneNumber);
	}

	@Override
	public int updateEnrollee(long enrolleeId, String name, Boolean activationStatus, Date birthDate,
			String phoneNumber) {
		return enrolleeDao.updateEnrollee(enrolleeId, name, activationStatus, birthDate, phoneNumber);
	}

	@Override
	public void deleteEnrollee(long enrolleeId) {
		enrolleeDao.deleteEnrollee(enrolleeId);
	}

	@Override
	public Enrollee addDependentToEnrollee(long enrolleeId, String name, Date birthDate) {
		return enrolleeDao.addDependentToEnrollee(enrolleeId, name, birthDate);
	}

	@Override
	public Enrollee removeDependentFromEnrollee(long enrolleeId, long dependentId) {
		return enrolleeDao.removeDependentFromEnrollee(enrolleeId, dependentId);
	}

	@Override
	public Enrollee updateDependentFromEnrollee(long enrolleeId, long dependentId, String name, Date birthDate) {
		return enrolleeDao.updateDependentFromEnrollee(enrolleeId, dependentId, name, birthDate);
	}
}
