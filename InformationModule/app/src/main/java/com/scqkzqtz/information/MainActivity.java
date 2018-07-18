package com.scqkzqtz.information;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InformationFragment informationFragment = new InformationFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.short_stock_container,informationFragment).commit();

    }
}
