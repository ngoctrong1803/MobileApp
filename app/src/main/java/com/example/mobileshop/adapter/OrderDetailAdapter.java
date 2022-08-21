package com.example.mobileshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileshop.R;
import com.example.mobileshop.model.Product;
import com.example.mobileshop.model.ProductInOrder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderDetailAdapter extends BaseAdapter {

    Context context;
    ArrayList<ProductInOrder> listProductInOrder;


    public OrderDetailAdapter(Context context, ArrayList<ProductInOrder> listProductInOrder) {
        this.context = context;
        this.listProductInOrder = listProductInOrder;
    }
    @Override
    public int getCount() {
        return listProductInOrder.size();
    }

    @Override
    public Object getItem(int position) {
        return listProductInOrder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        public TextView txtViewProductNameInOrderDetail, txtViewQuanlitiInOrderDetail, txtViewPriceInOrderDetail;
        public ImageView imageViewOrderDetail;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if(view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_detail_listview, null);
            viewHolder.txtViewProductNameInOrderDetail = (TextView) view.findViewById(R.id.txtViewProductNameInOrderDetail);
            viewHolder.txtViewPriceInOrderDetail = (TextView) view.findViewById(R.id.txtViewPriceInOrderDetail);
            viewHolder.txtViewQuanlitiInOrderDetail = (TextView) view.findViewById(R.id.txtViewQuanlitiInOrderDetail);
            viewHolder.imageViewOrderDetail = (ImageView) view.findViewById(R.id.imageViewOrderDetail);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder  =(ViewHolder) view.getTag();
        }

        // gán dữ liệu
        ProductInOrder productInOrder = (ProductInOrder) getItem(position);
        viewHolder.txtViewProductNameInOrderDetail.setText(productInOrder.getProductName());
        viewHolder.txtViewQuanlitiInOrderDetail.setText(String.valueOf(productInOrder.getQuanliti()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtViewPriceInOrderDetail.setText(decimalFormat.format(productInOrder.getPrice()) +" Đ");

        Picasso.get().load(productInOrder.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imageViewOrderDetail);
        return view;
    }
}
