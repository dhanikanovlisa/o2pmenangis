package com.o2pjualan.Classes;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class PluginManager {
    public static ArrayList<String> getClassNameFromJar(JarInputStream jarFile) throws Exception {
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

    public static ArrayList<String> getClassNameFromJar(String jarPath) throws Exception {
        return getClassNameFromJar(new JarInputStream(new FileInputStream(jarPath)));
    }

    public static Pair<String, Class<?>> loadJarFile(String filePath) throws Exception {
        ArrayList<String> classNames = getClassNameFromJar(filePath);
        Pair<String, Class<?>> loaded = new Pair<>();
        File f = new File(filePath);
        URLClassLoader classLoader = new URLClassLoader(new URL[]{f.toURI().toURL()});
        for (String className : classNames) {
            try {
                Class<?> c = classLoader.loadClass(className);
                if(!className.contains(".pluginChartController")){
                    loaded.setKey(className);
                    loaded.setValue(c);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Class " + className + " was not found!");
            }
        }
        return loaded;
    }
}