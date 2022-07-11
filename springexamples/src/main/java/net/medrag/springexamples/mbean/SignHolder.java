package net.medrag.springexamples.mbean;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Use jconsole to change this value.
 * @author Stanislav Tretyakov
 * 11.07.2022
 */
@Component
public class SignHolder implements SignHolderMBean {

    private final AtomicBoolean active = new AtomicBoolean(false);

    @Override
    public void setActive(boolean active) {
        this.active.set(active);
    }

    @Override
    public boolean getActive() {
        return this.active.get();
    }

    @SneakyThrows
    @PostConstruct
    public void init(){
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("net.medrag.springexamples.mbean:type=SignHolder");
        mbs.registerMBean(this, name);
    }
}
