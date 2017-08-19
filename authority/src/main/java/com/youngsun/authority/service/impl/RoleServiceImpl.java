package com.youngsun.authority.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youngsun.authority.dto.RoleDto;
import com.youngsun.authority.dto.RolePermissionDto;
import com.youngsun.authority.entity.RoleEntity;
import com.youngsun.authority.entity.UserRoleEntity;
import com.youngsun.authority.mapper.RoleMapper;
import com.youngsun.authority.service.PermissionService;
import com.youngsun.authority.service.RolePermissionService;
import com.youngsun.authority.service.RoleService;
import com.youngsun.authority.service.UserRoleService;
import com.youngsun.vo.RolePermissionVo;
import com.youngsun.vo.RoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by 国平 on 2016/10/21.
 */
@Service
public class RoleServiceImpl extends BasicServiceImpl<RoleEntity> implements RoleService {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RoleMapper roleMapper;

    public List<RolePermissionDto> getAllwithPermission(String belongTo) {
//        roleMapper.getAllRoleWithPermissionByUserId(belongTo);
        return null;
    }

    @Override
    public List<RolePermissionDto> getAllRoleWithPermissionByUserId(Integer start, Integer pageSize, String userId, String belongTo) {
        List<RolePermissionDto> rolePermissionDtos = roleMapper.getAllRoleWithPermissionByUserId(userId, belongTo);
        return rolePermissionDtos;
    }

    @Override
    public List<RoleDto> getRoleByUserId(String userId) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userId);
        List<UserRoleEntity> userRoleEntities = userRoleService.getAllByObject(userRoleEntity);
        List<RoleDto> roleDtos = null;
        if (!CollectionUtils.isEmpty(userRoleEntities)) {
            List<String> roleIds = userRoleEntities.stream().map(e -> e.getRoleId()).collect(Collectors.toList());
            Example example = new Example(RoleEntity.class);
            example.createCriteria().andIn("id", roleIds);
            List<RoleEntity> roleEntities = roleMapper.selectByExample(example);
            roleDtos = roleEntities.stream().map(e -> {
                RoleDto roleDto = new RoleDto();
                BeanUtils.copyProperties(e, roleDto);
                return roleDto;
            }).collect(Collectors.toList());
        }
        return roleDtos;
    }

    @Override
    public PageInfo<RoleDto> getAllRoles(int page, int limit,String belongTo) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setAvailable(true);
        roleEntity.setBelongTo(belongTo);
        List<RoleEntity> roleEntities =  roleMapper.select(roleEntity);
        List<RoleDto> roleDtos = roleEntities.stream().map(e -> {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(e, roleDto);
            return roleDto;
        }).collect(Collectors.toList());
        PageInfo<RoleDto> returnPage = new PageInfo<>(roleDtos);
        Integer count = roleMapper.selectCount(null);
        Integer pages = count%limit==0 ? count/limit : (count/limit) + 1;
        returnPage.setTotal(count);
        returnPage.setPages(pages);
        return returnPage;
    }

    @Override
    public PageInfo<RoleDto> getByRoleVo(Integer start, Integer limit, RoleVo roleVo) {
        PageHelper.startPage(start, limit);
        Example example = new Example(RoleEntity.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isEmpty(roleVo.getName())) {
            roleVo.setName(null);
        } else {
            criteria.andLike("name", "%"+roleVo.getName()+"%");
        }
        if (StringUtils.isEmpty(roleVo.getAvailable())) {
            roleVo.setAvailable(null);
        } else {
            criteria.andEqualTo("available", roleVo.getAvailable());
        }
        criteria.andEqualTo("belongTo",roleVo.getBelongTo());
        List<RoleEntity> roleEntities = roleMapper.selectByExample(example);
        List<RoleDto> roleDtos = roleEntities.stream().map(e -> {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(e, roleDto);
            return roleDto;
        }).collect(Collectors.toList());
        PageInfo<RoleDto> returnPage = new PageInfo<>(roleDtos);
        Integer count = roleMapper.selectCountByExample(example);
        Integer pages = count%limit==0 ? count/limit : (count/limit) + 1;
        returnPage.setTotal(count);
        returnPage.setPages(pages);
        return returnPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDto save(RoleVo roleVo) {
        RoleEntity roleEntity = new RoleEntity();
        BeanUtils.copyProperties(roleVo, roleEntity);
        if (StringUtils.isEmpty(roleVo.getId())) {
            roleEntity.setInsertDate(new Date());
            roleEntity.setId(null);
            roleMapper.insert(roleEntity);
        } else {
            roleEntity.setInsertUserId(null);
            roleMapper.updateByPrimaryKeySelective(roleEntity);
        }
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(roleEntity, roleDto);
        return roleDto;
    }

    @Override
    public void delete(String id) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(id);
        roleEntity.setAvailable(false);
        roleMapper.updateByPrimaryKeySelective(roleEntity);
    }

    @Override
    public PageInfo<RoleDto> getAllRolesByUserId(int page, int limit, String userId) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userId);
        List<UserRoleEntity> userRoleEntities = userRoleService.getAllByObject(userRoleEntity);
        if (CollectionUtils.isEmpty(userRoleEntities)) {
            return null;
        }
        List<String> roleIds = userRoleEntities.stream().map(e -> e.getRoleId()).collect(Collectors.toList());
        Example example = new Example(RoleEntity.class);
        example.createCriteria().andIn("id", roleIds);
        PageHelper.startPage(page, limit);
        List<RoleEntity> roleEntities = roleMapper.selectByExample(example);
        List<RoleDto> roleDtos = roleEntities.stream().map(e -> {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(e, roleDto);
            return roleDto;
        }).collect(Collectors.toList());
        PageInfo<RoleDto> returnPage = new PageInfo<>(roleDtos);
        Integer count = roleMapper.selectCountByExample(example);
        Integer pages = count%limit==0 ? count/limit : (count/limit) + 1;
        returnPage.setTotal(count);
        returnPage.setPages(pages);
        return returnPage;
    }


}
