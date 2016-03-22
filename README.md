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



```














