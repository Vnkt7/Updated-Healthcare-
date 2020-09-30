package org.eureka.registry.repos;

import org.eureka.registry.entities.Enrollee;
import org.springframework.data.repository.CrudRepository;

public interface EnrolleeRepository extends CrudRepository<Enrollee, Long> {}
