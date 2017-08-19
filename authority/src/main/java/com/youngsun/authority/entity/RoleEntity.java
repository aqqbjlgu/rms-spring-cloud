package com.youngsun.authority.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 国平 on 2017/6/18.
 */
@Entity
@Table(name = "t_role")
public class RoleEntity extends BasicEntity {
    /**
     * 角色名称.
     */
    private String name;
    /**
     * 是否可用,1：可用，0不可用
     */
    private boolean available;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
