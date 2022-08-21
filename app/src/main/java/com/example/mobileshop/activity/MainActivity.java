package com.example.mobileshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileshop.R;
import com.example.mobileshop.adapter.CategoryAdapter;
import com.example.mobileshop.adapter.ProductAdapter;
import com.example.mobileshop.model.Cart;
import com.example.mobileshop.model.Category;
import com.example.mobileshop.model.Product;
import com.example.mobileshop.model.User;
import com.example.mobileshop.ultil.CheckConnection;
import com.example.mobileshop.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;


import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbarTrangchu;
    ViewFlipper viewFlipperTrangchu;
    RecyclerView  recyclerViewTrangchu;
    NavigationView navigationViewTrangchu;
    ListView listViewTrangchu;
    DrawerLayout drawerLayoutTrangchu;

    //Danh sách các thể loại
    ArrayList<Category> listCategory;
    CategoryAdapter categoryAdapter;
    int Id= 0;
    String CategoryName= "";
    String CategoryImage="";
    //danh sách các sản phẩm mới
    ArrayList<Product> listNewProducts;
    ProductAdapter newProductAdapter;

    //giỏ hàng
    public static ArrayList<Cart> listCart;

    //tài khoản đăng nhập
    public static User user= new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();

        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            ActionBar();
            ActionViewFlipper();
            GetDataCategory();
            GetNewProducts();
            CatchOnItemListView();
        }
        else
        {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra kết nối");
        }

    }

    /* --------------------------------------PHẦN MENU------------------------------------------------*/
    // gắn giỏ hàng vào Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        //chỗ này quan trọng để làm đăng kí đăng nhập
        if(user.getFullName() !=null)
        {
            //ẩn đi login
//            menu.findItem(R.id.menuLogin).setVisible(false);
            menu.findItem(R.id.menuLogin).setTitle(user.getFullName());
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
                if (user.getFullName()==null)
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
                if (user.getFullName()==null)
                {
                    // chuyển sang màn hình đăng kí
                    Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intentRegister);
                }
                else
                {
                    user = new User();
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Đã đăng xuất!");
                    Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentMain);
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    /*--------------------------------------------------HẾT MENU--------------------------------------------------------*/

    // click các item trên list view
    private void CatchOnItemListView() {
        listViewTrangchu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng kiểu tra kết nối mạng");
                        }
                        drawerLayoutTrangchu.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this, ListProductActivity.class);
                            intent.putExtra("categoryID", listCategory.get(position).getCategoryID());
                            intent.putExtra("categoryName", listCategory.get(position).getCategoryName());
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng kiểu tra kết nối mạng");
                        }
                        drawerLayoutTrangchu.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this, ListProductActivity.class);
                            intent.putExtra("categoryID", listCategory.get(position).getCategoryID());
                            intent.putExtra("categoryName", listCategory.get(position).getCategoryName());
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng kiểu tra kết nối mạng");
                        }
                        drawerLayoutTrangchu.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this, ListProductActivity.class);
                            intent.putExtra("categoryID", listCategory.get(position).getCategoryID());
                            intent.putExtra("categoryName", listCategory.get(position).getCategoryName());
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng kiểu tra kết nối mạng");
                        }
                        drawerLayoutTrangchu.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng kiểu tra kết nối mạng");
                        }
                        drawerLayoutTrangchu.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng kiểu tra kết nối mạng");
                        }
                        drawerLayoutTrangchu.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void GetNewProducts() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest  jsonArrayRequest = new JsonArrayRequest(Server.NewProductsUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response !=null)
                {
                    int ProductID= 0;
                    String ProductName = "";
                    double Price = 0;
                    String Image = "";
                    String Description = "";
                    String Content ="";
                    int Quanliti = 0;
                    int CategoryID = 0;
                    for(int i=0; i < response.length();i++)
                    {
                        try
                        {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ProductID = jsonObject.getInt("ProductID");
                            ProductName = jsonObject.getString("ProductName");
                            Price = jsonObject.getDouble("Price");
                            Image = jsonObject.getString("Image");
                            Description = jsonObject.getString("Description");
                            Content = jsonObject.getString("Content");
                            Quanliti = jsonObject.getInt("Quanliti");
                            CategoryID = jsonObject.getInt("CategoryID");
                            listNewProducts.add(new Product(ProductID, ProductName, Price, Image, Description, Content, Quanliti, CategoryID));
                            newProductAdapter.notifyDataSetChanged();

                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDataCategory() {
        RequestQueue requestQueue  = Volley.newRequestQueue(getApplicationContext());
        // lấy dữ liệu từ json
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.CategoryUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listCategory.add(new Category(0,"Trang Chủ","https://cdn.pixabay.com/photo/2020/02/23/11/17/icon-4873054_1280.png"));
                categoryAdapter.notifyDataSetChanged();
                if (response !=null){
                    for(int i =0; i< response.length();i++)
                    {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Id = jsonObject.getInt("CategoryID");
                            CategoryName = jsonObject.getString("CategoryName");
                            CategoryImage = jsonObject.getString("Image");
                            listCategory.add(new Category(Id,CategoryName,CategoryImage));
                            categoryAdapter.notifyDataSetChanged();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }

                    }
                }
                listCategory.add(new Category(0,"Liên Hệ","https://e7.pngegg.com/pngimages/579/482/png-clipart-ringing-phone-logo-whatsapp-dialer-android-google-contacts-phone-logo-blue-text-thumbnail.png"));
                categoryAdapter.notifyDataSetChanged();
                listCategory.add(new Category(0,"Thông Tin","https://upload.wikimedia.org/wikipedia/commons/5/54/Information.png"));
                categoryAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {

            ArrayList<String> listBanner = new ArrayList<>();
            listBanner.add("http://bibeco.vn/wp-content/uploads/2018/04/banner2-1.jpg");
            listBanner.add("https://img.kam.vn/images/414x0/a9aa9e6a471f42b482d22277a60b39e2/image/newshop-sale-den-50-tat-ca-sach-ngoai-ngu-anh-phap-nhat-trung-han.jpg");
            listBanner.add("https://lh3.googleusercontent.com/ZQc0YqiDGezODqerE9Gk8dh_d__m8hYRchmWjXZxtSG4LJmDgj-jh85RCbaWxPqtcPD21x8QFKfu7j5RF8M76J1fC_zpzvzWCFbuZMjioFjem6wZ-ci0vro7i4HZNYNa82_6wSo");
            listBanner.add("https://haymora.com/blog/wp-content/uploads/2019/05/haymora-danh-gia-cong-ty.jpg");

            for(int i=0; i<listBanner.size(); i++)
            {
                ImageView imageView = new ImageView(getApplicationContext());
                Picasso.get().load(listBanner.get(i)).into(imageView);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                viewFlipperTrangchu.addView(imageView);
            }

            viewFlipperTrangchu.setFlipInterval(5000);
            viewFlipperTrangchu.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipperTrangchu.setInAnimation(animation_slide_in);
        viewFlipperTrangchu.setOutAnimation(animation_slide_out);

    }

    // hiển thị navigation
    private void ActionBar()
    {
        setSupportActionBar(toolbarTrangchu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTrangchu.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarTrangchu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutTrangchu.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Mapping() {
        toolbarTrangchu =(Toolbar) findViewById(R.id.toolbarTrangchu);
        viewFlipperTrangchu = (ViewFlipper) findViewById(R.id.viewFlipperTrangchu);
        recyclerViewTrangchu = (RecyclerView) findViewById(R.id.recyclerViewTrangchu);
        navigationViewTrangchu = (NavigationView) findViewById(R.id.navigationViewTrangchu);
        listViewTrangchu = (ListView) findViewById(R.id.listviewTrangchu);
        drawerLayoutTrangchu = (DrawerLayout) findViewById(R.id.drawerLayoutTrangchu);
        listCategory = new ArrayList<>();

        //Hiển thị các thể loại sản phẩm
        categoryAdapter= new CategoryAdapter(listCategory, getApplicationContext());


        listViewTrangchu.setAdapter(categoryAdapter);

        //Hiển thị các sản phẩm mới
        listNewProducts = new ArrayList<>();
        newProductAdapter = new ProductAdapter(getApplicationContext(),listNewProducts);
        recyclerViewTrangchu.setHasFixedSize(true);
        recyclerViewTrangchu.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewTrangchu.setAdapter(newProductAdapter);
        if(listCart !=null){

        }
        else{
            listCart =new ArrayList<>();
        }


    }
}