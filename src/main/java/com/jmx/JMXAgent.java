package com.jmx;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by joway on 16/2/11.
 */
public class JMXAgent {

    private MBeanServer mbeanServer;

    private String jmxServerName = "TestJMXServer";

    public void start() throws MalformedObjectNameException,
            NullPointerException, InstanceAlreadyExistsException,
            MBeanRegistrationException, NotCompliantMBeanException, IOException {

        int rmiPort = 1099;

        // jdkfolder/bin/rmiregistry.exe 9999
        Registry registry = LocateRegistry.createRegistry(rmiPort);

//        MBeanServer mbs = MBeanServerFactory.createMBeanServer(jmxServerName);
        mbeanServer = ManagementFactory.getPlatformMBeanServer();



        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + jmxServerName);
        System.out.println("JMXServiceURL: " + url.toString());
        JMXConnectorServer jmxConnServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbeanServer);
        jmxConnServer.start();

    }

    public void registerMBean(StatusMBean statusMBean) throws NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, MalformedObjectNameException {
        ObjectName objName = new ObjectName(jmxServerName + ":name=" + statusMBean.getName());
        mbeanServer.registerMBean(statusMBean, objName);
    }

    public static void main(String[] args) throws MalformedObjectNameException, InstanceAlreadyExistsException, NotCompliantMBeanException, MBeanRegistrationException, IOException {
        JMXAgent agent = new JMXAgent();
        agent.start();
        agent.registerMBean(new Status("pause","good"));
        agent.registerMBean(new Status("start","bad"));
    }
}
