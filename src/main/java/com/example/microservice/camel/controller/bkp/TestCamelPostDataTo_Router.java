/**
 * 
 */
package com.example.microservice.camel.controller.bkp;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import com.example.microservice.model.Employee;

/**
 * @author govindarajuv
 *
 */
@Component
public class TestCamelPostDataTo_Router extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// route for REST POST Call
		JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);

		// using new processor
		from("direct:emailSchedulerNewProcessor").process(new EmployeeProcessor()).marshal(jsonDataFormat)
				.setHeader(Exchange.HTTP_METHOD, simple("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.to("http4://localhost:8081/postEmailMessageQueue").process(new EmployeeProcessor());

		// 2. using DynamicProcessor - Single route
		/*
		from("direct:emailSchedulerDynamicProcessor").marshal(jsonDataFormat)
				.setHeader(Exchange.HTTP_METHOD, simple("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.to("http4://localhost:8081/postEmailMessageQueue");
		*/
		
		// 3. using DynamicProcessor - mutliple routes
		from("direct:emailSchedulerDynamicProcessor").marshal(jsonDataFormat)
				.setHeader(Exchange.HTTP_METHOD, simple("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.to("http4://localhost:8081/postEmailMessageQueue","http4://localhost:8081/postEmailMessageQueue2");//.to("http4://localhost:8081/postEmailMessageQueue2");
	
		// 4. Exception Handling
				from("direct:emailSchedulerDynamicProcessorException").marshal(jsonDataFormat)
						.setHeader(Exchange.HTTP_METHOD, simple("POST"))
						.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
						.onException(Exception.class)
						.to("http4://localhost:8081/postEmailMessageQueue");//.to("http4://localhost:8081/postEmailMessageQueue2");
	}

}
