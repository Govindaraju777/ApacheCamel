/**
 * 
 */
package com.example.microservice.camel.publisherService.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservice.model.Employee;

import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@RestController
@Slf4j
@RequestMapping("/consumer")
public class TestDataConsumeData_Controller {
	
	@PostMapping(value = "/rest", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Employee postDataToActiveMQ(@RequestBody Employee employee) {
		log.info("reqeust from /consumer/rest");
		return Employee.builder().build().setEmpId("1").setName("Consumed data - by /consumer/rest");
	}
	@PostMapping(value = "/rest_TestException", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Employee postDataToActiveMQ_TestException(Employee employee) throws Exception {
		log.info("reqeust from /rest_TestException");
		//return Employee.builder().build().setEmpId("1").setName("rest_TestException");
		throw new Exception("EXCEPTION FROM Consumer REST API -/consumer/rest_TestException");
	}
	
	@PostMapping(value = "/restXML", consumes = MediaType.APPLICATION_XML_VALUE)
	public Employee postDataToActiveXml(@RequestBody Employee employee) {
		log.info("reqeust from /consumer/restXML");
		return Employee.builder().build().setEmpId("1").setName("Consumed data - by /consumer/restXML");
	}
	
	
	@PostMapping(value = "/postEmailMessageQueue", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Employee postDataToMessageQueue(Employee employee) {
		log.info("reqeust from /postEmailMessageQueue");
		return Employee.builder().build().setEmpId("1").setName("NewName_1");
	}
	
	@PostMapping(value = "/postEmailMessageQueue2", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Employee postDataToMessageQueue2(Employee employee) {
		log.info("reqeust from /postEmailMessageQueue2");
		return Employee.builder().build().setEmpId("1").setName("NewName_postEmailMessageQueue2");
	}

	
	
	
	@GetMapping(value = "/test")
	public Employee test(Employee employee) throws Exception {
		log.info("reqeust from /rest_TestException");
		return Employee.builder().build().setEmpId("1").setName("test");
	}
}