package com.github.andriilab.promasy.data.reports.models;

import java.util.Collection;
import java.util.Map;

public class BidsReportRequest extends ReportRequest<BidReportModel>{
    public BidsReportRequest(Map<String, Object> parameters, Collection<BidReportModel> modelsList) {
        super(parameters, modelsList);
    }
}
