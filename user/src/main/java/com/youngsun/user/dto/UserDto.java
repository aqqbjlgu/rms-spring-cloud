package com.youngsun.user.dto;



import com.youngsun.user.entity.UserEntity;

import java.util.List;

/**
 * Created by 国平 on 2016/12/8.
 */
public class UserDto extends UserEntity {
    private String id;
    private String userOrgPositionId;
    private String orgId;
    private String orgName;
    private String posId;
    private String posName;
    private String name;
    private String password;
    private Integer sex;
    private String idCard;
    private String phone;
    private String nickName;
    private String image;
    private String status;
    private String email;
    private List<UserOrgPosDto> userOrgPosDtos;
    
    public UserDto(String id, String userOrgPositionId, String orgId, String orgName, String posId, String posName, String name, String password, Integer sex, String idCard, String phone, String nickName, String image, String status, String email) {
        this.id = id;
        this.userOrgPositionId = userOrgPositionId;
        this.orgId = orgId;
        this.orgName = orgName;
        this.posId = posId;
        this.posName = posName;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.idCard = idCard;
        this.phone = phone;
        this.nickName = nickName;
        this.image = image;
        this.status = status;
        this.email = email;
    }
    
    public UserDto() {
    }
    
    public String getOrgId() {
        return orgId;
    }
    
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    public String getOrgName() {
        return orgName;
    }
    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    public String getPosId() {
        return posId;
    }
    
    public void setPosId(String posId) {
        this.posId = posId;
    }
    
    public String getPosName() {
        return posName;
    }
    
    public void setPosName(String posName) {
        this.posName = posName;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
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
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUserOrgPositionId() {
        return userOrgPositionId;
    }
    
    public void setUserOrgPositionId(String userOrgPositionId) {
        this.userOrgPositionId = userOrgPositionId;
    }
    
    public List<UserOrgPosDto> getUserOrgPosDtos() {
        return userOrgPosDtos;
    }
    
    public void setUserOrgPosDtos(List<UserOrgPosDto> userOrgPosDtos) {
        this.userOrgPosDtos = userOrgPosDtos;
    }
}
