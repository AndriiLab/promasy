package com.github.andriilab.promasy.data.reports.models;

import java.util.Collection;
import java.util.Map;

public class CpvAmountsReportRequest extends ReportRequest<CpvAmountReportModel> {
    public CpvAmountsReportRequest(Map<String, Object> parameters, Collection<CpvAmountReportModel> modelsList) {
        super(parameters, modelsList);
    }
}
