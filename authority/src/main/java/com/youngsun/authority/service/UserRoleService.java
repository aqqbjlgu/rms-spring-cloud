package com.youngsun.authority.service;

import com.youngsun.authority.entity.UserRoleEntity;

import java.util.List;


/**
 * Created by 国平 on 2016/10/21.
 */
public interface UserRoleService extends BasicService<UserRoleEntity> {
    /**
     * 保存全部用户和角色关联关系。
     * @param userRoleEntities
     * @param userId
     * @return
     */
    Integer saveAll(List<UserRoleEntity> userRoleEntities, String userId) ;

    /**
     * 删除与UserId对应的用户和角色关联关系。
     * @param userId
     */
    void deleteByUserId(String userId);
}
