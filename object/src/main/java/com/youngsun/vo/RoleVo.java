package com.youngsun.vo;

import java.io.Serializable;

/**
 * Created by 国平 on 2017/6/28.
 */
public class RoleVo extends BasicVo  implements Serializable {
    /**
     * 角色名称.
     */
    private String name;
    /**
     * 是否可用,1：可用，0不可用
     */
    private Boolean available;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
