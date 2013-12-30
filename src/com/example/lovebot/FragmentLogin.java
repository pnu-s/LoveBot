package com.example.lovebot;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentLogin extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.activity_fragment_login, container,
				false);
		final Button btnInscription = (Button) v
				.findViewById(R.id.buttonInscription);

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

		final Button contactsButton = (Button) v.findViewById(R.id.buttonCo);

		contactsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						ContactsActivity.class);
				startActivity(intent);
			}
		});

		return v;
	}
}