package com.cnu2016.assignment02.SmartClient;

class SmartClient {
    private ApplianceCode code;
    private Status currState;

    SmartClient(){

    }
    SmartClient(ApplianceCode code, Status currState) {
        this.code = code;
        this.currState = currState;
    }

    public Status getCurrState() {
        return currState;
    }

    public void setCurrState(Status currState) {
        this.currState = currState;
    }

    public ApplianceCode getCode() {
        return code;
    }
    public void setCode(ApplianceCode code) {
        this.code = code;
    }

}