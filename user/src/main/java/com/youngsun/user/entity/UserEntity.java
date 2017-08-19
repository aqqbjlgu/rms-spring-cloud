package com.youngsun.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 国平 on 2016/10/20.
 */
@Entity
@Table(name = "t_user")
public class UserEntity extends BasicEntity {
    
    public static final String PRINCIPAL_TYPE = "user";
    /**
     * 姓名
     */
    @Column(name="t_name")
    private String name;
    /**
     * 密码
     */
    @Column(name = "t_password")
    private String password;
    /**
     * 姓别
     */
    @Column(name = "t_sex")
    private Integer sex;
    /**
     * 身份证
     */
    @Column(name = "t_idcard")
    private String idCard;
    /**
     * 手机号
     */
    @Column(name = "t_phone")
    private String phone;
    /**
     * 昵称
     */
    @Column(name = "t_nickname")
    private String nickName;
    /**
     * 头像
     */
    @Column(name = "t_image")
    private String image;
    /**
     * 状态
     */
    @Column(name = "t_status")
    private String status;
    
    /**
     * 邮箱
     */
    @Column(name = "t_email")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
