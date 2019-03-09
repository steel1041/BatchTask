package com.alchemint.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cheng on 2019/3/6.
 */
@Document(collection = "OracleOperation")
public class OracleOperation implements Serializable{

    @Id
    private String id;

    /**合约hash**/
    private String asset;

    /**用户地址**/
    @Indexed
    private String addr;

    /**操作txid**/
    private String txid;

    /**区块高度**/
    private Long blockindex;

    /**交易序号**/
    private Integer n;

    /**操作类型**/
    private String key;

    /**操作金额**/
    private BigDecimal value;

    private Date time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public Long getBlockindex() {
        return blockindex;
    }

    public void setBlockindex(Long blockindex) {
        this.blockindex = blockindex;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "OracleOperation{" +
                "id='" + id + '\'' +
                ", asset='" + asset + '\'' +
                ", addr='" + addr + '\'' +
                ", txid='" + txid + '\'' +
                ", blockindex=" + blockindex +
                ", n=" + n +
                ", key='" + key + '\'' +
                ", value=" + value +
                ", time=" + time +
                '}';
    }
}
