# `Java`动态代理与`SpringAOP`


**动态代理**是动态地生成具体委托类的代理类实现对象

## 静态代理代码示例

```
interface Calculator {

    int add(int a,int b);
}

class ClaculatorImpl implements Calculator {

    @Override
    public int add(int a,int b) {
        return a+b;
    }
}

import java.lang.reflect.Proxy;

public class CalculatorProxy implements Calculator {

    private Calculator calculator;

    CalculatorProxy(Calculator calculator) {
        this.calculator = calculator;
    }

    public int add(int a,int b) {

        System.out.println("执行Add前时间："+System.currentTimeMillis());
        int result = calculator.add(a,b);

        System.out.println("执行Add后时间："+System.currentTimeMillis());

        return result;
    }

    public static void main(String[] args) {
        Calculator calculator = new ClaculatorImpl();
        CalculatorProxy calculatorProxy = new CalculatorProxy(calculator);
        System.out.println(calculatorProxy.add(3,4));

    }
}


```

## 动态代理代码示例

### 动态代理代码示例01
```
public class LogHandler implements InvocationHandler {

    Object obj;

    LogHandler(Object obj) {
        this.obj = obj;
    }

    public Object invoke(Object obj1,Method method,Object[] args) throws Throwable{
        this.doBefore();

        Object o = method.invoke(obj,args);

        this.doAfter();
        return o;
    }

    public void doBefore() {
        System.out.println("do this before");
    }

    public void doAfter() {
        System.out.println("do this after");
    }
}


public void testDynamicProxy() {
        Calculator calculator = new ClaculatorImpl();

        LogHandler lh = new LogHandler(calculator);

        Calculator proxy = (Calculator) Proxy.newProxyInstance(calculator.getClass().getClassLoader(),calculator.getClass().getInterfaces(),lh);

        System.out.println(proxy.add(1,1));
    }


```

### 动态代理代码示例02

```
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface IUserDao {
    void save();
    void find();
}

class UserDao implements IUserDao {

    @Override
    public void save() {
        System.out.println("模拟： 保存用户！ ");
    }

    @Override
    public void find() {
        System.out.println("查询用户");
    }
}

public class ProxyFactory {

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    // 获取当前执行的方法的方法名
                    String methodName = method.getName();
                    // 方法返回值
                    Object result = null;
                    if ("find".equals(methodName)) {
                        // 直接调用目标对象方法
                        result = method.invoke(target, args);
                    } else {
                        System.out.println("开启事务...");
                        // 执行目标对象方法
                        result = method.invoke(target, args);
                        System.out.println("提交事务...");
                    }
                    return result;
            }
        });

        return proxy;
    }

    public static void main(String[] args) {
        IUserDao target = new UserDao();

        System.out.println("目标对象： " +target.getClass());

        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstance();

        System.out.println("代理对象： " + proxy.getClass());

        proxy.save();

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        proxy.find();
    }
}


```

## 参考资料


http://www.importnew.com/31318.html
