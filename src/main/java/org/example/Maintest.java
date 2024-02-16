package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Maintest {
    public static void main(String[] args) {
//        ExecutorService executor = Executors.newFixedThreadPool(5); // 根据需要设置线程池大小
//
//        for (int i = 0; i < 5; i++) {
//            int finalI = i;
//            executor.execute(() -> {
//                System.out.println(finalI);
//            });
//        }
//
//
//        executor.shutdown();
//    }
        String login = Main.login();
        Main.record(login);
    }
    }
