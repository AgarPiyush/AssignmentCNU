package com.cnu2016.assignment02.SmartClient;

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
