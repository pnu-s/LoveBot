package main.activity;

import com.example.lovebot.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SuccessActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_success);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.success, menu);
		return true;
	}

}
