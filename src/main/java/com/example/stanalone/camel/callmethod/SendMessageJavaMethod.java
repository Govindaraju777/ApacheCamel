package com.example.stanalone.camel.callmethod;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@Slf4j
public class SendMessageJavaMethod {
	public static void main(String[] args) throws Exception {
		ReceiverMethodExample receiverBean = new ReceiverMethodExample();
		SimpleRegistry registery = new SimpleRegistry();
		registery.put("myRegisteredServiceBean", receiverBean);  //jdbc insert can be achieved by registering jdbc object
		CamelContext ctx = new DefaultCamelContext(registery);
		
		ctx.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:start").to("bean:myRegisteredServiceBean?method=receive");
			}
		});

		ctx.start();
		ProducerTemplate producer = ctx.createProducerTemplate();
		producer.sendBody("direct:start", "Hello");

	}
}
