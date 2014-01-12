package main.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.constant.ServiceConstants;

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
import android.util.Log;

public class LoginService extends AsyncTask<String, Void, String>
{
    @Override
    protected String doInBackground(String... params)
    {
    	// Récupération des paramètres
        String numero = params[0];
        String password = params[1];
        
        String uri = ServiceConstants.LOGIN;
        
        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("numero", numero));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        
        // Ajout des paramètres dans l'URL
        uri += "?" + URLEncodedUtils.format(nameValuePairs, "utf-8");
        
        HttpGet httpGet = new HttpGet(uri);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        try
        {
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet, new BasicHttpContext());
            String response = EntityUtils.toString(httpResponse.getEntity());
            
            // Récupération de la réponse JSON
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString("token");
        }
        catch(JSONException jsonException)
        {
        	Log.e("log_tag",
					"jSON " + jsonException.toString());
        }
        catch(ClientProtocolException clientProtocolException)
        {
        	Log.e("log_tag",
					"ClientProtocol " + clientProtocolException.toString());
        }
        catch(IOException ioException)
        {
        	Log.e("log_tag",
					"IO " + ioException.toString());
        }
        return null;
    }
}