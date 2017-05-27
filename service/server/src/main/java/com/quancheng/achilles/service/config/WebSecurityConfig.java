package com.quancheng.achilles.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("qcpassword").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("qcpassword").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("qianyu@quancheng-ec.com").password("password").roles("KEYCUSTOMER");
        auth.inMemoryAuthentication().withUser("jihongyan@quancheng-ec.com").password("password").roles("KEYCUSTOMER");
        auth.inMemoryAuthentication().withUser("xiayang@quancheng-ec.com").password("password").roles("OPERATIVE");
        
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.authorizeRequests().anyRequest().authenticated().and().httpBasic();

		http.authorizeRequests().antMatchers("/swagger-*","/v2/**", "/actuator/**", "/configuration/**", "/api/**").permitAll().anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll().and().logout().permitAll();
		http.csrf().ignoringAntMatchers("/api/**" );
	}

	/**
	 * http://stackoverflow.com/questions/24726218/spring-security-refused-to-execute-script-from
	 * 
	 * http://stackoverflow.com/questions/24916894/serving-static-web-resources-in-spring-boot-spring-security-application/24920752#24920752
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**","/elite/**");
	}
}
