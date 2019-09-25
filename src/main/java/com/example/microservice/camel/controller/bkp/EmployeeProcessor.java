package com.example.microservice.camel.controller.bkp;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.example.microservice.model.Employee;

public class EmployeeProcessor implements Processor {
	
	
	/**
	 * 
	 */
	public EmployeeProcessor() {
		// TODO Auto-generated constructor stub
	}
	
	Employee employee;
	
	public EmployeeProcessor(Employee employee) {
		this.employee=employee;
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		// exchange.getIn().setHeader("accept", "application/json");
		// exchange.getIn().setBody("{empId:1, name:testjsonDataProcessor}");
		exchange.getIn().setBody(employee);

	}
}