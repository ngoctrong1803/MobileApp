package com.example.mobileshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.R;
import com.example.mobileshop.adapter.OrderDetailAdapter;
import com.example.mobileshop.model.ProductInOrder;
import com.example.mobileshop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListOrderDetailActivity extends AppCompatActivity {


    Toolbar toolbarOrderDetail;
    ListView listViewOrderDetail;
    OrderDetailAdapter orderDetailAdapter;
    ArrayList<ProductInOrder> listProductInOrder;
    TextView txtViewTotalPrice;
    
    int orderID= 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_detail);
        
        Mapping();
        ActionToolbar();
        GetOrderID();
        GetData();

    }

    private void GetOrderID() {
        orderID=  getIntent().getIntExtra("OrderID", -1);
    }

    private void GetData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ListOrderDetailUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int productID = 0;
                String productName = "";
                int quanliti = 0;
                double price = 0;
                String image = "";
                double totalPrice =0;

                if(response != null && response.length() != 2)
                {
                    try
                    {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i=0; i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            productID = jsonObject.getInt("ProductID");
                            productName = jsonObject.getString("ProductName");
                            quanliti = jsonObject.getInt("Quanliti");
                            price = jsonObject.getDouble("Price");
                            totalPrice = totalPrice + price;
                            image = jsonObject.getString("Image");
                            listProductInOrder.add(new ProductInOrder(productID, productName, quanliti, price, image));
                            orderDetailAdapter.notifyDataSetChanged();
                        }
                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                        txtViewTotalPrice.setText(decimalFormat.format(totalPrice) +" Đ");
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            // đưa dữ liệu lên server
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param =new HashMap<String, String>();
                // đưa categoryID lên server
                param.put("OrderID", String.valueOf(orderID));
                return param;
            }

        };
        requestQueue.add(stringRequest);
    }

    // nút trở về
    private void ActionToolbar() {
        toolbarOrderDetail.setTitle("Chi tiết đơn hàng");
        setSupportActionBar(toolbarOrderDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarOrderDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Mapping() {
        toolbarOrderDetail = (Toolbar) findViewById(R.id.toolbarListOrderDetail);
        listViewOrderDetail= (ListView) findViewById(R.id.listViewOrderDetail);
        listProductInOrder = new ArrayList<>();
        orderDetailAdapter = new OrderDetailAdapter(getApplicationContext(), listProductInOrder);
        listViewOrderDetail.setAdapter(orderDetailAdapter);
        txtViewTotalPrice = (TextView) findViewById(R.id.txtViewTotalPriceInOrderDetail);
    }
}