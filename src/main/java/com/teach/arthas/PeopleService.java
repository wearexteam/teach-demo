package com.teach.arthas;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class PeopleService implements SmartLifecycle {

    public static String name = "PeopleService";

    public PeopleService() {
    }

    private Random random = new Random();

    private Timer timer;

    public List<People> filter(int age, String name, List<People> peopleList) {
        trace();
        return makePeople();
    }

    public static List<People> makePeople() {
        People p1 = new People().setAddress("beijing1").setName("zhang san").setAge(22);
        People p2 = new People().setAddress("beijing2").setName("zhang si").setAge(18);
        return Arrays.asList(p1, p2);
    }

    static List<People> makePeopleAsParams() {
        People p1 = new People().setAddress("shanghai1").setName("li san").setAge(22);
        People p2 = new People().setAddress("shanghai2").setName("li si").setAge(18);
        return Arrays.asList(p1, p2);
    }


    public void trace() {
        try {
            int randomInt = random.nextInt(8);
            TimeUnit.MILLISECONDS.sleep(randomInt * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("trace");
    }

    @Override
    public void start() {
        timer = new Timer("people-timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                filter(22, "li", List.of(new People().setAge(40).setAddress("shenzhen")));
                filter(18, "zhang", makePeopleAsParams());
            }
        }, 1000, 1000);
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}