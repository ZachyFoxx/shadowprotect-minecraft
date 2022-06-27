package net.voxhash.shadowprotect.util.web.endpoint;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class RegistrationEndpoint {

    private String url;
    private String email;
    private String username;
    private String password;
    private String token;

    public RegistrationEndpoint(String email, String username, String password) throws UnsupportedEncodingException {
        this.url = "http://10.20.0.11:6060/api/register";
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getToken() {
        return token;
    }
    
    public int register() {
        try {
            HttpResponse response;
            CloseableHttpClient httpClient = HttpClients
            .custom()
            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build();            
            HttpPost request = new HttpPost(url);

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("username", username));
            pairs.add(new BasicNameValuePair("password", password));
            pairs.add(new BasicNameValuePair("email", email));
            request.setEntity(new UrlEncodedFormEntity(pairs));

            try {
                response = httpClient.execute(request);
                String responseBody = EntityUtils.toString(response.getEntity());
                JsonElement jsonElement = new JsonParser().parse(responseBody);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (response.getStatusLine().getStatusCode() == 200) {
                    token = jsonObject.get("token").getAsString();
                    return 200;
                } else {
                    return response.getStatusLine().getStatusCode();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 500;
            } finally {
                httpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 500;
        }
    }
}
