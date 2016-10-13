package com.payulatam.keycloak.storage.user;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import java.util.UUID;

/**
 * @author <a href="mailto:jhonatan.zambrano@payulatam.com">Jhonatan A. Zambrano</a>
 *         7/10/2016
 */
public class PayUUser extends AbstractUserAdapterFederatedStorage {

    protected PayUUserEntity payUUserEntity;
    protected String keycloakId;

    public PayUUser(KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel, PayUUserEntity payUUserEntity) {
        super(session, realm, storageProviderModel);
        this.payUUserEntity = payUUserEntity;
        keycloakId = StorageId.keycloakId(storageProviderModel, UUID.randomUUID().toString());
    }

    @Override
    public String getUsername() {
        return "jzherran";
    }

    @Override
    public void setUsername(String username) {

    }
}
