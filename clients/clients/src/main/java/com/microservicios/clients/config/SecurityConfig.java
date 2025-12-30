package com.microservicios.clients.config;

import com.microservicios.clients.application.ClientService;
import com.microservicios.clients.infrastructure.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig
{

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter (JwtUtil jwtUtil, ClientService clientService)
    {
        FilterRegistrationBean<AuthFilter> bean = new FilterRegistrationBean<>(new AuthFilter(jwtUtil, clientService));

        bean.addUrlPatterns("/api/*");
        bean.setOrder(2);

        return bean;
    }

}
