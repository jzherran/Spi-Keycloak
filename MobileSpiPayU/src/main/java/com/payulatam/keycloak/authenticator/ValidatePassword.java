/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.payulatam.keycloak.authenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.events.Errors;
import org.keycloak.models.*;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.services.managers.AuthenticationManager;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:jhonatan.zambrano@payulatam.com">Jhonatan A. Zambrano</a>
 *         6/10/2016
 */
public class ValidatePassword extends AbstractMobileGrantAuthenticator {

    private static final String PROVIDER_ID = "mobile-grant-validate-credentials";

    public static final String DATA_PASSWORD = "password";

    @Override
    public void authenticate(AuthenticationFlowContext context) {

        MultivaluedMap<String, String> inputData = context.getHttpRequest().getDecodedFormParameters();
        String username = inputData.getFirst(AuthenticationManager.FORM_USERNAME);
        String password = inputData.getFirst(ValidatePassword.DATA_PASSWORD);

        if (username == null || password == null) {
            context.getEvent().error(Errors.INVALID_USER_CREDENTIALS);
            Response challengeResponse = errorResponse(Response.Status.UNAUTHORIZED.getStatusCode(), "invalid_request", "Missing parameters");
            context.failure(AuthenticationFlowError.INVALID_CREDENTIALS, challengeResponse);
            return;
        }

        HashMap<String, String>  parameters = new HashMap<>();
        parameters.put(AuthenticationManager.FORM_USERNAME, username);
        parameters.put(ValidatePassword.DATA_PASSWORD, password);

        UserModel user = context.getSession().userStorageManager().getUserByEmail(username, context.getRealm());
        if(user != null) {
            UserCredentialModel credentialModel = UserCredentialModel.password(password);
            boolean validationOutput = context.getSession().userStorageManager().validCredentials(context.getSession(), context.getRealm(), user, credentialModel);
            if(validationOutput)
                context.setUser(user);
        }
        else {
            context.getEvent().error(Errors.USER_NOT_FOUND);
            Response challengeResponse = errorResponse(Response.Status.UNAUTHORIZED.getStatusCode(), "not_found_user", "User not found.");
            context.failure(AuthenticationFlowError.UNKNOWN_USER, challengeResponse);
            return;
        }
        context.success();
    }

    // TODO Set true to end test
    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {

    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }


    @Override
    public String getDisplayType() {
        return "Credentials Mobile";
    }

    @Override
    public String getReferenceCategory() {
        return null;
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public String getHelpText() {
        return "Validates the credentials supplied as a form parameter in mobile grant request";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return new LinkedList<>();
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
