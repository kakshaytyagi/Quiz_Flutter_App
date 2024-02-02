package org.app.edufun.Configuration.security;

//@SuppressAjWarnings("deprecation")
//@Configuration
public class SecurityConfig{
	
//	@Bean
//	public HttpSessionEventPublisher httpSessionEventPublisher() {
//	    return new HttpSessionEventPublisher();
//	}
//	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) {
//	   try {
//		   http
//	        .sessionManagement(session -> session
//	            .maximumSessions(1)
//	        );
//	    return http.build();
//	   }catch(Exception e) {
//		   e.printStackTrace();
//		   return null;
//	   }
//	}

	/*
	

	private static final String usersQuery = "select userName, userPassword, 1 FROM users where email = ?";
	private static final String rolesQuery = "select userName, role FROM users where userName = ?";

	@Autowired
	private javax.sql.DataSource ds;

	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

	@Bean
	@ConfigurationProperties("spring.datasource")
	public javax.sql.DataSource ds() {
		return DataSourceBuilder.create().build();
	}
//
//	/* Spring Security Configurations Starts */
//
//	@Autowired
//	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).dataSource(ds).authoritiesByUsernameQuery(rolesQuery).passwordEncoder(passwordEncoder());
//	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.httpBasic().and().authorizeRequests().anyRequest().authenticated();
//		http.csrf().disable();
//	}
	
//	protected void configure(HttpSecurity http) throws Exception { 
//	      http.csrf().disable() .authorizeRequests().anyRequest()
//	      .authenticated() .and() 
//	      .formLogin()
//	      .and() 
//	      .rememberMe()
//	      .and().logout().logoutUrl("/logout") 
//	      .logoutSuccessUrl("/login") .deleteCookies("remember-me"); 
//	   } 
	
	/* Spring Security Configurations End */
	

 
}