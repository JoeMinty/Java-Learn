使用`java`程序实现`Shell`中的如下功能

```
cat /tmp/log/log.log |grep "moons"|sort|uniq -c |sort -nc
```


**代码示例：**

```
import java.io.*;
import java.util.*;


public class SearchWord {

    private static class ValueComparator implements Comparator<Map.Entry<String,Integer>>
    {
        public int compare(Map.Entry<String,Integer> m,Map.Entry<String,Integer> n)
        {
            return n.getValue()-m.getValue();
        }
    }


    public static void main(String[] args) throws FileNotFoundException ,IOException {

        HashMap<String, Integer> hm = new HashMap<>();

        BufferedReader bfr = new BufferedReader(new FileReader("test.log"));
        String str = null;

        while ((str = bfr.readLine()) != null) {
            if (str.indexOf("moons") != -1) {
                if (hm.containsKey(str)) {
                    int temp_value = hm.get(str);
                    hm.put(str, temp_value + 1);
                } else {
                    hm.put(str, 1);
                }

            }
            continue;

        }

        List<Map.Entry<String,Integer>> list=new ArrayList<>();
        list.addAll(hm.entrySet());
        SearchWord.ValueComparator vc=new ValueComparator();
        Collections.sort(list,vc);
        //System.out.println(list.iterator().hasNext());
        for(Iterator<Map.Entry<String,Integer>> it=list.iterator();it.hasNext();)
        {
            System.out.println(it.next());
        }


    }
}


```
