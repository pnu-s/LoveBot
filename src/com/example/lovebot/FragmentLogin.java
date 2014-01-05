package com.example.lovebot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentLogin extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.activity_fragment_login, container,
				false);
		final Button btnInscription = (Button) v
				.findViewById(R.id.buttonInscription);

		btnInscription.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				FragmentInscription fi = new FragmentInscription();
				ft.replace(R.id.fragmentContainer, fi);
				ft.commit();

			}
		});

		final Button contactsButton = (Button) v
				.findViewById(R.id.buttonCo);
		
		contactsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// on récupere les infos entrées et on cast en String
				String num = ((EditText) getView().findViewById(R.id.login_co)).getText().toString();
				String passwd = ((EditText) getView().findViewById(R.id.passwd_co)).getText().toString();
				
				// on appelle LoginService
				LoginService loginService = new LoginService();
				try {
					// on recupere le token synonyme de bonne connexion
					String token = loginService.execute(num, passwd).get();
					if (token != null) {
						// si il y en a un on va dans l'activité contacts
						Intent intent = new Intent(getActivity(),
								ContactsActivity.class);
						startActivity(intent);
					}
					else{
						new AlertDialog.Builder(getActivity())
						.setTitle("Erreur")
						.setMessage("Erreur dans le num / mdp").show();
					}
				} catch (InterruptedException interruptedException) {
					Log.e("log_tag",
							"interrupted " + interruptedException.toString());
				} catch (ExecutionException executionException) {
					Log.e("log_tag",
							"execution" + executionException.toString());
				} catch (NullPointerException nullPointerException) {
					Log.e("log_tag",
							"nullpointer " + nullPointerException.toString());
				}
			}
		});

		return v;
	}
}
