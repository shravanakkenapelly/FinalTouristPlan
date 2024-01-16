package com.admin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final AdminAuthenticationEntryPoint adminAuthenticationEntryPoint;
	private final AdminAuthenticationProvider adminAuthenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http

				.exceptionHandling(customizer -> customizer.authenticationEntryPoint(adminAuthenticationEntryPoint))
				.addFilterBefore(new JwtAuthFilter(adminAuthenticationProvider), BasicAuthenticationFilter.class)
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						(requests) -> requests.requestMatchers(HttpMethod.POST, "/adminlogin", "/adminregister")
								.permitAll().anyRequest().authenticated())

		;
		return http.build();
	}

	protected void configure(HttpSecurity http) throws Exception {

		http

				.authorizeRequests().requestMatchers("/", "/home").permitAll().anyRequest().authenticated().and()
				.formLogin().loginPage("/login").permitAll().and().logout().logoutUrl("/adminlogout") // Specify the
																										// logout URL
				.logoutSuccessUrl("/") // Redirect to the home page after logout
				.invalidateHttpSession(true).deleteCookies("JSESSIONID");
	}
}
