package com.snehal.CustomerApplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author $nehal
 *
 */

public class ServletInitializer extends SpringBootServletInitializer {
	  @Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	  return application.sources(CustomerApplication.class);
	  }
	}