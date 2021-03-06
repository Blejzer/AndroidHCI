package ba.fit.androidhci;

import ba.fit.androidhci.util.TextViewEx;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoadingListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class DetailActivity extends Activity {
	
	private DisplayImageOptions options;
	private ImageLoader imageLoader;

	private String pid;
	private ProgressBar pbar;
	private TextView tvRegPlates;
	private TextViewEx tvDescription;
	private ImageView imgView;
	private Button editVehicle;
	private Bundle b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_detail);
		
		pbar = (ProgressBar) findViewById(R.id.pbardesc);
		tvRegPlates = (TextView) findViewById(R.id.tvregplate);
		tvDescription = (TextViewEx) findViewById(R.id.tvdescription);
		imgView = (ImageView) findViewById(R.id.imgdesc);
		editVehicle = (Button) findViewById(R.id.editVehicleBtn);

		b = getIntent().getExtras();

		String regplate = b.getString("regplate");
		String description = b.getString("description");
		pid = b.getString("pid");

		tvRegPlates.setText(regplate);
		tvDescription.setText(description, true);

		String url = b.getString("link");
		loadImageFromURL(url);
		
		editVehicle.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DetailActivity.this, EditActivity.class);
				intent.putExtra("link", b.getString("link"));
				intent.putExtra("regplate", b.getString("regplate"));
				intent.putExtra("description", b.getString("description"));
				intent.putExtra("pid", pid);
				startActivity(intent);
			}
		});

		
	}

	private void loadImageFromURL(String url) {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.profile)
				.showImageForEmptyUrl(R.drawable.profile).cacheInMemory()
				.cacheOnDisc().build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(url, imgView, options,
				new ImageLoadingListener() {
					@Override
					public void onLoadingComplete() {
						pbar.setVisibility(View.INVISIBLE);

					}

					@Override
					public void onLoadingFailed() {

						pbar.setVisibility(View.INVISIBLE);
					}

					@Override
					public void onLoadingStarted() {
						pbar.setVisibility(View.VISIBLE);
					}
				});

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
