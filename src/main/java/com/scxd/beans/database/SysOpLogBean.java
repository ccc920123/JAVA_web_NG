package com.scxd.beans.database;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.scxd.toolkit.CustomDateSerializer;

import java.sql.Date;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 15:32 2018/12/26
 * @Modified By:
 */
public class SysOpLogBean {
    private  String rn;
    private String id;
    private Date opTime;
    private String opContent;
    private String opUrl;
    private Long opResult;
    private String opIp;
    private Date cTime;
    private String uname="";
    private String checkdigit;
    private Long logType;
    private Long funcType;
    private String opResultInfo;
    private String realname="游客";
    private long requestTime;
    private String opStrResult;

    public String getOpStrResult() {
        return opStrResult;
    }

    public void setOpStrResult(String opStrResult) {
        this.opStrResult = opStrResult;
    }

    public String getRn() {
        return rn;
    }

    public void setRn(String rn) {
        this.rn = rn;
    }

    public String getOpUrl() {
        return opUrl;
    }

    public void setOpUrl(String opUrl) {
        this.opUrl = opUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }


    public String getOpContent() {
        return opContent;
    }

    public void setOpContent(String opContent) {
        this.opContent = opContent;
    }

    public Long getOpResult() {
        return opResult;
    }

    public void setOpResult(Long opResult) {
        this.opResult = opResult;
    }

    public String getOpIp() {
        return opIp;
    }

    public void setOpIp(String opIp) {
        this.opIp = opIp;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getCheckdigit() {
        return checkdigit;
    }

    public void setCheckdigit(String checkdigit) {
        this.checkdigit = checkdigit;
    }

    public Long getLogType() {
        return logType;
    }

    public void setLogType(Long logType) {
        this.logType = logType;
    }

    public Long getFuncType() {
        return funcType;
    }

    public void setFuncType(Long funcType) {
        this.funcType = funcType;
    }

    public String getOpResultInfo() {
        return opResultInfo;
    }

    public void setOpResultInfo(String opResultInfo) {
        this.opResultInfo = opResultInfo;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }




    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public Long getRequestTime() {
        return requestTime;
    }
}
