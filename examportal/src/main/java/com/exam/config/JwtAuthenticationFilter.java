package com.exam.config;

import com.exam.services.implemetation.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter implements Filter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader;
        requestTokenHeader = request.getHeader("Authorization");
        System.out.println(requestTokenHeader);

        String userName=null;
        String jwtToken=null;
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
           jwtToken=requestTokenHeader.substring(7);
           try {
               userName = this.jwtUtil.extractUsername(jwtToken);
           }catch (ExpiredJwtException e){
               System.out.println("jwt token expired");
           }
           catch (Exception e){
               e.printStackTrace();
               System.out.println("Error");
           }

        }
        else{
            System.out.println("Invalid Token , not starts with bearer string");
        }

        if(userName!=null&& SecurityContextHolder.getContext().getAuthentication()==null) {

            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (this.jwtUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken
                                (userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.
                        setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }


        }
        else {
            System.out.println("Token is not  valid ");
        }

        filterChain.doFilter(request,response);
    }


    @Override
    public void init(FilterConfig filterConfig) throws javax.servlet.ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, javax.servlet.FilterChain filterChain) throws IOException, javax.servlet.ServletException {
        final String requestTokenHeader = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        System.out.println(requestTokenHeader);

        String userName = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                userName = this.jwtUtil.extractUsername(jwtToken);
            } catch (ExpiredJwtException e) {
                System.out.println("jwt token expired");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error");
            }
        } else {
            System.out.println("Invalid Token, does not start with the bearer string");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (this.jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) servletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } else {
            System.out.println("Token is not valid");
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }


}