package net.medrag.reactivit.reactivitapp.bombarder.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Stanislav Tretyakov
 * 24.05.2022
 */
@Component
public class BombardSignHolder implements BombardSignHolderMBean {

    private final AtomicBoolean active = new AtomicBoolean(false);

    @Override
    public void setActive(boolean active) {
        this.active.set(active);
    }

    @Override
    public boolean getActive() {
        return this.active.get();
    }

    public String switchDispatch() {
        final var b = active.get();
        if (active.compareAndSet(b, !b)) {
            return b ? "OFF" : "ON";
        } else {
            return b ? "ON" : "OFF";
        }
    }

    @SneakyThrows
    @PostConstruct
    public void init(){
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("net.medrag.bombarder.service:type=BombardSignHolder");
        mbs.registerMBean(this, name);
    }
}
