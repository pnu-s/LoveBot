package main.fragment;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.concurrent.ExecutionException;

import main.activity.ContactsActivity;
import main.activity.SuccessActivity;
import main.service.LoginService;

import com.example.lovebot.R;

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
		final Button btnConnexion = (Button) v.findViewById(R.id.buttonCo);

		// Fonction qui permet d'accéder au formulaire d'inscription
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

		// Fonction qui permet la connexion à l'application
		btnConnexion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Récupération des infos entrées en String
				String num = ((EditText) getView().findViewById(R.id.login_co))
						.getText().toString();
				String passwd = ((EditText) getView().findViewById(
						R.id.passwd_co)).getText().toString();

				// Chiffrement du mot de passe
				passwd = encryptPassword(passwd);

				// Appel de LoginService
				LoginService loginService = new LoginService();
				try {
					
					// Récupération du token synonyme d'inscription réussie
					String token = (String) loginService.execute(num, passwd)
							.get();
					
					if (token != null) {
						// Si l'utilisateur n'est pas déja dans une relation
						if (!token.equals("1")) {
							Intent intent = new Intent(getActivity(),
									ContactsActivity.class);
							intent.putExtra("key", token);
							startActivity(intent);
							
						//Sinon accès à la page de "succès"
						} else {
							Intent intent2 = new Intent(getActivity(),
									SuccessActivity.class);
							startActivity(intent2);
						}
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

	// Fonction permettant le chiffrement du mdp sur le client
	private static String encryptPassword(String password) {
		String sha1 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(password.getBytes("UTF-8"));
			sha1 = byteToString(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sha1;
	}

	private static String byteToString(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

}
