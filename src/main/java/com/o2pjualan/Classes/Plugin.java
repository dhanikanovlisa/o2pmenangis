package com.o2pjualan.Classes;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.io.Serializable;

public class Plugin implements Serializable {
    private String pluginName;
    private String jarFilePath;
    private String className;

    public Plugin(String pluginName, String jarFilePath, String className) {
        this.pluginName = pluginName;
        this.jarFilePath = jarFilePath;
        this.className = className;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getJarFilePath() {
        return jarFilePath;
    }

    public void setJarFilePath(String jarFilePath) {
        this.jarFilePath = jarFilePath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
