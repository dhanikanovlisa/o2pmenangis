package com.o2pjualan.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

@Data
@XmlRootElement(name = "PluginManager")
@XmlAccessorType(XmlAccessType.FIELD)
public class PluginManager implements Serializable {
    private List<String> plugins;
    public PluginManager(){
        this.plugins = new ArrayList<>();
    }
    public PluginManager(String pluginName) {
        this.plugins = new ArrayList<String>();
        this.plugins.add(pluginName);
    }

    public PluginManager(@JsonProperty("plugins") ArrayList<String> plugins) {
        this.plugins = plugins;
    }
    public ArrayList<String> getClassNameFromJar(JarInputStream jarFile) throws Exception {
        ArrayList<String> classNames = new ArrayList<>();
        try {
            JarEntry jar;
            while (true) {
                jar = jarFile.getNextJarEntry();
                if (jar == null) {
                    break;
                }
                if (jar.getName().endsWith(".class")) {
                    String className = jar.getName().replace("/", ".");
                    String myClass = className.substring(0, className.lastIndexOf('.'));
                    classNames.add(myClass);
                }
            }
        } catch (Exception e) {
            throw new Exception("Error while getting class name from jarfile!");
        }
        return classNames;
    }

    public ArrayList<String> getPlugins() {
        return (ArrayList<String>) plugins;
    }

    public ArrayList<String> getClassNameFromJar(String jarPath) throws Exception {
        return getClassNameFromJar(new JarInputStream(new FileInputStream(jarPath)));
    }

    public Class<?> loadJarFile(String filePath) throws Exception {
        ArrayList<String> classNames = getClassNameFromJar(filePath);
        File f = new File(filePath);
        URLClassLoader classLoader = new URLClassLoader(new URL[]{f.toURI().toURL()});
        for (String className : classNames) {
            try {
                Class<?> c = classLoader.loadClass(className);
                if(!className.contains(".pluginChartController")){
                    addPlugin(className);
                    return c;
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Class " + className + " was not found!");
            }
        }
        return null;
    }
    public void addPlugin(String className){
        this.plugins.add(className);
    }
}
