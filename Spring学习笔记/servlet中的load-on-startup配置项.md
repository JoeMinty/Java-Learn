# `web.xml`中`<load-on-start>n</load-on-satrt>`作用



```
<servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring-mvc.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```



我们注意到它里面包含了这段配置：`<load-on-startup>1</load-on-startup>`，那么这个配置有什么作用呢？

## 作用如下：

  - 1.`load-on-startup`元素标记容器是否在启动的时候就加载这个`Servlet`(实例化并调用其`init()`方法)。

  - 2.它的值必须是一个整数，表示`Servlet`应该被载入的顺序。；

  - 3.当值为0或者大于0时，表示容器在启动时就加载并初始化这个`Servlet`。

  - 4.当值小于0或者没有指定时，则表示容器在该`Servlet`被请求时，才会去加载。

  - 5.正数的值越小，该`Servlet`的优先级就越高，应用启动时就优先加载。

  - 6.当值相同的时候，容器就会自己选择优先加载。


所以，`<load-on-startuo>x</load-on-startuo>`中x的取值1,2,3,4,5代表的是优先级，而非启动延迟时间。



通常大多数`Servlet`是在用户第一次请求的时候由应用服务器创建并初始化，但`<load-on-startup>n</load-on-startup>`   可以用来改变这种状况，根据自己需要改变加载的优先级！



**所以，提取出重点就是第一次访问`Controller`的速度大幅提升。**