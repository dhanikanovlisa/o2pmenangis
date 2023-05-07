package com.o2pjualan.Classes;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@Data
@XmlRootElement(name = "plugins")
@XmlAccessorType(XmlAccessType.FIELD)
public class Plugins implements Serializable {
    private ArrayList<Plugin> plugins;

    public Plugins() {
        this.plugins = new ArrayList<Plugin>();
    }
    public Plugin getPluginByName(String pluginName){
        for(Plugin p: plugins){
            if(p.getPluginName().equals(pluginName)){
                return p;
            }
        }
        return null;
    }

    public boolean addPlugin(Plugin plugin) {
        for (Plugin p : plugins) {
            if (p.getPluginName().equals(plugin.getPluginName())) {
                return false;
            }
        }
        plugins.add(plugin);
        return true;
    }

    public void removePlugin(Plugin plugin){
        this.plugins.remove(plugin);
    }
}
