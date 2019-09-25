/**
 * 
 */
package com.example.stanalone.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@Slf4j
public class ConsumeActiveMqData {
	public static void main(String[] args) {
		CamelContext ctx = new DefaultCamelContext();

		// configure jms component
		//ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:8161");
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		try {
			ctx.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("jms:queue:my_queue").to("seda:end");
				}
			});
			ctx.start();
			
			ConsumerTemplate consumer = ctx.createConsumerTemplate();
			String msg = consumer.receiveBody("seda:end",String.class);
			log.info("received message from activeMq : " + msg);
			
			
			Thread.sleep(5 * 60 * 1000);
			ctx.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
