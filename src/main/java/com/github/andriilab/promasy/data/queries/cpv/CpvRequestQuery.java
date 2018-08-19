package com.github.andriilab.promasy.data.queries.cpv;

import com.github.andriilab.promasy.data.queries.IQuery;

public class CpvRequestQuery implements IQuery {
    private final String cpvRequest;
    private final int depth; // 0 - exact lvl, -1 - upper level, 1 - lower level

    public CpvRequestQuery(String cpvRequest, int depth) {
        this.cpvRequest = cpvRequest;
        this.depth = depth;
    }

    public String getCpvRequest() {
        return cpvRequest;
    }

    public int getDepth() {
        return depth;
    }
}
