package com.example.mobileshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.R;
import com.example.mobileshop.adapter.ListOrderAdapter;
import com.example.mobileshop.model.Order;
import com.example.mobileshop.model.Product;
import com.example.mobileshop.ultil.CheckConnection;
import com.example.mobileshop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ListOrderActivity extends AppCompatActivity {


    Toolbar toolbarListOrder;
    ListView listViewOrder;
    ListOrderAdapter listOrderAdapter;
    ArrayList<Order> listOrder;
    int userID =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);
        Mappping();
        ActionToolbar();
        GetData();
        ClickItem();
    }

    // xử lý khi click vào đơn hàng hiển thị chi tiết đơn hàng.
    private void ClickItem() {
        listViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // click sẽ chuyển qua trang chi tiết sản phẩm
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ListOrderDetailActivity.class);
                intent.putExtra("OrderID", listOrder.get(position).getOrderID());
                startActivity(intent);
            }
        });
    }

    private void GetData() {
        // biến đưa request lên cho server
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ListOrderUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //nhận dữ liệu ở đây
                int OrderID = 0;
                String ReceiverName = "";
                String Phone = "";
                String ReceiverAddress = "";
                String ReceivedDate = "";
                int Status = -2;
                int UserID = 0;
                if(response != null && response.length() != 2)
                {
                    try
                    {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0; i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            OrderID = jsonObject.getInt("OrderID");
                            Phone = jsonObject.getString("Phone");
                            ReceiverName = jsonObject.getString("ReceiverName");
                            ReceiverAddress = jsonObject.getString("Address");
                            UserID = jsonObject.getInt("UserID");
                            String bookingDateStr = jsonObject.getString("BookingDate");
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date bookingDate = formatter.parse(bookingDateStr);
                            String receivedDateStr = jsonObject.getString("ReceivedDate");
                            Date receivedDate = formatter.parse(receivedDateStr);
                            Status = jsonObject.getInt("Status");
                            listOrder.add(new Order(OrderID, ReceiverName, Phone, ReceiverAddress, UserID,receivedDate, bookingDate, Status));
                            listOrderAdapter.notifyDataSetChanged();
                        }

                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else {
                        CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn chưa có đơn hàng!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            // đưa dữ liệu ở đây
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param =new HashMap<String, String>();
                // đưa categoryID lên server
                param.put("UserID",String.valueOf(MainActivity.user.getUserID()));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    // nút trở về
    private void ActionToolbar() {
        toolbarListOrder.setTitle("Danh sách đơn hàng");
        setSupportActionBar(toolbarListOrder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarListOrder.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Mappping() {
        toolbarListOrder =(Toolbar) findViewById(R.id.toolbarListOrder);
        listViewOrder = (ListView) findViewById(R.id.listViewOrder);
        listOrder = new ArrayList<>();
        listOrderAdapter = new ListOrderAdapter(getApplicationContext(), listOrder);
        listViewOrder.setAdapter(listOrderAdapter);

    }
}