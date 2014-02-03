package ba.fit.androidhci;

import java.util.List;

import ba.fit.androidhci.util.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class MainScreenActivity extends Activity implements OnItemClickListener {
	private static final String feed = "http://www.tabletzasvakog.com/android_fit/get_all_products.php";

	List<Item> arrayOfList;
	ListView listView;
	private String search_url = "";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main_screen);

		listView = (ListView) findViewById(R.id.listview);
		listView.setOnItemClickListener(this);
		if(getIntent().hasExtra("link")){
			Bundle extras = getIntent().getExtras();
			search_url = extras.getString("link");
		} else{
			search_url = feed;
		}
		Log.d("link", search_url);
		if (Utils.isNetworkAvailable(MainScreenActivity.this)) {
			new MyTask().execute(search_url);
		} else {
			showToast("Nema mrezne konekcije!!!");
		}
		
	}

	
	// My AsyncTask start...

	class MyTask extends AsyncTask<String, Void, Void> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(MainScreenActivity.this);
			pDialog.setMessage("Ucitavam...");
			pDialog.show();

		}

		@Override
		protected Void doInBackground(String... params) {
			arrayOfList = new NamesParser().getData(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == arrayOfList || arrayOfList.size() == 0) {
				showToast("Nema podataka sa weba!!!");
				Intent i = new Intent(getApplicationContext(),
						MenuScreenActivity.class);
				// Closing all previous activities
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			} else {
				setAdapterToListview();
			}
		}
	}

	public void setAdapterToListview() {
		NewsRowAdapter objAdapter = new NewsRowAdapter(MainScreenActivity.this,
				R.layout.row, arrayOfList);
		listView.setAdapter(objAdapter);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Item item = arrayOfList.get(position);
		Intent intent = new Intent(MainScreenActivity.this, DetailActivity.class);
		intent.putExtra("link", item.getLink());
		intent.putExtra("regplate", item.getRegPlate());
		intent.putExtra("description", item.getDescription());
		startActivity(intent);
		
	}
}
