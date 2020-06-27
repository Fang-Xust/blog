package frank.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SafeThread {

    private static final int NUM = 20;
    private static final int COUNT = 10000;
//    private static ConcurrentHashMap<String, AtomicInteger> MAP = new ConcurrentHashMap<>();
//    private static AtomicInteger SUM = new AtomicInteger();

    public static void main(String[] args) {
        ConcurrentHashMap<String, AtomicInteger> MAP = new ConcurrentHashMap<>();
        // 同时启动20个线程，每个线程对同一个变量执行操作：循环10000次，每次循环++操作
        // 所有线程执行完毕之后，打印变量值，检查是否是预期的结果（20*10000）
        for(int i=0; i<=10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    for(int j=0; j<COUNT; j++){
//                        SUM.incrementAndGet();
                        AtomicInteger init = new AtomicInteger();
                        AtomicInteger exist = MAP.putIfAbsent("path", init);
                        if(exist == null){
                            exist = init;
                        }
                        exist.incrementAndGet();
//                    }
                }
            }).start();
        }
        while(Thread.activeCount() > 1){//debug运行
            Thread.yield();
        }
//        System.out.println(SUM);
        System.out.println("===="+MAP.get("path"));
    }
}
