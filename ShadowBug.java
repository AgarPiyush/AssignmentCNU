//package com.bugs;

/**
 * Created by Piyush on 7/20/16.
 */

import java.io.IOException;

/**
 * Rule: findbugs:DLS_DEAD_LOCAL_STORE_SHADOWS_FIELD
 *  Initialize circle object which is not used after initialization thoriws the error
 */
class Circle
{
    private int radius;
    Circle(int radius)
    {
        this.radius = radius;
    }
}

public class ShadowBug {

    void doRestTask()
    {
            // some code
    }
    void voilates() // Gives warning
    {
        Circle obj = new Circle(2); // obj is not used after initialization
        doRestTask();
    }
}

