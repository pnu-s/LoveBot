package com.example.lovebot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;

public class LoginService extends AsyncTask<String, Void, String>
{
    @Override
    protected String doInBackground(String... params)
    {
    	//on recup les param
        String numero = params[0];
        String password = params[1];
        
        //uri en clair
        String uri = "http://lovebot.byethost17.com/login.php";
        
        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("numero", numero));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        
        // important : ajout des paramètres dans l'url
        uri += "?" + URLEncodedUtils.format(nameValuePairs, "utf-8");
        
        HttpGet httpGet = new HttpGet(uri);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        try
        {
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet, new BasicHttpContext());
            String response = EntityUtils.toString(httpResponse.getEntity());
            //on recupere la réponse en JSON
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString("token");
        }
        catch(JSONException jsonException)
        {
        }
        catch(ClientProtocolException clientProtocolException)
        {

        }
        catch(IOException ioException)
        {

        }
        return null;
    }
}