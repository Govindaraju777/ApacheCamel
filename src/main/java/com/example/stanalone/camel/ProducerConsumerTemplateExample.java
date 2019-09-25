/**
 * 
 */
package com.example.stanalone.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@Slf4j
public class ProducerConsumerTemplateExample {

	public static void main(String[] args) throws Exception {
			CamelContext ctx = new DefaultCamelContext();
			ctx.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from("direct:start")
					.to("seda:end");
				}
			});
			
			ctx.start();
			ProducerTemplate producer = ctx.createProducerTemplate();
			producer.sendBody("direct:start","test message......");
			
			
			ConsumerTemplate consumer = ctx.createConsumerTemplate();
			String message = consumer.receiveBody("seda:end",String.class);
			
			
			log.info("\n\n\n received message : " + message);
			
			
	}
}
