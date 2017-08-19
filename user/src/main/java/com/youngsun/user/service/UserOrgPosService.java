package com.youngsun.user.service;

import com.youngsun.user.entity.UserOrgPosEntity;

import java.util.List;

/**
 * Created by 国平 on 2016/10/21.
 */
public interface UserOrgPosService extends BasicService<UserOrgPosEntity> {
    
    void deletByPid(List<String> pids);
    
}
