package org.eureka.registry.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.eureka.registry.entities.Dependent;
import org.eureka.registry.entities.Enrollee;
import org.eureka.registry.repos.DependentRepository;
import org.eureka.registry.repos.EnrolleeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EnrolleeDaoImpl implements EnrolleeDao {

	@Autowired
	private EnrolleeRepository enrolleeRepository;

	@Autowired
	private DependentRepository dependentRepository;

	@Override
	public List<Enrollee> getEnrollees() {
		List<Enrollee> enrolleeList = new ArrayList<>();
		enrolleeRepository.findAll().forEach(enrolleeList::add);
		log.info("Enrollees size {}", enrolleeList.size());
		return enrolleeList;
	}

	@Override
	public Enrollee getEnrolleeById(long enrolleeId) {
		Optional<Enrollee> enrollee = enrolleeRepository.findById(enrolleeId);
		log.info("Enrollee with id {} exists {}", enrolleeId, enrollee.isPresent());
		if (enrollee.isPresent())
			return enrollee.get();
		else
			return null;
	}

	@Override
	public Long saveEnrollee(String name, boolean activationStatus, Date birthDate, String phoneNumber) {
		Enrollee enrollee = new Enrollee();
		enrollee.setName(name);
		enrollee.setPhoneNumber(phoneNumber);
		enrollee.setActivationStatus(activationStatus);
		enrollee.setBirthDate(birthDate);
		long savedId = enrolleeRepository.save(enrollee).getId();
		log.info("Enrollee saved with id {}", savedId);
		return savedId;
	}

	@Override
	public int updateEnrollee(long enrolleeId, String name, Boolean activationStatus, Date birthDate,
			String phoneNumber) {
		Optional<Enrollee> enrolleeOpt = enrolleeRepository.findById(enrolleeId);
		log.info("Enrollee update for id {} exists {}", enrolleeId, enrolleeOpt.isPresent());
		if (enrolleeOpt.isPresent()) {
			Enrollee enrollee = enrolleeOpt.get();
			enrollee.setName(null != name ? name : enrollee.getName());
			enrollee.setActivationStatus(null != activationStatus ? activationStatus : enrollee.isActivationStatus());
			enrollee.setBirthDate(null != birthDate ? birthDate : enrollee.getBirthDate());
			enrollee.setPhoneNumber(null != phoneNumber ? phoneNumber : enrollee.getPhoneNumber());
			enrolleeRepository.save(enrollee);
			return 1;
		}
		return 0;
	}

	@Override
	public void deleteEnrollee(long enrolleeId) {
		log.info("Deleting enrollee with id {}", enrolleeId);
		enrolleeRepository.deleteById(enrolleeId);
	}

	@Override
	public Enrollee addDependentToEnrollee(long enrolleeId, String name, Date birthDate) {
		Optional<Enrollee> enrolleeOpt = enrolleeRepository.findById(enrolleeId);
		log.info("Adding dependent to enrollee with id {} exists {} ", enrolleeId, enrolleeOpt.isPresent());
		if (enrolleeOpt.isPresent()) {
			Enrollee enrollee = enrolleeOpt.get();
			Dependent dependent = new Dependent();
			dependent.setName(name);
			dependent.setBirthDate(birthDate);
			enrollee.getDependents().add(dependentRepository.save(dependent));
			return enrolleeRepository.save(enrollee);
		}
		return null;
	}

	@Override
	public Enrollee removeDependentFromEnrollee(long enrolleeId, long dependentId) {
		Optional<Enrollee> enrolleeOpt = enrolleeRepository.findById(enrolleeId);
		log.info("Removing dependent to enrollee with id {} exists {} ", enrolleeId, enrolleeOpt.isPresent());
		if (enrolleeOpt.isPresent()) {
			Optional<Dependent> dependentOpt = dependentRepository.findById(dependentId);
			log.info("Dependent with id {} exists {}", dependentId, dependentOpt.isPresent());
			if (dependentOpt.isPresent()) {
				Enrollee enrollee = enrolleeOpt.get();
				enrollee.getDependents().remove(dependentOpt.get());
				return enrolleeRepository.save(enrollee);
			}
		}
		return null;
	}

	@Override
	public Enrollee updateDependentFromEnrollee(long enrolleeId, long dependentId, String name, Date birthDate) {
		Optional<Enrollee> enrolleeOpt = enrolleeRepository.findById(enrolleeId);
		log.info("Updating dependent in enrollee with id {} exists {} ", enrolleeId, enrolleeOpt.isPresent());
		if (enrolleeOpt.isPresent()) {
			Enrollee enrollee = enrolleeOpt.get();
			enrollee.getDependents().stream().forEach(dependent -> {
				if (dependent.getId() == dependentId) {
					dependent.setName(null != name ? name : dependent.getName());
					dependent.setBirthDate(null != birthDate ? birthDate : dependent.getBirthDate());
				}
			});
			return enrolleeRepository.save(enrollee);
		}
		return null;
	}

}
