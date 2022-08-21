package com.example.mobileshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.R;
import com.example.mobileshop.model.User;
import com.example.mobileshop.ultil.CheckConnection;
import com.example.mobileshop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnRegister;
    EditText editTextUserName;
    EditText editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Mapping();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextUserName.getText().toString().equals(""))
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "bạn chưa nhập tên đăng nhập!");
                }
                else if (editTextPassword.getText().toString().equals(""))
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "bạn chưa nhập mật khẩu!");
                }
                else {
                    Login();
                }
            }
            }
        );
    }

    private void GetUser(String response) {
        if(response != null)
        {
            try {
                JSONObject jsonObject = new JSONObject(response);
                int userID = jsonObject.getInt("UserID");
                String userName= jsonObject.getString("UserName");
                String password= jsonObject.getString("Password");
                String fullName= jsonObject.getString("FullName");
                String address= jsonObject.getString("Address");
                String phone= jsonObject.getString("Phone");
                String email= jsonObject.getString("Email");
                MainActivity.user = new User(userID, userName, password, fullName, address, phone, email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            CheckConnection.ShowToast_Short(getApplicationContext(), "Không gán được tại khoản!");
        }
    }


    private void Login() {
        //biến đưa request lên cho server
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.LoginUrl;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            //nhận dữ liệu về
            @Override
            public void onResponse(String response) {
                if(response.equals("0"))
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "mật khẩu không đúng!");
                }
                else if (response.equals("-1"))
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "tài khoản không tồn tại!");
                }
                else
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Đăng nhập thành công!");
                    GetUser(response);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "chương trình lỗi!");
            }
        }){
            //đưa dữ liệu lên
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param =new HashMap<String, String>();
                String userNameTemp= editTextUserName.getText().toString();
                String passwordTemp= editTextPassword.getText().toString();

                param.put("UserName", userNameTemp);
                param.put("Password", passwordTemp);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Mapping() {
        btnLogin = findViewById(R.id.btnLoginInLogin);
        btnRegister = findViewById(R.id.btnRegisterInLogin);
        editTextUserName = findViewById(R.id.editTextUserNameInLogin);
        editTextPassword = findViewById(R.id.editTextPasswordInLogin);
    }
}