/**
 * 
 */
package com.example.microservice.camel.api;

import java.util.List;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.camel.model.RouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping("/camel")
@Slf4j
public class CamelDataPublisherGatewayAPI {
	@Autowired
	ProducerTemplate producerTemplate;

	@Autowired
	ConsumerTemplate consumerTemplate;
	
	
	// single route
	@PostMapping(value="/postToRestConsumer",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},produces = "text/plain")
	public String postDataToSingleMsgQueue(@RequestBody Employee employee) {
		//1. Pre programmed scheduler
		//producerTemplate.sendBody("direct:emailScheduler", "Calling via Spring Boot Rest Controller");
		
		Exchange exchange = producerTemplate.send("direct:postToRestConsumerSingleServer", new CreateEmployeeProcessor(employee));
		Message outMessage = exchange.getOut();
		String outbody = outMessage.getBody(String.class);
		log.info("outbody : " + outbody);
		
		List<RouteDefinition> routes = exchange.getContext().getRouteDefinitions();
		for(RouteDefinition route : routes) {
			System.out.println(route);
		}
		return "responseFromRestAPI - "+outbody;
	}
	
	
	@PostMapping(value="/postToRest_ConsumerException",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},produces = "text/plain")
	public String postDataToSingleMsgQueue_ExceptionTest(@RequestBody Employee employee) {
		Exchange exchange = producerTemplate.send("direct:postToRestConsumerExceptionHandling", new CreateEmployeeProcessor(employee));
		final HttpOperationFailedException e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, HttpOperationFailedException.class);
        final String responseBody = e.getResponseBody();
		log.info("exception message : " + responseBody);
		return "responseFromRestAPI Exception  - "+ responseBody;
	}
	
	
	//multiple routes
	@PostMapping(value="/postToRestConsumers",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},produces = "text/plain")
	public String postDataToMutlipleMsgQueue(@RequestBody Employee employee) {
		Exchange exchange = producerTemplate.send("direct:postToRestConsumerMultipleRoutes", new CreateEmployeeProcessor(employee));
		Message outMessage = exchange.getOut();
		String outbody = outMessage.getBody(String.class);
		log.info("outbody : " + outbody);
		
		List<RouteDefinition> routes = exchange.getContext().getRouteDefinitions();
		for(RouteDefinition route : routes) {
			System.out.println(route);
		}
		return "responseFromRestAPI - "+outbody;
	}
	
	
}
