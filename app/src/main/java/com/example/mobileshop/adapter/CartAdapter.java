package com.example.mobileshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileshop.R;
import com.example.mobileshop.activity.CartActivity;
import com.example.mobileshop.activity.MainActivity;
import com.example.mobileshop.model.Cart;
import com.example.mobileshop.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {

    Context context;
    ArrayList<Cart> listCart;

    public CartAdapter(Context context, ArrayList<Cart> listCart) {
        this.context = context;
        this.listCart = listCart;
    }

    @Override
    public int getCount() {
        return listCart.size();
    }

    @Override
    public Object getItem(int position) {
        return listCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        // các thuộc tính trong layout cart_listview
        public TextView txtViewProductNameInCart, txtViewProductPriceInCart;
        public ImageView imageProductInCart;
        public Button btnDecreaseQuanliti, btnValues, btnIncreaseQuanliti;



    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view ==null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cart_listview,null);
            viewHolder.txtViewProductNameInCart = view.findViewById(R.id.txtViewProductNameInCart);
            viewHolder.txtViewProductPriceInCart = view.findViewById(R.id.txtViewProductPriceInCart);
            viewHolder.imageProductInCart = view.findViewById(R.id.imageProductInCart);
            viewHolder.btnDecreaseQuanliti = view.findViewById(R.id.btnDecreaseQuanliti);
            viewHolder.btnValues = view.findViewById(R.id.btnValues);
            viewHolder.btnIncreaseQuanliti = view.findViewById(R.id.btnIncreaseQuanliti);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        // lấy dữ liệu ra và gán cho layout
        Cart cart = (Cart) getItem(position);
        viewHolder.txtViewProductNameInCart.setText(cart.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtViewProductPriceInCart.setText(decimalFormat.format(cart.getProductPrice())+" Đ");
        Picasso.get().load(cart.getImage())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(viewHolder.imageProductInCart);
        viewHolder.btnValues.setText(cart.getQuanliti()+"");
        // button tăng giảm số lượng
        int quanliti= Integer.parseInt(viewHolder.btnValues.getText().toString());
        if(quanliti>=10)
        {
            viewHolder.btnIncreaseQuanliti.setVisibility(View.INVISIBLE);
            viewHolder.btnDecreaseQuanliti.setVisibility(View.VISIBLE);
        }
        else if (quanliti<=1)
        {
            viewHolder.btnIncreaseQuanliti.setVisibility(View.VISIBLE);
            viewHolder.btnDecreaseQuanliti.setVisibility(View.INVISIBLE);
        }
        else
        {
            viewHolder.btnIncreaseQuanliti.setVisibility(View.VISIBLE);
            viewHolder.btnDecreaseQuanliti.setVisibility(View.VISIBLE);
        }
        viewHolder.btnIncreaseQuanliti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int newQuanliti =  Integer.parseInt(viewHolder.btnValues.getText().toString()) + 1;
                int nowQuanliti = MainActivity.listCart.get(position).getQuanliti();
                double nowPrice = MainActivity.listCart.get(position).getProductPrice();
                if (newQuanliti <= MainActivity.listCart.get(position).getStock())
                {
                    MainActivity.listCart.get(position).setQuanliti(newQuanliti);
                    double newPrice = (nowPrice * newQuanliti/ nowQuanliti);
                    MainActivity.listCart.get(position).setProductPrice(newPrice);
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    viewHolder.txtViewProductPriceInCart.setText(decimalFormat.format(cart.getProductPrice())+" Đ");
                    // update lại dữ liệu cho tổng tiền
                    CartActivity.EventUtil();
                    if(newQuanliti > 9){
                        viewHolder.btnIncreaseQuanliti.setVisibility(View.INVISIBLE);
                        viewHolder.btnDecreaseQuanliti.setVisibility(View.VISIBLE);
                        viewHolder.btnValues.setText(String.valueOf(newQuanliti));
                    }
                    else {
                        viewHolder.btnIncreaseQuanliti.setVisibility(View.VISIBLE);
                        viewHolder.btnDecreaseQuanliti.setVisibility(View.VISIBLE);
                        viewHolder.btnValues.setText(String.valueOf(newQuanliti));
                    }
                }
                else
                {
                    CheckConnection.ShowToast_Short(context.getApplicationContext(), "Số lượng sản phẩm chỉ còn: "+MainActivity.listCart.get(position).getStock()+"sản phẩm!");
                }

            }
        });
        viewHolder.btnDecreaseQuanliti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQuanliti =  Integer.parseInt(viewHolder.btnValues.getText().toString()) - 1;
                int nowQuanliti = MainActivity.listCart.get(position).getQuanliti();
                double nowPrice = MainActivity.listCart.get(position).getProductPrice();
                MainActivity.listCart.get(position).setQuanliti(newQuanliti);
                double newPrice = (nowPrice * newQuanliti/ nowQuanliti);
                MainActivity.listCart.get(position).setProductPrice(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtViewProductPriceInCart.setText(decimalFormat.format(cart.getProductPrice())+" Đ");
                // update lại dữ liệu cho tổng tiền
                CartActivity.EventUtil();
                if(newQuanliti < 2){
                    viewHolder.btnIncreaseQuanliti.setVisibility(View.VISIBLE);
                    viewHolder.btnDecreaseQuanliti.setVisibility(View.INVISIBLE);
                    viewHolder.btnValues.setText(String.valueOf(newQuanliti));
                }
                else {
                    viewHolder.btnIncreaseQuanliti.setVisibility(View.VISIBLE);
                    viewHolder.btnDecreaseQuanliti.setVisibility(View.VISIBLE);
                    viewHolder.btnValues.setText(String.valueOf(newQuanliti));
                }            }
        });
        return view;
    }
}
