package com.scm.Configuration;


import com.scm.Services.SecurityCustomDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    public SecurityCustomDetailsService userDetailsService;

    @Autowired
    private OauthAuthenticationSuccessHandler successHandler;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize->{
                   authorize.requestMatchers("/user/**").authenticated()
                           .anyRequest().permitAll();
                });

        httpSecurity.
                formLogin(formLogin->{
                    formLogin.loginPage("/login")
                            .loginProcessingUrl("/login-user")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .successForwardUrl("/user/dashboard");
                            // .failureUrl("/login?error=true");
                });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.logout(
                logoutForm->{
                    logoutForm.logoutUrl("/do-logout")
                            .logoutSuccessUrl("/login?logout=true")
                            .invalidateHttpSession(true)
                            .clearAuthentication(true);
                }
        );

        // oauth2 configurations

        httpSecurity
                    .oauth2Login(oauth->{
                        oauth.loginPage("/login");
                        oauth.successHandler(successHandler);
                    });


        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
