package org.app.edufun.Configuration.app_config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class App_Config {

	@Bean
	public CorsFilter corsFilter() {
	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	CorsConfiguration config = new CorsConfiguration();
	config.setAllowCredentials(false);
	config.setAllowedOriginPatterns(Collections.singletonList("*"));
	config.addAllowedOrigin("*");
	config.addAllowedHeader("*");
	//config.addAllowedHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, enctype");
	config.addAllowedMethod("OPTIONS");
	config.addAllowedMethod("GET");
	config.addAllowedMethod("POST");
	config.addAllowedMethod("PUT");
	config.addAllowedMethod("DELETE");
	
	source.registerCorsConfiguration("/**", config);
	return new CorsFilter(source);
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
	    return new CommonsMultipartResolver();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	 @Override
//	  public void addCorsMappings(CorsRegistry registry) {
//
//	    registry.addMapping("/**")
//	      .allowedOrigins("http://35.173.186.169:8000")
//	      .allowedMethods("GET", "POST","PUT","PATCH","DELETE")
//	      .allowedHeaders("header1", "header2", "header3")
//	      .exposedHeaders("header1", "header2")
//	      .allowCredentials(true).maxAge(3600);
//
//	    // Add more mappings...
//	  }
	
	
//	  @Bean public CorsFilter corsFilter() { UrlBasedCorsConfigurationSource source
//	  = new UrlBasedCorsConfigurationSource(); CorsConfiguration config = new
//	  CorsConfiguration(); config.setAllowCredentials(true);
//	  config.addAllowedOrigin(CorsConfiguration.ALL);
//	  config.addAllowedHeader(CorsConfiguration.ALL);
////	  config.addAllowedHeader("Access-Control-Allow-Headers","Authorization, Content-Type, enctype"); 
//	  config.addAllowedMethod("OPTIONS");
//	  config.addAllowedMethod("GET"); config.addAllowedMethod("POST");
//	  config.addAllowedMethod("PUT"); config.addAllowedMethod("DELETE");
//	  
//	  source.registerCorsConfiguration("/**", config); return new
//	  CorsFilter(source); }
	 
	/*
	 * @Bean public CorsFilter corsFilter() { UrlBasedCorsConfigurationSource source
	 * = new UrlBasedCorsConfigurationSource(); CorsConfiguration config = new
	 * CorsConfiguration(); config.setAllowCredentials(true);
	 * config.addAllowedOrigin("*"); config.addAllowedHeader("*"); //
	 * config.addAllowedHeader("Access-Control-Allow-Headers", "Authorization, //
	 * Content-Type, enctype"); config.addAllowedMethod("OPTIONS");
	 * config.addAllowedMethod("GET"); config.addAllowedMethod("POST");
	 * config.addAllowedMethod("PUT"); config.addAllowedMethod("DELETE");
	 * 
	 * source.registerCorsConfiguration("/**", config); return new
	 * CorsFilter(source); }
	 */
	/*
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/api/**") .allowedOrigins("*") .allowedMethods("*")
	 * .allowedHeaders("header1", "header2", "header3") .exposedHeaders("header1",
	 * "header2") .allowCredentials(true).maxAge(3600); }
	 */
	/*
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**") .allowedOrigins("*") .allowedMethods("*")
	 * .allowedHeaders("*") .allowCredentials(true) .maxAge(4800); }
	 */

//	@SuppressWarnings("deprecation")
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
//						.allowedHeaders("*");
//			}
//		};
//	}
	/*
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("*").allowedMethods("GET", "POST", "PUT", "DELETE","HEAD","PATCH").allowedOrigins("http://localhost:8098");
			}
		};
	}
  */

/*	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("*").allowedMethods("GET", "POST", "PUT", "DELETE","HEAD","PATCH").allowedOrigins("http://34.238.255.203:8098").allowedHeaders("*");
			}
		};
	}
	*/
	 
}
