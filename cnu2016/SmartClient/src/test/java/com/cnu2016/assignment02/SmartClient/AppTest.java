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
        App obj = new App();
        obj.ForCO("on","4",CO);
        assertEquals(CO.getStatus(), Status.ON);
        obj.ForCO("off","4",CO);
        assertEquals(CO.getStatus(), Status.OFF);
        
        CO.unsetStatus();
        obj.ForCO("on","4",CO);
        assertEquals(CO.getStatus(), Status.ON);
        obj.ForCO("off","4",CO);
        assertEquals(CO.getStatus(), Status.OFF);
        
    }
    @Test
    public void ForWHTest()
    {
        SmartClient WH = new SmartClient();
        WH.setStatus();
        App obj = new App();
        obj.ForWH("on","4",WH);
        assertEquals(WH.getStatus(), Status.ON);
        obj.ForWH("off","4",WH);
        assertEquals(WH.getStatus(), Status.OFF);
        
        WH.unsetStatus();
        obj.ForWH("on","4",WH);
        assertEquals(WH.getStatus(), Status.ON);
        obj.ForWH("off","4",WH);
        assertEquals(WH.getStatus(), Status.OFF);
        
    }
    @Test
    public void ForACTest()
    {
        SmartClient AC = new SmartClient();
        AC.setStatus();
        App obj = new App();
        obj.ForAC("on","4",AC);
        assertEquals(AC.getStatus(), Status.ON);
        obj.ForAC("off","4",AC);
        assertEquals(AC.getStatus(), Status.OFF);
        
        AC.unsetStatus();
        obj.ForAC("on","4",AC);
        assertEquals(AC.getStatus(), Status.ON);
        obj.ForAC("off","4",AC);
        assertEquals(AC.getStatus(), Status.OFF);
    }
    @Test
    public void Read_Compute()
    {
        SmartClient AC = new SmartClient();
        SmartClient WH = new SmartClient();
        SmartClient CO = new SmartClient();
        App obj = new App();
        String test_input = "unit_testing.txt";
        obj.Read_Compute(test_input, AC, WH, CO);
        assertEquals(AC.getStatus(), Status.ON);
        assertEquals(WH.getStatus(), Status.OFF);
        assertEquals(CO.getStatus(), Status.OFF);
      
    }
    @Test
    public void EnumTest()
    {
        Status st = Status.valueOf("ON");
        assertEquals(st, Status.ON);
        
    }
}