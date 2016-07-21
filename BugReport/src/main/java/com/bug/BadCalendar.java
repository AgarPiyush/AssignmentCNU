package com.bug;
import com.bug.Worker;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BadCalendar {
    static SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss");


    public static String getFormattedDate(Date aDate) {
        return formatter.format(aDate);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            Worker worker = new Worker();
            worker.setName("Thread " + i);
            worker.start();
        }
    }
}
