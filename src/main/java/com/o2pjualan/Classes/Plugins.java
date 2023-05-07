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

    public void addPlugin(Plugin plugin) {
        this.plugins.add(plugin);
    }

    public void removePlugin(Plugin plugin){
        this.plugins.remove(plugin);
    }
}
