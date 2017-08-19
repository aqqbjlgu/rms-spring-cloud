package com.youngsun.user.service.impl;

import com.youngsun.user.entity.UserOrgPosEntity;
import com.youngsun.user.mapper.UserOrgPosMapper;
import com.youngsun.user.service.UserOrgPosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * Created by 国平 on 2016/10/21.
 */
@Service
public class UserOrgPosServiceImpl extends BasicServiceImpl<UserOrgPosEntity> implements UserOrgPosService {
    
    @Autowired
    private UserOrgPosMapper userOrgPosMapper;
    
    public void deletByPid(List<String> pids){
        Example example = new Example(UserOrgPosEntity.class);
        example.createCriteria().andIn("pId", pids);
        userOrgPosMapper.deleteByExample(example);
    }
}
