package com.example.microservice.camel.controller.bkp;
///**
// * 
// */
//package com.example.microservice.camel.controller;
//
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.microservice.model.Employee;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author govindarajuv
// *
// */
//@RequestMapping
//@RestController
//@Slf4j
//public class TestXmlConsumerApi {
//	@PostMapping(value = "/testXml", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
//	public Employee acceptData(@RequestBody Employee employee) throws Exception {
//		log.info("reqeust from testXml");
//		return Employee.builder().build().setEmpId("1").setName("testjsonDataProcessor");
//	}
//
//}
