/**
 * 
 */
package com.example.microservice.camel.controller;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

/**
 * @author govindarajuv
 *
 */
public class TestCamelPostDataTo_Router extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// route for REST POST Call
		JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);

		from("file:C:/inboxPOST?noop=true").process(new CreateEmployeeProcessor()).marshal(jsonDataFormat)
				.setHeader(Exchange.HTTP_METHOD, simple("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json")).to("http://localhost:8080/employee")
				.process(new MyProcessor());

	}

}
