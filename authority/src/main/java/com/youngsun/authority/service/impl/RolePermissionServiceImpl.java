package com.youngsun.authority.service.impl;

import com.youngsun.authority.dto.RolePermissionDto;
import com.youngsun.authority.entity.RolePermissionEntity;
import com.youngsun.authority.mapper.RolePermissionMapper;
import com.youngsun.authority.service.RolePermissionService;
import com.youngsun.vo.RolePermissionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 国平 on 2016/10/21.
 */
@Service
public class RolePermissionServiceImpl extends BasicServiceImpl<RolePermissionEntity> implements RolePermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<RolePermissionDto> getRolePermissionByRoleId(String roleId) {
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setRoleId(roleId);
        List<RolePermissionEntity> rolePermissionEntities = rolePermissionMapper.select(rolePermissionEntity);
        return rolePermissionEntities.stream().map(e -> {
            RolePermissionDto rolePermissionDto = new RolePermissionDto();
            BeanUtils.copyProperties(e,rolePermissionDto);
            return rolePermissionDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<RolePermissionDto> getRolePermissionByRoleIds(List<String> roleIds) {
        Example example = new Example(RolePermissionEntity.class);
        example.createCriteria().andIn("roleId", roleIds);
        List<RolePermissionEntity> rolePermissionEntities = rolePermissionMapper.selectByExample(example);
        return getRolePermissionVos(rolePermissionEntities);
    }

    @Override
    public void deleteByRoleId(String roleId) {
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setRoleId(roleId);
        rolePermissionMapper.delete(rolePermissionEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveAll(List<RolePermissionEntity> rolePermissionEntities, String roleId) {
        deleteByRoleId(roleId);
        super.save(rolePermissionEntities);
        return null;
    }

    private List<RolePermissionDto> getRolePermissionVos(List<RolePermissionEntity> rolePermissionEntities) {
        return rolePermissionEntities.stream().map(e -> {
            RolePermissionDto RolePermissionDto = new RolePermissionDto();
            BeanUtils.copyProperties(e, RolePermissionDto);
            return RolePermissionDto;
        }).collect(Collectors.toList());
    }
}
