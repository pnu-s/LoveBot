package com.example.lovebot;

import java.util.concurrent.ExecutionException;
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

		final Button contactsButton = (Button) v.findViewById(R.id.buttonCo);

		contactsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// on récupere les infos entrées et on cast en String
				String num = ((EditText) getView().findViewById(R.id.login_co))
						.getText().toString();
				String passwd = ((EditText) getView().findViewById(
						R.id.passwd_co)).getText().toString();
				// on appelle LoginService
				LoginService loginService = new LoginService();
				try {
					// on recupere le token synonyme de bonne connexion
					
					String token = (String) loginService.execute(num, passwd).get();					
					if (token != null && !token.equals("1")) {
						// si il y en a un on va dans l'activité contacts
						Intent intent = new Intent(getActivity(),
								ContactsActivity.class);
						intent.putExtra("key", token);
						startActivity(intent);
					} else if (token.equals("1")) {
						Intent intent2 = new Intent(getActivity(),
								SuccessActivity.class);
						startActivity(intent2);
					} else {
						new AlertDialog.Builder(getActivity()).setMessage(
								"Identifiants incorrects.").show();
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
