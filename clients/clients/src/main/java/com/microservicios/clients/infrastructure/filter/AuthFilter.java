package com.microservicios.clients.infrastructure.filter;

import com.microservicios.clients.application.ClientService;
import com.microservicios.clients.config.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthFilter implements Filter
{

    private final JwtUtil jwtUtil;

    private final ClientService clientService;


    public AuthFilter(JwtUtil jwtUtil, ClientService clientService) {
        this.jwtUtil = jwtUtil;
        this.clientService = clientService;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI();

        if (uri.equals("/clients/new_client"))
        {
            filterChain.doFilter(req,resp);
            return;
        }

        String aut = req.getHeader("Authorization");

        if (aut == null || false == aut.startsWith("Bearer "))
        {
            resp.setStatus(401);
            return;
        }

        String token = aut.substring(7);
    }
}
