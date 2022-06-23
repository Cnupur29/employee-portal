package com.schaudha.employeeportal.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.cors.CorsConfiguration.ALL;
//access-control-allow-origin: *
//access-control-allow-origin: https://stackoverflow.com
//access-control-allow-credentials: true
//cross-origin-resource-policy: cross-origin
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    //origin: https://stackoverflow.com
    //referer: https://stackoverflow.com/
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
      /*  .headers()
                .addHeaderWriter(
                    new StaticHeadersWriter("Access-Control-Allow-Origin", "*"))
                .and()*/
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowCredentials(true);
                    configuration.addAllowedOrigin("http://localhost:8082");
                    configuration.addAllowedMethod("*");
                    configuration.addAllowedHeader("*");/*
                    configuration.setAllowedOrigins(Arrays.asList("*"));
                    configuration.setAllowedMethods(Arrays.asList("GET","POST"));
                    configuration.setAllowedHeaders(Collections.singletonList("*"));*/
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    return configuration;
                })
                .and()
                .csrf()
                .disable()
                .oauth2ResourceServer()
                .jwt();
    }

   /* @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8082")
                        .allowedMethods("PUT", "DELETE", "POST", "GET", "OPTIONS")
                        .allowedHeaders("Access-Control-Allow-Origin")
                        .exposedHeaders("Access-Control-Allow-Origin")
                        .allowCredentials(false).maxAge(3600);
            }
        };
    }*/

}
