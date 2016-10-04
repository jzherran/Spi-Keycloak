package com.payulatam.authenticator;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.CredentialProviderFactory;
import org.keycloak.models.KeycloakSession;

/**
 * @author <a href="mailto:jhonatan.zambrano@payulatam.com">Jhonatan A. Zambrano</a>
 *         4/10/2016
 */
public class ApiCredentialProviderFactory implements CredentialProviderFactory<ApiCredentialProvider> {

    @Override
    public ApiCredentialProvider create(KeycloakSession keycloakSession, ComponentModel componentModel) {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public CredentialProvider create(KeycloakSession session) {
        return null;
    }
}
