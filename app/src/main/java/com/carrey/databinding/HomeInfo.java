package com.carrey.databinding;

/**
 * Created by carrey on 16/3/23.
 */
public class HomeInfo {

    public Class clzz;
    public String className;

    public HomeInfo(Class clzz) {
        this.clzz = clzz;
        className=clzz.getName();
    }
}
