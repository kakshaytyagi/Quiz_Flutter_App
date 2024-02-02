package org.app.edufun.Configuration.app_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
public class DependenciesConfig {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
//	@Bean
//	public PreFilter preFilter() {
//		return new PreFilter();
//	}
//	@Bean
//	public PostFilter postFilter() {
//		return new PostFilter();
//	}
//	@Bean
//	public ErrorFilter errorFilter() {
//		return new ErrorFilter();
//	}
////	@Bean
//	public RouteFilter routeFilter() {
//		return new RouteFilter(passwordEncoder);
//	}

}
