package com.youngsun.authority.controller;

import com.youngsun.authority.dto.RolePermissionDto;
import com.youngsun.authority.entity.RolePermissionEntity;
import com.youngsun.authority.service.RolePermissionService;
import com.youngsun.common.util.ErrorType;
import com.youngsun.common.util.ExceptionUtil;
import com.youngsun.common.util.JsonUtils;
import com.youngsun.common.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 国平 on 2017/6/28.
 */
@RestController
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolepermissionService;
    private static final Logger log = LoggerFactory.getLogger(RolePermissionController.class);


    @PostMapping(value = "/rolePermission/add/{roleId}")
    public Result save(@RequestBody String rolePermissionDtos, @PathVariable String roleId){
        List<RolePermissionDto> rolePermissionDtosList = JsonUtils.jsonToList(rolePermissionDtos, RolePermissionDto.class);
        try {
            List<RolePermissionEntity> rolePermissionEntities = rolePermissionDtosList.stream().map(e -> {
                RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
                BeanUtils.copyProperties(e, rolePermissionEntity);
                return rolePermissionEntity;
            }).collect(Collectors.toList());
            rolepermissionService.saveAll(rolePermissionEntities, roleId);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(rolePermissionDtosList);
    }
}
