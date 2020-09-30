package org.eureka.registry.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "DEPENDENT")
@Data
public class Dependent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2159754256003815056L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "BIRTH_DATE")
	@Temporal(TemporalType.DATE)
	private Date birthDate;
}
