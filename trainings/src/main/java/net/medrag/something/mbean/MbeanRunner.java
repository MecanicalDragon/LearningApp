package net.medrag.something.mbean;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

/**
 *
 * This is an example of how we can change bean state with JAVA_HOME/bin/jconsole.exe
 * All you need is connect to this application over the main menu, find this bean and change its value.
 * IMPORTANT: mbean must implement interface, named <beanClassName+'MBean'>.
 *
 * @author Stanislav Tretyakov
 * 31.10.2021
 */
public class MbeanRunner {

    public static void main(String[] args)
            throws MalformedObjectNameException,
            NotCompliantMBeanException,
            InstanceAlreadyExistsException,
            MBeanRegistrationException,
            InterruptedException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("net.medrag.something.mbean:type=Example");
        Example mbean = new Example("value one");
        mbs.registerMBean(mbean, name);

        while (!mbean.getValue().equals("quit")) {
            System.out.println(mbean.getValue());
            TimeUnit.SECONDS.sleep(5);
        }

        System.out.println(mbean.getValue());
    }
}
