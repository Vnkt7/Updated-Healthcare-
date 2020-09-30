package org.eureka.registry.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "ENROLLEE")
@Data
public class Enrollee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7429188008714421406L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ACTIVATION_STATUS")
	private boolean activationStatus;
	
	@Column(name = "BIRTH_DATE")
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Dependent> dependents = new ArrayList<>();
	
}
