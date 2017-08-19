package com.youngsun.authority.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 国平 on 2017/6/18.
 */
@Entity
@Table(name = "t_user_role")
public class UserRoleEntity extends BasicEntity{

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
