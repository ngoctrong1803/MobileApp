package com.example.mobileshop.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobileshop.R;
import com.example.mobileshop.adapter.CartAdapter;
import com.example.mobileshop.ultil.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ListView listViewCart;
    TextView txtViewNotification;
    static TextView txtViewTotalPrice;
    Button btnAddProduct, btnPay;
    Toolbar toolbarCart;
    CartAdapter cartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Mapping();
        ActionToolBar();
        CheckData();
        // cập nhật tổng tiền
        EventUtil();
        // bắt sự kiện xóa cho sản phẩm trong giỏ hàng
        CatchOnItemInListView();
        // bắt sự kiện click vào button
        EnventButtonClick();

    }

    private void EnventButtonClick() {
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.listCart.size()>0 && MainActivity.user.getFullName()!=null)
                {
                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                    startActivity(intent);
                }
                else if (MainActivity.listCart.size()==0)
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Giỏ hàng trống!");
                }
                else
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng đăng nhập để đặt hàng!");
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void CatchOnItemInListView() {
        listViewCart.setOnItemLongClickListener  (new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Bạn có muốn xóa sản phẩm này!");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartAdapter.notifyDataSetChanged();
                        EventUtil();
                    }
                });
                
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.listCart.size() <=0)
                        {
                            txtViewNotification.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            MainActivity.listCart.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            EventUtil();
                            if (MainActivity.listCart.size() <= 0) {
                                txtViewNotification.setVisibility(View.VISIBLE);
                            } else {
                                txtViewNotification.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                EventUtil();
                            }
                        }
                    }
                });

                builder.show();
                return true;
            }
        });

    }

    public static void EventUtil() {
        double totalPrice = 0;
        for(int i= 0; i<MainActivity.listCart.size();i++)
        {
            totalPrice += MainActivity.listCart.get(i).getProductPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtViewTotalPrice.setText(decimalFormat.format(totalPrice)+" Đ");
    }

    private void CheckData() {
        if (MainActivity.listCart.size()<=0)
        {
            // cập nhật lại giỏ hàng nếu gặp trường hợp xóa sản phẩm
            cartAdapter.notifyDataSetChanged();
            txtViewNotification.setVisibility(View.VISIBLE);
            listViewCart.setVisibility(View.INVISIBLE);
        }
        else
        {
            // cập nhật lại giỏ hàng nếu gặp trường hợp xóa sản phẩm
            cartAdapter.notifyDataSetChanged();
            txtViewNotification.setVisibility(View.INVISIBLE);
            listViewCart.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCart .setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Mapping() {
        listViewCart = findViewById(R.id.listViewCart);
        txtViewNotification = findViewById(R.id.txtViewNotification);
        txtViewTotalPrice = findViewById(R.id.txtViewTotalPrice);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnPay = findViewById(R.id.btnPay);
        toolbarCart = findViewById(R.id.toolbarCart);
        cartAdapter = new CartAdapter(CartActivity.this, MainActivity.listCart);
        listViewCart.setAdapter(cartAdapter);
    }
}