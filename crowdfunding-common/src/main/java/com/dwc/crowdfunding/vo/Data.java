package com.dwc.crowdfunding.vo;

import com.dwc.crowdfunding.bean.MemberCert;
import com.dwc.crowdfunding.bean.User;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<User> datas = new ArrayList<>();

    private List<Integer> ids = new ArrayList<>();

    private List<MemberCert> memberCerts = new ArrayList<>();

    public List<User> getDatas() {
        return datas;
    }

    public void setDatas(List<User> datas) {
        this.datas = datas;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<MemberCert> getMemberCerts() {
        return memberCerts;
    }

    public void setMemberCerts(List<MemberCert> memberCerts) {
        this.memberCerts = memberCerts;
    }
}
