/**
 * 
 */
package com.example.microservice.camel.api;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.example.microservice.model.Employee;

import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@Component
@Slf4j
public class MultipleConsumerEndPointRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// route for REST POST Call
		JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);
		
		// publish
		from("direct:postToRestConsumerMultipleRoutes")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				log.info("-----------");
			}
		})
		.marshal(jsonDataFormat)
				.setHeader(Exchange.HTTP_METHOD, simple("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		//.to(ExchangePattern.OutIn,"http4://localhost:8081/consumer/rest")
		.to("http4://localhost:8081/consumer/rest","http4://localhost:8081/consumer/restXML");
		//.multicast()
		//.to("http4://localhost:8081/consumer/restXML");
//		.process(new Processor() {
//			@Override
//			public void process(Exchange exchange) throws Exception {
//				exchange.getIn().setBody(Employee.builder().build().setDesignation("test"));
//				log.info("-----------");
//			}
//		})
				//.setHeader(Exchange.HTTP_METHOD, simple("POST"))
				//.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
				//.to("http4://localhost:8081/consumer/restXML");
	}

}
