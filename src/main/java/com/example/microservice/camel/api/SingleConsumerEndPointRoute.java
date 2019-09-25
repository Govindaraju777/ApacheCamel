/**
 * 
 */
package com.example.microservice.camel.api;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import com.example.microservice.model.Employee;

/**
 * @author govindarajuv
 *
 */
@Component
public class SingleConsumerEndPointRoute extends RouteBuilder {
	int count;
	@Override
	public void configure() throws Exception {
		// route for REST POST Call
		JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);
		
		intercept().process(new Processor() {
			public void process(Exchange exchange) {
				count++;
				System.out.println("interceptor called " + count + " times " + exchange.getIn().getBody());
			}
		});
		
		
		// using new processor
//		from("direct:emailSchedulerNewProcessor").process(new CreateEmployeeProcessor()).marshal(jsonDataFormat)
//				.setHeader(Exchange.HTTP_METHOD, simple("POST"))
//				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
//				.to("http4://localhost:8081/postEmailMessageQueue").process(new CreateEmployeeProcessor());

		// publish
		from("direct:postToRestConsumerSingleServer").marshal(jsonDataFormat)
				.setHeader(Exchange.HTTP_METHOD, simple("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json")) //.onException(Exception.class)
				.to("http4://localhost:8081/consumer/rest");
			
		/*
		 .process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						String outputFromClient1 = exchange.getIn().getBody(String.class);
						outputFromClient1 = outputFromClient1.toUpperCase();
						exchange.getIn().setMessageId(outputFromClient1);
						log.info("converted output to upper");
					}
				});
		*/
		//.log("OUT BODY -- : ${body} ");
		
		
		// Exception Handling
		from("direct:postToRestConsumerExceptionHandling").marshal(jsonDataFormat)
				.setHeader(Exchange.HTTP_METHOD, simple("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))//.onException(Exception.class)
				.to("http4://localhost:8081/consumer/rest_TestException");
	}

}