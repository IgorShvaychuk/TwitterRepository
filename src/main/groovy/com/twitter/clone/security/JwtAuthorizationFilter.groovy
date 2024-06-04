package com.twitter.clone.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil
    private final UserDetailsService userDetailsService

    JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil
        this.userDetailsService = userDetailsService
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String header = request.getHeader("Authorization")
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7)
            String username = jwtUtil.extractUsername(token)

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username)
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    authentication.details = new WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().setAuthentication(authentication)
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}
