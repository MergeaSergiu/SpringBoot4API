package dev.spring.API.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CognitoUserDetailsService {

    @Value("${spring.aws.USER_POOL_ID}")
    private String userPoolId;

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String clientId;

    private final CognitoIdentityProviderClient cognitoIdentityProvider;

    public CognitoUserDetailsService(CognitoIdentityProviderClient cognitoIdentityProvider) {
        this.cognitoIdentityProvider = cognitoIdentityProvider;
    }

    public UserDetails loadUserByUsername(String username, String password) {

        try {
            Map<String, String> authParams = new HashMap<>();
            authParams.put("USERNAME", username);
            authParams.put("PASSWORD", password);

            AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                    .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                    .authParameters(authParams)
                    .userPoolId(userPoolId)
                    .clientId(clientId).build();

            AdminInitiateAuthResponse authResponse = cognitoIdentityProvider.adminInitiateAuth(authRequest);

            AuthenticationResultType result = authResponse.authenticationResult();

            if (result != null && result.accessToken() != null) {
                // Authentication successful

                // Retrieve user info from Cognito
                AdminGetUserResponse userResponse = cognitoIdentityProvider.adminGetUser(
                        AdminGetUserRequest.builder()
                                .userPoolId(userPoolId)
                                .username(username)
                                .build()
                );

                String sub = userResponse.userAttributes().stream()
                        .filter(a -> "sub".equals(a.name()))
                        .map(AttributeType::value)
                        .findFirst()
                        .orElse(username);

                String email = userResponse.userAttributes().stream()
                        .filter(a -> "email".equals(a.name()))
                        .map(AttributeType::value)
                        .findFirst()
                        .orElse(username);

                // Return Spring Security UserDetails
                return new User(
                        username,
                        password, // you can put a dummy value, password is not used after login
                        true, true, true, true,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                );

            } else {
                if (authResponse.challengeName() != null) {
                    authResponse.challengeName();
                }
                throw new RuntimeException("Authentication failed for user: " + username);
            }

        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("User not found: " + username);
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Cognito error: " + e.awsErrorDetails().errorMessage(), e);
        }

    }
}
