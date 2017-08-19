package com.youngsun.authority.dto;

import java.util.List;

/**
 * Created by 国平 on 2017/7/11.
 */
public class RoleDto extends BasicDto {
    /**
     * 资源名称
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
