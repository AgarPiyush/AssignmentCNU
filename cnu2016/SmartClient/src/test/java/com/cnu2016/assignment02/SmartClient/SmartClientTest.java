package com.cnu2016.assignment02.SmartClient;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SmartClientTest 
{
    @Test
    public void setStatusTest()
    {
        SmartClient obj = new SmartClient();
        obj.setStatus();
        assertEquals(obj.getStatus(), Status.ON);
    }
    @Test
    public void unsetStatusTest()
    {
        SmartClient obj = new SmartClient();
        obj.unsetStatus();
        assertEquals(obj.getStatus(), Status.OFF);
    }
    @Test
    public void getStatusTest()
    {
        SmartClient obj = new SmartClient();
        obj.setStatus();
        Status check_status = obj.getStatus();
        assertEquals(check_status, Status.ON);
        obj.unsetStatus();
        check_status = obj.getStatus();
        assertEquals(check_status, Status.OFF);
    }
}