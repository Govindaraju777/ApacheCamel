package com.example.stanalone.camel.callmethod;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@Slf4j
public class SendMessageJavaBean {
	public static void main(String[] args) throws Exception {
		CamelContext ctx = new DefaultCamelContext();
		ctx.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:start")
				.to("class:com.example.stanalone.camel.callmethod.ReceiverMethodExample?method=receive");
			}
		});

		ctx.start();
		ProducerTemplate producer = ctx.createProducerTemplate();
		producer.sendBody("direct:start", "Hello");

	}
}
