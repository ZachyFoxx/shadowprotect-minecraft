package net.voxhash.shadowprotect.util.web.endpoint;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class VerifyEndpoint {

    private String url;
    private String token;

    public VerifyEndpoint(String token) throws UnsupportedEncodingException {
        this.url = "http://10.20.0.11:6060/api/verify?token=" + token;
        this.token = token;
    }
    
    public int verify() {
        try {
            HttpResponse response;
            CloseableHttpClient httpClient = HttpClients
            .custom()
            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build();
            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("token", token));
            
            HttpGet getConnection = new HttpGet(url);
            
            try {
                response = httpClient.execute(getConnection);
                if (response.getStatusLine().getStatusCode() == 200) {
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
