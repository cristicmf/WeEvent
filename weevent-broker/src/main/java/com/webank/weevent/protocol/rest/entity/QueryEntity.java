package com.webank.weevent.protocol.rest.entity;

import java.math.BigInteger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class QueryEntity {

    private String groupId;
    private Integer pageNumber;
    private Integer pageSize;
    private String pkHash;
    private BigInteger blockNumber;
    private String nodeName;

    public QueryEntity() {
    }

    public QueryEntity(String groupId, Integer pageNumber, Integer pageSize, String pkHash, BigInteger blockNumber) {
        this.groupId = groupId;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pkHash = pkHash;
        this.blockNumber = blockNumber;

    }

}
