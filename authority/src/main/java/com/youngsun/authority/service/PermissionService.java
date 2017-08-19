package com.youngsun.authority.service;


import com.github.pagehelper.PageInfo;
import com.youngsun.authority.dto.NavigationTreeDto;
import com.youngsun.authority.dto.PermissionDto;
import com.youngsun.authority.entity.PermissionEntity;
import com.youngsun.vo.PermissionVo;

import java.util.List;

/**
 * Created by 国平 on 2016/10/21.
 */
public interface PermissionService extends BasicService<PermissionEntity> {

    PageInfo<PermissionDto> getPermissionByRoleId(Integer start, Integer limit, String roleId);

    PageInfo<PermissionDto> getPermissionByIds(Integer start, Integer limit, List<String> ids);

    List<PermissionDto> getPermissionByRoleIds(List<String> roleIds);

    List<PermissionDto> getPermissionByParentId(String parentId);

    PageInfo<PermissionDto> getAllPermission(Integer start, Integer limit, String belongTo);

    PageInfo<PermissionDto> getByPermissionVo(Integer start, Integer limit, PermissionVo permissionVo);

    List<PermissionDto> getAllPermissionWithTree(String belongTo);

    PermissionDto save(PermissionVo permissionVo);

    List<NavigationTreeDto> getNavigationTree(List<PermissionVo> permissionVos);

    void delete(String id, Boolean leaf, String parentId);
}
