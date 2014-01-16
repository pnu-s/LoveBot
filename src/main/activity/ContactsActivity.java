package main.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import main.service.AmourService;

import com.example.lovebot.R;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ContactsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		// Récupération du numéro de l'utilisateur
		Intent intent = getIntent();
		final String number = intent.getStringExtra("key");

		final ListView lv = (ListView) findViewById(R.id.listView1);
		final ArrayList<String> list = new ArrayList<String>();

		// Récupération de la ligne de titre
		final LinearLayout title = (LinearLayout) findViewById(R.id.title);

		// Fonction affichant le nom des auteurs
		title.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(v.getContext()).setTitle("Auteurs")
						.setMessage("S. Pruneau & H. Burlini")
						.setPositiveButton("Ok", null).show();
			}
		});

		// Récupération des contacts de l'utilisateur et remplissage de
		// l'ArrayList
		Cursor phones = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {
			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			list.add(name + " | " + phoneNumber);
		}
		phones.close();

		// Tri de la liste
		Collections.sort(list);
		
		// Adaptation de l'ArrayList à la ListView
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		lv.setAdapter(arrayAdapter);
		lv.setTextFilterEnabled(true);

		// Fonction permettant de choisir un contact
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, final View v,
					int position, long id) {

				final String user1 = number;
				final String[] user2 = lv.getItemAtPosition(position)
						.toString().split("\\|");

				// "Nettoyage" du numéro de téléphone de la cible
				user2[1] = user2[1].replace("+33", "").replace(" ", "")
						.replace(".", "").replace("-", "");

				// Demande de confirmation
				new AlertDialog.Builder(v.getContext())
						.setMessage("Es-tu sûr(e) que c'est l'amour de ta vie?")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										// Appel d'amourService
										AmourService amourService = new AmourService();
										try {

											// Récupération du token
											String token = amourService
													.execute(user1, user2[1])
													.get();
											
											String msg = "Default";
											if (token != null) {
												msg = token;
											} else {
												msg = "Problème lors du choix du contact.";
											}

											Toast toast = Toast.makeText(
													v.getContext(), msg,
													Toast.LENGTH_LONG);
											toast.show();
										} catch (InterruptedException interruptedException) {
											Log.e("log_tag",
													"interrupted "
															+ interruptedException
																	.toString());
										} catch (ExecutionException executionException) {
											Log.e("log_tag",
													"execution"
															+ executionException
																	.toString());
										} catch (NullPointerException nullPointerException) {
											Log.e("log_tag",
													"nullpointer "
															+ nullPointerException
																	.toString());
										}
									}
								}).setNegativeButton("Non", null).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.contacts, menu);
		return true;
	}

}
