package com.moons.Demo;

public class RuntimeConstanPoolOOM {
    public static void main(String[] args) {
        String str2 = new String("str")+new String("01");
        str2.intern();
        String str1 = "str01";
        System.out.println(str2==str1);


        String str3 = new String("str03");
        str3.intern();
        String str4 = "str03";
        System.out.println(str3==str4);
    }
}
