package com.cnu2016.assignment02.SmartClient;
import java.io.*;
import java.util.*;

enum Status { ON , OFF } 
public class App
{
    static void ForWH(String whNewStatus, String time, SmartClient WH)
    {
            if (whNewStatus.equals("on")) 
            {
                Status currentStatus = WH.getStatus();
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
                Status currentStatus = WH.getStatus();
                if (currentStatus == Status.ON) 
                {
                    System.out.println("Washing machine turned off at " + time);
                }   
                else 
                {
                    System.out.println("Washing machine already off at " + time);
                }
                WH.unsetStatus();
               
            }
    }
    static void ForAC(String acNewStatus, String time, SmartClient AC)
    {
        if(acNewStatus.equals("on")) 
        {
            Status currentStatus = AC.getStatus();
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
            Status currentStatus = AC.getStatus();
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
    static void ForCO(String coNewStatus, String time, SmartClient CO)
    {
        if (coNewStatus.equals("on")) 
        {
            Status currentStatus = CO.getStatus();
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
            Status currentStatus = CO.getStatus();
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
    public static void main(String args[]) 
    {
        ArrayList<String> request = new ArrayList<String>();
        SmartClient AC = new SmartClient();
        SmartClient WH = new SmartClient();
        SmartClient CO = new SmartClient();
        AC.unsetStatus();
        WH.unsetStatus();
        CO.unsetStatus();
        
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("test.txt"));
            String line = br.readLine();
            while(line != null)
            {
                request.add(line);
                line = br.readLine();
            }
            Collections.sort(request);
            
		    for (int i = 0; i < request.size(); i++) 
		    {
		         String ind_request = request.get(i);
		         String parts[] = ind_request.split(" ");
		         if( parts[2].equals("ac"))
		         {
		             ForAC(parts[1], parts[0], AC);
		         }
		         else if( parts[2].equals("co") )
		         {
		              ForCO(parts[1], parts[0], CO);
		         }
		         else if( parts[2].equals("wh") )
		         {
		              ForWH(parts[1], parts[0], WH);
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
}