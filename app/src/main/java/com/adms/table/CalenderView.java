package com.adms.table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.adms.table.utility.SingleOptionAlertWithoutTitle;
import com.adms.table.utility.TT_StaticClass;
import com.adms.table.utility.Tide_Table_Async;
import com.adms.table.utility.Tide_Table_Response_Interface;
import com.adms.table.utility.Tide_Table_Utility;


public class CalenderView extends Activity implements OnItemSelectedListener,
		OnClickListener,Tide_Table_Response_Interface {
  //  private InterstitialAd interstitial;
	ArrayList<String> tid ;
	ArrayList<String> date ;
	ArrayList<String> tidetype;
	ArrayList<String> location ;
	ArrayList<String> tide_time ;
	ArrayList<String> tide_height;
	private String json_status="";
	private boolean isCurrentdate = false;
	TextView textView_heading_premium;
	ArrayList<String> newtidetime = null;
	ArrayList<String> newtideheight = null;
	ArrayList<String> sundays;
	ArrayList<String> tempdate,temptype;
	
//	View viewdate;
//	ImageView iv_tide;
	TextView tv_means;
	String goal="" ;
	String loadmonth,loadyear;
	Boolean isInternetPresent = false;
	JSONObject jObect;
	HashMap<String,String> params;
	PopupWindow pw;
	TextView height,time;
	Point p;
	int devicewidth,deviceheight;
	int m,y;
	
	int j;
	String monthdate;
	String monthdate1;
	String value1;
	boolean selected = true;
	String currentdate;
	String value;
	ArrayList<String> day;
	ArrayList<String> yearCount;
	int addDayValue = 0;
	int blankDayBox = 0;
	Button btnSchedule, btnHome, btnChat, btnContacts, btnAdvanced, btnNotes;
	int monthCode = 0;
	int yearCode = 0;
	String SelecteddayValue = "";
	int convDayCode = 0;
	int dayCode = 0, dayNameCode = 0, posForCYear = 0;
	boolean isLeapYear;
	int cDay, cMonth, cYear, cHour, cMinute, cSecond;
	String[] month = { "01", "02", "03", "04", "05", "06", "07", "08", "09",
			"10", "11", "12" };
	String[] month_name = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
			"Aug", "Sept", "Oct", "Nov", "Dec" };
	ArrayList<String> mArrayListScheduleDate;
	String selectedMonth, selectedYear, currMonth;
	int width, btnWidth;
	GridView list;
	TextView sun, mon, tue, wed, thur, fri, sat, currentDate;
	ListView memoList;
	Dialog startDialog;
	Spinner months, years;

	SQLiteDatabase eventDataRead;
	ArrayList<String> selectedList = new ArrayList<String>();
	ArrayList<String> lat = new ArrayList<String>();
	ArrayList<String> lng = new ArrayList<String>();

	ArrayList<Integer> selectedColor = new ArrayList<Integer>();
	int colorValueCurrent = 0;
	int colorValuePrevoius = 0;
	SharedPreferences.Editor prefsEditor;
	SharedPreferences mypref;
	LinearLayout calenderViewMain;
	SharedPreferences mPreferences;
	TextView mTextViewToday;
	boolean trigger = false;

	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_calender);
		new Tide_Table_Utility();
		isInternetPresent = Tide_Table_Utility
		.isNetworkConnected(CalenderView.this);
		jObect = new JSONObject();
		
		// Prepare the Interstitial Ad
//        interstitial = new InterstitialAd(CalenderView.this);
//        // Insert the Ad Unit ID
//        interstitial.setAdUnitId("ca-app-pub-2542422396039496/5993715966");
//
//        //Locate the Banner Ad in activity_main.xml
//        AdView adView = (AdView) this.findViewById(R.id.adView);
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
        
		mPreferences = PreferenceManager.getDefaultSharedPreferences(CalenderView.this);
		calenderViewMain = (LinearLayout) findViewById(R.id.calenderViewMain);
		tv_means = (TextView)findViewById(R.id.tv_means);
		String means = "≈ Days with high tide";
		SpannableString ss=  new SpannableString(means);
		 ss.setSpan(new RelativeSizeSpan(1.5f),0,1,0); // set size
		 ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textcolor)), 0, means.length(), 0);// set color
		 tv_means.setText(ss); 
		 tv_means.setVisibility(View.GONE);
		mTextViewToday = (TextView) findViewById(R.id.today_date);
		mypref = PreferenceManager
				.getDefaultSharedPreferences(CalenderView.this);
		prefsEditor = mypref.edit();

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		btnWidth = (width / 2) - 30;
		width = (width / 7);
		deviceheight= size.y;
		devicewidth = size.x;
		Log.i("DEVISEEEE    HEIGHT", "" + deviceheight);
		Log.i("DEVISEEEE    Width ", "" + devicewidth);
		startDialog = new Dialog(this);

		day = new ArrayList<String>();
		yearCount = new ArrayList<String>();
		textView_heading_premium = (TextView)findViewById(R.id.textView_heading_premium);
		list = (GridView) findViewById(R.id.cal_datelist);
		months = (Spinner) findViewById(R.id.month);
		years = (Spinner) findViewById(R.id.year);

		sun = (TextView) findViewById(R.id.cal_sun);
		mon = (TextView) findViewById(R.id.cal_mon);
		tue = (TextView) findViewById(R.id.cal_tue);
		wed = (TextView) findViewById(R.id.cal_wed);
		thur = (TextView) findViewById(R.id.cal_thur);
		fri = (TextView) findViewById(R.id.cal_fri);
		sat = (TextView) findViewById(R.id.cal_sat);

		sun.setWidth(width);
		mon.setWidth(width);
		tue.setWidth(width);
		wed.setWidth(width);
		thur.setWidth(width);
		fri.setWidth(width);
		sat.setWidth(width);

		list.setColumnWidth(width - 5);

		// ------------- get the current date and time

		Calendar calander = Calendar.getInstance();
		cDay = calander.get(Calendar.DAY_OF_MONTH);
		cMonth = calander.get(Calendar.MONTH) + 1;
		cYear = calander.get(Calendar.YEAR);
		selectedMonth = "" + cMonth;
		selectedYear = "" + cYear;
		loadmonth = ""+cMonth;
		loadyear = "" + cYear;
		cHour = calander.get(Calendar.HOUR);
		cMinute = calander.get(Calendar.MINUTE);
		cSecond = calander.get(Calendar.SECOND);

		currentdate = (cDay + ":" + (cMonth) + ":" + cYear);
		String date_proper = "";
		if (cMonth == 1) {
			date_proper = "January" + "," + cYear;
		} else if (cMonth == 2) {
			date_proper = "February" + "," + cYear;
		} else if (cMonth == 3) {
			date_proper = "March" + "," + cYear;
		} else if (cMonth == 4) {
			date_proper = "April" + "," + cYear;
		} else if (cMonth == 5) {
			date_proper = "May" + "," + cYear;
		} else if (cMonth == 6) {
			date_proper = "June" + "," + cYear;
		} else if (cMonth == 7) {
			date_proper = "July" + "," + cYear;
		} else if (cMonth == 8) {
			date_proper = "August" + "," + cYear;
		} else if (cMonth == 9) {
			date_proper = "September" + "," + cYear;
		} else if (cMonth == 10) {
			date_proper = "October" + "," + cYear;
		} else if (cMonth == 11) {
			date_proper = "November" + "," + cYear;
		} else if (cMonth == 12) {
			date_proper = "December" + "," + cYear;
		}
		if (date_proper.charAt(1) == ',') {
			date_proper = "0" + date_proper;
		}

		mTextViewToday.setText(date_proper);

		// currentDate.setText(cDay+" :"+(cMonth)+" :"+cYear);
		// Toast.makeText(CalenderView.this, "CurrentDate="+currentdate,
		// 30000).show();
		/* currentTime.setText(cHour+":"+cMinute+":"+cSecond); */
		// --------------- count the year for drop down

		for (int count = 1900; count <= 2100; count++) {
			yearCount.add("" + count);
		}

		// -------------show the month drop down

		ArrayAdapter<String> monthss = new ArrayAdapter<String>(this,
				R.layout.spinner_item, month);
		monthss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		months.setAdapter(monthss);

		// -------------show the year drop down

		ArrayAdapter<String> yearss = new ArrayAdapter<String>(this,
				R.layout.spinner_item, yearCount);
		yearss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		years.setAdapter(yearss);

		months.setOnItemSelectedListener(this);
		years.setOnItemSelectedListener(this);

		String monthorg = months.getSelectedItem().toString();

		if (cMonth == 1) {
			currMonth = "01";
			months.setSelection(0);
		} else if (cMonth == 2) {
			currMonth = "02";
			months.setSelection(1);

		} else if (cMonth == 3) {
			currMonth = "03";
			months.setSelection(2);
		} else if (cMonth == 4) {
			currMonth = "04";
			months.setSelection(3);
		} else if (cMonth == 5) {
			currMonth = "05";
			months.setSelection(4);
		} else if (cMonth == 6) {
			currMonth = "06";
			months.setSelection(5);
		} else if (cMonth == 7) {
			currMonth = "07";
			months.setSelection(6);
		} else if (cMonth == 8) {
			currMonth = "08";
			months.setSelection(7);
		} else if (cMonth == 9) {
			currMonth = "09";
			months.setSelection(8);
		} else if (cMonth == 10) {
			currMonth = "10";
			months.setSelection(9);
		} else if (cMonth == 11) {
			currMonth = "11";
			months.setSelection(10);
		} else {
			currMonth = "12";
			months.setSelection(11);
		}

		for (int yearposget = 0; yearposget < yearCount.size(); yearposget++) {
			if (yearCount.get(yearposget).equalsIgnoreCase("" + cYear)) {
				posForCYear = yearposget;
				break;
			}
		}
		years.setSelection(posForCYear);

		// --------------on click on grid view

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View arg1, int pos,
					long arg3) {
				String spinnercost1 = (parent.getItemAtPosition(pos))
						.toString();
			}
		});
months.setVisibility(View.INVISIBLE);
years.setVisibility(View.INVISIBLE);
		
//		new DailyMeetingAsyn().execute();

	}

	 public void displayInterstitial() {
	        // If Ads are loaded, show Interstitial else show nothing.
//	        if (interstitial.isLoaded()) {
//	            interstitial.show();
//	        }
	    }
	
	@Override
	protected void onResume() {
		super.onResume();
		if(isInternetPresent){
			POSTPARAM();
	}
	else{
			SingleOptionAlertWithoutTitle.ShowAlertDialog(CalenderView.this, "Error", "No internet access", "Ok");
		}
	}
	
	class DateList extends BaseAdapter {

		DateList(int getMonth1) {
			// Log.e("", "get the month1 value" + getMonth1);

			// show day according month and year selection

			if (dayCode == 0) {
				// Log.e("", " The day is Saturday");
				// Log.e("", "loop fo day code " + dayCode);
				dayNameCode = 6;

				if (getMonth1 == 28) {
					blankDayBox = 1;
				} else if (getMonth1 == 30) {
					blankDayBox = 6;
				} else {
					blankDayBox = 5;
				}
			} else if (dayCode == 6) {
				// Log.e("", " The day is Friday");
				// Log.e("", "loop fo day code " + dayCode);
				dayNameCode = 5;

				if (getMonth1 == 28) {
					blankDayBox = 2;
				} else if (getMonth1 == 30) {
					blankDayBox = 0;
				} else {
					blankDayBox = 6;
				}
			} else if (dayCode == 5) {
				// Log.e("", " The day is Thursday");
				// Log.e("", "loop fo day code " + dayCode);
				dayNameCode = 4;

				if (getMonth1 == 28) {
					blankDayBox = 3;
				} else if (getMonth1 == 30) {
					blankDayBox = 1;
				} else {
					blankDayBox = 0;
				}
			} else if (dayCode == 4) {
				// Log.e("", " The day is Wednesday");
				// Log.e("", "loop fo day code " + dayCode);
				dayNameCode = 3;

				if (getMonth1 == 28) {
					blankDayBox = 4;
				} else if (getMonth1 == 30) {
					blankDayBox = 2;
				} else {
					blankDayBox = 1;
				}
			} else if (dayCode == 3) {
				// Log.e("", " The day is Tueaday");
				// Log.e("", "loop fo day code " + dayCode);
				dayNameCode = 2;

				if (getMonth1 == 28) {
					blankDayBox = 5;
				} else if (getMonth1 == 30) {
					blankDayBox = 3;
				} else {
					blankDayBox = 2;
				}
			} else if (dayCode == 2) {
				// Log.e("", " The day is Monday");
				dayNameCode = 1;
				// Log.e("", "loop fo day code " + dayCode);

				if (getMonth1 == 28) {
					blankDayBox = 6;
				} else if (getMonth1 == 30) {
					blankDayBox = 4;
				} else {
					blankDayBox = 3;
				}
			} else {
				// Log.e("", " The day is Sunday");
				// Log.e("", "loop fo day code " + dayCode);
				dayNameCode = 0;
				if (getMonth1 == 28) {
					blankDayBox = 6;
				} else if (getMonth1 == 30) {
					blankDayBox = 5;
				} else {
					blankDayBox = 4;
				}
			}
			if (isLeapYear) {
				// Log.e("", " old day name code  space" + dayNameCode
				// + "old last blank space" + blankDayBox);
				dayNameCode = dayNameCode + 1;
				blankDayBox = blankDayBox - 1;
				isLeapYear = false;
				// Log.e("", "new day name code space " + dayNameCode
				// + "new last blank space" + blankDayBox);
				if (selectedMonth.equalsIgnoreCase("Febuary")) {
					getMonth1 = 29;
					blankDayBox = blankDayBox - 1;
				}
			}
			for (int clculateDay = 1; clculateDay <= dayNameCode; clculateDay++) {
				day.add("");
//				selectedColor.add(Color.parseColor("#E4E4E4"));
				selectedColor.add(Color.parseColor("#FFFFFF"));
			}

			for (int clculateDay = 1; clculateDay <= getMonth1; clculateDay++) {
				day.add("" + clculateDay);
//				selectedColor.add(Color.parseColor("#E4E4E4"));
				selectedColor.add(Color.parseColor("#FFFFFF"));
			}

			for (int clculateDay = 1; clculateDay <= blankDayBox; clculateDay++) {
				day.add("");
//				selectedColor.add(Color.parseColor("#E4E4E4"));
				selectedColor.add(Color.parseColor("#FFFFFF"));
			}
			// Log.e("", " spaces before day no----- " + dayNameCode
			// + " blank space value----- " + blankDayBox);
			// Log.e("", "no of day is" + day);
		}

		public DateList() {

		}

		public int getCount() {

			return day.size();
		}

		public Object getItem(int position) {

			return position;
		}

		public long getItemId(int position) {

			return position;
		}

		@SuppressWarnings("unused")
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View v = LayoutInflater.from(CalenderView.this).inflate(
					R.layout.datetext, null);
			final TextView dateText = (TextView) v
					.findViewById(R.id.dateTextNo);
			final ImageView dateTextSelectedImage = (ImageView) v
					.findViewById(R.id.dateHighTide);

			/* current date Selected calender */
			dateText.setBackgroundColor(selectedColor.get(position));
			if (day.get(position).equalsIgnoreCase("" + cDay)
					&& selectedMonth.equalsIgnoreCase("" + currMonth)
					&& selectedYear.equalsIgnoreCase("" + cYear)) {
				dateText.setBackgroundColor(Color.parseColor("#51C4FF"));
				// dateText.setBackgroundColor(Color.parseColor("#2C9A74"));
			}

			/* date events contains data then it will be selected */

			// String mDay1=day.get(position).toString();
			final String mDay;
			if (day.get(position).length() == 1) {
				mDay = "0" + day.get(position).toString();
			} else {
				mDay = day.get(position).toString();
			}
			try{
				if(selectedMonth.length()==1){
					selectedMonth ="0"+selectedMonth;
				}
			if (mArrayListScheduleDate.contains((selectedYear + "-"
					+ (selectedMonth) + "-" + mDay).toString())) {
				int index = mArrayListScheduleDate.indexOf((selectedYear + "-"
					+ (selectedMonth) + "-" + mDay).toString());
				if(tidetype.get(index).equalsIgnoreCase("High")){
					Log.i("Tide type", tidetype.get(index));
					dateTextSelectedImage.setVisibility(View.VISIBLE);
				}
				else{
					dateTextSelectedImage.setVisibility(View.GONE);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			
			
			/* selected date events will be shown */
			dateText.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

					selectedColor.remove(colorValueCurrent);
					monthdate = selectedYear + "-" + (selectedMonth) + "-"
							+ mDay;
					Log.e("monthdate", "" + monthdate);
					colorValueCurrent = position;
					colorValuePrevoius = colorValueCurrent;
					selectedColor.set(colorValueCurrent,
							Color.parseColor("#88FF0000"));
					Log.e("monthdate", "" + monthdate);
					
					try{	
						SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date date = null;
							date = inFormat.parse(monthdate);
						SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
						goal = outFormat.format(date); 
						Log.e("Goal", "Goal = " + goal);
						if (day.get(position).equalsIgnoreCase("" + cDay)
								&& selectedMonth.equalsIgnoreCase("" + currMonth)
								&& selectedYear.equalsIgnoreCase("" + cYear)) {
							isCurrentdate = true;
						}
						else{
							isCurrentdate = false;
						}
					
					String whatclick = dateText.getText().toString();
					int startdate,enddate;
					startdate = ((Integer.parseInt(whatclick)*4)-4);
					enddate = ((Integer.parseInt(whatclick)*4));
					newtideheight = new ArrayList<String>();
					newtidetime= new ArrayList<String>();
					for(int k=startdate;k<enddate;k++){
						newtideheight.add(tide_height.get(k));
						newtidetime.add(tide_time.get(k));
					}
					Log.e("tide time", ""+newtidetime);
					Log.e("tide height", ""+newtideheight);
					dateText.setBackgroundColor(Color.parseColor("#BFBFBF"));
					LayoutInflater layoutInflater 
				     = (LayoutInflater)v.getContext()
				      .getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = layoutInflater.inflate(R.layout.dataraw, null);
					TextView tv_h_first = (TextView)layout.findViewById(R.id.tv_height_first);
					TextView tv_h_second = (TextView)layout.findViewById(R.id.tv_height_sec);
					TextView tv_h_third = (TextView)layout.findViewById(R.id.tv_height_thrd);
					TextView tv_h_forth = (TextView)layout.findViewById(R.id.tv_height_forth);
					TextView tv_t_first = (TextView)layout.findViewById(R.id.tv_tide_first);
					TextView tv_t_second = (TextView)layout.findViewById(R.id.tv_tide_sec);
					TextView tv_t_third = (TextView)layout.findViewById(R.id.tv_tide_thrd);
					TextView tv_t_forth = (TextView)layout.findViewById(R.id.tv_tide_forth);
					TextView tv_popup_date = (TextView)layout.findViewById(R.id.tv_popup_date);
					if(whatclick.length()==1){
						whatclick = "0"+whatclick;
					}
					tv_popup_date.setText(whatclick+"-"+selectedMonth+"-"+selectedYear);
					tv_h_first.setText(newtideheight.get(0).toString());
					tv_h_second.setText(newtideheight.get(1).toString());
					tv_h_third.setText(newtideheight.get(2).toString());
					tv_h_forth.setText(newtideheight.get(3).toString());
					tv_t_first.setText(newtidetime.get(0).toString());
					tv_t_second.setText(newtidetime.get(1).toString());
					tv_t_third.setText(newtidetime.get(2).toString());
					tv_t_forth.setText(newtidetime.get(3).toString());
					
					pw = new PopupWindow(layout,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT, true);
					pw.setBackgroundDrawable(new BitmapDrawable());
					pw.setOutsideTouchable(true);
					
					
					pw.setOnDismissListener(new OnDismissListener() {
						
						@Override
						public void onDismiss() {
							// TODO Auto-generated method stub
							Log.e("Here", "Dismiss");
								if (isCurrentdate==true){
									dateText.setBackgroundColor(Color.parseColor("#51C4FF"));
								}
								else{
									dateText.setBackgroundColor(Color.parseColor("#FFFFFF"));
								}
							isCurrentdate = false;
							newtideheight.clear();
							newtidetime.clear();
						}
						
					});
					pw.showAsDropDown(dateText);
					}
					 catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				
			});
			if (day.get(position).equalsIgnoreCase("" + cDay)
					&& (selectedMonth).equalsIgnoreCase(currMonth)) {
			}
			dateText.setText("" + day.get(position));
			return v;
		}
	}

	// --------------on drop down selection

	@SuppressWarnings("unused")
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

		switch (arg0.getId()) {
		case R.id.month:
			selectedMonth = months.getSelectedItem().toString().trim();
			day.clear();

			list = (GridView) findViewById(R.id.cal_datelist);
			if (selectedMonth.equalsIgnoreCase("02")) {
				addDayValue = 28;
			} else if (selectedMonth.equalsIgnoreCase("01")
					|| selectedMonth.equalsIgnoreCase("03")
					|| selectedMonth.equalsIgnoreCase("05")
					|| selectedMonth.equalsIgnoreCase("07")
					|| selectedMonth.equalsIgnoreCase("08")
					|| selectedMonth.equalsIgnoreCase("10")
					|| selectedMonth.equalsIgnoreCase("12")) {
				addDayValue = 31;

			} else {
				addDayValue = 30;

			}

			// --------------get the month code

			if (selectedMonth.equalsIgnoreCase("01")
					|| selectedMonth.equalsIgnoreCase("10")) {
				monthCode = 01;
			} else if (selectedMonth.equalsIgnoreCase("04")
					|| selectedMonth.equalsIgnoreCase("07")) {
				monthCode = 00;
			} else if (selectedMonth.equalsIgnoreCase("05")) {
				monthCode = 02;
			} else if (selectedMonth.equalsIgnoreCase("08")) {
				monthCode = 03;
			} else if (selectedMonth.equalsIgnoreCase("02")
					|| selectedMonth.equalsIgnoreCase("03")
					|| selectedMonth.equalsIgnoreCase("11")) {
				monthCode = 04;
			} else if (selectedMonth.equalsIgnoreCase("06")) {
				monthCode = 05;
			} else {
				monthCode = 06;
			}

			dayCode = 1 + monthCode + yearCode;

			if (dayCode >= 7) {
				dayCode = dayCode % 7;
				int devi = dayCode / 7;
			}
			try {
				selectedColor.remove(colorValueCurrent);
			} catch (Exception e) {
				// TODO: handle exception
			}

			list.setAdapter(new DateList(addDayValue));
			if (trigger) {
//				new DailyMeetingAsyn().execute();
				POSTPARAM();
				trigger=false;
			}
			break;

		case R.id.year: // year selection
			day.clear();
			selectedYear = years.getSelectedItem().toString().trim();
			if (selectedYear.equalsIgnoreCase("1900")
					|| selectedYear.equalsIgnoreCase("1906")
					|| selectedYear.equalsIgnoreCase("1917")
					|| selectedYear.equalsIgnoreCase("1923")
					|| selectedYear.equalsIgnoreCase("1928")
					|| selectedYear.equalsIgnoreCase("1934")
					|| selectedYear.equalsIgnoreCase("1945")
					|| selectedYear.equalsIgnoreCase("1951")
					|| selectedYear.equalsIgnoreCase("1956")
					|| selectedYear.equalsIgnoreCase("1962")
					|| selectedYear.equalsIgnoreCase("1973")
					|| selectedYear.equalsIgnoreCase("1979")
					|| selectedYear.equalsIgnoreCase("1984")
					|| selectedYear.equalsIgnoreCase("1990")
					|| selectedYear.equalsIgnoreCase("2001")
					|| selectedYear.equalsIgnoreCase("2007")
					|| selectedYear.equalsIgnoreCase("2012")
					|| selectedYear.equalsIgnoreCase("2018")
					|| selectedYear.equalsIgnoreCase("2029")
					|| selectedYear.equalsIgnoreCase("2035")
					|| selectedYear.equalsIgnoreCase("2040")
					|| selectedYear.equalsIgnoreCase("2046")
					|| selectedYear.equalsIgnoreCase("2057")
					|| selectedYear.equalsIgnoreCase("2063")
					|| selectedYear.equalsIgnoreCase("2068")
					|| selectedYear.equalsIgnoreCase("2074")
					|| selectedYear.equalsIgnoreCase("2085")
					|| selectedYear.equalsIgnoreCase("2091")
					|| selectedYear.equalsIgnoreCase("2096")) {
				yearCode = 0;
			} else if (selectedYear.equalsIgnoreCase("1901")
					|| selectedYear.equalsIgnoreCase("1907")
					|| selectedYear.equalsIgnoreCase("1912")
					|| selectedYear.equalsIgnoreCase("1918")
					|| selectedYear.equalsIgnoreCase("1929")
					|| selectedYear.equalsIgnoreCase("1935")
					|| selectedYear.equalsIgnoreCase("1940")
					|| selectedYear.equalsIgnoreCase("1946")
					|| selectedYear.equalsIgnoreCase("1957")
					|| selectedYear.equalsIgnoreCase("1963")
					|| selectedYear.equalsIgnoreCase("1968")
					|| selectedYear.equalsIgnoreCase("1974")
					|| selectedYear.equalsIgnoreCase("1985")
					|| selectedYear.equalsIgnoreCase("1991")
					|| selectedYear.equalsIgnoreCase("1996")
					|| selectedYear.equalsIgnoreCase("2002")
					|| selectedYear.equalsIgnoreCase("2013")
					|| selectedYear.equalsIgnoreCase("2019")
					|| selectedYear.equalsIgnoreCase("2024")
					|| selectedYear.equalsIgnoreCase("2030")
					|| selectedYear.equalsIgnoreCase("2041")
					|| selectedYear.equalsIgnoreCase("2047")
					|| selectedYear.equalsIgnoreCase("2052")
					|| selectedYear.equalsIgnoreCase("2058")
					|| selectedYear.equalsIgnoreCase("2069")
					|| selectedYear.equalsIgnoreCase("2075")
					|| selectedYear.equalsIgnoreCase("2080")
					|| selectedYear.equalsIgnoreCase("2086")
					|| selectedYear.equalsIgnoreCase("2097"))

			{
				yearCode = 1;
			} else if (selectedYear.equalsIgnoreCase("1902")
					|| selectedYear.equalsIgnoreCase("1913")
					|| selectedYear.equalsIgnoreCase("1919")
					|| selectedYear.equalsIgnoreCase("1924")
					|| selectedYear.equalsIgnoreCase("1930")
					|| selectedYear.equalsIgnoreCase("1941")
					|| selectedYear.equalsIgnoreCase("1947")
					|| selectedYear.equalsIgnoreCase("1952")
					|| selectedYear.equalsIgnoreCase("1958")
					|| selectedYear.equalsIgnoreCase("1969")
					|| selectedYear.equalsIgnoreCase("1975")
					|| selectedYear.equalsIgnoreCase("1980")
					|| selectedYear.equalsIgnoreCase("1986")
					|| selectedYear.equalsIgnoreCase("1991")
					|| selectedYear.equalsIgnoreCase("2003")
					|| selectedYear.equalsIgnoreCase("2008")
					|| selectedYear.equalsIgnoreCase("2014")
					|| selectedYear.equalsIgnoreCase("2025")
					|| selectedYear.equalsIgnoreCase("2031")
					|| selectedYear.equalsIgnoreCase("2036")
					|| selectedYear.equalsIgnoreCase("2042")
					|| selectedYear.equalsIgnoreCase("2053")
					|| selectedYear.equalsIgnoreCase("2059")
					|| selectedYear.equalsIgnoreCase("2064")
					|| selectedYear.equalsIgnoreCase("2070")
					|| selectedYear.equalsIgnoreCase("2081")
					|| selectedYear.equalsIgnoreCase("2087")
					|| selectedYear.equalsIgnoreCase("2092")
					|| selectedYear.equalsIgnoreCase("2098"))

			{
				yearCode = 2;
			} else if (selectedYear.equalsIgnoreCase("1903")
					|| selectedYear.equalsIgnoreCase("1908")
					|| selectedYear.equalsIgnoreCase("1914")
					|| selectedYear.equalsIgnoreCase("1925")
					|| selectedYear.equalsIgnoreCase("1931")
					|| selectedYear.equalsIgnoreCase("1936")
					|| selectedYear.equalsIgnoreCase("1942")
					|| selectedYear.equalsIgnoreCase("1953")
					|| selectedYear.equalsIgnoreCase("1959")
					|| selectedYear.equalsIgnoreCase("1964")
					|| selectedYear.equalsIgnoreCase("1970")
					|| selectedYear.equalsIgnoreCase("1976")
					|| selectedYear.equalsIgnoreCase("1981")
					|| selectedYear.equalsIgnoreCase("1987")
					|| selectedYear.equalsIgnoreCase("1992")
					|| selectedYear.equalsIgnoreCase("1998")
					|| selectedYear.equalsIgnoreCase("2009")
					|| selectedYear.equalsIgnoreCase("2015")
					|| selectedYear.equalsIgnoreCase("2020")
					|| selectedYear.equalsIgnoreCase("2026")
					|| selectedYear.equalsIgnoreCase("2037")
					|| selectedYear.equalsIgnoreCase("2043")
					|| selectedYear.equalsIgnoreCase("2048")
					|| selectedYear.equalsIgnoreCase("2054")
					|| selectedYear.equalsIgnoreCase("2065")
					|| selectedYear.equalsIgnoreCase("2071")
					|| selectedYear.equalsIgnoreCase("2082")
					|| selectedYear.equalsIgnoreCase("2076")
					|| selectedYear.equalsIgnoreCase("2093")
					|| selectedYear.equalsIgnoreCase("2099")) {
				yearCode = 3;
			} else if (selectedYear.equalsIgnoreCase("1909")
					|| selectedYear.equalsIgnoreCase("1915")
					|| selectedYear.equalsIgnoreCase("1920")
					|| selectedYear.equalsIgnoreCase("1926")
					|| selectedYear.equalsIgnoreCase("1937")
					|| selectedYear.equalsIgnoreCase("1943")
					|| selectedYear.equalsIgnoreCase("1948")
					|| selectedYear.equalsIgnoreCase("1954")
					|| selectedYear.equalsIgnoreCase("1965")
					|| selectedYear.equalsIgnoreCase("1971")
					|| selectedYear.equalsIgnoreCase("1982")
					|| selectedYear.equalsIgnoreCase("1993")
					|| selectedYear.equalsIgnoreCase("1999")
					|| selectedYear.equalsIgnoreCase("2004")
					|| selectedYear.equalsIgnoreCase("2010")
					|| selectedYear.equalsIgnoreCase("2021")
					|| selectedYear.equalsIgnoreCase("2027")
					|| selectedYear.equalsIgnoreCase("2032")
					|| selectedYear.equalsIgnoreCase("2038")
					|| selectedYear.equalsIgnoreCase("2049")
					|| selectedYear.equalsIgnoreCase("2055")
					|| selectedYear.equalsIgnoreCase("2060")
					|| selectedYear.equalsIgnoreCase("2066")
					|| selectedYear.equalsIgnoreCase("2077")
					|| selectedYear.equalsIgnoreCase("2083")
					|| selectedYear.equalsIgnoreCase("2088")
					|| selectedYear.equalsIgnoreCase("2094"))

			{
				yearCode = 4;
			} else if (selectedYear.equalsIgnoreCase("1904")
					|| selectedYear.equalsIgnoreCase("1910")
					|| selectedYear.equalsIgnoreCase("1921")
					|| selectedYear.equalsIgnoreCase("1927")
					|| selectedYear.equalsIgnoreCase("1932")
					|| selectedYear.equalsIgnoreCase("1938")
					|| selectedYear.equalsIgnoreCase("1949")
					|| selectedYear.equalsIgnoreCase("1955")
					|| selectedYear.equalsIgnoreCase("1960")
					|| selectedYear.equalsIgnoreCase("1966")
					|| selectedYear.equalsIgnoreCase("1977")
					|| selectedYear.equalsIgnoreCase("1983")
					|| selectedYear.equalsIgnoreCase("1988")
					|| selectedYear.equalsIgnoreCase("1994")
					|| selectedYear.equalsIgnoreCase("2005")
					|| selectedYear.equalsIgnoreCase("2011")
					|| selectedYear.equalsIgnoreCase("2016")
					|| selectedYear.equalsIgnoreCase("2022")
					|| selectedYear.equalsIgnoreCase("2033")
					|| selectedYear.equalsIgnoreCase("2039")
					|| selectedYear.equalsIgnoreCase("2044")
					|| selectedYear.equalsIgnoreCase("2050")
					|| selectedYear.equalsIgnoreCase("2061")
					|| selectedYear.equalsIgnoreCase("2067")
					|| selectedYear.equalsIgnoreCase("2072")
					|| selectedYear.equalsIgnoreCase("2078")
					|| selectedYear.equalsIgnoreCase("2089")
					|| selectedYear.equalsIgnoreCase("2095")
					|| selectedYear.equalsIgnoreCase("2100")) {
				yearCode = 5;
			} else if (selectedYear.equalsIgnoreCase("1905")
					|| selectedYear.equalsIgnoreCase("1911")
					|| selectedYear.equalsIgnoreCase("1916")
					|| selectedYear.equalsIgnoreCase("1922")
					|| selectedYear.equalsIgnoreCase("1933")
					|| selectedYear.equalsIgnoreCase("1939")
					|| selectedYear.equalsIgnoreCase("1944")
					|| selectedYear.equalsIgnoreCase("1950")
					|| selectedYear.equalsIgnoreCase("1961")
					|| selectedYear.equalsIgnoreCase("1967")
					|| selectedYear.equalsIgnoreCase("1972")
					|| selectedYear.equalsIgnoreCase("1978")
					|| selectedYear.equalsIgnoreCase("1989")
					|| selectedYear.equalsIgnoreCase("1995")
					|| selectedYear.equalsIgnoreCase("2000")
					|| selectedYear.equalsIgnoreCase("2006")
					|| selectedYear.equalsIgnoreCase("2017")
					|| selectedYear.equalsIgnoreCase("2023")
					|| selectedYear.equalsIgnoreCase("2028")
					|| selectedYear.equalsIgnoreCase("2034")
					|| selectedYear.equalsIgnoreCase("2045")
					|| selectedYear.equalsIgnoreCase("2051")
					|| selectedYear.equalsIgnoreCase("2056")
					|| selectedYear.equalsIgnoreCase("2062")
					|| selectedYear.equalsIgnoreCase("2073")
					|| selectedYear.equalsIgnoreCase("2079")
					|| selectedYear.equalsIgnoreCase("2084")
					|| selectedYear.equalsIgnoreCase("2090")) {
				yearCode = 6;

			}

			int convOfSelectedYear = Integer.parseInt(selectedYear);
			if ((convOfSelectedYear % 4) == 0
					&& (selectedMonth.equalsIgnoreCase("01") || selectedMonth
							.equalsIgnoreCase("02"))) {
				isLeapYear = true;
			}

			// ---------------find the day name value code

			dayCode = 1 + monthCode + yearCode;

			if (dayCode >= 7) {
				dayCode = dayCode % 7;
				int devi = dayCode / 7;

			}
			try {
				selectedColor.remove(colorValueCurrent);
			} catch (Exception e) {
				// TODO: handle exception
			}
			list.setAdapter(new DateList(addDayValue));
			if (trigger) {
//				new DailyMeetingAsyn().execute();
				POSTPARAM();
				trigger = false;
			}

			break;
		}
	}
	
	private void POSTPARAM() {
		// TODO Auto-generated method stub
		if(Tide_Table_Utility.isNetworkConnected(getApplicationContext())){
//			String date = monthdate;
			try {
				Log.e("Month", "month = "  +loadmonth);
				Log.e("Year", "year = "  +loadyear);
				jObect.put("Location_ID", "1");
				jObect.put("Month1", loadmonth);
				jObect.put("Year1", loadyear);

				params = new HashMap<String, String>();
				params.put("Location_ID","1");
				params.put("Month1",loadmonth);
				params.put("Year1",loadyear);

				new Tide_Table_Async(CalenderView.this,2, jObect, "DATA VIA MONTH AND YEAR",
						(Tide_Table_Response_Interface) CalenderView.this,params).execute();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else{
			SingleOptionAlertWithoutTitle.ShowAlertDialog(CalenderView.this,
					"No Internet Connection",
					"Please check your internet connection", "OK");
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_last:
			 y = Integer.parseInt(selectedYear);
			 m = Integer.parseInt(selectedMonth); 
			if(m<=1){
				m=12;
				y--;
			}
			else{
				m--;
			}
			setCalenderAndDate();
			break;
		case R.id.btn_next:
			 y = Integer.parseInt(selectedYear);
			 m = Integer.parseInt(selectedMonth); 
			if (m > 11) {
				m= 1;
				y++;
			} else {
				m++;
			}

			setCalenderAndDate();
			break;
		case R.id.ib_back:
				finish();
			Intent it = new Intent(getApplicationContext(), DashboardActivity.class);
			startActivity(it);
			break;
		case R.id.ib_off:
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
		default:
			break;
		}
	}

	private void setCalenderAndDate() {
		// TODO Auto-generated method stub
		day.clear();

		list = (GridView) findViewById(R.id.cal_datelist);
		if (m==02) {
			addDayValue = 28;
		} else if (m==1
				|| m==3
				|| m==5
				|| m==7
				|| m==8
				|| m==10
				|| m==12){
			addDayValue = 31;

		} else {
			addDayValue = 30;

		}

		// --------------get the month code

		if (m==1|| m==10) {
			monthCode = 01;
		} else if (m==4|| m==7) {
			monthCode = 00;
		} else if (m==5) {
			monthCode = 02;
		} else if (m==8) {
			monthCode = 03;
		} else if (m==2
				|| m==3
				|| m==11) {
			monthCode = 04;
		} else if (m==6) {
			monthCode = 05;
		} else {
			monthCode = 06;
		}

		if (y==1900
				|| y==1906
				|| y==1917
				|| y==1923
				|| y==1928
				|| y==1934
				|| y==1945
				|| y==1951
				|| y==1956
				|| y==1962
				|| y==1973
				|| y==1979
				|| y==1984
				|| y==1990
				|| y==2001
				|| y==2007
				|| y==2012
				|| y==2018
				|| y==2029
				|| y==2035
				|| y==2040
				|| y==2046
				|| y==2057
				|| y==2063
				|| y==2068
				|| y==2074
				|| y==2085
				|| y==2091
				|| y==2096) {
			yearCode = 0;
		} else if (y==1901
				|| y==1907
				|| y==1912
				|| y==1918
				|| y==1929
				|| y==1935
				|| y==1940
				|| y==1946
				|| y==1957
				|| y==1963
				|| y==1968
				|| y==1974
				|| y==1985
				|| y==1991
				|| y==1996
				|| y==2002
				|| y==2013
				|| y==2019
				|| y==2024
				|| y==2030
				|| y==2041
				|| y==2047
				|| y==2052
				|| y==2058
				|| y==2069
				|| y==2075
				|| y==2080
				|| y==2086
				|| y==2097)

		{
			yearCode = 1;
		} else if (y==1902
				|| y==1913
				|| y==1919
				|| y==1924
				|| y==1930
				|| y==1941
				|| y==1947
				|| y==1952
				|| y==1958
				|| y==1969
				|| y==1975
				|| y==1980
				|| y==1986
				|| y==1991
				|| y==2003
				|| y==2008
				|| y==2014
				|| y==2025
				|| y==2031
				|| y==2036
				|| y==2042
				|| y==2053
				|| y==2059
				|| y==2064
				|| y==2070
				|| y==2081
				|| y==2087
				|| y==2092
				|| y==2098)

		{
			yearCode = 2;
		} else if (y==1903
				|| y==1908
				|| y==1914
				|| y==1925
				|| y==1931
				|| y==1936
				|| y==1942
				|| y==1953
				|| y==1959
				|| y==1964
				|| y==1970
				|| y==1976
				|| y==1981
				|| y==1987
				|| y==1992
				|| y==1998
				|| y==2009
				|| y==2015
				|| y==2020
				|| y==2026
				|| y==2037
				|| y==2043
				|| y==2048
				|| y==2054
				|| y==2065
				|| y==2071
				|| y==2082
				|| y==2076
				|| y==2093
				|| y==2099) {
			yearCode = 3;
		} else if (y==1909
				|| y==1915
				|| y==1920
				|| y==1926
				|| y==1937
				|| y==1943
				|| y==1948
				|| y==1954
				|| y==1965
				|| y==1971
				|| y==1982
				|| y==1993
				|| y==1999
				|| y==2004
				|| y==2010
				|| y==2021
				|| y==2027
				|| y==2032
				|| y==2038
				|| y==2049
				|| y==2055
				|| y==2060
				|| y==2066
				|| y==2077
				|| y==2083
				|| y==2088
				|| y==2094)

		{
			yearCode = 4;
		} else if (y==1904
				|| y==1910
				|| y==1921
				|| y==1927
				|| y==1932
				|| y==1938
				|| y==1949
				|| y==1955
				|| y==1960
				|| y==1966
				|| y==1977
				|| y==1983
				|| y==1988
				|| y==1994
				|| y==2005
				|| y==2011
				|| y==2016
				|| y==2022
				|| y==2033
				|| y==2039
				|| y==2044
				|| y==2050
				|| y==2061
				|| y==2067
				|| y==2072
				|| y==2078
				|| y==2089
				|| y==2095
				|| y==2100) {
			yearCode = 5;
		} else if (y==1905
				|| y==1911
				|| y==1916
				|| y==1922
				|| y==1933
				|| y==1939
				|| y==1944
				|| y==1950
				|| y==1961
				|| y==1967
				|| y==1972
				|| y==1978
				|| y==1989
				|| y==1995
				|| y==2000
				|| y==2006
				|| y==2017
				|| y==2023
				|| y==2028
				|| y==2034
				|| y==2045
				|| y==2051
				|| y==2056
				|| y==2062
				|| y==2073
				|| y==2079
				|| y==2084
				|| y==2090) {
			yearCode = 6;

		}

		int convOfSelectedYear = y;
		if ((convOfSelectedYear % 4) == 0
				&& (m==1 || m==2)) {
			isLeapYear = true;
		}

		// ---------------find the day name value code

		dayCode = 1 + monthCode + yearCode;

		if (dayCode >= 7) {
			dayCode = dayCode % 7;
			int devi = dayCode / 7;

		}
		try {
			selectedColor.remove(colorValueCurrent);
		} catch (Exception e) {
			// TODO: handle exception
		}
		list.setAdapter(new DateList(addDayValue));
		selectedMonth = ""+m;
		selectedYear =""+y;
		loadmonth = ""+m;
		loadyear = ""+y;
		String date_proper="";
		if (m== 1) {
			date_proper = "January" + "," + y;
		} else if (m == 2) {
			date_proper = "February" + "," + y;
		} else if (m == 3) {
			date_proper = "March" + "," + y;
		} else if (m == 4) {
			date_proper = "April" + "," + y;
		} else if (m == 5) {
			date_proper = "May" + "," + y;
		} else if (m == 6) {
			date_proper = "June" + "," + y;
		} else if (m == 7) {
			date_proper = "July" + "," + y;
		} else if (m == 8) {
			date_proper = "August" + "," + y;
		} else if (m == 9) {
			date_proper = "September" + "," + y;
		} else if (m == 10) {
			date_proper = "October" + "," + y;
		} else if (m == 11) {
			date_proper = "November" + "," + y;
		} else if (m == 12) {
			date_proper = "December" + "," + y;
		}
		if (date_proper.charAt(1) == ',') {
			date_proper = "0" + date_proper;
		}
		mTextViewToday.setText(date_proper);
		POSTPARAM();
	}
	
	
	@Override
	public void Get_Response(String REsponse) {
		// TODO Auto-generated method stub
		//Log.e("Get Transection Response","Transection REsponse = " + REsponse);
		JSONObject reader = null;
		boolean error = false;
		tid = new ArrayList<String>();
		date = new ArrayList<String>();
		tidetype = new ArrayList<String>();
		location = new ArrayList<String>();
		tide_time = new ArrayList<String>();
		tide_height = new ArrayList<String>();
		 mArrayListScheduleDate = new ArrayList<String>();
		try{
			reader = new JSONObject(REsponse);
			json_status = reader.getString("Success");
		}
		catch(JSONException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Log.i("Status", json_status);
		if (json_status.equalsIgnoreCase("True")) {
			JSONArray jArray;
			try {
				jArray = reader.getJSONArray("Data");
				if(jArray!=null){
					for(int i=0 ; i<jArray.length();i++){
						JSONObject mJsonObject = jArray.getJSONObject(i);
//						Log.i("TID", "TID = " + mJsonObject.getInt("tid"));
//						Log.i("DATE", "DATE = " + mJsonObject.getString("date"));
//						Log.i("tidetype", "tidetype = " + mJsonObject.getString("tidetype"));
//						Log.i("loc", "loc = " + mJsonObject.getString("loc"));
//						Log.i("time", "time = " + mJsonObject.getString("time"));
//						Log.i("height", "height = " + mJsonObject.getString("height"));
						//tid.add(""+mJsonObject.getInt("tid"));
						String schedule_date = mJsonObject.getString("TideDate");
//						if(mArrayListScheduleDate.contains(schedule_date[0])){
//
//						}
//						else{
//							mArrayListScheduleDate.add(schedule_date[0]);
//						}


						if(!mArrayListScheduleDate.contains(schedule_date)){
							mArrayListScheduleDate.add(schedule_date);
						}




						String date1 = mJsonObject.getString("TideDate");
						if(!date.contains(date1)){
							date.add(date1);
							tidetype.add(mJsonObject.getString("TideType"));
						}
//						if(!location.contains(mJsonObject.getString("loc"))){
//							location.add(mJsonObject.getString("loc"));
//						}
						String time = mJsonObject.getString("Tide_Time");
						if(time.length()==4){
							time = "0"+time;
						}
						tide_time.add(time);
						tide_height.add(mJsonObject.getString("Tide_Feet"));
				}
//					Log.i("TID", "TID list= " + tid);
//					Log.i("DATE", "DATE list= " + date);
//					Log.i("date for tide type", "date for tidetype list= " + mArrayListScheduleDate);
//					Log.i("tide type", "tidetype list= " + tidetype);
//					Log.i("loc", "loc list= " + location);
//					Log.i("time", "time list= " + tide_time);
//					Log.i("height", "height list= " + tide_height);
					trigger = true;
					list.setAdapter(new DateList());					
					textView_heading_premium.setText(TT_StaticClass.location_name);
					tv_means.setVisibility(View.VISIBLE);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error = true;
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				error = true;
			}
			if(error == true){
				SingleOptionAlertWithoutTitle.ShowAlertDialog(CalenderView.this, "Error", "Some data missing.\nPlease try again after some time.", "Ok");
			}
		}
		else if(json_status.equalsIgnoreCase("2")){
			try {
				SingleOptionAlertWithoutTitle.ShowAlertDialog(CalenderView.this,
						"Error", reader.getString("error_msg"), "OK");
				tv_means.setVisibility(View.GONE);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else if(json_status.equalsIgnoreCase("3")){
			try {
				SingleOptionAlertWithoutTitle.ShowAlertDialog(CalenderView.this,
						"Error", reader.getString("error_msg"), "OK");
				tv_means.setVisibility(View.GONE);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else if(json_status.equalsIgnoreCase("0")){
			try {
				SingleOptionAlertWithoutTitle.ShowAlertDialog(CalenderView.this,
						"Error", reader.getString("error_msg"), "OK");
				tv_means.setVisibility(View.GONE);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else{
			SingleOptionAlertWithoutTitle.ShowAlertDialog(CalenderView.this,
					"Error", "Check internet connection and retry with valid data.", "OK");
		}
	
	}

	@Override
	public void SetDialohResponse(boolean b) {
		// TODO Auto-generated method stub
		
	}
}