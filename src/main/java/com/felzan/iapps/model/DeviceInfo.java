package com.felzan.iapps.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceInfo {

    private ScreenInfo screenInfo;
    private OsInfo osInfo;
    private AppInfo appInfo;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String id;
}
