# `Java`单例模式

## 单例模式的两种简单实现

### 双检锁模式

```

public class Singleton {

  private static volatile Singleton singleton = null;
  private Singleton() {
  }
  
  public static Singleton getSingleton() {
    if (singleton == null) { // 尽量避免重复进入同步块
      synchronized (Singleton.class) { // 同步.class，意味着对同步类方法调用
        if (singleton == null) {
          singleton = new Singleton();
        }
      }
    }
    return singleton;
  }
}

```


### 内部类实现模式

```

public class Singleton {
  private Singleton(){}
  public static Singleton getSingleton(){
    return Holder.singleton;
  }
  
  private static class Holder {
    private static Singleton singleton = new Singleton();
  }
}

```
