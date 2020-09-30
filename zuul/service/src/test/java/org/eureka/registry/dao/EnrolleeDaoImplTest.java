package org.eureka.registry.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eureka.registry.entities.Enrollee;
import org.eureka.registry.repos.EnrolleeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class EnrolleeDaoImplTest {

	@InjectMocks
	private EnrolleeDaoImpl enrolleeDbImpl;

	@Mock
	private EnrolleeRepository enrolleeRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetEnrollees() {
		Mockito.when(enrolleeRepository.findAll())
				.thenReturn(Stream.of(getSampleEnrollee(123)).collect(Collectors.toList()));
		List<Enrollee> enrollees = enrolleeDbImpl.getEnrollees();
		Assert.assertNotNull(enrollees);
		Assert.assertFalse(enrollees.isEmpty());
		Assert.assertTrue(123 == enrollees.get(0).getId());
	}
	
	@Test
	public void testGetEnrollee() {
		Mockito.when(enrolleeRepository.findById(123L)).thenReturn(Optional.of(getSampleEnrollee(123)));
		Enrollee enrollee = enrolleeDbImpl.getEnrolleeById(123);
		Assert.assertNotNull(enrollee);
		Assert.assertTrue(123 == enrollee.getId());
	}
	
	@Test
	public void saveEnrollee() {
		Enrollee enrollee = getSampleEnrollee(123L);
		Mockito.when(enrolleeRepository.save(Mockito.any(Enrollee.class))).thenReturn(enrollee);
		long enrolleeId = enrolleeDbImpl.saveEnrollee(enrollee.getName(), enrollee.isActivationStatus(), 
				enrollee.getBirthDate(), enrollee.getPhoneNumber());
		Assert.assertEquals(123L, enrolleeId);
	}

	private Enrollee getSampleEnrollee(long id) {
		Enrollee enrollee = new Enrollee();
		enrollee.setId(id);
		enrollee.setActivationStatus(false);
		enrollee.setBirthDate(new Date());
		enrollee.setName("test name");
		enrollee.setPhoneNumber("+1 (987) 831-2344");
		return enrollee;
	}
}
