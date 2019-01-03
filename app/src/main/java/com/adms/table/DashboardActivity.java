package com.adms.table;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.adms.table.utility.SingleOptionAlertWithoutTitle;
import com.adms.table.utility.TT_StaticClass;
import com.adms.table.utility.Tide_Table_Async;
import com.adms.table.utility.Tide_Table_Response_Interface;
import com.adms.table.utility.Tide_Table_Utility;


public class DashboardActivity extends Activity implements Tide_Table_Response_Interface{
	String app_version = "", os = "", os_version = "", udid = "",
			manufacture = "", device_model = "", serial_no = "";
	private int width;
	private int height;
	Boolean isInternetPresent = false;
	private JSONObject jObect;
	private String json_status;
	ArrayList<String> locid,locname;
	ListView lv_location_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_dashboard);
		new Tide_Table_Utility();
		isInternetPresent = Tide_Table_Utility.isNetworkConnected(DashboardActivity.this);
		jObect = new JSONObject();
		// Prepare the Interstitial Ad
//        interstitial = new InterstitialAd(DashboardActivity.this);
//        // Insert the Ad Unit ID
//        interstitial.setAdUnitId("ca-app-pub-2542422396039496/2621447168");
//
//        //Locate the Banner Ad in activity_main.xml
//        AdView adView = (AdView) this.findViewById(R.id.adView_dashboard);
////        adView.setAdSize(AdSize.SMART_BANNER);
//        // Request for Ads
//        AdRequest adRequest = new AdRequest.Builder()
//
//        // Add a test device to show Test Ads
////        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
////        .addTestDevice("")
//               .build();
//        // Load ads into Banner Ads
//        adView.loadAd(adRequest);
//
//     // Load ads into Interstitial Ads
//        interstitial.loadAd(adRequest);
//
//     // Prepare an Interstitial Ad Listener
//        interstitial.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                // Call displayInterstitial() function
//                displayInterstitial();
//            }
//        });
        Button ib_back = (Button)findViewById(R.id.ib_back);
        ib_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		lv_location_name =(ListView)findViewById(R.id.dashboard_location);
		
		StringBuilder builder = new StringBuilder();
		builder.append("android : ").append(Build.VERSION.RELEASE);

		Field[] fields = Build.VERSION_CODES.class.getFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			int fieldValue = -1;

			try {
				fieldValue = field.getInt(new Object());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			if (fieldValue == Build.VERSION.SDK_INT) {
				builder.append(" : ").append(fieldName).append(" : ");
				builder.append("sdk=").append(fieldValue);
			}
		}
		Log.e("ANDROID:::::", "OS: " + builder.toString());
		Log.e("Build.VERSION:::::", "" + Build.VERSION.RELEASE);
		Log.e("MANUFACTURERE:::::", "" + Build.MANUFACTURER);
		Log.e("MODEL:::::", "" + Build.MODEL);
		Log.e("PRODUCT:::::", "" + Build.BRAND);
		os = "Android";
		os_version = "" + Build.VERSION.RELEASE;
		manufacture = "" + Build.MANUFACTURER;
		device_model = "" + Build.MODEL;
		serial_no = "" + Build.SERIAL;

		try {
			PackageInfo pInfo = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			app_version = pInfo.versionName;
			Log.e("versionName:::::", "OS: " + pInfo.versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		Log.i("DEVISEEEE    HEIGHT", "" + height);
		Log.i("DEVISEEEE    Width ", "" + width);
		
	}

	private void POSTPARAM() {
		// TODO Auto-generated method stub
		if(Tide_Table_Utility.isNetworkConnected(getApplicationContext())){
		try{
			//jObect.put("check", 1);


			new Tide_Table_Async(DashboardActivity.this,3, jObect, "LIST of LOCATION",
				(Tide_Table_Response_Interface) DashboardActivity.this,null)
				.execute();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		}
		else{
			SingleOptionAlertWithoutTitle.ShowAlertDialog(DashboardActivity.this, "Error", "No internet access", "Ok");
		}
	}


	
	public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
//        if (interstitial.isLoaded()) {
//            interstitial.show();
//        }
    }

	@Override
	public void Get_Response(String REsponse) {
		// TODO Auto-generated method stub
		Log.e("RESPONSE","RESPONSE = "+REsponse);
		JSONObject reader = null;

//		{
//			"Success": "True",
//				"Data": [
//			{
//				"Location_ID": "1",
//					"LocationName": "Bhavnagar Port"
//			}
//  ]
//		}

		try {
			reader = new JSONObject(REsponse);


			//json_status = reader.getString("status");
			json_status = reader.getString("Success");


				
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		Log.i("Status", json_status);
		if (json_status.equalsIgnoreCase("True")) {
			JSONObject job1;
			JSONArray jArray;
			try {
				//jArray = reader.getJSONArray("location_records");
				jArray = reader.getJSONArray("Data");


				Log.e("JARRAY", "JARRAY = " + jArray);
				locid = new ArrayList<>();
				locname = new ArrayList<>();
				for(int i=0;i<jArray.length();i++){
					job1 = jArray.getJSONObject(i);
					Log.e("Dahsboard", "Loc id =" + job1.getString("Location_ID"));
					Log.e("Dashboard", "Loc name=" + job1.getString("LocationName"));
					locid.add(job1.getString("Location_ID"));
					locname.add(job1.getString("LocationName"));
				}
				lv_location_name.setAdapter(new LocationGetter(getApplicationContext(), locname));	
				lv_location_name.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
//						Toast.makeText(DashboardActivity.this, locname.get(position).toString(), 1).show();
						TT_StaticClass.location_name = locname.get(position).toString();
						TT_StaticClass.location_id = locid.get(position).toString();
						Intent it = new Intent(DashboardActivity.this, CalenderView.class);
						startActivity(it);
						finish();
					}
				});
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else if(json_status.equalsIgnoreCase("2")){
			try {
				SingleOptionAlertWithoutTitle.ShowAlertDialog(DashboardActivity.this,
						"Error", reader.getString("error_msg"), "OK");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else if(json_status.equalsIgnoreCase("False")){
			try {
				SingleOptionAlertWithoutTitle.ShowAlertDialog(DashboardActivity.this,
						"Error", reader.getString("error_msg"), "OK");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			SingleOptionAlertWithoutTitle.ShowAlertDialog(DashboardActivity.this,
					"Error", "Check internet connection and retry with valid data.", "OK");
		}


//		else if(json_status.equalsIgnoreCase("3")){
//			try {
//				SingleOptionAlertWithoutTitle.ShowAlertDialog(DashboardActivity.this,
//						"Error", reader.getString("error_msg"), "OK");
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//		else if(json_status.equalsIgnoreCase("0")){
//			try {
//				SingleOptionAlertWithoutTitle.ShowAlertDialog(DashboardActivity.this,
//						"Error", reader.getString("error_msg"), "OK");
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}

	}

	@Override
	public void SetDialohResponse(boolean b) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isInternetPresent){
			POSTPARAM();
		}
		else{
			SingleOptionAlertWithoutTitle.ShowAlertDialog(DashboardActivity.this, "Error", "No internet access", "Ok");
		}
	}
}
