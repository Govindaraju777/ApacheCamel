/**
 * 
 */
package com.example.stanalone.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author govindarajuv
 *
 */
public class ActiveMqFile {
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
					from("file:input_box?noop=true").to("jms:queue:my_queue");
				}
			});
			ctx.start();
			Thread.sleep(5 * 60 * 1000);
			ctx.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
