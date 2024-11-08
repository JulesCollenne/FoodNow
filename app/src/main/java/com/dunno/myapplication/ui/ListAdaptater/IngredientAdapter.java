package com.dunno.myapplication.ui.ListAdaptater;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dunno.myapplication.R;

import java.util.List;


/**
 * Classe permettant de personnaliser la liste d'ingrédient: Une image et un nom pour chaque élément
 */

public class IngredientAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<String> ingredientName;
    private List<String> ingredientID;

    public IngredientAdapter(Context context, List<String> ingredientNames,  List<String> ingredientID){

        ingredientName = ingredientNames;
        this.ingredientID = ingredientID;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return ingredientName.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredientName.get(position);
    }

    public String getName(int position) {
        return ingredientName.get(position);
    }

    public String getID(int position) {
        return ingredientID.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.list_item_ingredient, null);

        ImageView iv_ingredient_image = v.findViewById(R.id.ingredient_image);
        TextView tv_ingredient_name = v.findViewById(R.id.ingredient_name);

        String ingredient_name = ingredientName.get(position);


        String tmp = "ingid"+ingredientID.get(position);
        Context context = iv_ingredient_image.getContext();
        int id = context.getResources().getIdentifier(tmp, "drawable", context.getPackageName());
        iv_ingredient_image.setImageResource(id);

        tv_ingredient_name.setText(ingredient_name);

        return v;
    }

}
