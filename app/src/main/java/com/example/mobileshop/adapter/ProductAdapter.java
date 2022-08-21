package com.example.mobileshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobileshop.R;
import com.example.mobileshop.activity.ProductDetail;
import com.example.mobileshop.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    //Màn hình muốn đổ dữ liệu
    Context context;
    //Đanh sách dữ liệu muốn đổ
    ArrayList<Product> listProduct;

    public ProductAdapter(Context context, ArrayList<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //khởi tạo lại view đã thiết kế layout ở bên ngoài
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newproduct, null);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product product = listProduct.get(position);
        holder.txtProductName.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtProductPrice.setText("Giá: "+ decimalFormat.format(product.getPrice()) +" Đ");
        Picasso.get().load(product.getImage())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(holder.imgProductImage);

    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder
    {
        public ImageView imgProductImage;
        public TextView txtProductName, txtProductPrice;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgProductImage = (ImageView) itemView.findViewById(R.id.imageViewProduct);
            txtProductName = (TextView) itemView.findViewById(R.id.textViewProductName);
            txtProductPrice = (TextView) itemView.findViewById(R.id.textViewProductPrice);
            // sự kiện click và sản phẩm ở màn hình chính
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("productInformation", listProduct.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
