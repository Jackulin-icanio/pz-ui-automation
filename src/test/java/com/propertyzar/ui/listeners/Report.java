package com.propertyzar.ui.listeners;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Report {
    private String environment;
    private String applicationUrl;
    private String suiteName;
    private int totalTestRun;
    private int totalPassed;
    private int totalFailed;
    private int totalSkipped;
    private String totalTime;
}
