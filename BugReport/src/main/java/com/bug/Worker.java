package com.bug;
import java.util.Date;
import java.util.Random;

/**
 * Created by Piyush on 7/21/16.
 */
public class Worker extends Thread {
    private Random random = new Random();

    public void run() {
        long quitBy = System.currentTimeMillis() + 20000L; // in 20s
        while (System.currentTimeMillis() < quitBy) {
            // 1970-01-01 < date < 1970-01-02
            Date date = new Date(random.nextInt(86400000));
            String result = BadCalendar.getFormattedDate(date);
            try {
                // simulate some real work of varying length
                System.out.println(getName() + " working..." + result);
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
            }
        }
    }
}