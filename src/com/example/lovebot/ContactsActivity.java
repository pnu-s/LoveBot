package com.example.lovebot;

import java.util.ArrayList;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		final ListView listview = (ListView) findViewById(R.id.listView1);
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
			list.add(name + " " + phoneNumber);
		}
		phones.close();

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);

		listview.setAdapter(arrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts, menu);
		return true;
	}

}
