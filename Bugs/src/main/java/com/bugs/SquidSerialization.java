//package com.bugs;

/**
 * Created by Piyush on 7/20/16.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Rule: squid:S2118
 * When a class which doesnot inherits Serializable or nor extends a class that
 * does, then the object of the class cannot be written as the class does not extends serializable
 */
class SquidError {   // neither implements Serializable nor extends a class that does
    private String name;
    SquidSerialization(String name)
    { this.name = name;}
    void setName(String name)
    {
        this.name = name;
    }
    String getName(){
        return this.name;
    }
}

class SquidSerialization {

    public static void main() throws IOException {

        SquidError obj = new SquidError("Hey");
        FileOutputStream fout = new FileOutputStream(obj.getName());
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(obj); // Exception thrown here
    }
}
