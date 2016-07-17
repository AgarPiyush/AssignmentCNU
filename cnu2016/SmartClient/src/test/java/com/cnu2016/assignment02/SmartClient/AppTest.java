package com.cnu2016.assignment02.SmartClient;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AppTest
{
    @Test
    public void Read_ComputeTest()
    {
        App obj = new App();
        String test_input = "unit_testing.txt";
        final List<SmartClient> applianceObjList = new ArrayList<SmartClient>();
        List<String> applicancesIndex = new ArrayList<String>();
        for(ApplianceCode eachAppliance: ApplianceCode.values())
        {
            SmartClient applianceObj = new SmartClient(eachAppliance, Status.OFF);
            applianceObjList.add(applianceObj);
            applicancesIndex.add(eachAppliance.toString());
        }
        obj.Read_Compute(test_input, applicancesIndex, applianceObjList);
        int ac_index = applicancesIndex.indexOf(ApplianceCode.AC.toString());
        int wh_index = applicancesIndex.indexOf(ApplianceCode.WH.toString());
        int co_index = applicancesIndex.indexOf(ApplianceCode.CO.toString());
        assertEquals(applianceObjList.get(ac_index).getCurrState(), Status.ON);
        assertEquals(applianceObjList.get(wh_index).getCurrState(), Status.OFF);
        assertEquals(applianceObjList.get(co_index).getCurrState(), Status.OFF);


    }
    @Test
    public void EnumStatusTest()
    {
        Status st = Status.valueOf("ON");
        assertEquals(st, Status.ON);
        
    }
    @Test
    public void EnumApplianceCodeTest()
    {
        ApplianceCode st = ApplianceCode.valueOf("WH");
        assertEquals(st, ApplianceCode.WH);
        st = ApplianceCode.valueOf("CO");
        assertEquals(st, ApplianceCode.CO);
        st = ApplianceCode.valueOf("AC");
        assertEquals(st, ApplianceCode.AC);
    }

}
