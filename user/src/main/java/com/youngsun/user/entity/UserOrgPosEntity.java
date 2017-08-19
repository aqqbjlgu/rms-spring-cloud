package com.youngsun.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 人员组织岗位对应关系表
 * Created by 国平 on 2016/10/21.
 */
@Entity
@Table(name = "t_user_org_pos")
public class UserOrgPosEntity extends BasicEntity{
    /**
     * 人员ID
     */
    @Column(name = "t_pid")
    private String pId;
    /**
     * 组织ID
     */
    @Column(name = "t_orgid")
    private String orgId;
    /**
     * 岗位ID
     */
    @Column(name = "t_posid")
    private String posId;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }
}
