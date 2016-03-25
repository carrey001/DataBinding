package com.carrey.databinding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carrey.databinding.base.BaseDataBindingActivity;
import com.carrey.databinding.databinding.ActivityMainBinding;
import com.carrey.databinding.databinding.ItemHomeBinding;
import com.carrey.databinding.dynamic.RecycleViewActivity;
import com.carrey.databinding.include.IncludeActivity;
import com.carrey.databinding.viewstub.ViewStubActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        homeInfos = new ArrayList<>();
        mainBinding.recycle.setHasFixedSize(true);
        mainBinding.recycle.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recycle.setAdapter(new HomeAdapter(homeInfos));

    }


    private List<HomeInfo> homeInfos;

    private class HomeAdapter extends RecyclerView.Adapter<Holder> {


        public HomeAdapter(List<HomeInfo> homeInfos) {
            homeInfos.add(new HomeInfo(BaseDataBindingActivity.class));
            homeInfos.add(new HomeInfo(IncludeActivity.class));
            homeInfos.add(new HomeInfo(RecycleViewActivity.class));
            homeInfos.add(new HomeInfo(ViewStubActivity.class));
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {


            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent,false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.bindData(homeInfos.get(position));
        }


        @Override
        public int getItemCount() {
            return homeInfos.size();


        }


    }

    private  class Holder extends RecyclerView.ViewHolder {

        private ItemHomeBinding binding;

        public Holder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }


        public void bindData(final HomeInfo info) {
            binding.setHomeInfo(info);
            binding.itemHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.this.startActivity(new Intent(MainActivity.this,info.clzz));
                }
            });
        }
    }


}
