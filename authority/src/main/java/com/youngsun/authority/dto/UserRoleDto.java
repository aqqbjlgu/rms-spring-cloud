package com.youngsun.authority.dto;

/**
 * Created by 国平 on 2017/7/18.
 */
public class UserRoleDto extends BasicDto{
    private String userId;
    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
