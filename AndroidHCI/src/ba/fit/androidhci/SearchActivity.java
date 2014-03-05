package ba.fit.androidhci;

import ba.fit.androidhci.util.SystemUiHider;
import ba.fit.androidhci.util.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class SearchActivity extends Activity  implements OnClickListener{

	JSONParser jsonParser = new JSONParser();
	EditText inputSearchTerm;
	private Button searchButton;
	private TextView message;
	private ProgressDialog dialog = null;

	// url to create new product
	private static String url_search_vehicles = "http://www.tabletzasvakog.com/android_fit/get_custom_products.php?regPlate=";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);
		
		searchButton = (Button)findViewById(R.id.searchButton);
		inputSearchTerm = (EditText) findViewById(R.id.inputSearchTerm);
		message = (TextView) findViewById(R.id.tvMessage);
		
		message.setText("Unesite cijeli ili parcijalni broj registarskih oznaka.");
		
		searchButton.setOnClickListener(this);
		
		if (!Utils.isNetworkAvailable(SearchActivity.this)) {
			showToast("Nema mrezne konekcije!!!");
		} 
	}
	
	@Override
	public void onClick(View arg0) {
if (arg0==searchButton) {
			
			if(inputSearchTerm.getText().toString().compareTo("") == 0) {
			     // Your piece of code for example
			     Toast toast=Toast.makeText(getApplicationContext(), "Polje za pretrazivanje ne moze biti prazno!", Toast.LENGTH_SHORT);  
			     toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
			     toast.show();
			 } else {
			dialog = ProgressDialog.show(SearchActivity.this, "", "Pretrazujem bazu...", true);
			message.setText("Pretrazivanje je zapocelo.....");
			//Ovdje dodajem kod za pretrazivanje vozila u bazi
			Intent i = new Intent(SearchActivity.this,
					MainScreenActivity.class);
			i.putExtra("link", url_search_vehicles+inputSearchTerm.getText().toString());
			Log.d("link", i.getExtras().getString("link"));
			// Closing all previous activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			dialog.dismiss();
			message.setText("Izbrisite stari i unesite novi pojam za pretragu.");
			// kraj dodanog koda


			 }
		}
	}
	
	public void showToast(String msg) {

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.newvehicle:
			//define the file-name to save photo taken by Camera activity
			Intent i = new Intent(getApplicationContext(),
					NewVehicleActivity.class);
			// Closing all previous activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			return true;

		case R.id.gallery:
			Intent ii = new Intent(getApplicationContext(),
					MainScreenActivity.class);
			// Closing all previous activities
			ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(ii);
			return true;
		case R.id.search:
			Intent iii = new Intent(getApplicationContext(),
					SearchActivity.class);
			// Closing all previous activities
			iii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(iii);
			return true;
		case R.id.main:
			Intent iv = new Intent(getApplicationContext(),
					MenuScreenActivity.class);
			// Closing all previous activities
			iv.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(iv);
			return true;
		}
		return false;
	}



}
