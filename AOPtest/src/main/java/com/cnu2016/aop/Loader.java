package com.cnu2016.aop;

/**
 * Created by Piyush on 7/20/16.
 */
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;


public class Loader implements ClassFileTransformer {

    public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
        CtClass cl = null;
        try
        {
            ClassPool pool = ClassPool.getDefault();
            if(className.startsWith("com/cnu2016")) {
                cl = pool.makeClass(new java.io.ByteArrayInputStream(bytes));
                String classNames = cl.getName();
                System.out.println("Class names " + classNames);
                CtBehavior[] methods = cl.getDeclaredBehaviors();
                for (int i = 0; i <methods.length; i++) {
                    if(!methods[i].isEmpty())
                    {
                        methods[i].insertBefore("System.out.println(\"Number of arguments: \"); System.out.println($args.length);");
                        methods[i].insertBefore("System.out.println(\"Arguments: \"); for(int j=0; j<$args.length; j++) System.out.print(\" \"+$args[j]);System.out.println();");
                        methods[i].insertAfter("System.out.println(\"Return type: \"+$_);");
                    }
                }
                bytes = cl.toBytecode();
            }

        }
        catch (Exception e) {
     //       e.printStackTrace();
        }
        finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return bytes;
    }
}