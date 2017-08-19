package com.youngsun.authority.dto;

/**
 * Created by 国平 on 2017/6/18.
 */
public class RolePermissionDto extends BasicDto {

    private String roleId;
    private String permissionId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
