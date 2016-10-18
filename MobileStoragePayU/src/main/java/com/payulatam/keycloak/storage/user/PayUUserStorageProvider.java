package com.payulatam.keycloak.storage.user;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.*;
import org.keycloak.models.cache.CachedUserModel;
import org.keycloak.models.cache.OnUserCache;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserCredentialValidatorProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:jhonatan.zambrano@payulatam.com">Jhonatan A. Zambrano</a>
 *         7/10/2016
 */
@Stateless
@Local(PayUUserStorageProvider.class)
public class PayUUserStorageProvider implements UserStorageProvider,
        UserLookupProvider,
        UserCredentialValidatorProvider,
        UserQueryProvider,
        OnUserCache
{
    @PersistenceContext
    protected EntityManager em;

    protected ComponentModel model;
    protected KeycloakSession session;

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
        Query query = em.createNamedQuery("findPasswordMD5");
        query.setParameter("email", user.getUsername());
        String password = (String) query.getSingleResult();
        try {
            if(!password.isEmpty() && password.equals(digestMD5(input.get(0).getValue()))) {
                return true;
            }
            else {
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
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
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) { return new LinkedList<>(); }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult, int maxResults) { return new LinkedList<>(); }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) { return new LinkedList<>(); }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
        return new LinkedList<>();
    }

    @Override
    public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) { return new LinkedList<>(); }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        String username = StorageId.externalId(id);
        if(username != null) {
            Query query = em.createNamedQuery("getUserByUsername");
            query.setParameter("email", username);
            PayUUserEntity entity = (PayUUserEntity) query.getSingleResult();
            if (entity != null)
                return new PayUUser(session, realm, model, entity);
            else
                return null;
        } else
            return null;
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {

        Query query = em.createNamedQuery("getUserByUsername");
        query.setParameter("email", username);
        PayUUserEntity entity = (PayUUserEntity) query.getSingleResult();
        if(entity != null)
            return new PayUUser(session, realm, model, entity);
        else
            return null;
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
        Query query = em.createNamedQuery("getUserByEmail");
        query.setParameter("email", email);
        PayUUserEntity entity = (PayUUserEntity) query.getSingleResult();
        if(entity != null)
            return new PayUUser(session, realm, model, entity);
        else
            return null;
    }

    @Override
    public void onCache(RealmModel realm, CachedUserModel user) {
        user.getCachedWith().put(user.getId(), user);
    }

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

    private String digestMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Digest(in hex format):: " + sb.toString());

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
