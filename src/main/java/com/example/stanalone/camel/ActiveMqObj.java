package com.example.stanalone.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ActiveMqObj {
	public static void main(String[] args) {
		CamelContext ctx = new DefaultCamelContext();

		// configure jms component
		// ConnectionFactory connectionFactory = new
		// ActiveMQConnectionFactory("tcp://localhost:8161");
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		try {
			ctx.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("direct:start").to("jms:queue:my_queue");
				}
			});
			ctx.start();
			ProducerTemplate producer = ctx.createProducerTemplate();
			producer.sendBody("direct:start", new String("test object"));
			
			Thread.sleep(5 * 60 * 1000);
			ctx.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	

	}
}
