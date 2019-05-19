package com.dunno.myapplication.ui.ListAdaptater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dunno.myapplication.R;

import java.util.ArrayList;

/**
 * Classe permettant de personnaliser la liste de recette: Une image et un nom pour chaque élément
 */

public class RecipeAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<Integer> recipeID;
    private ArrayList<String> recipeName;

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

        ImageView iv_recipe_image = v.findViewById(R.id.recipe_image);
        TextView tv_recipe_name = v.findViewById(R.id.recipe_name);

        String recipe_name = recipeName.get(position);

        String tmp = "recid"+recipeID.get(position);
        Context context = iv_recipe_image.getContext();
        int id = context.getResources().getIdentifier(tmp, "drawable", context.getPackageName());
        iv_recipe_image.setImageResource(id);
        tv_recipe_name.setText(recipe_name);

        return v;

    }
}
