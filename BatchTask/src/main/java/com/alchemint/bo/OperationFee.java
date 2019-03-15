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
@Document(collection = "OperationFee")
public class OperationFee implements Serializable{

    @Id
    private String id;

    /**合约hash**/
    private String asset;

    /**用户地址**/
    @Indexed
    private String addr;

    /**创建SAR的txid**/
    @Indexed
    private String sarTxid;

    /**操作txid**/
    private String txid;

    /**区块高度**/
    private Long blockindex;

    /**交易序号**/
    private Integer n;

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

    public String getSarTxid() {
        return sarTxid;
    }

    public void setSarTxid(String sarTxid) {
        this.sarTxid = sarTxid;
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
        return "OperationFee{" +
                "id='" + id + '\'' +
                ", asset='" + asset + '\'' +
                ", addr='" + addr + '\'' +
                ", sarTxid='" + sarTxid + '\'' +
                ", txid='" + txid + '\'' +
                ", blockindex=" + blockindex +
                ", n=" + n +
                ", value=" + value +
                ", time=" + time +
                '}';
    }
}
