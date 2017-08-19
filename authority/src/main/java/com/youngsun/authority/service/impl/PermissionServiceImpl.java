package com.youngsun.authority.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youngsun.authority.controller.PermissionController;
import com.youngsun.authority.dto.NavigationTreeDto;
import com.youngsun.authority.dto.PermissionDto;
import com.youngsun.authority.dto.RolePermissionDto;
import com.youngsun.authority.entity.PermissionEntity;
import com.youngsun.authority.mapper.PermissionMapper;
import com.youngsun.authority.service.PermissionService;
import com.youngsun.authority.service.RolePermissionService;
import com.youngsun.authority.service.RoleService;
import com.youngsun.vo.PermissionVo;
import com.youngsun.vo.RolePermissionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by 国平 on 2016/10/21.
 */
@Service
public class PermissionServiceImpl extends BasicServiceImpl<PermissionEntity> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    private static final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);
    @Override
    public PageInfo<PermissionDto> getPermissionByRoleId(Integer start, Integer limit, String roleId) {
        List<RolePermissionDto> rolePermissionDtos = rolePermissionService.getRolePermissionByRoleId(roleId);
        if (CollectionUtils.isEmpty(rolePermissionDtos)) {
            return null;
        }
        List<String> permissionIds = rolePermissionDtos.stream().map(RolePermissionDto::getPermissionId).collect(Collectors.toList());
        return this.getPermissionByIds(start, limit, permissionIds);
    }

    @Override
    public PageInfo<PermissionDto> getPermissionByIds(Integer start, Integer limit, List<String> ids) {
        PageHelper.startPage(start, limit);
        Example example = new Example(PermissionEntity.class);
        example.createCriteria().andIn("id", ids);
        List<PermissionEntity> permissionEntities = permissionMapper.selectByExample(example);
        List<PermissionDto> permissionDtos = permissionEntities.stream().map(e -> {
            PermissionDto permissionDto = new PermissionDto();
            BeanUtils.copyProperties(e, permissionDto);
            permissionDto.setPermissionName(e.getName());
            permissionDto.setPermissionAvailable(e.getAvailable());
            return permissionDto;
        }).collect(Collectors.toList());
        PageInfo<PermissionDto> returnPage = new PageInfo<>(permissionDtos);
        Integer count = permissionMapper.selectCountByExample(example);
        Integer pages = count%limit==0 ? count/limit : (count/limit) + 1;
        returnPage.setTotal(count);
        returnPage.setPages(pages);
        return returnPage;
    }

    @Override
    public List<PermissionDto> getPermissionByRoleIds(List<String> roleIds) {
        List<RolePermissionDto> rolePermissionDtos = rolePermissionService.getRolePermissionByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(rolePermissionDtos)) {
            return null;
        }

        List<String> prermissionIds = rolePermissionDtos.stream().map(RolePermissionDto::getPermissionId).collect(Collectors.toList());
        Example example = new Example(PermissionEntity.class);
        example.createCriteria().andIn("id", prermissionIds);
        List<PermissionEntity> permissionEntities = permissionMapper.selectByExample(example);
        return permissionEntities.stream().map(e -> {
            PermissionDto permissionDto = new PermissionDto();
            BeanUtils.copyProperties(e, permissionDto);
            permissionDto.setPermissionName(e.getName());
            permissionDto.setPermissionAvailable(e.getAvailable());
            return permissionDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<PermissionDto> getPermissionByParentId(String parentId) {
        if("root".equals(parentId)){
            parentId = 0+"";
        }
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setParentId(parentId);
        permissionEntity.setAvailable(true);
        List<PermissionEntity> permissionEntities = permissionMapper.select(permissionEntity);
        return permissionEntities.stream().map(e -> {
            PermissionDto permissionDto = new PermissionDto();
            BeanUtils.copyProperties(e, permissionDto);
            permissionDto.setPermissionName(e.getName());
            permissionDto.setPermissionAvailable(e.getAvailable());
            return permissionDto;
        }).collect(Collectors.toList());
    }

    @Override
    public PageInfo<PermissionDto> getAllPermission(Integer start, Integer limit, String belongTo) {
        PageHelper.startPage(start, limit);
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setBelongTo(belongTo);
        permissionEntity.setAvailable(true);
        List<PermissionEntity> permissionEntities = permissionMapper.select(permissionEntity);
        List<PermissionDto> permissionDtos = permissionEntities.stream().map(e -> {
            PermissionDto permissionDto = new PermissionDto();
            BeanUtils.copyProperties(e, permissionDto);
            permissionDto.setPermissionAvailable(e.getAvailable());
            permissionDto.setPermissionName(e.getName());
            return permissionDto;
        }).collect(Collectors.toList());
        PageInfo<PermissionDto> returnPage = new PageInfo<>(permissionDtos);
        Integer count = permissionMapper.selectCount(null);
        Integer pages = count%limit==0 ? count/limit : (count/limit) + 1;
        returnPage.setTotal(count);
        returnPage.setPages(pages);
        return returnPage;
    }

    @Override
    public PageInfo<PermissionDto> getByPermissionVo(Integer start, Integer limit, PermissionVo permissionVo) {
        PageHelper.startPage(start, limit);
        Example example = new Example(PermissionEntity.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isEmpty(permissionVo.getPermissionName())) {
            permissionVo.setPermissionName(null);
        } else {
            criteria.andLike("name", "%"+permissionVo.getPermissionName()+"%");
        }
        if (StringUtils.isEmpty(permissionVo.getUrl())) {
            permissionVo.setUrl(null);
        } else {
            criteria.andLike("url", "%"+permissionVo.getUrl()+"%");
        }
        if (StringUtils.isEmpty(permissionVo.getPercode())) {
            permissionVo.setPercode(null);
        } else {
            criteria.andLike("percode", "%"+permissionVo.getPercode()+"%");
        }
        if (StringUtils.isEmpty(permissionVo.getType())) {
            permissionVo.setType(null);
        } else {
            criteria.andEqualTo("type", permissionVo.getType());
        }
        if (StringUtils.isEmpty(permissionVo.getPermissionAvailable())) {
            permissionVo.setPermissionAvailable(null);
        } else {
            criteria.andEqualTo("available", permissionVo.getPermissionAvailable());
        }
        criteria.andEqualTo("belongTo",permissionVo.getBelongTo());
        List<PermissionEntity> permissionEntities = permissionMapper.selectByExample(example);
        List<PermissionDto> permissionDtos = permissionEntities.stream().map(e -> {
            PermissionDto permissionDto = new PermissionDto();
            BeanUtils.copyProperties(e, permissionDto);
            permissionDto.setPermissionAvailable(e.getAvailable());
            permissionDto.setPermissionName(e.getName());
            return permissionDto;
        }).collect(Collectors.toList());
        PageInfo<PermissionDto> returnPage = new PageInfo<>(permissionDtos);
        Integer count = permissionMapper.selectCountByExample(example);
        Integer pages = count%limit==0 ? count/limit : (count/limit) + 1;
        returnPage.setTotal(count);
        returnPage.setPages(pages);
        return returnPage;
    }



    @Override
    public List<PermissionDto> getAllPermissionWithTree(String belongTo) {
        return getAllPermissionsWithTree(0+"", belongTo);
    }

    private List<PermissionDto> getAllPermissionsWithTree(String parentId, String belongTo) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setParentId(parentId);
        permissionEntity.setBelongTo(belongTo);
        permissionEntity.setAvailable(true);
        List<PermissionEntity> permissionEntities = permissionMapper.select(permissionEntity);
        List<PermissionDto> permissionDtos = new ArrayList<>();
        for(PermissionEntity permissionEntity1 : permissionEntities){
            PermissionDto permissionDto = new PermissionDto();
            permissionDto.setPermissionName(permissionEntity1.getName());
            permissionDto.setPermissionAvailable(permissionEntity1.getAvailable());
            BeanUtils.copyProperties(permissionEntity1, permissionDto);
            if(!permissionEntity1.getLeaf()){
                permissionDto.setChildren(getAllPermissionsWithTree(permissionEntity1.getId(), belongTo));
                permissionDtos.add(permissionDto);
            }else{
                permissionDtos.add(permissionDto);
            }
        }
        return permissionDtos;
    }

    @Override
    public List<NavigationTreeDto> getNavigationTree(List<PermissionVo> permissionVos) {
        List<NavigationTreeDto> navigationTreeDtos = new ArrayList<>();
        List<PermissionVo> permissionVoList = permissionVos.stream().filter(e -> e.getType().equals("1")).collect(Collectors.toList());
        permissionVoList.stream().forEach(e -> {
            NavigationTreeDto navigationTreeDto = new NavigationTreeDto();
            navigationTreeDto.setText(e.getPermissionName());
            navigationTreeDto.setLeaf(e.getLeaf());
            navigationTreeDto.setView(e.getUrl());
            navigationTreeDto.setRouteId(e.getUrl());
            navigationTreeDto.setIconCls(e.getIconCls());
            if (e.getLeaf()) {
                navigationTreeDtos.add(navigationTreeDto);
            }
            permissionVoList.stream().forEach(e1 -> {
                if (e1.getParentId().equals(e.getId())) {
                    if (e.getChildren() == null) {
                        e.setChildren(new ArrayList());
                    }
                    e.getChildren().add(e1);
                }
            });
        });
        return navigationTreeDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id, Boolean leaf, String parentId) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setId(id);
        permissionEntity.setAvailable(false);
        permissionMapper.updateByPrimaryKeySelective(permissionEntity);
        if (!leaf) {
            PermissionEntity childPermissionEntity = new PermissionEntity();
            childPermissionEntity.setParentId(id);
            List<PermissionEntity> permissionEntities = permissionMapper.select(childPermissionEntity);
            List<String> ids = permissionEntities.stream().map(e -> e.getId()).collect(Collectors.toList());
            Example example = new Example(PermissionEntity.class);
            example.createCriteria().andIn("id", ids);
            childPermissionEntity.setAvailable(false);
            permissionMapper.updateByExampleSelective(childPermissionEntity, example);
        }
        PermissionEntity permissionEntity1 = new PermissionEntity();
        permissionEntity1.setParentId(parentId);
        permissionEntity1.setAvailable(true);
        List <PermissionEntity> permissionEntities = permissionMapper.select(permissionEntity1);
        if (CollectionUtils.isEmpty(permissionEntities)) {
            PermissionEntity permissionParentEntity = new PermissionEntity();
            permissionParentEntity.setId(parentId);
            permissionParentEntity.setLeaf(true);
            permissionParentEntity.setAvailable(false);
            permissionMapper.updateByPrimaryKeySelective(permissionParentEntity);
        }
    }

    private List<NavigationTreeDto> getNavigationTrees(String parentId, String type, String belongTo) {
        Example example = new Example(PermissionEntity.class);
        example.createCriteria().andEqualTo("parentId", parentId)
                                .andEqualTo("belongTo", belongTo)
                                .andEqualTo("available", true)
                                .andEqualTo("type", type);
        example.setOrderByClause("sort_string asc");
        List<PermissionEntity> permissionEntities = permissionMapper.selectByExample(example);
        List<NavigationTreeDto> navigationTreeDtos = new ArrayList<>();
        for(PermissionEntity permissionEntity : permissionEntities){
            NavigationTreeDto navigationTreeDto = new NavigationTreeDto();
            navigationTreeDto.setText(permissionEntity.getName());
            navigationTreeDto.setLeaf(permissionEntity.getLeaf());
            navigationTreeDto.setView(permissionEntity.getUrl());
            navigationTreeDto.setRouteId(permissionEntity.getUrl());
            navigationTreeDto.setIconCls(permissionEntity.getIconCls());
            if(!permissionEntity.getLeaf()){
                navigationTreeDto.setChildren(getNavigationTrees(permissionEntity.getId(), permissionEntity.getType(), belongTo));
                navigationTreeDtos.add(navigationTreeDto);
            }else{
                navigationTreeDtos.add(navigationTreeDto);
            }
        }
        return navigationTreeDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public PermissionDto save(PermissionVo permissionVo) {
        PermissionEntity permissionEntity = new PermissionEntity();
        PermissionEntity permissionParentEntity = null;
        StringBuilder sb = new StringBuilder();
        if (!permissionVo.getParentId().equals("0") && !StringUtils.isEmpty(permissionVo.getParentId())) {
            permissionParentEntity = permissionMapper.selectByPrimaryKey(permissionVo.getParentId());
            permissionVo.setParentIds(getParentIds(permissionVo.getParentId(), sb));
        } else {
            permissionVo.setParentIds("0");
        }

        BeanUtils.copyProperties(permissionVo, permissionEntity);
        permissionEntity.setName(permissionVo.getPermissionName());
        permissionEntity.setAvailable(permissionVo.getPermissionAvailable());
        if (StringUtils.isEmpty(permissionVo.getId())) {
            permissionEntity.setLeaf(true);
            permissionEntity.setInsertDate(new Date());
            permissionEntity.setId(null);
            permissionMapper.insert(permissionEntity);
        } else {
            permissionEntity.setInsertUserId(null);
            permissionMapper.updateByPrimaryKeySelective(permissionEntity);
        }
        if (permissionParentEntity != null) {
            permissionParentEntity.setLeaf(false);
            permissionMapper.updateByPrimaryKeySelective(permissionParentEntity);
        }
        PermissionDto permissionDto = new PermissionDto();
        BeanUtils.copyProperties(permissionEntity, permissionDto);
        permissionDto.setPermissionAvailable(permissionEntity.getAvailable());
        permissionDto.setPermissionName(permissionEntity.getName());
        return permissionDto;
    }

    private String getParentIds(String parentId, StringBuilder sb) {
        PermissionEntity permissionParentEntity = permissionMapper.selectByPrimaryKey(parentId);
        if (permissionParentEntity != null && !permissionParentEntity.getLeaf()) {
            sb.append(parentId).append(",");
            getParentIds(permissionParentEntity.getParentId(), sb);
        } else {
            sb.append(parentId).append(",");
        }

        String parentIds = sb.substring(0, sb.length()-1);
        return parentIds;
    }

}
