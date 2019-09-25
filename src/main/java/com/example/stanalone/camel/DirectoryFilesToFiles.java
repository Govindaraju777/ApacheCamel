/**
 * 
 */
package com.example.stanalone.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


/**
 * @author govindarajuv
 *
 */
public class DirectoryFilesToFiles {

	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:input_box?noop=true").to("file:output_box");
			}
		});
		
		/*
		 * int i=1; while(i<=1000) { context.start(); i++; }
		 */
		
		while(true) {
			context.start();
		}
	}
}