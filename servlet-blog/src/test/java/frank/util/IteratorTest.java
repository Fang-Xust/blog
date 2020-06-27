package frank.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IteratorTest {

    public static void main(String[] args) {

        //非线程安全的数据结构，内部的迭代器都是一种fail-fast快速失败的迭代器
        //在迭代操作非线程安全的数据结构时，修改数据操作，会导致下一次的迭代抛异常
        //--->多线程对同一个非线程安全的数据结构操作，一个线程遍历，如果另一个线程修改了，下次迭代时也会抛异常
        List<Integer> list = new ArrayList<>();
        int len = 10000;
        for(int i=0; i<len; i++){
            list.add(i);
        }
        //实现了Iterable接口的数据结构，都可以使用for循环遍历，底层实现是基于迭代器来遍历
        for(Integer i : list){
            if(i == 3){
                list.remove(3);
            }
            System.out.println(i);
        }
        //map遍历是通过内部的Entry来遍历，也适用于上边说的快速失败迭代器
        HashMap<String, Integer> map = new HashMap<>();
        for(Map.Entry<String, Integer> entry : map.entrySet()){
        }
    }

//    private static Map<String, Integer> MAP = new HashMap<>();
//
//    static{
//        for(int i=0; i<100000; i++){
//            MAP.put("A"+i, i);
//        }
//    }

//    public static void main(String[] args) {
//        new Thread(()->{
//            for(int i=0; i<100000; i++){
//                MAP.put("X"+i, i);
//            }
//
//        }).start();
//        new Thread(()->{
//            for(Entry<String, Integer>)
//        }).start();
//    }
}
