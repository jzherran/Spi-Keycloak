package com.payulatam.keycloak.storage.user;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

import javax.naming.InitialContext;

/**
 * @author <a href="mailto:jhonatan.zambrano@payulatam.com">Jhonatan A. Zambrano</a>
 *         7/10/2016
 */
public class PayUUserStorageProviderFactory implements UserStorageProviderFactory<PayUUserStorageProvider> {

    private static final PayUUserStorageProvider provider = new PayUUserStorageProvider();

    @Override
    public PayUUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        try {
            provider.setModel(model);
            provider.setSession(session);
            return provider;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public String getId() {
        return "mobile-storage-payulatam";
    }

    @Override
    public String getHelpText() {
        return "PayU Latam user storage provider";
    }

    @Override
    public void close() {

    }
}
