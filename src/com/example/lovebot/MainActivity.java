package com.example.lovebot;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.activity_main);
	    getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,R.drawable.ic_launcher);
		View v = findViewById(android.R.id.title);
		v.setOnClickListener(new OnClickListener() {
			public void onClick(View v2) {
				afficherAuthors(v2);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		setTitle("Nouveau Titre");
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		//R.layout.title);
		return true;
	}
	
	public void afficherAuthors(View v) {
		new AlertDialog.Builder(this).setTitle("Auteurs")
				.setMessage("S. Pruneau & H. Burlini").show();
	}

}
