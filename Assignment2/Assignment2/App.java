package com.cnu2016.assignment02.SmartClient;
import java.io.*;
import java.util.*;
enum Status { ON , OFF } 

class SmartClient
{
    private Status currStatus;
    void setStatus()
    {
        this.currStatus = Status.ON;
    }
    void unsetStatus()
    {
        this.currStatus = Status.OFF;
    }
    Status getStatus()
    {
        return this.currStatus;
    }
}

public class App
{
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
		      
		         if( parts[2].equals("ac") )
		         {
		             if(parts[1].equals("on"))
		             {
		                 Status currentStatus = AC.getStatus();
		                 if(currentStatus == Status.OFF)
		                 {
		                     System.out.println("AC turned on at "+parts[0]);
		                     AC.setStatus();
		                 }
		                 else
		                 {
		                     System.out.println("AC already on at "+parts[0]);
		                 }
		             }
		             else
		             {
		                 Status currentStatus = AC.getStatus();
		                 if(currentStatus == Status.ON)
		                 {
		                     System.out.println("AC turned off at " +parts[0]);
		                     AC.unsetStatus();
		                 }
		                 else
		                 {
		                     System.out.println("AC already off at "+parts[0]);
		                 }
		             }
		         }
		         else if( parts[2].equals("co") )
		         {
		                    if(parts[1].equals("on"))
		             {
		                 Status currentStatus = CO.getStatus();
		                 if(currentStatus == Status.OFF)
		                 {
		                     System.out.println("Cooking oven turned on at "+parts[0]);
		                     CO.setStatus();
		                 }
		                 else
		                 {
		                     System.out.println("Cooking oven already on at "+parts[0]);
		                 }
		             }
		             else
		             {
		                 Status currentStatus = CO.getStatus();
		                 if(currentStatus == Status.ON)
		                 {
		                     System.out.println("Cooking oven turned off at " +parts[0]);
		                     CO.unsetStatus();
		                 }
		                 else
		                 {
		                     System.out.println("Cooking oven already off at "+parts[0]);
		                 }
		             }
		         }
		         else if( parts[2].equals("wh") )
		         {
		              if(parts[1].equals("on"))
		             {
		                 Status currentStatus = WH.getStatus();
		                 if(currentStatus == Status.OFF)
		                 {
		                     System.out.println("Washing machine turned on at "+parts[0]);
		                     WH.setStatus();
		                 }
		                 else
		                 {
		                     System.out.println("Washing machine already on at "+parts[0]);
		                 }
		             }
		             else
		             {
		                 Status currentStatus = WH.getStatus();
		                 if(currentStatus == Status.ON)
		                 {
		                     System.out.println("Washing machine turned off at " +parts[0]);
		                     WH.unsetStatus();
		                 }
		                 else
		                 {
		                     System.out.println("Washing machine already off at "+parts[0]);
		                 }
		             }
		         }
		    } 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }  
}