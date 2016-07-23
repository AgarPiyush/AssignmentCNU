package com.bugfind;
import java.nio.file.Files;
import java.util.Arrays;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.ba.XClass;
import edu.umd.cs.findbugs.classfile.DescriptorFactory;
import edu.umd.cs.findbugs.classfile.MethodDescriptor;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;

/**
 *  Logic for detecting thread.run and runnable.run bug
 *
 */

public class BugDetector extends BytecodeScanningDetector {

    private BugReporter reporter;
    public BugDetector(BugReporter bugReporter)
    {
        this.reporter = bugReporter;
    }
    // Checking if any superclass extends Thread
    boolean threadInSuperClass(XClass currentClass) {
        if(currentClass == null)
            return false;
        if(currentClass.toString().equals("java/lang/Thread"))
            return true;
        try {
            return threadInSuperClass(currentClass.getSuperclassDescriptor().getXClass());
        }
        catch(Exception e) {
            return false;
        }
    }


    @Override
    public void sawMethod() {
        MethodDescriptor invokedMethod = getMethodDescriptorOperand();
        ClassDescriptor invokedObject = getClassDescriptorOperand();

        try {
            boolean threadInClass =  threadInSuperClass(invokedObject.getXClass());
            ClassDescriptor interfaces[] = invokedObject.getXClass().getInterfaceDescriptorList();
            String interfaceList = Arrays.toString(interfaces);
            boolean interfaceInClass = false;
            if(interfaceList.contains("java/lang/Runnable"))
                interfaceInClass = true;
            System.out.println(threadInClass + " "+invokedObject.getXClass());
            if (invokedMethod != null  && "run".equals(invokedMethod.getName())) {

                 if(threadInClass == true) {
                     reporter.reportBug( new BugInstance(this, "THREAD_ERROR", HIGH_PRIORITY)
                                     .addClassAndMethod(this).addSourceLine(this));
                     System.out.println("Thread Bug");
                 }
                else {
                     if(interfaceInClass == true) {
                         reporter.reportBug( new BugInstance(this, "RUNNABLE_ERROR", HIGH_PRIORITY)
                                 .addClassAndMethod(this).addSourceLine(this));
                         System.out.println("Interface Bug");
                     }
                 }
             }
        }
        catch (Exception e) {
            System.out.println("Exception always");
        }
    }
}

