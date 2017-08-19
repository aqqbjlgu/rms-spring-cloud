package com.youngsun.authority.mapper;

import com.youngsun.authority.dto.RolePermissionDto;
import com.youngsun.authority.entity.RoleEntity;
import com.youngsun.common.mapper.MyMapper;

import java.util.List;


public interface RoleMapper extends MyMapper<RoleEntity> {

    List<RolePermissionDto> getAllRoleWithPermissionByUserId(String userId, String belongTo);

}

