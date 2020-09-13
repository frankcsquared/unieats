package com.example.unieats.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.unieats.R;
import com.example.unieats.models.Food;
import com.example.unieats.models.FoodList;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends ArrayAdapter<FoodList> {

    private static final String TAG = "ItemAdapter";
    private Context mContext;
    int mResource;

    public ItemAdapter(@NonNull Context context, int resource, List<FoodList> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName2();
        int cals = getItem(position).getCals();
        int amt = getItem(position).getAmt();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView calories = (TextView) convertView.findViewById(R.id.cal);
        TextView amount = (TextView) convertView.findViewById(R.id.amt);
        TextView item = (TextView) convertView.findViewById(R.id.food);

        calories.setText(Integer.toString(cals));
        amount.setText(Integer.toString(amt));
        item.setText(name);

        return convertView;
    }
}
