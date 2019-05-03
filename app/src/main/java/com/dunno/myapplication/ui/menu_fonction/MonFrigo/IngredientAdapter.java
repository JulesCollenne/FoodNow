package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dunno.myapplication.R;

public class IngredientAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] ingredientName;

    public IngredientAdapter(Context context, String[] ingredientNames){

        ingredientName = ingredientNames;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return ingredientName.length;
    }

    @Override
    public Object getItem(int position) {
        return ingredientName[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.list_item_ingredient, null);

        ImageView iv_ingredient_image = (ImageView) v.findViewById(R.id.ingredient_image);
        TextView tv_ingredient_name = (TextView) v.findViewById(R.id.ingredient_name);

        String ingredient_name = ingredientName[position];

        iv_ingredient_image.setImageResource(R.drawable.autres);
        tv_ingredient_name.setText(ingredient_name);

        return v;
    }

}
