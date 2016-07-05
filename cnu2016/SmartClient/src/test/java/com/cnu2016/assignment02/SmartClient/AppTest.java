package com.cnu2016.assignment02.SmartClient;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AppTest
{
    @Test
     public void ForCOTest()
    {
        SmartClient CO = new SmartClient();
        CO.setStatus();
        App.ForCO("on","4",CO);
        assertEquals(CO.getStatus(), Status.ON);
        App.ForCO("off","4",CO);
        assertEquals(CO.getStatus(), Status.OFF);
        
        CO.unsetStatus();
        App.ForCO("on","4",CO);
        assertEquals(CO.getStatus(), Status.ON);
        App.ForCO("off","4",CO);
        assertEquals(CO.getStatus(), Status.OFF);
        
    }
    @Test
    public void ForWHTest()
    {
        SmartClient WH = new SmartClient();
        WH.setStatus();
        App.ForWH("on","4",WH);
        assertEquals(WH.getStatus(), Status.ON);
        App.ForWH("off","4",WH);
        assertEquals(WH.getStatus(), Status.OFF);
        
        WH.unsetStatus();
        App.ForWH("on","4",WH);
        assertEquals(WH.getStatus(), Status.ON);
        App.ForWH("off","4",WH);
        assertEquals(WH.getStatus(), Status.OFF);
        
    }
    @Test
    public void ForACTest()
    {
        SmartClient AC = new SmartClient();
        AC.setStatus();
        App.ForAC("on","4",AC);
        assertEquals(AC.getStatus(), Status.ON);
        App.ForAC("off","4",AC);
        assertEquals(AC.getStatus(), Status.OFF);
        
        AC.unsetStatus();
        App.ForAC("on","4",AC);
        assertEquals(AC.getStatus(), Status.ON);
        App.ForAC("off","4",AC);
        assertEquals(AC.getStatus(), Status.OFF);
    }
}