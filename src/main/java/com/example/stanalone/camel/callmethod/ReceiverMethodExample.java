/**
 * 
 */
package com.example.stanalone.camel.callmethod;

import lombok.extern.slf4j.Slf4j;

/**
 * @author govindarajuv
 *
 */
@Slf4j
public class ReceiverMethodExample {

	public void receive(String message) {
		log.info("------------------------ \n\n\n Received message : " + message + "\n\n---------------");
	}
	
}
