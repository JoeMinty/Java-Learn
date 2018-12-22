# 如何将`HashMap`按照`value`值排序

这里要用到一个`Comparator`的接口，里面只有一个方法，`compare（）`,我们实现这个接口就好，很简单

```
private class ValueComparator implements Comparator<Map.Entry<String, Integer>>  
	    {  
	        public int compare(Map.Entry<String, Integer> mp1, Map.Entry<String, Integer> mp2)   
	        {  
	            return mp2.getValue() - mp1.getValue();  
	        }  
	    }  

```

这里为啥用`mp2-mp1`呢，因为我想要的排序结果是从大到小，默认的是从小到大排序。这样调换一下位置就不要再写一个循环，倒叙输出了。
使用的时候也很简单，调用`Collections`工具类的`sort`方法时，传入我们自己写的这个实现类的对象，作为参数就可以了。先根据实际需求，创建一个`List`。


```
Map<String,Integer> map=new HashMap<>();
List<Map.Entry<String,Integer>> list=new ArrayList<>();

```

然后直接就可以排序啦。

```
list.addAll(map.entrySet());
ValueComparator vc=new ValueComparator();
Collections.sort(list,vc);

```

很简单吧。
下面附上一个完整的小程序

```
import java.util.*;
class Test
{
	private static class ValueComparator implements Comparator<Map.Entry<String,Integer>>
	{
		public int compare(Map.Entry<String,Integer> m,Map.Entry<String,Integer> n)
		{
			return n.getValue()-m.getValue();
		}
	}
	public static void main(String[] args) 
	{
		Map<String,Integer> map=new HashMap<>();
		map.put("a",1);
		map.put("c",3);
		map.put("b",5);
		map.put("f",7);
		map.put("e",6);
		map.put("d",8);
		List<Map.Entry<String,Integer>> list=new ArrayList<>();
		list.addAll(map.entrySet());
		Test.ValueComparator vc=new ValueComparator();
		Collections.sort(list,vc);
		for(Iterator<Map.Entry<String,Integer>> it=list.iterator();it.hasNext();)
		{
			System.out.println(it.next());
		}
	}
}

```

输出：

```
d=8
f=7
e=6
b=5
c=3
a=1
```
