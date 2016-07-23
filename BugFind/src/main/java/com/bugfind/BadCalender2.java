package com.bugfind;
/**
 * This class contains all the test cases of threads and Runnable
 */

// Thread and Runnable test
class Runnable1 extends Thread implements Runnable
{
    Runnable1()
    {

    }
    @Override
    public void run()
    {

    }
    void fun()
    {
        Thread obj = new Thread(new Runnable1());
        obj.run();
    }
}
// Runnable test
class Runnable2 implements Runnable
{
    @Override
    public void run()
    {

    }
    void fun()
    {
        Runnable2 obj = new Runnable2();
        obj.run();
    }
}


// Threads tests


class Thread1 extends  Thread
{
    void fun()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

            }
        }).run();
    }
}

class Thread2 extends Thread1
{
    @Override
    public void run()
    {

    }
    void fun2()
    {
        Thread2 obj = new Thread2();
        obj.run();
    }
}

public class BadCalender2 extends  Thread2{

    @Override
    public void run()
    {

    }

    public static void main(String[] args)
    {
        BadCalender2 obj = new BadCalender2();
        obj.run();
    }
}
