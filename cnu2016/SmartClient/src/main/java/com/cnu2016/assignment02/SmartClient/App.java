package com.cnu2016.assignment02.SmartClient;
import java.io.*;
import java.util.*;

enum Status { ON , OFF } 
public class App
{
    void ForWH(String whNewStatus, String time, SmartClient WH)
    {
            Status currentStatus = WH.getStatus();
            if (whNewStatus.equals("on")) 
            {
                if (currentStatus == Status.OFF) 
                {
                    System.out.println("Washing machine turned on at " + time);
                } 
                else 
                {
                    System.out.println("Washing machine already on at " + time);
                }
                WH.setStatus();
            }
            else 
            {
                if (currentStatus == Status.ON) 
                {
                    System.out.println("Washing machine turned off at" + time);
                }   
                else 
                {
                    System.out.println("Washing machine already off at " + time);
                }
                WH.unsetStatus();
            }
    }
    void ForAC(String acNewStatus, String time, SmartClient AC)
    {
        Status currentStatus = AC.getStatus();
        if(acNewStatus.equals("on")) 
        {
            if (currentStatus == Status.OFF) 
            {
                System.out.println("AC turned on at " + time);
            } 
            else 
            {
                System.out.println("AC already on at " + time);
            }
                 AC.setStatus();
        }
        else 
        {
            if (currentStatus == Status.ON) 
            {
                System.out.println("AC turned off at " + time);
            } 
            else 
            {
                System.out.println("AC already off at " + time);
            }
             AC.unsetStatus();
        }
    }
    void ForCO(String coNewStatus, String time, SmartClient CO)
    {
        Status currentStatus = CO.getStatus();
        if (coNewStatus.equals("on")) 
        {
            if (currentStatus == Status.OFF) 
            {
                System.out.println("Cooking oven turned on at " + time);
            } 
            else 
            {
                System.out.println("Cooking oven already on at " + time);
            }
            CO.setStatus();
        } 
        else 
        {
            if (currentStatus == Status.ON) 
            {
                System.out.println("Cooking oven turned off at " + time);
            } 
            else 
            {
                System.out.println("Cooking oven already off at " + time);
            }
            CO.unsetStatus();
            
        }
    }
    void Read_Compute(String input_file, SmartClient AC, SmartClient WH, SmartClient CO)
    {
        ArrayList<String> request = new ArrayList<String>();
        AC.unsetStatus();
        WH.unsetStatus();
        CO.unsetStatus();
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(input_file));
            String line = br.readLine();
            while(line != null)
            {
                request.add(line);
                line = br.readLine();
            }
            Collections.sort(request);
            App obj = new App();
		    for (int i = 0; i < request.size(); i++) 
		    {
		         String ind_request = request.get(i);
		         String parts[] = ind_request.split(" ");
		         if( parts[2].equals("ac"))
		         {
		             obj.ForAC(parts[1], parts[0], AC);
		         }
		         else if( parts[2].equals("co") )
		         {
		              obj.ForCO(parts[1], parts[0], CO);
		         }
		         else if( parts[2].equals("wh") )
		         {
		              obj.ForWH(parts[1], parts[0], WH);
		         }
		    } 
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();   
        }
    }
    public static void main(String args[]) 
    {
        SmartClient AC = new SmartClient();
        SmartClient WH = new SmartClient();
        SmartClient CO = new SmartClient();
        String input_file = "test.txt";
        App obj = new App();
        obj.Read_Compute(input_file, AC, WH, CO);
    }  
}