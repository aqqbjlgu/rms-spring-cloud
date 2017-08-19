package com.youngsun.authority.service;

import com.github.pagehelper.PageInfo;
import com.youngsun.authority.dto.RoleDto;
import com.youngsun.authority.dto.RolePermissionDto;
import com.youngsun.authority.entity.RoleEntity;
import com.youngsun.vo.RolePermissionVo;
import com.youngsun.vo.RoleVo;

import java.util.List;


/**
 * Created by 国平 on 2016/10/21.
 */
public interface RoleService extends BasicService<RoleEntity> {

    /**
     * 获取所有的角色，包含资源信息。不分页
     * @return
     */
    public List<RolePermissionDto> getAllwithPermission(String belongTo);

    /**
     * 获取所有的角色，包含资源信息。分页
     * @return
     */
    List<RolePermissionDto> getAllRoleWithPermissionByUserId(Integer start, Integer pageSize, String userId, String belongTo);

    /**
     * 根据UserId获取所有角色信息
     * @param userId
     * @return
     */
    List<RoleDto> getRoleByUserId(String userId);

    /**
     * 根据所属系统获取所有角色信息
     * @param belongTo
     * @return
     */
    PageInfo<RoleDto> getAllRoles(int page, int limit,String belongTo);

    /**
     * 根据RoleVo对像获取所有角色信息
     * @param roleVo
     * @return
     */
    PageInfo<RoleDto> getByRoleVo(Integer start, Integer limit, RoleVo roleVo);

    /**
     * 保存对像
     * @param roleVo
     * @return
     */
    RoleDto save(RoleVo roleVo);

    /**
     * 删除对像
     * @param id
     */
    void delete(String id);

    /**
     * 根据用户Id获取角色信息
     * @param page
     * @param limit
     * @param userId
     * @return
     */
    PageInfo<RoleDto> getAllRolesByUserId(int page, int limit, String userId);
}
