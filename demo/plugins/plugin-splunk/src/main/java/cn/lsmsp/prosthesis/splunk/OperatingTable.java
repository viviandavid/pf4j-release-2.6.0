package cn.lsmsp.prosthesis.splunk;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginException;
import org.pf4j.PluginWrapper;
import org.pf4j.demo.api.Greeting;

import javax.swing.*;

/**
 * @author LSD
 * @date 2020/12/15 15:36
 */
public class OperatingTable extends Plugin{
    /**
     * Constructor to be used by plugin manager for plugin instantiation.
     * Your plugins have to provide constructor with this exact signature to
     * be successfully loaded by manager.
     *
     * @param wrapper
     */
    public OperatingTable(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() throws PluginException {
        super.start();
    }

    @Override
    public void stop() throws PluginException {
        super.stop();
    }

    @Override
    public void delete() throws PluginException {
        super.delete();
    }

    @Extension
    public static class test implements Greeting{

        @Override
        public String getGreeting() {
            return null;
        }
    }
}
