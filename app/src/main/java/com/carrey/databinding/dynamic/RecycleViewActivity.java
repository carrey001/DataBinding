package com.carrey.databinding.dynamic;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carrey.databinding.R;
import com.carrey.databinding.databinding.ActivityRecycleviewBinding;
import com.carrey.databinding.databinding.RecycleItemBind;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carrey on 16/3/23.
 */
public class RecycleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRecycleviewBinding baseBinding = DataBindingUtil.setContentView(this, R.layout.activity_recycleview);

        baseBinding.recycle.setHasFixedSize(true);
        baseBinding.recycle.setLayoutManager(new LinearLayoutManager(this));
        baseBinding.recycle.setAdapter(new RecycleAdapter(mData));


    }

    private List<RecycleBean> mData =new ArrayList<>();

    private class RecycleAdapter extends RecyclerView.Adapter<Holder> {


        public RecycleAdapter(List<RecycleBean> recycleBeans) {
            recycleBeans.add(new RecycleBean("张1","男"));
            recycleBeans.add(new RecycleBean("张2","男"));
            recycleBeans.add(new RecycleBean("张3","男"));
            recycleBeans.add(new RecycleBean("张4","男"));
            recycleBeans.add(new RecycleBean("张5","男"));
            recycleBeans.add(new RecycleBean("张6","男"));
            recycleBeans.add(new RecycleBean("张7","男"));
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {


            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, null));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.bindData(mData.get(position));
        }


        @Override
        public int getItemCount() {
            return mData.size();


        }


    }

    private  class Holder extends RecyclerView.ViewHolder {

        private RecycleItemBind binding;

        public Holder(View itemView) {
            super(itemView);
            binding=  DataBindingUtil.bind(itemView);


        }


        public void bindData(final RecycleBean info) {
            binding.setItem(info);
//            binding.itemHome.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    RecycleViewActivity.this.startActivity(new Intent(MainActivity.this,info.clzz));
//                }
//            });
        }
    }
}
