package com.example.mobileshop.adapter;

import android.content.Context;
import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileshop.R;
import com.example.mobileshop.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListProductAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> listProduct;

    public ListProductAdapter(Context context, ArrayList<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewHolder{
        public TextView txtProductName, txtProductPrice, txtProductDescription;
        public ImageView imageProduct;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.product_listview, null);
            viewHolder.txtProductName= (TextView) view.findViewById(R.id.txtViewProductName);
            viewHolder.txtProductPrice = (TextView) view.findViewById(R.id.txtViewProductPrice);
            viewHolder.txtProductDescription = (TextView) view.findViewById(R.id.txtViewProductDescription);
            viewHolder.imageProduct = (ImageView) view.findViewById(R.id.imageViewProduct);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder  =(ViewHolder) view.getTag();
        }
        // gán dữ liệu lên cho view
        Product product = (Product) getItem(position);
        viewHolder.txtProductName.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtProductPrice.setText("Giá: "+ decimalFormat.format(product.getPrice()) +" Đ");
        viewHolder.txtProductDescription.setMaxLines(2);
        viewHolder.txtProductDescription.setEllipsize(TextUtils.TruncateAt.END);

        viewHolder.txtProductDescription.setText(product.getDescripton());
        Picasso.get().load(product.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imageProduct);
        return view;
    }
}
