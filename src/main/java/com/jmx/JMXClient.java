package com.jmx;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by joway on 16/2/11.
 */
public class JMXClient {


    public void start() throws IOException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException, InvalidAttributeValueException, MalformedObjectNameException {
        // Create an RMI connector client and
        // connect it to the RMI connector server
        //
        System.out.println("Create an RMI connector client and " + "connect it to the RMI connector server");
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/TestJMXServer");
        JMXConnector jmxConnector = JMXConnectorFactory.connect(url, null);

        // Get an MBeanServerConnection
        //
        System.out.println("Get an MBeanServerConnection");
        MBeanServerConnection mServerConn = jmxConnector.getMBeanServerConnection();

        // Get domains from MBeanServer
        //
        System.out.println("Domains:");
        String domains[] = mServerConn.getDomains();
        for (int i = 0; i < domains.length; i++) {
            System.out.println("/tDomain[" + i + "] = " + domains[i]);
        }

        // Get MBean count
        //
        System.out.println("MBean count = " + mServerConn.getMBeanCount());

        // Query MBean names
        //
        System.out.println("Query MBeanServer MBeans:");
        Set names = mServerConn.queryNames(null, null);
        for (Iterator i = names.iterator(); i.hasNext(); ) {
            System.out.println("ObjectName = " + (ObjectName) i.next());
        }


        // get MBean obj name
        //
        ObjectName stdMBeanName = new ObjectName("TestJMXServer:name=good");


        // 调用某个object 的方法以及得到值
        // Proxy way to access MBean
        StatusMBean proxy = (StatusMBean) MBeanServerInvocationHandler.newProxyInstance(mServerConn, stdMBeanName, StatusMBean.class, false);
        System.out.println(proxy.getName());
        proxy.setName("another name");
        System.out.println(proxy.getName());


        // Close MBeanServer connection
        System.out.println("Close the connection to the server");
        jmxConnector.close();
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException, InvalidAttributeValueException, MalformedObjectNameException {
        new JMXClient().start();
    }


}
