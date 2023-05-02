package com.o2pjualan.Classes;

import jdk.tools.jlink.plugin.Plugin;

import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class pluginController {
    private HashMap<String, Class<?>> loadPlugin;
    public pluginController(){
        this.loadPlugin = new HashMap<>();
    }
    public void loadPlugin(String pluginFile) throws IOException, ClassNotFoundException {
        JarFile jarFile = new JarFile(pluginFile);
        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            if (entry.getName().endsWith(".class")) {
                String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                Class<?> clazz = Class.forName(className);
                loadPlugin.put(clazz.getName(), clazz);
                System.out.println(clazz.getName());
            }
        }
        jarFile.close();
    }

    public void removePlugin(String pluginName){
        loadPlugin.remove(pluginName);
    }
}
