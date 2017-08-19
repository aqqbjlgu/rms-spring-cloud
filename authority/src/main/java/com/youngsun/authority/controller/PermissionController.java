package com.youngsun.authority.controller;

import com.github.pagehelper.PageInfo;
import com.youngsun.authority.dto.NavigationTreeDto;
import com.youngsun.authority.dto.PermissionDto;
import com.youngsun.authority.service.PermissionService;
import com.youngsun.authority.service.RoleService;
import com.youngsun.common.util.ErrorType;
import com.youngsun.common.util.ExceptionUtil;
import com.youngsun.common.util.JsonUtils;
import com.youngsun.common.util.Result;
import com.youngsun.vo.OrgVo;
import com.youngsun.vo.PermissionVo;
import com.youngsun.vo.RoleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by 国平 on 2017/6/28.
 */
@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    private static final Logger log = LoggerFactory.getLogger(PermissionController.class);
    @GetMapping(value = "/permission/getPermissionByRoleId/{roleId}")
    public Result getPermissionByRoleId(Integer start, Integer limit, @PathVariable String roleId){
        PageInfo<PermissionDto> result;
        try {
            result = permissionService.getPermissionByRoleId(start, limit, roleId);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(result);
    }

    @GetMapping(value = "/permission/getPermissionByRoleIds")
    public Result getPermissionByRoleIds(String [] roleIds){
        List<PermissionDto> result;
        try {
            result = permissionService.getPermissionByRoleIds(Arrays.asList(roleIds));
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(result);
    }

    @GetMapping(value = "/permission/getAllPermissions/{belongTo}")
    public Result getAllPermissions(Integer start, Integer limit, @PathVariable String belongTo){
        PageInfo<PermissionDto> pageInfo = null;
        try {
            pageInfo = permissionService.getAllPermission(start, limit, belongTo);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(pageInfo);
    }

    @GetMapping(value = "/permission/getByPermissionVo")
    public Result getByPermissionVo(int page, int limit, String permissionVoString){
        PageInfo<PermissionDto> pageInfo;
        PermissionVo permissionVo = JsonUtils.jsonToPojo(permissionVoString, PermissionVo.class);
        try {
            pageInfo = permissionService.getByPermissionVo(page, limit, permissionVo);
        }catch (Exception e){
            log.error("500", e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(pageInfo);
    }

    @GetMapping(value = "/permission/getNavigationTree")
    public List<NavigationTreeDto> getNavigationTree(@RequestBody String permissionVos){
        List<PermissionVo> permissionVoList = JsonUtils.jsonToList(permissionVos, PermissionVo.class);
        List<NavigationTreeDto> navigationTreeDtos = new ArrayList<>();
        try {
            navigationTreeDtos = permissionService.getNavigationTree(permissionVoList);
        }catch (Exception e){
            log.error("500", e);
        }
        return navigationTreeDtos;
    }

    @GetMapping(value = "/permission/getAllPermissionWithTree/{belongTo}")
    public List<PermissionDto> getAllPermissionWithTree(@PathVariable String belongTo){
        List<PermissionDto> permissionDtos = new ArrayList<>();
        try {
            permissionDtos = permissionService.getAllPermissionWithTree(belongTo);
        }catch (Exception e){
            log.error("500", e);
        }
        return permissionDtos;
    }

    @PostMapping(value = "/permission/save")
    public Result save(String permissionVoString){
        PermissionVo permissionVo = JsonUtils.jsonToPojo(permissionVoString, PermissionVo.class);
        PermissionDto permissionDto;
        try {
            if(StringUtils.isEmpty(permissionVo.getParentId()) || "root".equals(permissionVo.getParentId())){
                permissionVo.setParentId("0");
            }
            permissionDto = permissionService.save(permissionVo);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(permissionDto);
    }

    @GetMapping(value = "/permission/delete/{id}/{leaf}/{parentId}")
    public Result delete(@PathVariable String id, @PathVariable Boolean leaf, @PathVariable String parentId){
        PermissionDto permissionDto = null;
        try {
            permissionService.delete(id, leaf, parentId);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(permissionDto);
    }

    @GetMapping(value = "/permission/getPermissionByParentId")
    public Result getPermissionByParentId(String node){
        List<PermissionDto> permissionDtos = null;
        try {
            permissionDtos = permissionService.getPermissionByParentId(node);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(permissionDtos);
    }
}
