package com.example.mobileshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mobileshop.R;
import com.example.mobileshop.model.Cart;
import com.example.mobileshop.model.Product;
import com.example.mobileshop.model.User;
import com.example.mobileshop.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ProductDetail extends AppCompatActivity {

    Toolbar toolbarProductDetail;
    ImageView imageViewProductDetail;
    TextView txtProductName , txtProductPrice, txtProductDescription;
    Spinner spinner;
    Button btnAddtoCrat;
    Button btnReadBook;

    // các biến để gán dữ liệu tạm thời
    int productID = 0;
    String productName = "";
    double price = 0;
    String image = "";
    String descripton = "";
    String content = "";
    int quanliti  = 0;
    int categoryID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Mapping();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        AddToCart();
        ReadBook();
    }

    private void ReadBook() {
        btnReadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookContentActivity.class);
                intent.putExtra("Content", content);
                intent.putExtra("ProductName", productName);
                startActivity(intent);
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


    private void AddToCart() {
        btnAddtoCrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nếu giỏ hàng có dữ liệu
                if(MainActivity.listCart.size()>0)
                {
                    int quanlitiInCart = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    // duyệt từng phần tử trong giỏ hàng
                    for (int i=0 ;i< MainActivity.listCart.size();i++)
                    {
                        // nếu tồn tại sản phẩm trùng
                        if(MainActivity.listCart.get(i).getProductID() == productID)
                        {
                            // đặt lại số lượng
                            int temp =MainActivity.listCart.get(i).getQuanliti();
                            int newQuanliti = temp + quanlitiInCart;
                            if(newQuanliti <= quanliti)
                            {
                                MainActivity.listCart.get(i).setQuanliti(newQuanliti);
                                if (MainActivity.listCart.get(i).getQuanliti()>10)
                                {
                                    MainActivity.listCart.get(i).setQuanliti(10);
                                    CheckConnection.ShowToast_Short(getApplicationContext(), "Số lượng mua tối đa là 10 sản phẩm");
                                }
                                // đặt lại giá
                                MainActivity.listCart.get(i).setProductPrice(price * MainActivity.listCart.get(i).getQuanliti());
                                exists = true;
                                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                CheckConnection.ShowToast_Short(getApplicationContext(),"Số lượng sản phẩm hiện tại chỉ còn: "+ quanliti+" sản phẩm!");
                                exists = true;
                            }

                        }
                    }
                    // nếu sản phẩm chưa tồn tại
                    if(exists == false)
                    {
                        if (quanlitiInCart <= quanliti)
                        {
                            double totalPrice = quanlitiInCart * price;
                            MainActivity.listCart.add(new Cart(productID, productName, totalPrice, image, quanlitiInCart, quanliti));
                            Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Số lượng sản phẩm hiện tại chỉ còn: "+ quanliti+" sản phẩm!");
                        }

                    }
                }
                // nếu chưa có dữ liệu trong giỏ hàng
                else
                {

                    int quanlitiInCart = Integer.parseInt(spinner.getSelectedItem().toString());
                    if(quanlitiInCart <= quanliti) {
                        double totalPrice = quanlitiInCart * price;
                        MainActivity.listCart.add(new Cart(productID, productName, totalPrice, image, quanlitiInCart, quanliti));
                        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        CheckConnection.ShowToast_Short(getApplicationContext(),"Số lượng sản phẩm hiện tại chỉ còn: "+ quanliti+" sản phẩm!");
                    }
                }
//                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] quanliti = new Integer[]{
                1,2,3,4,5,6,7,8,9,10
        };
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.select_dialog_item, quanliti);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        Product product = (Product) getIntent().getSerializableExtra("productInformation");
        productID = product.getProductID();
        productName = product.getProductName();
        price = product.getPrice();
        image = product.getImage();
        descripton = product.getDescripton();
        content = product.getContent();
        quanliti = product.getQuanliti();
        categoryID = product.getCategoryID();
        // đưa dữ liệu vào view
        txtProductName.setText(productName);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtProductPrice.setText("Giá: "+ decimalFormat.format(price)+ " Đ");
        txtProductDescription.setText(descripton);
        Picasso.get().load(image)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(imageViewProductDetail);
    }

    private void ActionToolbar() {

        setSupportActionBar(toolbarProductDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarProductDetail .setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Mapping() {
        toolbarProductDetail = (Toolbar) findViewById(R.id.toolbarProductDetail);
        imageViewProductDetail = (ImageView) findViewById(R.id.imageProductDetail);
        txtProductName = (TextView) findViewById(R.id.txtViewProductDetailName);
        txtProductPrice = (TextView) findViewById(R.id.txtViewProductDetailPrice);
        txtProductDescription = (TextView) findViewById(R.id.txtViewProductDetailDescription);
        btnReadBook = (Button) findViewById(R.id.btnReadBook);
        spinner = (Spinner) findViewById(R.id.spinnerProductDetail);
        btnAddtoCrat = (Button) findViewById(R.id.btnAddToCartInProductDetail);
    }
}