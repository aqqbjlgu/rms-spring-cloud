package com.youngsun.vo;

import java.io.Serializable;

/**
 * Created by 国平 on 2016/12/7.
 */
public class UserOrgPosVo  implements Serializable {
    
    /**
     * 人员ID
     */
    private String pId;
    /**
     * 组织ID
     */
    private String orgId;
    /**
     * 岗位ID
     */
    private String posId;
    
    public UserOrgPosVo(String pId) {
        this.pId = pId;
    }
    
    public UserOrgPosVo(String orgId, String pId) {
        this.orgId = orgId;
    }
    
    public UserOrgPosVo() {
    }
    
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
