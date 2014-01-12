package main.fragment;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.concurrent.ExecutionException;
import main.service.InscriptionService;
import com.example.lovebot.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
		final Button btnInscription = (Button) v
				.findViewById(R.id.buttonIscriptionCo);

		// Fonction qui permet de revenir au formulaire de connexion
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

		// Fonction qui permet l'inscription à l'application
		btnInscription.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Récupération des infos entrées en String
				String num = ((EditText) getView().findViewById(R.id.editText1))
						.getText().toString();
				String passwd = ((EditText) getView().findViewById(
						R.id.editText2)).getText().toString();

				// RegEx pour vérifier que le mot de passe respecte le format
				// minimum
				final String regexp = "(?=.*\\d)(?=.*[A-Za-z])[0-9A-Za-z!@#$%].{4,20}";

				if (passwd.matches(regexp)) {
					// Chiffrement du mot de passe
					passwd = encryptPassword(passwd);

					// Appel d'inscriptionService
					InscriptionService inscriptionService = new InscriptionService();
					try {
						// Récupération du token synonyme d'inscription réussie
						String token = inscriptionService.execute(num, passwd)
								.get();

						if (token != null) {
							new AlertDialog.Builder(getActivity())
									.setMessage(
											"Tu es bien inscrit. Tu peux désormais te connecter !")
									.setPositiveButton("Ok", null).show();

							// Retour à la page de connexion après l'inscription
							FragmentManager fm = getFragmentManager();
							FragmentTransaction ft = fm.beginTransaction();
							FragmentLogin fi = new FragmentLogin();
							ft.replace(R.id.fragmentContainer, fi);
							ft.commit();
						} else {
							new AlertDialog.Builder(getActivity()).setMessage(
									"Identifiant déjà utilisé!").show();
						}

					} catch (InterruptedException interruptedException) {
						Log.e("log_tag",
								"interrupted "
										+ interruptedException.toString());
					} catch (ExecutionException executionException) {
						Log.e("log_tag",
								"execution" + executionException.toString());
					} catch (NullPointerException nullPointerException) {
						Log.e("log_tag",
								"nullpointer "
										+ nullPointerException.toString());
					}
				} else {
					new AlertDialog.Builder(getActivity())
							.setMessage(
									"Mot de passe de 5 caractères minimum dont au moins 1 lettre et 1 chiffre")
							.show();
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
