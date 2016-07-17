package com.cnu2016.assignment02.SmartClient;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SmartClientTest 
{
    @Test
    public void setStatus()
    {
        SmartClient obj = new SmartClient();
        obj.setCurrState(Status.ON);
        assertEquals(obj.getCurrState(), Status.ON);
        obj.setCurrState(Status.OFF);
        assertEquals(obj.getCurrState(), Status.OFF);

    }
}