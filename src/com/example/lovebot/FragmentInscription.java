package com.example.lovebot;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
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

				//if(preg_match('/^(?=.*\d)(?=.*[A-Za-z])[0-9A-Za-z!@#$%]{5,20}$/', $parameters[":password"]))
				final String regexp = "(?=.*\\d)(?=.*[A-Za-z])[0-9A-Za-z!@#$%].{5,20}";
		        if (passwd.matches(regexp)) {
					passwd=encryptPassword(passwd);
			        				// on appelle inscriptionService
					InscriptionService inscriptionService = new InscriptionService();
					try {
						// on recupere le token synonyme d'inscription réussie
						String token = inscriptionService.execute(num, passwd)
								.get();
						if (token != null) {
							new AlertDialog.Builder(getActivity())
									.setMessage("Tu es bien inscrit. Tu peux désormais te connecter.")
									.setPositiveButton("Ok", null).show();
	
							FragmentManager fm = getFragmentManager();
							FragmentTransaction ft = fm.beginTransaction();
							FragmentLogin fi = new FragmentLogin();
							ft.replace(R.id.fragmentContainer, fi);
							ft.commit();
						} else {
							System.out.println(passwd);
							new AlertDialog.Builder(getActivity())
									.setMessage("Login et/ou Mdp incorrect.").show();
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
		        else {
		        	new AlertDialog.Builder(getActivity())
					.setMessage("MdP doit avoir au moins 1 chiffre, et un caractère alphabétique et fasse au moins 5 caractères.").show();
		        }
			}
		});

		return v;
	}
	private static String encryptPassword(String password)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}

	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
}
