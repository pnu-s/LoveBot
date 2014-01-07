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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class FragmentInscription extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.activity_fragment_inscription,
				container, false);
		
		final ImageButton btnRetour = (ImageButton) v
				.findViewById(R.id.buttonRetour);

		btnRetour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				FragmentLogin fi = new FragmentLogin();
				ft.replace(R.id.fragmentContainer, fi);
				ft.commit();

			}
		});

		final Button inscriptionButton = (Button) v
				.findViewById(R.id.buttonIscriptionCo);

		inscriptionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// on récupere les infos entrées et on cast en String
				String num = ((EditText) getView().findViewById(R.id.editText1))
						.getText().toString();
				String passwd = ((EditText) getView().findViewById(
						R.id.editText2)).getText().toString();

				// on appelle inscriptionService
				InscriptionService inscriptionService = new InscriptionService();
				try {
					// on recupere le token synonyme d'inscription réussie
					String token = inscriptionService.execute(num, passwd)
							.get();
					if (token != null) {
						new AlertDialog.Builder(getActivity())
								.setTitle("Inscription OK")
								.setMessage("Reussie!").show();

						FragmentManager fm = getFragmentManager();
						FragmentTransaction ft = fm.beginTransaction();
						FragmentLogin fi = new FragmentLogin();
						ft.replace(R.id.fragmentContainer, fi);
						ft.commit();
					} else {
						new AlertDialog.Builder(getActivity())
								.setTitle("Erreur")
								.setMessage("Erreur dans l'inscription").show();
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
