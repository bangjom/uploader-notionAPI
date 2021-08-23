package com.windfally.uploader.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";


    private final HttpSession httpSession;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("{}",authentication.getPrincipal());
        httpSession.setAttribute(USERNAME,request.getParameter(USERNAME));
        httpSession.setAttribute(PASSWORD,request.getParameter(PASSWORD));
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
