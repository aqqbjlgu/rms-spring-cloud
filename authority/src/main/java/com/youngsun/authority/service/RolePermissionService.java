package com.youngsun.authority.service;


import com.youngsun.authority.dto.RolePermissionDto;
import com.youngsun.authority.entity.RolePermissionEntity;
import com.youngsun.vo.RolePermissionVo;

import java.util.List;

/**
 * Created by 国平 on 2016/10/21.
 */
public interface RolePermissionService extends BasicService<RolePermissionEntity> {
    /**
     * 根据roleId获取Role和Permission表的关联数据
     * @param roleId
     * @return
     */
    List<RolePermissionDto> getRolePermissionByRoleId(String roleId);

    /**
     * 根据roleId集合获取Role和Permission表的关联数据
     * @param roleIds
     * @return
     */
    List<RolePermissionDto> getRolePermissionByRoleIds(List<String> roleIds);

    /**
     * 根据roleId删除相关数据
     * @param roleId
     */
    void deleteByRoleId(String roleId);

    /**
     * 批量保存
     * @param rolePermissionEntities
     * @return
     */
    Integer saveAll(List<RolePermissionEntity> rolePermissionEntities, String roleId);



}
