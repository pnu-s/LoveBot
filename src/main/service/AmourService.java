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

public class AmourService extends AsyncTask<String, Void, String>
{
    @Override
    protected String doInBackground(String... params)
    {
    	//on recup les param
        String user1 = params[0];
        String user2 = params[1];
        
        //url
        String uri = ServiceConstants.AMOUR;
        
        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user1", user1));
        nameValuePairs.add(new BasicNameValuePair("user2", user2));
        
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