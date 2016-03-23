package com.carrey.databinding.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.carrey.databinding.R;
import com.carrey.databinding.databinding.ActivityBaseBinding;

/**
 * Created by carrey on 16/3/22.
 */
public class BaseDataBindingActivity extends AppCompatActivity {

    private ActivityBaseBinding baseBinding;

    private ObservableUser observableUser = new ObservableUser();
    private PlanUser planUser = new PlanUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        baseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        baseBinding.setObservableUser(observableUser);
        baseBinding.setPlnUser(planUser);


    }

    public void showAll(View view) {

        baseBinding.setUser(new User("张三", 18));
        observableUser.setInfo("李四", 19);
        planUser.name.set("王五");
        planUser.age.set(20);

    }
}
