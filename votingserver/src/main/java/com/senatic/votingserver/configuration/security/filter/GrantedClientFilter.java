package com.senatic.votingserver.configuration.security.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.senatic.votingserver.configuration.security.constant.SecurityConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GrantedClientFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String grantedClientKey = request.getHeader(SecurityConstants.GRANTED_CLIENT_HEADER);

        if (grantedClientKey != null) {
            if( grantedClientKey.equals(SecurityConstants.GRANTED_CLIENT)) {
                System.out.println("passed the filter client_id");
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bad client_id header");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing client_id header");
        }  
    }
}
