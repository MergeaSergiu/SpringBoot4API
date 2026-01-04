package dev.spring.API.service;

import dev.spring.API.Dto.ProfileResponse;
import dev.spring.API.Dto.UserLoginRequest;
import dev.spring.API.Dto.UserRegistrationRequest;
import dev.spring.API.configuration.CognitoUtils;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentity.CognitoIdentityAsyncClient;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.*;

@Service
public class UserService {


    private final CognitoIdentityProviderClient cognitoClient;

    @Value("${spring.aws.USER_POOL_ID}")
    private String userPoolId;

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.cognito.client-secret}")
    private String clientSecret;

    public UserService(CognitoIdentityProviderClient cognitoClient) {

        this.cognitoClient = cognitoClient;
    }

    public void registerUser(UserRegistrationRequest userRequest) {
        try {

            Map<String, String> secretHashParam = new HashMap<>();
            secretHashParam.put("SECRET_HASH", CognitoUtils.calculateSecretHash(userRequest.username(), clientId, clientSecret));

            AdminCreateUserRequest createUserRequest = AdminCreateUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(userRequest.username())
                    .temporaryPassword(userRequest.password())
                    .userAttributes(
                            AttributeType.builder().name("given_name").value(userRequest.givenName()).build(),
                            AttributeType.builder().name("family_name").value(userRequest.familyName()).build(),
                            AttributeType.builder().name("email").value(userRequest.email()).build()
                            //AttributeType.builder().name("email_verified").value("true").build()
                    )
                    .desiredDeliveryMediums(DeliveryMediumType.EMAIL)
                    .clientMetadata(secretHashParam)
                    .build();

            cognitoClient.adminCreateUser(createUserRequest);

            AdminSetUserPasswordRequest setPasswordRequest =
                    AdminSetUserPasswordRequest.builder()
                            .userPoolId(userPoolId)
                            .username(userRequest.username())
                            .password(userRequest.password())
                            .permanent(true)
                            .build();

            cognitoClient.adminSetUserPassword(setPasswordRequest);

        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Error registering user: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    /**
     * Initiates login (admin auth) for username/password
     */
    public Map<String, String> loginUser(UserLoginRequest request) {
        try {
            Map<String, String> authParams = new HashMap<>();
            authParams.put("USERNAME", request.username());
            authParams.put("PASSWORD", request.password());
            authParams.put("SECRET_HASH", CognitoUtils.calculateSecretHash(request.username(), clientId, clientSecret));
            AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                    .userPoolId(userPoolId)
                    .clientId(clientId)
                    .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                    .authParameters(authParams)
                    .build();

            AdminInitiateAuthResponse authResponse = cognitoClient.adminInitiateAuth(authRequest);

            if(authResponse == null) throw new EntityNotFoundException("No response for login");
            // Check if user needs to change password

//            if(authResponse.challengeName() == ChallengeNameType.NEW_PASSWORD_REQUIRED) {
//                    throw new EntityNotFoundException("New password required");
//            }

            AdminGetUserResponse userResponse =
                    cognitoClient.adminGetUser(
                            AdminGetUserRequest.builder()
                                    .userPoolId(userPoolId)
                                    .username(request.username())
                                    .build()
                    );

            if(userResponse.userStatus().toString().equals("FORCE_CHANGE_PASSWORD")) {
                throw new EntityNotFoundException("User needs to change password");
            }

            boolean emailVerified = userResponse.userAttributes().stream()
                    .anyMatch(attr ->
                            attr.name().equals("email_verified") &&
                                    attr.value().equalsIgnoreCase("true")
                    );

            if(!emailVerified) {
                throw new EntityNotFoundException("Email address is not verified");
            }

            AuthenticationResultType result = authResponse.authenticationResult();

            // Login successful if AuthenticationResult exists
            // Return tokens
            Map<String, String> tokens = new HashMap<>();
            tokens.put("idToken", result.idToken());
            tokens.put("accessToken", result.accessToken());
            tokens.put("refreshToken", result.refreshToken());
            tokens.put("expiresIn", String.valueOf(result.expiresIn()));

            return tokens;

        } catch (CognitoIdentityProviderException e) {
            throw new EntityNotFoundException(e.awsErrorDetails().errorMessage(), e);
        }
    }

    public ProfileResponse profileInformation(Authentication authentication) {
        if(authentication == null) throw new EntityNotFoundException("No token");
        if(authentication instanceof JwtAuthenticationToken) {
            String username = (String) ((JwtAuthenticationToken) authentication).getTokenAttributes().get("username");
            return new ProfileResponse(username);
        }
        return new ProfileResponse();

    }
}
