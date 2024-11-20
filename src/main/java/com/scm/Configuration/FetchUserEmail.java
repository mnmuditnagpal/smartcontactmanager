package com.scm.Configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;


@Component
public class FetchUserEmail {

    public static String getLoggedInUserEmail(Authentication authentication){


        if(authentication instanceof OAuth2AuthenticationToken){

            String Oauth2Client = ((OAuth2AuthenticationToken)authentication).getAuthorizedClientRegistrationId();

            DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();

            if(Oauth2Client.equalsIgnoreCase("google")) {
                return user.getAttribute("email");
            }
            else if(Oauth2Client.equalsIgnoreCase("github")){
                return user.getAttribute("login")+"@gmail.com";
            }
        }

        return authentication.getName();

    }
}
