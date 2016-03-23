package com.carrey.databinding.include;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.carrey.databinding.R;
import com.carrey.databinding.databinding.ActivityIncludeBinding;

/**
 * Created by carrey on 16/3/23.
 */
public class IncludeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIncludeBinding baseBinding = DataBindingUtil.setContentView(this, R.layout.activity_include);

    }
}
