package com.github.andriilab.promasy.data.reports.models;

import lombok.Getter;

import java.util.Collection;
import java.util.Map;

public abstract class ReportRequest<T> {
    @Getter public Map<String, Object> parameters;
    @Getter public Collection<T> modelsList;

    protected ReportRequest(Map<String, Object> parameters, Collection<T> modelsList){
        this.parameters = parameters;
        this.modelsList = modelsList;
    }

}
