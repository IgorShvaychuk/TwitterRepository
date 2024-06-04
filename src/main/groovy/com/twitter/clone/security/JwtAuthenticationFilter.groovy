package com.twitter.clone.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.twitter.clone.model.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager
    private final JwtUtil jwtUtil

    JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager
        this.jwtUtil = jwtUtil
        setFilterProcessesUrl("/users/login")
    }

    @Override
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class)
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.username, user.password)
            authenticationManager.authenticate(authenticationToken)
        } catch (IOException e) {
            throw new RuntimeException(e)
        }
    }

    @Override
    void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String token = jwtUtil.generateToken(authResult.name)
        response.addHeader("Authorization", "Bearer " + token)
    }
}
