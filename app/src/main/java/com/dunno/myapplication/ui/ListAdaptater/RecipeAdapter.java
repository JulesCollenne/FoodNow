package com.dunno.myapplication.ui.ListAdaptater;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dunno.myapplication.R;

import java.util.ArrayList;

public class RecipeAdapter extends BaseAdapter {

    LayoutInflater mInflater;

    ArrayList<Integer> recipeID;
    ArrayList<String> recipeName;

    public RecipeAdapter(Context context, ArrayList<Integer> IDs, ArrayList<String> names){

        recipeID = IDs;
        recipeName = names;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return recipeID.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeID.get(position);
    }

    public String getName(int position){
        return recipeName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.list_item_recipe, null);

        ImageView iv_ingredient_image = (ImageView) v.findViewById(R.id.recipe_image);
        TextView tv_ingredient_name = (TextView) v.findViewById(R.id.recipe_name);

        String recipe_name = recipeName.get(position);

        iv_ingredient_image.setImageResource(R.drawable.ic_menu_camera);
        tv_ingredient_name.setText(recipe_name);

        return v;

    }
}
