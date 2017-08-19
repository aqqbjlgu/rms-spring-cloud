package com.youngsun.authority.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 国平 on 2017/6/18.
 */
@Entity
@Table(name = "t_role_permission")
public class RolePermissionEntity extends BasicEntity {
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
