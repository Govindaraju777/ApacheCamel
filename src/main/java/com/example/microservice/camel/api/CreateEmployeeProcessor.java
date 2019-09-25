/**
 * 
 */
package com.example.microservice.camel.api;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.example.microservice.model.Employee;

import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@Slf4j
public class CreateEmployeeProcessor implements Processor {
	Employee employee;
	
//	public CreateEmployeeProcessor(){
//		
//	}
	public CreateEmployeeProcessor(Employee employee){
		this.employee=employee;
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		//log.info(exchange.getIn().getBody(String.class));
		employee = (this.employee!=null? employee : Employee.builder().build().setDesignation("DefaultUser-by CreateEmployeeProcessor").setEmpId("100").setSalary(0) );
		exchange.getIn().setBody(employee);
		//exchange.getOut().setBody(employee);
	}

}
