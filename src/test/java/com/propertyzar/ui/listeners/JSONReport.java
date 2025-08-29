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

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class JSONReport {
    private String name;
    private int tests;
    private int errors;
    private int skipped;
    private int failures;
    private List<TestCase> testCases = new ArrayList<>();

    @Data
    @NoArgsConstructor
    public static class TestCase {
        private String classname;
        private String name;
        private String status;
        private double time;
    }
} 