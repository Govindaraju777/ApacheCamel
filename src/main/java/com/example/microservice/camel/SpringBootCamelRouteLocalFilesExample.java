/**
 * 
 */
package com.example.microservice.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@Component
@Slf4j
public class SpringBootCamelRouteLocalFilesExample extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		intercept().process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				log.info("Got new message --running SpringBootCamelRouteExample");
			}
		});
		from("file:input_box?noop=true").to("file:output_box");

	}

}
