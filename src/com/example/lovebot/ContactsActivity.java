package com.example.lovebot;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
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

		Intent intent = getIntent();
		final String number = intent.getStringExtra("key");
		
		final ImageView coeur = (ImageView) findViewById(R.id.imageView1);
		coeur.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(v.getContext()).setTitle("Auteurs")
						.setMessage("S. Pruneau & H. Burlini")
						.setPositiveButton("Ok", null).show();
			}
		});

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
				String user1 = number;
				String[] user2 = lv.getItemAtPosition(position).toString()
						.split("\\|");

				user2[1] = user2[1].replace("+33", "").replace(" ", "")
						.replace(".", "").replace("-","");

				// on appelle amourService
				AmourService amourService = new AmourService();
				try {
					// on recupere le token synonyme de liaison réussie
					String token = amourService.execute(user1, user2[1]).get();
					AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
					
					if (token != null) {
						adb.setMessage(token);
					} else {
						adb.setMessage("Problème lors du choix du contact.");
					}
					
					adb.setPositiveButton("Ok", null).show();
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
