package com.windfally.uploader.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class MultiReadableRequestBodyFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
        if(servletRequest.getContentType() == null){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        if(servletRequest.getContentType().startsWith("multipart/form-data")){
            filterChain.doFilter(servletRequest,servletResponse);
        } else{
            MultiReadableRequestBodyHttpServletRequest wrappedRequest = new MultiReadableRequestBodyHttpServletRequest((HttpServletRequest) servletRequest);
            filterChain.doFilter(wrappedRequest, servletResponse);
        }
    }
}
