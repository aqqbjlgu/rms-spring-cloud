package com.youngsun.user.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by 国平 on 2016/10/20.
 */
@MappedSuperclass
public class BasicEntity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
    private String id;
    /**
     * 插入时间
     */
    @Column(name = "t_insert_date")
    private Date insertDate;
    /**
     * 更新时间
     */
    @Column(name = "t_update_date")
    private Date upDateDate;
    /**
     * 插入者ID
     */
    @Column(name = "t_insert_userid")
    private String insertUserId;
    /**
     * 更新者ID
     */
    @Column(name = "t_update_userid")
    private String upDateUserId;
    /**
     * 所属系统
     */
    @Column(name = "t_belong")
    private String belongTo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getUpDateDate() {
        return upDateDate;
    }

    public void setUpDateDate(Date upDateDate) {
        this.upDateDate = upDateDate;
    }

    public String getInsertUserId() {
        return insertUserId;
    }

    public void setInsertUserId(String insertUserId) {
        this.insertUserId = insertUserId;
    }

    public String getUpDateUserId() {
        return upDateUserId;
    }

    public void setUpDateUserId(String upDateUserId) {
        this.upDateUserId = upDateUserId;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }
}
