package com.cesecsh.small_ics_system.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryObject {
    private String id;
    private String delFlag;

    private Integer pageSize;
    private Integer pageNum;
}
