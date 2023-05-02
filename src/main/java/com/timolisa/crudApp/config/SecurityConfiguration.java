package com.timolisa.crudApp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;

import static com.timolisa.crudApp.utility.AppConstants.CLIENT_ID;
import static com.timolisa.crudApp.utility.AppConstants.CLIENT_SECRET;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    // TODO review later
    // https://stackoverflow.com/questions/73089730/authorizerequests-vs-authorizehttprequestscustomizerauthorizehttprequestsc

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated()) // (AuthorizationManagerRequestsMatcherRegistry) registry for matching a request matcher to an authorization manager, it accepts a customizer
                .oauth2Login(Customizer.withDefaults())
                .build();

    }

    // ClientRegistration: A representation of a client registration with an OAuth 2.0 or OpenID Connect 1.0 Provider.
    @Bean
    public ClientRegistration googleClientRegistration() {
        return CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .build();
    }

    // ClientRegistrationRepository: Client registration information is ultimately stored and owned by the associated Authorization server;
    // Therefore, this repository provides the capability to store a sub-set copy of the primary client registration information externally
    // from the Authorization server.
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(googleClientRegistration());
    }

    // OAuth2AuthorizedClientRepository: implementations of this interface allows for the persistence of the Authorized client(s) between requests.
    // The purpose of the Authorized client is to associate an access token credential to a client application and resource owner, who is the Principal that
    // originally granted the authorization.
    @Bean
    public OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository(OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(oAuth2AuthorizedClientService);
    }

    // OAuth2AuthorizedClientService: responsible for management of Authorized clients.
    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }
}
