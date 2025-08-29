/*
 *
 * Copyright (c) 2025 Propertyzar, Inc.
 *     All rights reserved.
 *
 *     This software and its documentation are confidential and proprietary
 *     information of Propertyzar, Inc. Unauthorized use, duplication,
 *     or distribution is strictly prohibited.
 *
 */

package com.propertyzar.ui.listeners;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@XmlRootElement(name = "testsuite")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestNGReport {
    @XmlAttribute
    private String name;
    
    @XmlAttribute(required = true)
    private int tests;
    
    @XmlAttribute(required = true)
    private int errors;
    
    @XmlAttribute(required = true)
    private int skipped;
    
    @XmlAttribute(required = true)
    private int failures;
    
    @XmlElement(name = "testcase")
    private List<TestCase> testCases = new ArrayList<>();
    
    @Data
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class TestCase {
        @XmlAttribute
        private String classname;
        
        @XmlAttribute
        private String name;
        
        @XmlAttribute
        private String time;
    }
} 