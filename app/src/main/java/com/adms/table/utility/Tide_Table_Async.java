package com.adms.table.utility;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

public class Tide_Table_Async extends AsyncTask<Void, Void, Void> {
	//String BASE_URL = "http://admssvc.com/tide/index.php/api/";
	String BASE_URL = "http://tidetable.admsonline.com/WebService.asmx/";

	int class_no;
	String str,mStringResponse;
	JSONObject jObject,mJsonObject;
	Context ctxt;
	ProgressDialog progressDialog;
	String status;
	String msg;
	Tide_Table_Response_Interface Res_Intr;
	HashMap<String,String> params = new HashMap<String, String>();
	//0 for GET
	//1 for Post.
	/**-------------------------------------------CLASS NUMBERS---------------------**/
	/**-------------------------------------------1 GETTRANSECTION---------------------**/
	/**-------------------------------------------2 DATE VIA MONTH AND YEAR---------------------**/
	/**-------------------------------------------3 Locations-----------------------------------**/
	
	
	public  Tide_Table_Async(Context Activity, int Class_Id, JSONObject jToken, String string, Tide_Table_Response_Interface intrfce,HashMap<String,String> params)
	{
		class_no=Class_Id;
		str=string;
		jObject=jToken;
		ctxt=Activity;
		progressDialog = new ProgressDialog(ctxt);
		Res_Intr=intrfce;

		this.params = params;
	}
	

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		switch (class_no) {
		
		
		/**-------------------------------------------1 GETTRANSECTION------------------------------------------**/
		case 1:
			mStringResponse="";
			mStringResponse=new Tide_Table_Utility().Hit_Server(jObject, BASE_URL+"tide/gettransaction");
			Log.i("the responce is=======================",  mStringResponse);
			
			break;
		case 2:
			mStringResponse="";
			mStringResponse = new Tide_Table_Utility().Hit_Server(jObject,BASE_URL+"Get_Location_Tide_Table_Data",1,this.params);
			Log.i("the responce is=======================",  mStringResponse);
			break;
		case 3:
			mStringResponse="";
			//mStringResponse=new Tide_Table_Utility().Hit_Server(jObject, BASE_URL+"tide/getlocation");
			mStringResponse = new Tide_Table_Utility().Hit_Server(jObject, BASE_URL+"Get_Location_Master",0,null);

			Log.i("the responce is=======================",  mStringResponse);
			break;
			
		default:
			break;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		progressDialog.dismiss();
		switch (class_no) {
		
		/**-------------------------------------------1 GETTRANSECTION------------------------------------------**/
		case 1:
			Res_Intr.Get_Response(mStringResponse);
			break;
		case 2:
			Res_Intr.Get_Response(mStringResponse);
			break;
		case 3:
			Res_Intr.Get_Response(mStringResponse);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog.setMessage("Please wait...");
	    progressDialog.setIndeterminate(true);
	    progressDialog.setCancelable(false);
	    progressDialog.show();
	
	}
}
	



