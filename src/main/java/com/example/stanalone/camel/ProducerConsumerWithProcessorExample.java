/**
 * 
 */
package com.example.stanalone.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@Slf4j
public class ProducerConsumerWithProcessorExample {

	public static void main(String[] args) throws Exception {
		CamelContext ctx = new DefaultCamelContext();
		ctx.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:start").process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						log.info("\n\n processor\n\n");

						// interrupt and modify the message.
						String msg = exchange.getIn().getBody(String.class);
						msg = msg + " modified by processor";
						exchange.getOut().setBody(msg);
					}
				}).to("seda:end");
			}
		});

		ctx.start();
		ProducerTemplate producer = ctx.createProducerTemplate();
		producer.sendBody("direct:start", "test message......");

		ConsumerTemplate consumer = ctx.createConsumerTemplate();
		String message = consumer.receiveBody("seda:end", String.class);

		log.info("\n\n\n Received message : " + message);
	}

}
