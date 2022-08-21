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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.R;
import com.example.mobileshop.ultil.CheckConnection;
import com.example.mobileshop.ultil.Server;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnRegister;

    EditText editTextUserName;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    EditText editTextFullName;
    EditText editTextAddress;
    EditText editTextPhone;
    EditText editTextEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Mapping();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextUserName.getText().toString().equals(""))
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng nhập tài khoản!");
                }
                else if (editTextPassword.getText().toString().equals(""))
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng nhập Mật khẩu!");
                }
                else if (editTextFullName.getText().toString().equals(""))
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng nhập họ tên!");
                }
                else if (editTextAddress.getText().toString().equals(""))
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng nhập địa chỉ!");
                }
                else if (editTextPhone.getText().toString().equals(""))
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng nhập số điện thoại!");
                }
                else if (editTextEmail.getText().toString().equals(""))
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng nhập email!");
                }
                else if (editTextConfirmPassword.getText().toString().equals(editTextPassword.getText().toString())== false)
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Mật khẩu không khớp!");
                }
                else
                {
                    Register();
                }
            }
        });

    }

    private void Register() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.RegisterUrl;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CheckConnection.ShowToast_Short(getApplicationContext(), response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            // đưa dữ liệu lên server
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap <String, String> param= new HashMap<String, String>();

                param.put("UserName",editTextUserName.getText().toString());
                param.put("Password",editTextPassword.getText().toString());
                param.put("FullName",editTextFullName.getText().toString());
                param.put("Address",editTextAddress.getText().toString());
                param.put("Phone",editTextPhone.getText().toString());
                param.put("Email",editTextEmail.getText().toString());

                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Mapping() {
        btnLogin = findViewById(R.id.btnLoginInRegister);
        btnRegister = findViewById(R.id.btnRegisterInRegister);

        editTextUserName = findViewById(R.id.editTextUserNameInRegister);
        editTextPassword = findViewById(R.id.editTextPasswordInRegister);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPasswordInRegister);
        editTextFullName = findViewById(R.id.editTextFullNameInRegister);
        editTextAddress = findViewById(R.id.editTextAddressInRegister);
        editTextPhone = findViewById(R.id.editTextPhoneInRegister);
        editTextEmail = findViewById(R.id.editTextEmailInRegister);
    }
}