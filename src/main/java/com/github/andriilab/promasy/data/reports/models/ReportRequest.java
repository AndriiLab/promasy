package com.github.andriilab.promasy.data.reports.models;

import lombok.Getter;

import java.util.Collection;
import java.util.Map;

@Getter
public abstract class ReportRequest<T> {
    public final Map<String, Object> parameters;
    public final Collection<T> modelsList;

    protected ReportRequest(Map<String, Object> parameters, Collection<T> modelsList){
        this.parameters = parameters;
        this.modelsList = modelsList;
    }

}
