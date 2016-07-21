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
// Template
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

public synchronized void put(Object o) {
    while (buf.size()==MAX_SIZE) {
        wait(); // called if the buffer is full (try/catch removed for brevity)
    }
    buf.add(o);
    notify(); // called in case there are any getters or putters waiting
}

public synchronized Object get() {
    // Y: this is where C2 tries to acquire the lock (i.e. at the beginning of the method)
    while (buf.size()==0) {
        wait(); // called if the buffer is empty (try/catch removed for brevity)
        // X: this is where C1 tries to re-acquire the lock (see below)
    }
    Object o = buf.remove(0);
    notify(); // called if there are any getters or putters waiting
    return o;
}



