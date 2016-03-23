package com.carrey.databinding.base;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.carrey.databinding.BR;

/**
 * Created by carrey on 16/3/23.
 */
public class ObservableUser extends BaseObservable {

    private String name;
    private int age;

    @Bindable
    public String getName() {
        return name;
    }

    public void setInfo(String name,int age) {
        this.name = name;
        this.age =age;
        notifyPropertyChanged(BR.name);
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public int getAge() {
        return age;
    }

}
