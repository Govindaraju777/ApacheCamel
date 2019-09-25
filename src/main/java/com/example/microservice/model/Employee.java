package com.example.microservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class Employee {
	private String empId;
	private String name;
	private String designation;
	private double salary;
}