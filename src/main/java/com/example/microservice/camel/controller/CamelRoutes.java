package com.example.microservice.camel.controller;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRoutes extends RouteBuilder {
	
	
	@Override
	public void configure() throws Exception {
		intercept().process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				//exchange.getIn().setHeader("accept", "application/json");
				exchange.getIn().setHeader(Exchange.ACCEPT_CONTENT_TYPE, "application/json");
				exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
				exchange.getIn().setHeader(Exchange.HTTP_METHOD, "POST");

				log.info("\n\n ----------- \n Got new message --running SpringBootResetController \n\n ");
			}
		});
		
		/*
		 * from("direct:myMessageProcessorService").process(new Processor() {
		 * 
		 * @Override public void process(Exchange exchange) throws Exception { // TODO
		 * Auto-generated method stub } });
		 */
		//from("direct:myMessageProcessorService").log("Camel body: ${body}");
		
		//from("direct:myMessageProcessorService").to("direct:http://localhost:8081/employees/test");
		
		
		from("direct:myMessageProcessorService")
		.to("http4://localhost:8081/employees/jsonDataProcessor")
		//.to("http4://localhost:8081/employees/test2")
		.log("Out body: ${out.body}");
		
		//from("direct:myMessageProcessorService").log("Camel body: ${body}").to("seda:direct");
		//from("direct:myMessageProcessorService").process().exchange("bean:test");
		
		
		
	}
}