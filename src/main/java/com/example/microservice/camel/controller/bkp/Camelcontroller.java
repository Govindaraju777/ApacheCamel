/**
 * 
 */
package com.example.microservice.camel.controller.bkp;

import java.util.List;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.model.RouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservice.model.Employee;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@Api
@RestController
@RequestMapping("/employees")
@Slf4j
public class Camelcontroller {
	@Autowired
	ProducerTemplate producerTemplate;

	@Autowired
	ConsumerTemplate consumerTemplate;

	/*
	 * @RequestMapping(value = "/") public void startCamel() {
	 * producerTemplate.sendBody("direct:firstRoute",
	 * "Calling via Spring Boot Rest Controller"); }
	 */

	@GetMapping(value = "/test2", produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee testData2() throws Exception {
		log.info("reqeust from /employees/test2");
		// throw new Exception();
		return Employee.builder().build().setEmpId("1").setName("test");
	}
	
	@PostMapping(value = "/jsonDataProcessor", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Employee acceptData(@RequestBody Employee employee) throws Exception {
		log.info("reqeust from /employees/jsonDataProcessor");
		return Employee.builder().build().setEmpId("1").setName("testjsonDataProcessor");
	}

	
	@PostMapping(value="/multicast",consumes = MediaType.APPLICATION_JSON_VALUE)
	public Employee postDataToMultiCastMsgQueue(@RequestBody Employee employee) {
		//producerTemplate.sendBody("direct:firstRoute", "Calling via Spring Boot Rest Controller");
		//producerTemplate.sendBody("direct:myMessageProcessorService", employee);
		//Exchange str = consumerTemplate.receive("seda:direct");
		
		//Exchange exchange = producerTemplate.send("direct:emailScheduler", new MyProcessor());
		
		
		//1. Pre programmed scheduler
		//producerTemplate.sendBody("direct:emailScheduler", "Calling via Spring Boot Rest Controller");
		
		
		//2
		Exchange exchange = producerTemplate.send("direct:emailSchedulerDynamicProcessor", new EmployeeProcessor(employee));
		Message outMessage = exchange.getOut();
		String outbody = outMessage.getBody(String.class);
		log.info("outbody : " + outbody);
		
		List<RouteDefinition> routes = exchange.getContext().getRouteDefinitions();
		for(RouteDefinition route : routes) {
			System.out.println(route);
		}
		
		log.info("---------");
		//to http url
//		Exchange exchange = producerTemplate.send("http4://localhost:8081/employees/test", new Processor() {
//			public void process(Exchange exchange) throws Exception {
//				exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
//			}
//		});
//		Message messageExchangeStatus = exchange.getOut();
//		Object msgBody = messageExchangeStatus.getBody();
//		log.info("exchangeStatus " + messageExchangeStatus);
		
		
		return null;
	}
}


