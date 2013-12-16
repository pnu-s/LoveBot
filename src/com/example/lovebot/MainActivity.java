package com.example.lovebot;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final Button btnInscription = (Button) findViewById(R.id.buttonInscription);
		
		//requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.activity_main);
		/*getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.ic_launcher);

		View v = findViewById(android.R.id.title);
		v.setOnClickListener(new OnClickListener() {
			public void onClick(View v2) {
				afficherAuthors(v2);
			}
		});*/

		final Button contactsButton = (Button) findViewById(R.id.buttonCo);
		contactsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						ContactsActivity.class);
				startActivity(intent);
			}
		});
		
		btnInscription.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				FragmentInscription Inscription = new FragmentInscription();
				fragmentTransaction.replace(R.id.fragmentContainer, Inscription, "HELLO");
				fragmentTransaction.commit();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void afficherAuthors(View v) {
		new AlertDialog.Builder(this).setTitle("Auteurs")
				.setMessage("S. Pruneau & H. Burlini").show();
	}
	
	

}
