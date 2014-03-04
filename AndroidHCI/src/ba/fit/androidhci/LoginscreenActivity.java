package ba.fit.androidhci;

import ba.fit.androidhci.util.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginscreenActivity extends Activity implements OnClickListener{

	JSONParser jsonParser = new JSONParser();
	
	User checkedUser;

	EditText inputUserName, inputPassword;
	private Button loginButton;
	private TextView message;
	private ProgressDialog dialog = null;

	// url to create new product
	private static String url_for_login = "http://www.tabletzasvakog.com/android_fit/get_user_details.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_loginscreen);
		
		loginButton = (Button)findViewById(R.id.loginButton);
		inputUserName = (EditText) findViewById(R.id.inputUserName);
		inputPassword = (EditText) findViewById(R.id.inputPassword);
		
		message = (TextView) findViewById(R.id.tvMessage);
		
		if(getIntent().hasExtra("msg")){
			Bundle extras = getIntent().getExtras();
			message.setText(extras.getString("msg"));
		} else{
			message.setText("");
		}
		
		
		
		loginButton.setOnClickListener(this);
		
		if (!Utils.isNetworkAvailable(LoginscreenActivity.this)) {
			showToast("Nema mrezne konekcije!!!");
		} 
	}
	
	@Override
	public void onClick(View arg0) {
if (arg0==loginButton) {
			
			if(inputUserName.getText().toString().compareTo("") == 0 || inputPassword.getText().toString().compareTo("") == 0) {
			     // Your piece of code for example
			     Toast toast=Toast.makeText(getApplicationContext(), "Polja 'UserName' i 'Password' ne mogu biti prazna!", Toast.LENGTH_SHORT);  
			     toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
			     toast.show();
			 } else {
				 
				 // ovdje pozivam  mytask class
				 new MyTask().execute(url_for_login+"?username="+inputUserName.getText()+"&password="+inputPassword.getText());
			 }
		}
	}
	
	public void showToast(String msg) {

	}
	
	// My AsyncTask start...

	class MyTask extends AsyncTask<String, Void, Void> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(LoginscreenActivity.this);
			pDialog.setMessage("Provjeravam...");
			pDialog.show();

		}

		@Override
		protected Void doInBackground(String... params) {
			checkedUser = new NamesParser().getUserData(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == checkedUser.getUid()) {
				
				Context context = getApplicationContext();
				CharSequence text = "Unijeli ste pogresan user name i/ili password!";
				int duration = Toast.LENGTH_LONG;

				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
				
				String s = "Unijeli ste neispravne podatke!!!";

				Intent i = new Intent(getApplicationContext(),
						LoginscreenActivity.class);
				i.putExtra("msg", s);
				// Closing all previous activities
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			} else {
				Context context = getApplicationContext();
				CharSequence text = "Login uspjesan!" + " \n Dobrodosao, "+checkedUser.getUsername();
				int duration = Toast.LENGTH_LONG;

				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
				
				Intent i = new Intent(LoginscreenActivity.this,
						MenuScreenActivity.class);
				// Closing all previous activities
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		}
	}
	
	
}
