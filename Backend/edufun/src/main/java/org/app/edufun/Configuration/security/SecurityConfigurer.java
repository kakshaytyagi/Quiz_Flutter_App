package org.app.edufun.Configuration.security;

import org.app.edufun.Configuration.filters.JWTRequestFilter;
import org.app.edufun.Configuration.filters.MSRequestFilter;
import org.app.edufun.service_impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private JWTRequestFilter jWTRequestFilter;
	@Autowired
	private MSRequestFilter mSRequestFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll()
//		.and().csrf().disable().authorizeRequests().antMatchers("/doAdminLogin").permitAll()
//		.and().csrf().disable().authorizeRequests().antMatchers("/Recruit/Prof/search/profile").permitAll()
//		.and().csrf().disable().authorizeRequests().antMatchers("/getUser").permitAll()
		.anyRequest().authenticated()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jWTRequestFilter, UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(mSRequestFilter, BasicAuthenticationFilter.class);
		
		http.cors();
	}
	
	@Bean
	public AuthenticationManager auhenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
//	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("http://localhost:8000");
//			}
//		};
//	}


//	@Bean
//	CorsConfigurationSource corsConfigurationSource()
//	{
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("http://35.173.186.169:8000"));
//		configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
	
//	@Bean
//	public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		// config.addAllowedHeader("Access-Control-Allow-Headers", "Authorization,
//		// Content-Type, enctype");
//		config.addAllowedMethod("OPTIONS");
//		config.addAllowedMethod("GET");
//		config.addAllowedMethod("POST");
//		config.addAllowedMethod("PUT");
//		config.addAllowedMethod("DELETE");
//
//		source.registerCorsConfiguration("/**", config);
//		return new CorsFilter(source);
//	}
	
	// test this also
	
	
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//	   return new WebMvcConfigurerAdapter() {
//	      @Override
//	      public void addCorsMappings(CorsRegistry registry) {
//	         registry.addMapping("/**").allowedOrigins("http://35.173.186.169:8000");
//	      }    
//	   };
//	}
	
//	@Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(false);
////        config.addAllowedOrigin(CorsConfiguration.ALL);
////        config.addAllowedHeader(CorsConfiguration.ALL);
//        config.setAllowedOrigins(List.of("http://35.173.186.169:8000","http://35.173.186.169:8098"));
//		config.addAllowedHeader("*");
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("HEAD");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("DELETE");
//        config.addAllowedMethod("PATCH");
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
	
//	@Bean
//	public HttpFirewall allowSemicolonHttpFirewall() {
//	    StrictHttpFirewall firewall = new StrictHttpFirewall();
//	    firewall.setAllowBackSlash(true);
//	    return firewall;
//	}

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//	  super.configure(web);
//	  web.httpFirewall(allowSemicolonHttpFirewall());
//	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
		  super.configure(web);
//		  web.httpFirewall(allowSemicolonHttpFirewall());
        web.ignoring().antMatchers("/v2/api-docs",
                                   "/configuration/ui",
                                   "/swagger-resources/**",
                                   "/configuration/security",
                                   "/swagger-ui.html",
                                   "/webjars/**");
    }
}
