package com.max.easyhub.cpux.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.max.easyhub.cpux.R;
import com.max.easyhub.cpux.model.TheInfo;

public class ItemListBaseAdapter extends ArrayAdapter<TheInfo>  {

    LayoutInflater lInflater;
    Context ctx;
    ArrayList<TheInfo> mIdMap = new ArrayList<TheInfo>();

    public ItemListBaseAdapter(Context context, int textViewResourceId, ArrayList<TheInfo> prod) {
        super(context, textViewResourceId, prod);
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ctx = context;
        for (int i = 0; i < prod.size(); i++) {
            mIdMap.add(prod.get(i));
        }
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
//        if (view == null) {
//            
//        }
        view = lInflater.inflate(R.layout.item_listview, parent, false);
        if(!mIdMap.get(position).getName().equals("-")){
        	((TextView) view.findViewById(R.id.textView1)).setText(mIdMap.get(position).getName());
        	((TextView) view.findViewById(R.id.textView2)).setText(mIdMap.get(position).getValue());
        }else{
        	//Toast.makeText(ctx, "ok", Toast.LENGTH_SHORT).show();
        	((TextView) view.findViewById(R.id.textView1)).setVisibility(View.GONE);
        	((TextView) view.findViewById(R.id.textView2)).setVisibility(View.GONE);
        	((TextView) view.findViewById(R.id.textView3)).setVisibility(View.GONE);
        	LinearLayout lyt = (LinearLayout) view.findViewById(R.id.lyt);
        	lyt.setBackgroundColor(ctx.getResources().getColor(R.color.darker_grey));
        	lyt.getLayoutParams().height=2;
        }
        return view;
    }

//
//    @Override
//    public long getItemId(int position) {
//        if (position < 0 || position >= mIdMap.size()) {
//            return INVALID_ID;
//        }
//        return mIdMap.get(position);
//    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
