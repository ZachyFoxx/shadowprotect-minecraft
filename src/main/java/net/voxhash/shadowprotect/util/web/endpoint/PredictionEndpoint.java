package net.voxhash.shadowprotect.util.web.endpoint;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.voxhash.shadowprotect.util.web.Prediction;
import net.voxhash.shadowprotect.util.web.Type;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class PredictionEndpoint {

    private String url;
    private String apiKey;
    private String input;

    public PredictionEndpoint(String apiKey, String input) throws UnsupportedEncodingException {
        this.url = "http://10.20.0.11:6060/api/outcomes/?input=" + URLEncoder.encode((input), "UTF-8") + "&token=" +  URLEncoder.encode(apiKey, "UTF-8");
        this.apiKey = apiKey;
        this.input = input;
    }
    
    public Prediction getPrediction() {
        try {
            HttpResponse response;
            CloseableHttpClient httpClient = HttpClients
            .custom()
            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build();
            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("token", apiKey));
            nameValuePairs.add(new BasicNameValuePair("input", input));
            
            HttpGet getConnection = new HttpGet(url);
            
            try {
                response = httpClient.execute(getConnection);
                String JSONString = EntityUtils.toString(response.getEntity(), "UTF-8");
                JsonElement json = new JsonParser().parse(JSONString);
                JsonObject jsonObject = json.getAsJsonObject();
                String input = jsonObject.get("input").getAsString();
                double ham = jsonObject.get("confidence").getAsJsonObject().get("ham").getAsDouble();
                double spam = jsonObject.get("confidence").getAsJsonObject().get("spam").getAsDouble();
                double advert = jsonObject.get("confidence").getAsJsonObject().get("advert").getAsDouble();
                double scam = jsonObject.get("confidence").getAsJsonObject().get("scam").getAsDouble();
                double max = Math.max(ham, Math.max(spam, Math.max(advert, Math.max(scam, 0))));
                Type type = max == spam ? Type.SPAM : max == scam ? Type.SCAM : max == advert ? Type.ADVERT : Type.HAM;

                return new Prediction(input, type, max, "en", 1);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                httpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
