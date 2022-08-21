package com.example.mobileshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.R;
import com.example.mobileshop.ultil.CheckConnection;
import com.example.mobileshop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    EditText editTextUserName;
    EditText editTextPhone;
    EditText editTextAddress;
    EditText editTextEmail;
    EditText editTextReceivedDate;
    Button btnConfirm, btnBackToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Mapping();
        LoadData();
        //nút trở về

        btnBackToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // nút xác nhận
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            EventButtonConfirm();
        }
        else
        {
            CheckConnection.ShowToast_Short(getApplicationContext(), "vui lòng kiểm tra kết nối!");
        }
    }

    private void LoadData() {
        if(MainActivity.user.getFullName()!=null)
        {
            editTextUserName.setText(MainActivity.user.getFullName());
            editTextPhone.setText(MainActivity.user.getPhone());
            editTextEmail.setText(MainActivity.user.getEmail());
            editTextAddress.setText(MainActivity.user.getAddress());

            // chọn ngày giao hàng
            Calendar calendar = Calendar.getInstance();
            int Day= calendar.get(Calendar.DATE);
            int Month = calendar.get(Calendar.MONTH);
            int Year= calendar.get(Calendar.YEAR);
            editTextReceivedDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog( UserActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            if(year == Year && month == Month && dayOfMonth > Day )
                            {
                                calendar.set(year, month, dayOfMonth);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                editTextReceivedDate.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                            else if(year == Year && month > Month )
                            {
                                calendar.set(year, month, dayOfMonth);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                editTextReceivedDate.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                            else if(year > Year)
                            {
                                calendar.set(year, month, dayOfMonth);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                editTextReceivedDate.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                            else
                            {
                                CheckConnection.ShowToast_Short(getApplicationContext(), "Ngày nhận phải lớn hơn ngày hiện tại!");
                            }

                        }
                    }, Year, Month, Day);
                    datePickerDialog.show();
                }
            });

        }
    }

    private void EventButtonConfirm() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String userName =editTextUserName.getText().toString().trim();
               final String phone = editTextPhone.getText().toString().trim();
               final String address = editTextAddress.getText().toString().trim();
               final String email = editTextEmail.getText().toString().trim();
               final String receivedDate = editTextReceivedDate.getText().toString().trim();
                if(userName.length()>0 && phone.length()>0 && address.length()>0 && email.length()>0 && receivedDate.length()> 0 )
                {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.UserInformationUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {

                            if (Integer.parseInt(response)>0)
                            {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.OrderDetailUrl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1"))
                                        {
                                            MainActivity.listCart.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Đã đặt hàng thành công");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi đặt hàng! "+ response);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    //đưa dữ liệu lên server
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i=0; i<MainActivity.listCart.size();i++)
                                        {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("OrderID", response);
                                                jsonObject.put("ProductID", MainActivity.listCart.get(i).getProductID());
                                                jsonObject.put("ProductName", MainActivity.listCart.get(i).getProductName());
                                                jsonObject.put("Quanliti", MainActivity.listCart.get(i).getQuanliti());
                                                jsonObject.put("Price", MainActivity.listCart.get(i).getProductPrice());
                                                jsonObject.put("Stock", MainActivity.listCart.get(i).getStock()-MainActivity.listCart.get(i).getQuanliti());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String,String>();
                                        String temp = jsonArray.toString();
                                        hashMap.put("json", temp);
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        // đưa dữ liệu lên server
                        // nhớ làm thêm ------------------------------------------------
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("UserID", String.valueOf(MainActivity.user.getUserID()));
                            hashMap.put("UserName", userName);
                            hashMap.put("Phone", phone);
                            hashMap.put("Address", address);
                            Calendar calendar = Calendar.getInstance();
                            // ngày đặt hàng là ngày hiện tại
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                            String bookingDate = formatter.format(calendar.getTime());
                            hashMap.put("BookingDate", bookingDate);

                            // ngày nhận hàng
                            Date temp = new Date();
                            try {
                                SimpleDateFormat formatterTemp = new SimpleDateFormat("dd/MM/yyyy");
                                 temp = formatterTemp.parse(receivedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String receivedDateStr = formatter.format(temp);
                            hashMap.put("ReceivedDate", receivedDateStr);

                            // status
                            hashMap.put("Status", "-1");
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng điền đầy đủ thông tin!");
                }
            }
        });
    }

    private void Mapping() {
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        btnConfirm = findViewById(R.id.btnComfirm);
        btnBackToCart =findViewById(R.id.btnBackToCart);
        editTextReceivedDate = findViewById(R.id.editTextReceivedDate);
    }
}