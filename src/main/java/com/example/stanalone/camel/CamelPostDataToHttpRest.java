/**
 * 
 */
package com.example.stanalone.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@Slf4j
public class CamelPostDataToHttpRest {
	public static void main(String[] args) {
		CamelContext ctx = new DefaultCamelContext();

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		try {
			ctx.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from("direct:start")
					//.to("http4://localhost:8081/employees/test")
					.to("http4://localhost:8081/employees/jsonDataProcessor");
				}
			});
			ctx.start();
			ProducerTemplate producer = ctx.createProducerTemplate();
			
			//producer.sendBody("direct:start", new String("test object"));
			Exchange exchange = producer.send("http4://localhost:8081/employees/jsonDataProcessor",new Processor() {
				@Override
				public void process(Exchange exchange) throws Exception {
					exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
				}
			});
			
			Message out = exchange.getOut();
			Object outobj = out.getBody();
			int responseCode = out.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
			log.info("-------------------------"+responseCode);
			
			
			Thread.sleep(5 * 60 * 1000);
			ctx.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
