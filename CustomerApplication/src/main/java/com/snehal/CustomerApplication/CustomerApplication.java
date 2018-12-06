package com.snehal.CustomerApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author $nehal
 *
 */


@SpringBootApplication
@ComponentScan("com.snehal.controller")
@ComponentScan("com.snehal.entity.model")
@ComponentScan("com.snehal.customerservice")
@ComponentScan("com.snehal.dao")
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}
}
