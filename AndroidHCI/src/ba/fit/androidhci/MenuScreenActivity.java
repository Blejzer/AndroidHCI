package ba.fit.androidhci;

import ba.fit.androidhci.util.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuScreenActivity extends Activity {

	private Button searchButton, allVehiclesButton, newVehicleButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_menu_screen);

		searchButton = (Button) findViewById(R.id.buttonSearchVehicles);
		allVehiclesButton = (Button) findViewById(R.id.buttonAllVehicles);
		newVehicleButton = (Button) findViewById(R.id.buttonNewVehicle);
		
		if (!Utils.isNetworkAvailable(MenuScreenActivity.this)) {
			showToast("Nema mrezne konekcije!!!");
		} 

		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent ii = new Intent(getApplicationContext(),
						SearchActivity.class);
				// Closing all previous activities
				ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(ii);
			}
		});

		allVehiclesButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent ii = new Intent(getApplicationContext(),
						MainScreenActivity.class);
				// Closing all previous activities
				ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(ii);
			}
		});

		newVehicleButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						NewVehicleActivity.class);
				// Closing all previous activities
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	}
	
	public void showToast(String msg) {

	}
}
