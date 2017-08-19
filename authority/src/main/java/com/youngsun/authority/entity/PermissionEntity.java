package com.youngsun.authority.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 国平 on 2017/6/18.
 */
@Entity
@Table(name = "t_permission")
public class PermissionEntity extends BasicEntity {
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源类型：menu,button,
     */
    private String type;
    /**
     * 访问url地址
     */
    private String url;
    /**
     * 权限代码字符串
     */
    private String percode;
    /**
     * 父结点id
     */
    private String parentId;
    /**
     * 父结点id列表串
     */
    private String parentIds;
    /**
     * 排序号
     */
    private String sortString;
    /**
     * 是否可用,1：可用，0不可用
     */
    private Boolean available;
    /**
     * 图标
     */
    private String iconCls;

    /**
     * 是否是叶子节点.
     */
    private Boolean leaf;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPercode() {
        return percode;
    }

    public void setPercode(String percode) {
        this.percode = percode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getSortString() {
        return sortString;
    }

    public void setSortString(String sortString) {
        this.sortString = sortString;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
}
