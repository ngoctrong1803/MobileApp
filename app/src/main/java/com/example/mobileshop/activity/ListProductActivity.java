package com.example.mobileshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.R;
import com.example.mobileshop.adapter.ListProductAdapter;
import com.example.mobileshop.model.Product;
import com.example.mobileshop.model.User;
import com.example.mobileshop.ultil.CheckConnection;
import com.example.mobileshop.ultil.Server;

import androidx.appcompat.widget.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListProductActivity extends AppCompatActivity {

    Toolbar toolbarListProduct;
    ListView listViewListProduct;
    ListProductAdapter listProductAdapter;
    EditText editTextFind;
    int productID= 0;
    int page = 1;
    View footerView;
    boolean isLoading = false;
    boolean limitData = false;
    ArrayList<Product> listProduct;
    MyHandler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        Mappping();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetCategoryID();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
//            editTextFind.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    FindProduct();
//                }
//            });

        }
        else
        {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng kiểm tra kết nối");
        }
    }

    private void FindProduct() {
        editTextFind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().equals(""))
                {
                    GetData(page);
                    LoadMoreData();
                }
                else
                {
                    if(listProduct!=null)
                    {
                        ArrayList<Product> listProductTemp = listProduct;
                        for (Product product : listProductTemp)
                        {
                            String temp = product.getProductName();
                            if(!temp.contains(s.toString()))
                            {
                                try {
                                    listProduct.remove(product);
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }

                            }
                        }
                        listProductAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /* --------------------------------------PHẦN MENU------------------------------------------------*/
    // gắn giỏ hàng vào Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        //chỗ này quan trọng để làm đăng kí đăng nhập
        if(MainActivity.user.getFullName() !=null)
        {
            //ẩn đi login
//            menu.findItem(R.id.menuLogin).setVisible(false);
            menu.findItem(R.id.menuLogin).setTitle(MainActivity.user.getFullName());
            menu.findItem(R.id.menuRegister).setTitle("Đăng xuất");
            menu.findItem(R.id.menuListOrder).setVisible(true);
        }
        /*   menu.findItem(R.id.menuLogin).setVisible(false);*/
        return true;
    }

    //    sự kiên click vào giỏ hàng
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuCart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
                break;
            case R.id.menuListOrder:
                Intent intentOrder = new Intent(getApplicationContext(), ListOrderActivity.class);
                startActivity(intentOrder);
                break;
            case R.id.menuLogin:
                if (MainActivity.user.getFullName()==null)
                {
                    Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intentLogin);
                }
                else
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"chuyển đến màn hình thôn tin khách hàng!");
                }
                break;
            case R.id.menuRegister:
                if (MainActivity.user.getFullName()==null)
                {
                    // chuyển sang màn hình đăng kí
                    Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intentRegister);
                }
                else
                {
                    MainActivity.user = new User();
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Đã đăng xuất!");
                    Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentMain);
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    /*--------------------------------------------------HẾT MENU--------------------------------------------------------*/
    //  sự kiện click vào item
    private void LoadMoreData() {

        listViewListProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // click sẽ chuyển qua trang chi tiết sản phẩm
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductDetail.class);
                intent.putExtra("productInformation", listProduct.get(position));
                startActivity(intent);

            }
        });
        // sự kiện khi lướt đến sản phẩm cuối
        listViewListProduct.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            // khi vướt list view đến vị trí nào đó thì sẽ trả về ở hàm này
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            // khi vuốt list view thì sẽ trả về hảm này
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount
                        && totalItemCount!=0
                        && isLoading == false
                        && limitData == false)
                {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        // biến đưa request lên cho server
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.GetProductUrl+String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int ProductID = 0;
                String ProductName = "";
                double Price = 0 ;
                String Image = "";
                String Descripton = "";
                String Content = "";
                int Quanliti = 0;
                int CategoryID = 0;
                if (response !=null && response.length() != 2)
                {
                    listViewListProduct.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0; i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ProductID = jsonObject.getInt("ProductID");
                            ProductName = jsonObject.getString("ProductName");
                            Price = jsonObject.getDouble("Price");
                            Image = jsonObject.getString("Image");
                            Descripton = jsonObject.getString("Description");
                            Content = jsonObject.getString("Content");
                            Quanliti = jsonObject.getInt("Quanliti");
                            CategoryID = jsonObject.getInt("CategoryID");
                            listProduct.add(new Product(ProductID, ProductName, Price, Image, Descripton, Content, Quanliti, CategoryID));
                            listProductAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    limitData = true;
                    listViewListProduct.removeFooterView(footerView);
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Đã hiển thị toàn bộ sản phẩm.");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param =new HashMap<String, String>();
                // đưa categoryID lên server
                param.put("categoryID",String.valueOf(productID));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    // nút trở về
    private void ActionToolbar() {
        toolbarListProduct.setTitle(getIntent().getStringExtra("categoryName"));
        setSupportActionBar(toolbarListProduct);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarListProduct.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetCategoryID() {
        productID = getIntent().getIntExtra("categoryID", -1);
    }


    private void Mappping() {
        toolbarListProduct = (Toolbar) findViewById(R.id.toolbarListProduct);
        listViewListProduct = (ListView) findViewById(R.id.listViewListProduct);
        listProduct = new ArrayList<>();
        listProductAdapter = new ListProductAdapter(getApplicationContext(), listProduct);
        listViewListProduct.setAdapter(listProductAdapter);
//        editTextFind = (EditText) findViewById(R.id.editTextFind);
//        editTextFind.setVisibility(View.INVISIBLE);
        // hiển thị thêm sản phẩm
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        myHandler = new MyHandler();
    }
    // 2 class xử lý hiển thị thêm dữ liệu
    public class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what)
            {
                case 0:
                    listViewListProduct.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }
}