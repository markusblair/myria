package org.adventure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailService userDetailService;
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    	auth.userDetailsService(userDetailService);
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("myria-player");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/game/**").hasRole("myria-player")
			.antMatchers("/character/**").hasRole("myria-player")
			.antMatchers("/myria/**").hasRole("myria-player")
			.and().formLogin();
	}
    
    
}