package com.youngsun.authority.controller;

import com.github.pagehelper.PageInfo;
import com.youngsun.authority.dto.RoleDto;
import com.youngsun.authority.dto.RoleDto;
import com.youngsun.authority.dto.RolePermissionDto;
import com.youngsun.authority.dto.UserRoleDto;
import com.youngsun.authority.entity.RolePermissionEntity;
import com.youngsun.authority.entity.UserRoleEntity;
import com.youngsun.authority.service.RoleService;
import com.youngsun.authority.service.UserRoleService;
import com.youngsun.common.util.ErrorType;
import com.youngsun.common.util.ExceptionUtil;
import com.youngsun.common.util.JsonUtils;
import com.youngsun.common.util.Result;
import com.youngsun.vo.RoleVo;
import com.youngsun.vo.RoleVo;
import com.youngsun.vo.UserRoleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 国平 on 2017/6/28.
 */
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    private static final Logger log = LoggerFactory.getLogger(RoleController.class);
    @GetMapping(value = "/getRoleByUserId")
    public Result getRoleByUserId(String userId){
        List<RoleDto> result;
        try {
            result = roleService.getRoleByUserId(userId);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(result);
    }

    @GetMapping(value = "/role/getAllRoles/{belongTo}")
    public Result getAllRoles(int page, int limit, @PathVariable String belongTo){
        PageInfo<RoleDto> result;
        try {
            result = roleService.getAllRoles(page, limit, belongTo);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(result);
    }

    @GetMapping(value = "/role/getByRoleVo")
    public Result getByRoleVo(Integer start, Integer limit, String roleVoString){
        PageInfo<RoleDto> result;
        RoleVo roleVo = JsonUtils.jsonToPojo(roleVoString, RoleVo.class);
        try {
            result = roleService.getByRoleVo(start, limit, roleVo);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(result);
    }

    @PostMapping(value = "/role/save")
    public Result roleSave(String roleVoString){
        RoleDto roleDto;
        RoleVo roleVo = JsonUtils.jsonToPojo(roleVoString, RoleVo.class);
        try {
            roleDto = roleService.save(roleVo);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(roleDto);
    }

    @GetMapping(value = "/role/delete/{id}")
    public Result delete(@PathVariable String id){
        RoleDto roleDto = null;
        try {
            roleService.delete(id);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(roleDto);
    }

    @GetMapping(value = "/role/getAllRolesByUserId/{userId}")
    public Result getAllRolesByUserId(int page, int limit, @PathVariable String userId){
        PageInfo<RoleDto> result;
        try {
            result = roleService.getAllRolesByUserId(page, limit, userId);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(result);
    }

    @PostMapping(value = "/role/userRole/add/{userId}")
    public Result saveUserRole(@RequestBody String userRoleDtos, @PathVariable String userId, BindingResult result, HttpSession session){
        List<UserRoleDto> userRoleDtosList = JsonUtils.jsonToList(userRoleDtos, UserRoleDto.class);
        try {
            if (result.getErrorCount()>0){
                String errorMessage = "";
                for (FieldError error : result.getFieldErrors()){
                    errorMessage += error.getField()+ ":" + error.getDefaultMessage()+"</br>";
                }
                return Result.build(500, errorMessage, false, ErrorType.NormException.toString());
            }
            List<UserRoleEntity> userRoleEntities = userRoleDtosList.stream().map(e -> {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                BeanUtils.copyProperties(e, userRoleEntity);
                return userRoleEntity;
            }).collect(Collectors.toList());
            userRoleService.saveAll(userRoleEntities, userId);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(userRoleDtosList);
    }

}
