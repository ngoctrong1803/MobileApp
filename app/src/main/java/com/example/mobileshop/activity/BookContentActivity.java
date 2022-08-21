package com.example.mobileshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mobileshop.R;

public class BookContentActivity extends AppCompatActivity {

    Toolbar toolbarBookContent;
    TextView textViewBookContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_content);
        Mapping();
        ActionToolbar();
        GetInformation();
    }
    // nút trở về
    private void ActionToolbar() {
        toolbarBookContent.setTitle(getIntent().getStringExtra("ProductName"));
        setSupportActionBar(toolbarBookContent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarBookContent.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetInformation() {
        toolbarBookContent.setTitle(getIntent().getStringExtra("ProductName"));
        if(getIntent().getStringExtra("Content")== null)
        {
            textViewBookContent.setText("nội dung hiện đang được cập nhật! vui lòng qua lại sau.");
        }
        else
        {
            textViewBookContent.setText(getIntent().getStringExtra("Content"));
        }

    }

    private void Mapping() {
        textViewBookContent = findViewById(R.id.txtViewBookContent);
        toolbarBookContent = findViewById(R.id.toolbarBookContent);

    }
}