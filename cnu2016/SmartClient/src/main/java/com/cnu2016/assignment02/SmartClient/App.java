package com.cnu2016.assignment02.SmartClient;
import java.io.*;
import java.util.*;

enum Status { ON , OFF }
enum ApplianceCode {WH, AC, CO}
enum indexs {timeCurrent(0), newStatus(1), applianceType(2);
    public final int value;
    indexs(final int value) {
        this.value = value;
    }
}
public class App
{
    // Schedule timer
    static void newTimer(Timer timer, TimerTask task, long time)
    {
        timer.schedule(task, time*1000);
    }
    // Print status of all Appliance
    void printAllStatus(final List<SmartClient> applianceList, long time)
    {
        for(SmartClient eachAppliance : applianceList)
        {
            System.out.println("Appliacne "+eachAppliance.getCode()+" Status "+eachAppliance.getCurrState()+ " at Time "+time);
        }
    }

    // Computations when status is changed
    void compute(List<String> request, List<String> applicancesIndex, final List<SmartClient> applianceObjList) throws  InterruptedException
    {
        Timer timer = new Timer();
        for(String ind_request : request) {
            String parts[] = ind_request.split(",");
            final long time = Long.valueOf(parts[indexs.timeCurrent.value]).longValue();
            String newFileStatus = parts[indexs.newStatus.value];
            String applianceType = parts[indexs.applianceType.value];
            final Status newStatus;
            final int index = applicancesIndex.indexOf(applianceType.toUpperCase());
            if(index == -1) {
                System.out.println("Appliance not found");
                continue;
            }
            if (newFileStatus.equalsIgnoreCase("ON"))
                newStatus = Status.ON;
            else
                newStatus = Status.OFF;
            final SmartClient currAppliacne = applianceObjList.get(index);
            newTimer(timer, new TimerTask()
            {
                @Override
                public void run() {
                    currAppliacne.setCurrState(newStatus);
                    printAllStatus(applianceObjList, time);
                }}, time);
        }
        Thread.sleep(10000);
        timer.cancel();

    }
    // reading from file
    void Read_Compute(String input_file, List<String> applicancesIndex, final List<SmartClient> applianceObjList )
    {
        List<String>request = new ArrayList<String>();

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(input_file));
            String line = br.readLine();
            while (line != null) {
                request.add(line);
                line = br.readLine();
            }
            Collections.sort(request);
            compute(request, applicancesIndex, applianceObjList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    // Creating map of appliance type to get index in the object list:
    public static void main(String args[]) 
    {
        final List<SmartClient> applianceObjList = new ArrayList<SmartClient>();
        List<String> applicancesIndex = new ArrayList<String>();
        for(ApplianceCode eachAppliance: ApplianceCode.values())
        {
            SmartClient obj = new SmartClient(eachAppliance, Status.OFF);
            applianceObjList.add(obj);
            applicancesIndex.add(eachAppliance.toString());
        }
        String input_file = "test.txt";
        App obj = new App();
        obj.Read_Compute(input_file, applicancesIndex, applianceObjList);
    }  
}