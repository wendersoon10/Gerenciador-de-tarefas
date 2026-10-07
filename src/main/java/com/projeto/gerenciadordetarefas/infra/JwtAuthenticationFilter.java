package com.projeto.gerenciadordetarefas.infra;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AuthorizationService authorizationService;
    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   AuthorizationService authorizationService,
                                   @Qualifier ("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtService = jwtService;
        this.authorizationService = authorizationService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authToken = request.getHeader("Authorization");

        if(authToken != null && authToken.startsWith("Bearer ")){
            try {
                String token = authToken.substring(7);
                String email = jwtService.lerEmail(token);

                UserDetails userDetails = authorizationService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (io.jsonwebtoken.JwtException ex) {
                // Captura todas as exceções de JWT (incluindo expiração) e envia para o RestControllerAdvice
                resolver.resolveException(request, response, null, ex);
                return;
            }
        }
        filterChain.doFilter(request, response);

    }

}
