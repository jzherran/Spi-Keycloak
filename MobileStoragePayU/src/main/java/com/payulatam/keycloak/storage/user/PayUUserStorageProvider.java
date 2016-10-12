package com.payulatam.keycloak.storage.user;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.*;
import org.keycloak.models.cache.CachedUserModel;
import org.keycloak.models.cache.OnUserCache;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserCredentialValidatorProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

//import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author <a href="mailto:jhonatan.zambrano@payulatam.com">Jhonatan A. Zambrano</a>
 *         7/10/2016
 */
public class PayUUserStorageProvider implements UserStorageProvider,
        UserLookupProvider,
        UserCredentialValidatorProvider,
        UserQueryProvider,
        OnUserCache{
    
    protected EntityManager em = getEntityManager();
    protected ComponentModel model;
    protected KeycloakSession session;

    private EntityManager getEntityManager() {
        if (this.em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("keycloack-payulatam-storage");

            em = emf.createEntityManager();
        }
        return em;
    }

    public void setModel(ComponentModel model) {
        this.model = model;
    }

    public void setSession(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void preRemove(RealmModel realm) {

    }

    @Override
    public void preRemove(RealmModel realm, GroupModel group) {

    }

    @Override
    public void preRemove(RealmModel realm, RoleModel role) {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean validCredentials(KeycloakSession session, RealmModel realm, UserModel user, List<UserCredentialModel> input) {
        return false;
    }

    @Override
    public int getUsersCount(RealmModel realm) {
        return 0;
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm) {

        try {
            callHttpClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {

        try {
            callHttpClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm) {
        return new LinkedList<>();
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) { return new LinkedList<>(); }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
        return new LinkedList<>();
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult, int maxResults) {
        PayUUser payuUser = new PayUUser(session, realm, model);
        payuUser.setAttribute("payuInfo", Arrays.asList("nuevo", "atributo"));
        payuUser.setAttribute("demoInfo", Arrays.asList("atributo", "demo"));

        UserEntity entity = em.find(UserEntity.class, 4L);
        if (entity == null) {

            //return null;
        }

        return Arrays.asList(payuUser);
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) { return new LinkedList<>(); }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
        return new LinkedList<>();
    }

    @Override
    public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) { return new LinkedList<>(); }

    public void callHttpClient () throws IOException {
        String url = "https://api.spotify.com/v1/albums/0sNOF9WDwhWunNAHPD3Baj";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);

        HttpResponse response = client.execute(get);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result);
    }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        PayUUser payuUser = new PayUUser(session, realm, model);
        payuUser.setAttribute("payuInfo", Arrays.asList("nuevo", "atributo"));
        payuUser.setAttribute("demoInfo", Arrays.asList("atributo", "demo"));
        return payuUser;
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
        return null;
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
        return null;
    }

    @Override
    public void onCache(RealmModel realm, CachedUserModel user) {
        System.out.println("");
    }
}
