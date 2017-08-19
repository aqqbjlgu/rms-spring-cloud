package com.youngsun.authority.service.impl;

import com.youngsun.authority.entity.RolePermissionEntity;
import com.youngsun.authority.entity.UserRoleEntity;
import com.youngsun.authority.mapper.UserRoleMappper;
import com.youngsun.authority.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Created by 国平 on 2016/10/21.
 */
@Service
public class UserRoleServiceImpl extends BasicServiceImpl<UserRoleEntity> implements UserRoleService {
    @Autowired
    private UserRoleMappper userRoleMappper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveAll(List<UserRoleEntity> userRoleEntities, String userId) {
        deleteByUserId(userId);
        Integer count = null;
        if (!CollectionUtils.isEmpty(userRoleEntities)) {
            count = super.save(userRoleEntities);
        }
        return count;
    }

    @Override
    public void deleteByUserId(String userId) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userId);
        userRoleMappper.delete(userRoleEntity);
    }
}
