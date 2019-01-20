package com.github.andriilab.promasy.data.queries.cpv;

import com.github.andriilab.promasy.data.queries.IQuery;
import lombok.Getter;

public class CpvRequestQuery implements IQuery {
    @Getter private final String cpvRequest;
    @Getter private final int depth; // 0 - exact lvl, -1 - upper level, 1 - lower level

    public CpvRequestQuery(String cpvRequest, int depth) {
        this.cpvRequest = cpvRequest;
        this.depth = depth;
    }
}
