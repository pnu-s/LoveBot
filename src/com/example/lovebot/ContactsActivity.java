package com.example.lovebot;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class ContactsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		final ImageView coeur = (ImageView) findViewById(R.id.imageView1);
		coeur.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(v.getContext()).setTitle("Auteurs")
				.setMessage("S. Pruneau & H. Burlini").show();
			}
		});

		TelephonyManager mTelephonyMgr;  
		mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		final String yourNumber = mTelephonyMgr.getLine1Number();  
		//new AlertDialog.Builder(this).setTitle("Amour OK").setMessage("Reussi! " + yourNumber).show();

		final ListView lv = (ListView) findViewById(R.id.listView1);
		final ArrayList<String> list = new ArrayList<String>();

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

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);

		lv.setAdapter(arrayAdapter);
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				/*
				 * AlertDialog.Builder adb = new AlertDialog.Builder(lv
				 * .getContext()); adb.setTitle("ListView OnClick");
				 * adb.setMessage("Selected Item is = " +
				 * lv.getItemAtPosition(position) + " and "+ id);
				 * adb.setPositiveButton("Ok", null); adb.show();
				 */
				String user1 = yourNumber;
				String[] user2 = lv.getItemAtPosition(position).toString().split("\\|");

				// on appelle inscriptionService
				AmourService amourService = new AmourService();
				try {
					// on recupere le token synonyme de liaison réussie
					String token = amourService.execute(user1, user2[1]).get();
					if (token != null) {
						new AlertDialog.Builder(v.getContext())
								.setTitle("Amour OK")
								.setMessage("Reussi! " + token).show();
					} else {
						new AlertDialog.Builder(v.getContext())
								.setTitle("Erreur")
								.setMessage("Erreur :" + token + ": fz "+user2[1]).show();
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts, menu);
		return true;
	}

}
