package com.dwc.crowdfunding.bean;

/**
 * 流程审批单：用于记录流程走到哪一步
 */
public class Ticket {
    private int id;
    private int memberid; //会员id
    private String piid; //流程实例id
    private String status; // 0 - 审核中， 1 - 审核完毕
    private String authcode; // 验证码
    private String step; // 目前走到哪一步： acctType-账户类型，basicInfo-基本信息，uploadCertFile-资质文件上传，checkEmail-邮箱确认

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public String getPiid() {
        return piid;
    }

    public void setPiid(String piid) {
        this.piid = piid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
