package com.o2pjualan.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@NoArgsConstructor
@XmlRootElement(name = "plugin")
@XmlAccessorType(XmlAccessType.FIELD)
public class Plugin implements Serializable {
    private String pluginName;
    private String jarFilePath;
    private String className;

    public Plugin(@JsonProperty("pluginName") String pluginName,
                  @JsonProperty("jarFilePath") String jarFilePath,
                  @JsonProperty("className") String className) {
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
