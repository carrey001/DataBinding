##DataBinding 再也不用写findviewbyId了
[google 官网地址](http://developer.android.com/tools/data-binding/guide.html) 需要梯子。。。

<br>
这篇文章主要介绍怎么使用databinding。layout 和logic 之间代码关系写法。


- 这个库是需要api7以上（android 2.1+）

- gradle 插件1.5.0-alpha1+

##构建环境##

sdk manager更新support library 并且 在项目中添加
dataBinding开关

``` 

android｛

...
dataBinding{
enable = true
}
}

```

如果你有个使用databinding的lib库，你的主项目的build.gradle也需要上述设置。
对于android studio 的版本要求Android studio 1.3+

## DataBinding layout files

不废话事例如下：
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>


        <variable
            name="user"
            type="com.carrey.databinding.base.User" />

        <variable
            name="observableUser"
            type="com.carrey.databinding.base.ObservableUser" />


        <import
            alias="planUsers"
            type="com.carrey.databinding.base.PlanUser" />

        <variable
            name="plnUser"
            type="planUsers" />

    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="5dp"
            android:text="common"
            android:textSize="16sp" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="5dp"
            android:text="@{user.name}"
            android:textSize="16sp" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="5dp"
            android:text="@{String.valueOf(user.age)}"
            android:textSize="16sp" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="5dp"
            android:text="observble"
            android:textSize="16sp" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="5dp"
            android:text="@{observableUser.name}"
            android:textSize="16sp" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="5dp"
            android:text="@{String.valueOf(observableUser.age)}"
            android:textSize="16sp" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="5dp"
            android:text="paln"
            android:textSize="16sp" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="5dp"
            android:text="@{plnUser.name}"
            android:textSize="16sp" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="5dp"
            android:text="@{String.valueOf(plnUser.age)}"
            android:textSize="16sp" />

        <Button
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:onClick="showAll"
            android:text="点击显示全部"/>


    </LinearLayout>
</layout>

dataBinding 的layout 已经有些不同了， 它上以<layout></layout> 为根节点。 并且紧跟着一个<data></data> 和一个View的根目录。

我们在**data** 元素节点下来描述一个可能在后面用的类对象，后面使用“@{}”来引用这个类点成员变量或者方法。看下面👇

```
<data>
        <variable
            name="user"
            type="com.carrey.databinding.base.User" />

        <variable
            name="observableUser"
            type="com.carrey.databinding.base.ObservableUser" />


        <import
            alias="planUsers"
            type="com.carrey.databinding.base.PlanUser" />

        <variable
            name="plnUser"
            type="planUsers" />

    </data>

。。。
 <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:padding="5dp"
            android:text="@{user.name}"
            android:textSize="16sp" />           


```

我们看到上面User 的对象有三种：

- common JavaBean

```
package com.carrey.databinding.base;

/**
 * Created by carrey on 16/3/23.
 */
public class User {

    public String name;
    public int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```
- 继承了BaseObservable 

```
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
```
- 带特殊成员的类

```
package com.carrey.databinding.base;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by carrey on 16/3/23.
 */
public class PlanUser {

    public ObservableField<String> name=new ObservableField<>();
    public ObservableInt age =new ObservableInt();
}

```

## Binding Data

好了 到这里 我们layout 和javaBean 都已经写好。 现在就需要把数据绑定到页面了。通常我们在activity 中都会有一步setContentView（layoutID）的方法。 现在我们要替 使用DataBindinig 中的方法来替换： 👇看这里看这里

```
 private ActivityBaseBinding baseBinding;

    private ObservableUser observableUser = new ObservableUser();
    private PlanUser planUser = new PlanUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	//	setContentView();

        baseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        baseBinding.setObservableUser(observableUser);
        baseBinding.setPlnUser(planUser);


    }

```

DataBinding 会自动生成一class文件 名字会和layout文件名类似：（activity_base.xml 生成的class 名称为ActivityBaseBinding） 这个文件会包含一切从layout 中的view 和view 绑定的表达式或者数据。 着意味着我🚪使用这个binding来做view查找以及更新对操作。

举个例子：我们在这个页面加了个按钮。 点击后 会 更新User相关的数据。那 我们怎么更新页面呢。 看代码

```
public void showAll(View view) {

        baseBinding.setUser(new User("张三", 18));
        observableUser.setInfo("李四", 19);
        planUser.name.set("王五");
        planUser.age.set(20);

    }
```
这个方法为view的点击事件。 我们可以看到。baseBinding.setUser();可以更新数据和界面。
observable 更新了数据 直接就更新来页面。
planUser 也上同样的。 更新数据就更新来页面。

[传送门activity_base.xml](https://github.com/carrey001/DataBinding/tree/master/app/src/main/res/layout/activity_base.xml)


[传送门BaseDataBindingActivity.java](https://github.com/carrey001/DataBinding/blob/master/app/src/main/java/com/carrey/databinding/base/BaseDataBindingActivity.java)


##listView RevcycleView(dynamic binding)


































