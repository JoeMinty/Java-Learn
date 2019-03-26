## 如何边遍历边移除`Collection`中的元素

## 正确的处理方式：

边遍历边修改`Collection`的**唯一正确方式**是使用`Iterator.remove()`方法，如下：

```
Iterator<Integer> it = list.iterator();
while(it.hasNext()){
    // do something
    it.remove();
}

```

## 一种最常见的错误代码如下：

```
for(Integer i : list){
    list.remove(i)
}

```

运行以上错误代码会报`ConcurrentModificationException`异常。这是因为当使用`foreach(for(Integer i : list))`语句时，会自动生成一个`iterator`来遍历该 `list`，但同时该`list`正在被`Iterator.remove()`修改。`Java`一般不允许一个线程在遍历`Collection`时另一个线程修改它。
