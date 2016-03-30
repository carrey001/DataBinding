##DataBinding 再也不用写findviewbyId了
[google 官网地址](http://developer.android.com/tools/data-binding/guide.html) 需要梯子。。。

<br>
这篇文章主要介绍怎么使用databinding。layout 和logic 之间代码关系写法。


- 这个库是需要api7以上（android 2.1+）

- gradle 插件1.5.0-alpha1+

##构建环境##

sdk manager更新support library 并且 在项目中添加
dataBinding开关

    
    android｛
    
    ...
    dataBinding{
    enable = true
    }
    }


   
如果你有个使用databinding的lib库，你的主项目的build.gradle也需要上述设置。
对于android studio 的版本要求Android studio 1.3+

## DataBinding layout files

不废话事例如下：
    
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
    
    


我们看到上面User 的对象有三种：

- common JavaBean
    
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

- 继承了BaseObservable 
    
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


##imports
在<data> 节点下使用 和java文件中的导入包类似（来自官方文档）

	<data>
	    <import type="android.view.View"/>
	</data>
	
	<TextView
	   android:text="@{user.lastName}"
	   android:layout_width="wrap_content"
	   android:layout_height="wrap_content"
	   android:visibility="@{user.isAdult ? View.VISIBLE : View.GONE}"/>


设置别名
	
	
	<import type="android.view.View"/>
	<import type="com.example.real.estate.View"
	        alias="Vista"/>


在data节点下 有个很重要的元素 variable

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

我们定义了一些成员变量。并可以在xml 控件中使用。 当这些成员的某些值改变时。会通知View去刷新。

##Custom Binding Class Names
通常情况下，生成的binding class 的名称来自layout文件的名字。按照文件名 去掉下划线_ 并加上后缀“Binding”。 如果我们想生成特定名称的binding class 只需要加上一句话。

	<data class="ContactItem">
	    ...
	</data>
    
则生成的binding class 为ContactItem。 我们在activity中得到的binding class就为为ContactItem。

##include
 在xml文件中我们可以通过include 来引用一个已经存在的layout 到当前的layout。
在dataBinding 的layout中我们依然可以使用此种方式。如果需要把当前layout中的variable 传递到include layout文件中 需要我们使用一下方法

```
    		<?xml version="1.0" encoding="utf-8"?>
    		<layout xmlns:android="http://schemas.android.com/apk/res/android"
    		xmlns:bind="http://schemas.android.com/apk/res-auto">
    		
    		<data class=".IncludeBeanBinding">
    		
    		<variable
    		name="includeBean"
    		type="com.carrey.databinding.include.IncludeBean" />
    		</data>
    		
    		<LinearLayout
    		android:layout_width="match_parent"
    		android:layout_height="match_parent"
    		android:orientation="vertical">
    		
    		<include
    		layout="@layout/include_item"
    		bind:includeBean="@{includeBean}" />
    		
    		</LinearLayout>
    		</layout>

```
 使用namespace bind 把当前variable 传递到 include layout file。


 备注：
Data binding does not support include as a direct child of a merge element. For example, the following layout is not supported:

<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:bind="http://schemas.android.com/apk/res-auto">
   <data>
       <variable name="user" type="com.example.User"/>
   </data>
   <merge>
       <include layout="@layout/name"
           bind:user="@{user}"/>
       <include layout="@layout/contact"
           bind:user="@{user}"/>
   </merge>
</layout>


## ViewStub
ViewStub 的xml数据传递和include类似

```
    <layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:bind="http://schemas.android.com/tools">

    <data>

        <variable
            name="viewBean"
            type="com.carrey.databinding.viewstub.ViewBean" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <ViewStub
            android:id="@+id/view_stub"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout="@layout/item_view_stub11"
            bind:sss="@{viewBean}" />

    </LinearLayout>
</layout>

```

在activity中 我们得到bingding对象。

通过binding 对象拿到viewStub 去inflate。好像并没有什么不对。 但是运行的时候会一直报错。。。。

看官方文档  在那么多的英文字面 中发现了ViewStubProxy 于是我 进行了一下操作。

	((ViewStubProxy)baseBinding.viewStub).getViewStub().inflate();

 虽然一直有条红线在下方 ，但是神奇的运行了起来。

点击查看报红的原因，viewStub 不能转换成ViewStubProxy 。。。。

下次分析分析源码 看看原因。。。。
 
##listView RevcycleView(dynamic binding)

listView 或者recycleView 操作其实和普通的view类似。 注意的区别是在adapter中。

我直接在贴adapter的代码了。。看这里--看这里--

```
 private  class Holder extends RecyclerView.ViewHolder {

        private RecycleItemBind binding;

        public Holder(View itemView) {
            super(itemView);
            binding=  DataBindingUtil.bind(itemView);


        }


        public void bindData(final RecycleBean info) {
            binding.setItem(info);
        }
    }

```

在创建holder对象的时候。使用dataBinding。 然后搞定。。。。 就这么简单


<br><line>
----

##以上就是dataBinding的基本应用。。
[demo地址:git@github.com:carrey001/DataBinding.git](git@github.com:carrey001/DataBinding.git)











































































