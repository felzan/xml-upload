package com.felzan.iapps.controller.request;

import com.felzan.iapps.model.DeviceInfo;
import com.felzan.iapps.model.EPaper;
import com.felzan.iapps.model.GetPages;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "epaperRequest")
public class EPaperRequest {

    private DeviceInfo deviceInfo;
    private GetPages getPages;

    public EPaper.EPaperBuilder toEPaperBuilder() {
        return EPaper.builder()
                .newspaperName(deviceInfo.getAppInfo().getNewspaperName())
                .width(deviceInfo.getScreenInfo().getWidth())
                .height(deviceInfo.getScreenInfo().getHeight())
                .dpi(deviceInfo.getScreenInfo().getDpi());
    }
}
