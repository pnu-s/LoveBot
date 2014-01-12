package main.activity;

import main.fragment.FragmentLogin;
import com.example.lovebot.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Récupération de la ligne de titre
		final LinearLayout title = (LinearLayout) findViewById(R.id.title);
		
		// Fonction affichant les auteurs de l'application
		title.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(v.getContext()).setTitle("Auteurs")
				.setMessage("S. Pruneau & H. Burlini").setPositiveButton("Ok", null).show();
			}
		});
		
		// Démarrage du fragment dédié à la connexion
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		FragmentLogin fl = new FragmentLogin();
		ft.replace(R.id.fragmentContainer, fl);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
