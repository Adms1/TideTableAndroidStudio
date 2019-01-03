package com.adms.table.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
//import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;

@SuppressWarnings("unused")
public class Tide_Table_Utility {

	static Tide_Table_Response_Interface interfacce;
//	Interfacee interfacee;
	public Dialog dia;
	 private DatePicker date_picker;
	 private static TimePicker time_picker;
	  static final int DATE_DIALOG_ID = 100;
	     //static android.support.v7.widget.PopupMenu popmenu;
	private static String[] name;
	     private static int min=0;
	     private static int hrs=0;
	     private static int sec=00;
	     private Calendar cal;
	     private int day;
	     private int month;
	     private int year;
	 	static Dialog picker;
	 	static Button set;
	 	static DatePicker datep;
	static JSONObject json;
	// static QuickAction quickAction;
	private String ActioItem;
	private static String imei;
	private static JSONObject json_LL;
	static Context Cntxt;
	public static Bitmap photo=null;

	public static int Login_Success=0;

	public static boolean LogOut_Status=false;

	private static String RES_msg;

	protected static boolean Editext_space_validation=true;
	
	
	
	public static void CreateToastMessage(Context activity, String string) {
		// TODO Auto-generated method stub
//		Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
		Toast toast = Toast.makeText(activity, string, Toast.LENGTH_SHORT);
		  toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		  toast.show();
	}

//-------------------- Checking Internet Connection -------------------------------
	public static boolean isNetworkConnected(Context ctxt) {
		InternetDetector cd = new InternetDetector(ctxt);
		Boolean isInternetPresent = cd.isConnectingToInternet();

		return isInternetPresent;
	}
	
/**---------------------- Shared Prefrences -----------------------------------------**/
	public static void SharePrefrences(Context context, String Value,
			String Key_Value) {
		SharedPreferences pref = context.getSharedPreferences("My Prefrences",
				0);
		pref.edit().putString(Key_Value, Value).commit();
	}

	public static String Get_Prefrance_Value(Context context, String Key_Value) {
		SharedPreferences pref = context.getSharedPreferences("My Prefrences",
				0);
		String Result_Val = pref.getString(Key_Value, null);

		return Result_Val;

	}
	
	public static void Delete_Prefrance_Value(Context context, String Key_Value) {
		SharedPreferences pref = context.getSharedPreferences("My Prefrences",
				0);
		pref.edit().remove(Key_Value).commit();

	}

/**--------------------------------------------- Yes / No Alert Dialog -----------------------------------------**/
	
	
	public static void AlerDialg(final Context user_List, String aLERT_TITLE,
			String aLERT_MESSAGE, Tide_Table_Response_Interface intrface ) {
		// TODO Auto-generated method stub
		
		interfacce=intrface;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				user_List);

		// set title
		alertDialogBuilder.setTitle(aLERT_TITLE);

		// set dialog message
		alertDialogBuilder
				.setMessage(aLERT_MESSAGE)
				.setCancelable(false)
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity
								
//								new Async(user_List, "DELETE_USER", 3)
//										.execute();

								interfacce.SetDialohResponse(true);
							}
						})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
					//	dialog.cancel();
						interfacce.SetDialohResponse(false);
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	public static void AlerDialg2(final Context user_List, String aLERT_TITLE,
			String aLERT_MESSAGE, Tide_Table_Response_Interface intrface ) {
		// TODO Auto-generated method stub
		
		interfacce=intrface;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				user_List);

		// set title
		alertDialogBuilder.setTitle(aLERT_TITLE);

		// set dialog message
		alertDialogBuilder
				.setMessage(aLERT_MESSAGE)
				.setCancelable(false)
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity
								
//								new Async(user_List, "DELETE_USER", 3)
//										.execute();

								interfacce.SetDialohResponse(true);
							}
						});
				

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	public static String Imei_Number(Context Cntxt){
		TelephonyManager tm = (TelephonyManager) Cntxt.getSystemService(Context.TELEPHONY_SERVICE);
        // get IMEI
           imei= tm.getDeviceId();
       	Log.v("imei",""+imei);
		return imei;
	}
	//public static void QuickActionApi(ArrayList<String> quickActionchList,
//			Tracking cntxt, View v, final String string) {
//		// TODO Auto-generated method stub
//		Cntxt = cntxt;
//	 
//		quickAction = new QuickAction(Cntxt);
//		//ActioItem = string;
//		
//		//quickAction = new QuickAction2(fragmentActivity);
//		
//		
//		//Log.i("STRING FROM BEHIND", "" + ActioItem);
//		for (int i = 0; i < quickActionchList.size(); i++) {
//			ActionItem nextItem = new ActionItem(i, quickActionchList.get(i));
//			quickAction.addActionItem(nextItem, i, quickActionchList.size());
//
//		}
//
//		quickAction.show(v);
//		quickAction
//				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
//
//					private String val;
//
//					public void onItemClick(QuickAction source, int pos,
//							int actionId) {
//						if(string.equals("TRACKING")){
//							Staticclass.U_ID=Staticclass.USER_ID_LIST.get(pos);
//							Staticclass.edit.setText(Staticclass.USER_NAME_LIST.get(pos));
//						}
//					}
//				});
//	}
//	public static void DatePicker(final Tracking tracking) {
//		// TODO Auto-generated method stub
//		picker = new Dialog(tracking);
//		picker.setContentView(R.layout.picker_frag);
//		picker.setTitle("Select Date and Time");
//
//		datep = (DatePicker) picker.findViewById(R.id.datePicker);
//		time_picker = (TimePicker) picker.findViewById(R.id.timePicker1);
//		// timep = (TimePicker)picker.findViewById(R.id.timePicker1);
//		set = (Button) picker.findViewById(R.id.btnSet);
//
//		set.setOnClickListener(new View.OnClickListener() {
//
//			private Date date;
//
//			@SuppressLint("SimpleDateFormat")
//			@Override
//			public void onClick(View view) {
//				// TODO Auto-generated method stub
//				//Log.v("DATE COUNT IS ", "" + COUNT);
//				month = datep.getMonth() + 1;
//				day = datep.getDayOfMonth();
//				year = datep.getYear();
//				
//				min=time_picker.getCurrentMinute();
//				hrs=time_picker.getCurrentHour();
//				if(hrs==0 && min==0){
//					Common_Utilities.CreateToastMessage(tracking, "PLEASE SELECT THE TIME");
//				}else{
//					
//					Staticclass.edit.setText(year+"-"+month+"-"+day+" "+hrs+":"+min+":"+sec);
//				}
//				//sec=time_picker.getCurr;
//				
//				// hour = timep.getCurrentHour();
//				// minute = timep.getCurrentMinute();
//				// time.setText("Time is "+hour+":" +minute);
//				//if (COUNT == 1) {
//					//SDate.setText(day + "/" + month + "/" + year);
//					SimpleDateFormat dateConverter = new SimpleDateFormat(
//							"dd/MM/yyyy");
//					//						date = (Date) dateConverter.parse(SDate.getText()
////								.toString());
//					// date1 = (Date) dateConverter.parse(EDate);
//					//StaticClass.SDate = date;
//					Log.i("DATE", "" + date);
//					//StaticClass.SetSdate(date);
//			//	}
//					//else if (COUNT == 2) {
////					EDate.setText(day + "/" + month + "/" + year);
////					SimpleDateFormat dateConverter = new SimpleDateFormat(
////							"dd/MM/yyyy");
////					try {
////						date = (Date) dateConverter.parse(EDate.getText()
////								.toString());
////						StaticClass.EDate = date;
////						// date1 = (Date) dateConverter.parse(EDate);
////						Log.i("EDATE", "" + date);
////
////					} catch (ParseException e1) {
////						// TODO Auto-generated catch block
////						e1.printStackTrace();
////					}
////					StaticClass.SetEdate(date);
////					// StaticClass.SetEdate(EDate.getText().toString());
////				}
//				picker.dismiss();
//			}
//		});
//		picker.show();
//
//	}

//	public static void GetLocation(Context cntxt2) {
//		// TODO Auto-generated method stub
//		GPSTracker Gps=new GPSTracker(Cntxt);
//		Log.v("LATITUDEEEEEEEEEEEEEE", ""+Gps.getLatitude());
//	}
/**----------------------------------------Date time-------------------------------------------------------**/
	
/**--------------------------------------------- Email Validation -----------------------------------------**/
	public static boolean isEmailValid(String email) {
		Log.e("CHK IF EMAIL IS VALID", ""+email);
		boolean isValid;
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		} else {

			Log.i(".....Email", "Not valid");

			isValid = false;

		}
		return isValid;
	}

/**--------------------------------------------- Hit Server code -----------------------------------------**/
	public String Hit_Server(JSONObject mJsonToken, String string) {
		String status = "";

		HttpGet httppost = null;
		try {
			httppost = new HttpGet(string);
			HttpParams myParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(myParams, 10000);
			HttpConnectionParams.setSoTimeout(myParams, 30 * 10000);
			httppost.setHeader("Content-type","application/json");
			HttpClient httpclient = new DefaultHttpClient(myParams);
			// JSONObject jsonObject = new JSONObject();
			// jsonObject.put("body_dict", mJsonToken);
			// StringEntity se;
			// if (jsonObject.toString() == null) {
			// se = new StringEntity("", HTTP.UTF_8);
			// } else {
			// se = new StringEntity(jsonObject.toString(), HTTP.UTF_8);
			// }
			StringEntity se;
			if (mJsonToken.toString() == null) {
				se = new StringEntity("", HTTP.UTF_8);
			} else {
				//se = new StringEntity(mJsonToken.toString(), HTTP.UTF_8);
				//se = new StringEntity(mJsonToken.toString(), HTTP.UTF_8);

			}
//			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
//					"application/json"));
//			httppost.setEntity(se);
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String getResult = EntityUtils.toString(entity);
//			Log.e("ffffffffffffffffffffffffff", ""+getResult);
			status = getResult;
		}
		catch (Exception e) {
			e.printStackTrace();

		} finally {
		}
		return status;

	}
	public String Hit_Server(JSONObject mJsonToken, String string, int methodType,HashMap<String,String> map) {
		//for post request.
		String status = "";

		if(methodType == 1) {



			HttpPost httppost = null;
			try {
				httppost = new HttpPost(string);
				HttpParams myParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(myParams, 10000);
				HttpConnectionParams.setSoTimeout(myParams, 30 * 10000);
				httppost.setHeader("Content-type", "application/json");
				HttpClient httpclient = new DefaultHttpClient(myParams);
				// JSONObject jsonObject = new JSONObject();
				// jsonObject.put("body_dict", mJsonToken);
				// StringEntity se;
				// if (jsonObject.toString() == null) {
				// se = new StringEntity("", HTTP.UTF_8);
				// } else {
				// se = new StringEntity(jsonObject.toString(), HTTP.UTF_8);
				// }

				StringBuilder postData = new StringBuilder();
				for (Map.Entry<String,String> param : map.entrySet()) {
					if (postData.length() != 0) postData.append('&');
					postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
					postData.append('=');
					postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
				}
				byte[] postDataBytes = postData.toString().getBytes("UTF-8");


				StringEntity se;
				if (mJsonToken.toString() == null) {
					se = new StringEntity("", HTTP.UTF_8);
				} else {
					//se = new StringEntity(postData.toString(), HTTP.UTF_8);
					se = new StringEntity(mJsonToken.toString(), HTTP.UTF_8);

				}
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				//se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded"));



				httppost.setEntity(se);
				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				String getResult = EntityUtils.toString(entity);
//			Log.e("ffffffffffffffffffffffffff", ""+getResult);
				status = getResult;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
			}
		}else if(methodType ==0){
			HttpGet httppost = null;
			try {
				httppost = new HttpGet(string);
				HttpParams myParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(myParams, 10000);
				HttpConnectionParams.setSoTimeout(myParams, 30 * 10000);
				httppost.setHeader("Content-type", "application/json");
				HttpClient httpclient = new DefaultHttpClient(myParams);
				// JSONObject jsonObject = new JSONObject();
				// jsonObject.put("body_dict", mJsonToken);
				// StringEntity se;
				// if (jsonObject.toString() == null) {
				// se = new StringEntity("", HTTP.UTF_8);
				// } else {
				// se = new StringEntity(jsonObject.toString(), HTTP.UTF_8);
				// }



				StringEntity se;
				if (mJsonToken.toString() == null) {
					se = new StringEntity("", HTTP.UTF_8);
				} else {
					//se = new StringEntity(mJsonToken.toString(), HTTP.UTF_8);
					//se = new StringEntity(mJsonToken.toString(), HTTP.UTF_8);

				}
//			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
//					"application/json"));
//			httppost.setEntity(se);
				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				String getResult = EntityUtils.toString(entity);
//			Log.e("ffffffffffffffffffffffffff", ""+getResult);
				status = getResult;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
			}
		}
		return status;

	}
//////////////////////////////// Country ////////////////////////////////////////
public static ArrayList<String> Get_Country(){
	Locale[] locale = Locale.getAvailableLocales();
	ArrayList<String> countries = new ArrayList<String>();
	String country;
	for( Locale loc : locale ){
	    country = loc.getDisplayCountry();
	    if( country.length() > 0 && !countries.contains(country) ){
	        countries.add( country );
	    }
	}
	Log.i("Countries", ""+countries);
//	Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
	Collections.sort(countries, new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }
    });
	return countries;
	}
/*///////////////////////////////////// Heigth //////////////////////////////////
public static ArrayList<String> Get_height(){
		String[] Height = new String [] {"<5'(<152cm)" , "5'0\"(152cm)", "5'1\"(155cm)", "5'2\"(157cm)", "5'3\"(160cm)"
				, "5'4\"(163cm)", "5'5\"(165cm)", "5'6\"(168cm)", "5'7\"(170cm)", "5'8\"(173cm)", "5'9\"(175cm)"
				, "5'10\"(178cm)", "5'11\"(180cm)", "6'0\"(183cm)", "6'1\"(185cm)", "6'2\"(188cm)"
				, "6'3\"(191cm)", "6'4\"(193cm)", "6'5\"(196cm)", "6'6\"(198cm)", "6'7\"(201cm)"
				, "6'8\"(203cm)", "6'9\"(206cm)", "6'10\"(208cm)", ">6'10\"(>208cm)"};
//		ArrayList<String> list = (ArrayList<String>) Arrays.asList(Height);
		ArrayList<String> wordList = (ArrayList<String>) Arrays.asList(Height);  		
	return wordList;
	
}*/
/**-------------------------------image convert -------------------------------**/
public static String convertBitmapUriToBase64(File mfile, Context mContext) {

	String mBase64String = "";
	Bitmap mPhoto;

	String filePath = mfile.getAbsoluteFile().toString();
	String ext = filePath.substring(filePath.lastIndexOf("/"),
			filePath.length());

	BitmapFactory.Options options = new BitmapFactory.Options();
	options.inSampleSize = 8;
	mPhoto = BitmapFactory.decodeFile(filePath, options);
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	if (ext.equalsIgnoreCase("png")) {
		mPhoto.compress(Bitmap.CompressFormat.PNG, 100, baos); // bm
	} else {
		mPhoto.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm
	}
	byte[] b = baos.toByteArray();
	mBase64String = Base64.encodeToString(b, Base64.DEFAULT);
	try {
		b = null;
		baos.flush();
		baos.close();
	} catch (Exception e) {
		e.printStackTrace();
	}

	return mBase64String;

}
public static Bitmap rotateBitmap(Bitmap bitmap, String degrees) {
	Matrix mat = new Matrix();
	mat.postRotate(Float.parseFloat(degrees));
	Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
			bitmap.getWidth(), bitmap.getHeight(), mat, true);
	return rotatedBitmap;
}


public static Bitmap getBitmapFromURL(String src,Context context) {
	try {
		URL url = new URL(src);
		HttpURLConnection connection = (HttpURLConnection) url
				.openConnection();
		connection.setDoInput(true);
		connection.connect();
		InputStream input = connection.getInputStream();
		Bitmap myBitmap = BitmapFactory.decodeStream(input);
		// Log.e("Bitmap","returned");
		if (myBitmap != null) {
			return myBitmap;
		} else {
			// getBitmapFromURL(CONSTANTS.getDefaultIcon);
		}

		return myBitmap;
	} catch (IOException e) {
		e.printStackTrace();
		Log.e("Exception", e.getMessage());
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter); 
//		MailUtils.sendMail("" + writer.toString());
		return null;
	}
}

}
