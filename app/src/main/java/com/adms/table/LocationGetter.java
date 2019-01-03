package com.adms.table;

import java.util.ArrayList;

import com.adms.table.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocationGetter extends BaseAdapter{
	ArrayList<String> locationname;
	Context context;
	private static LayoutInflater inflater=null;
	
	public LocationGetter(Context context, ArrayList<String> locationname) {
		super();
		this.locationname = locationname;
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return locationname.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder
    {
        TextView loc_name;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		try{
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.list_location_row, null);
				holder.loc_name = (TextView)convertView.findViewById(R.id.row_locationname);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.loc_name.setText(locationname.get(position).toString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
        return convertView;
	}

	
}
