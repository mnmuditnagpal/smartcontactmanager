package com.scm.Configuration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.Models.Providers;
import com.scm.Models.User;
import com.scm.Repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

@Component
public class OauthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

                Logger logger = LoggerFactory.getLogger(this.getClass());

                logger.info("OAuth2 Handler");

                String authenticationClientId = ((OAuth2AuthenticationToken)authentication).getAuthorizedClientRegistrationId();

                logger.info(authenticationClientId);

                DefaultOAuth2User oAuth2User = (DefaultOAuth2User)authentication.getPrincipal();

                logger.info(oAuth2User.toString());

                User saveUser = new User();

                saveUser.setEmailVerified(true);
                saveUser.setTerms(true);
                saveUser.setEnabled(true);
                saveUser.setPassword("dummy");
                saveUser.setPhoneVerified(true);
                saveUser.setPhoneNumber("0000000000");


                if(authenticationClientId.equalsIgnoreCase("google")){
                    saveUser.setName(oAuth2User.getAttribute("name"));
                    saveUser.setAbout("This account is created using google oauth2 authorization");
                    saveUser.setEmail(oAuth2User.getAttribute("email"));
                    saveUser.setProvider(Providers.GOOGLE);
                    saveUser.setProviderUserId(oAuth2User.getAttribute("name"));
                    saveUser.setProfilePic(oAuth2User.getAttribute("picture"));
                }

                if(authenticationClientId.equalsIgnoreCase("github")){
                    saveUser.setName(oAuth2User.getAttribute("name")==null?oAuth2User.getAttribute("login"):oAuth2User.getAttribute("name"));
                    saveUser.setAbout("This account is created using github oauth2 authorization");
                    saveUser.setEmail(oAuth2User.getAttribute("email")==null?oAuth2User.getAttribute("email"):oAuth2User.getAttribute("login")+"@gmail.com");
                    saveUser.setProvider(Providers.GITHUB);
                    saveUser.setProviderUserId(oAuth2User.getAttribute("login"));
                    saveUser.setProfilePic(oAuth2User.getAttribute("avatar_url"));
                }

                Optional<User> userOptional = userRepository.findByEmail(saveUser.getEmail());


                if(userOptional.isEmpty()){
                   userRepository.save(saveUser);
                    logger.info(oAuth2User.getAttribute("email")+" User details saved successfully");
                }else{
                    logger.info("user already exists");
                }

                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }

}
