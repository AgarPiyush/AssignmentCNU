package com.bugs;

/**
 * Created by Piyush on 7/20/16.
 */

/**
 * Rule: squid:squid:S2446
 * When multiple threads are waiting to be notified during sleep on same monitor, notify() would awake one of the
 * thread in random order, it might not be the one desired. Therefore, we should instead use notifyAll() to ensure
 * all the thread wake up on that monitor, thus awekening the required thread.
 */

class notifyAll extends Thread{

    @Override
    public void run()
    {
        synchronized(this)
        {
            // ...
            notify();  // Noncompliant, as this will only wake up a single random thread, instead use notifyAll().
                        // It might be possible that the thread that woke up is waiting for some other lock, thereby causing deadlock
        }
    }
}



