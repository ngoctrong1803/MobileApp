package com.example.mobileshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileshop.R;
import com.example.mobileshop.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    ArrayList<Category> listCategory;
    Context context;

    public CategoryAdapter(ArrayList<Category> listCategory, Context context) {
        this.listCategory = listCategory;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return listCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder{
        // các thuộc tính trong layout : category_listview.xml
        TextView txtCategoryName;
        ImageView imgCategory;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder =null;
        if(view ==null) // khi view rỗng
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // giúp get được service là layout
            view = inflater.inflate(R.layout.category_listview,null);
            viewHolder.txtCategoryName = view.findViewById(R.id.textViewListCategory);
            viewHolder.imgCategory = view.findViewById(R.id.imageViewListCategory);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder =(ViewHolder) view.getTag();
        }
        Category category = (Category) getItem(i);
        viewHolder.txtCategoryName.setText(category.getCategoryName());
        Picasso.get().load(category.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imgCategory);

        return view ;
    }
}
