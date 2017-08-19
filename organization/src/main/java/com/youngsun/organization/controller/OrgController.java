package com.youngsun.organization.controller;

import com.youngsun.common.util.ErrorType;
import com.youngsun.common.util.ExceptionUtil;
import com.youngsun.common.util.Result;
import com.youngsun.organization.dto.OrganizationDto;
import com.youngsun.organization.entity.OrgEntity;
import com.youngsun.organization.service.OrganizationService;
import com.youngsun.vo.OrgVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by 国平 on 2016/10/23.
 */
@RestController
@RequestMapping("/org")
public class OrgController {
    @Autowired
    private OrganizationService organizationService;
    private static final Logger log = LoggerFactory.getLogger(OrgController.class);
    @GetMapping(value = "/getAll")
    public Result getAll(String node){
        List<OrgEntity> result;
        try {
            result = organizationService.getAllByParentId(node);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(result);
    }
    
    @GetMapping(value = "/getAllByRule")
    public Result getAllByRule(String orgId){
        List<OrgEntity> result;
        try {
            result = organizationService.getAllByRule(orgId);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(result);
    }
    
    @GetMapping(value = "/getAllBesideSelf")
    public Result getAllBesideSelf(String node){
        List<OrgEntity> result;
        try {
            result = organizationService.getAllByParentId(node);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(result);
    }
    
    @GetMapping(value = "/getAllWithoutTree")
    public Result getAll(int page, int limit){
        Page<OrgEntity> pageReturn = null;
        try {
            PageRequest pageRequest = new PageRequest(page-1, limit);
            pageReturn = organizationService.getAll(pageRequest);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(pageReturn);
    }
    
    @GetMapping(value = "/getAllByName/{name}")
    public Result getAllByName(@PathVariable String name){
        List<OrgEntity> orgs = null;
        try {
            orgs = organizationService.getAllByName(name);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(orgs);
    }


    @GetMapping(value = "/getOrgRuleByOrgId/{orgId}")
    public Result getOrgRuleByOrgId(@PathVariable String orgId){
        List<OrgEntity> orgs = null;
        try {
            orgs = organizationService.getOrgRuleByOrgId(orgId);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(orgs);
    }
    
    @GetMapping(value = "/getAllLikeTree")
    public List<OrganizationDto> getAllLikeTree(){
        List<OrganizationDto> result = organizationService.getAllByParentId() ;
        return result;
    }
    
    @DeleteMapping(value = "deleteAll")
    public Result deleteAll(String[] ids){
        try {
            List idsList = CollectionUtils.arrayToList(ids);
            organizationService.delete(idsList);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.build(200,"删除成功",true);
    }
    
    @PostMapping(value = "add")
    public Result add(OrgVo orgVo, BindingResult result, HttpSession session){
        OrgEntity orgEntity = new OrgEntity();
        try {
            if (result.getErrorCount()>0){
                String errorMessage = "";
                for (FieldError error : result.getFieldErrors()){
                    errorMessage += error.getField()+ ":" + error.getDefaultMessage()+"</br>";
                }
                return Result.build(500, errorMessage, false, ErrorType.NormException.toString());
            }
            if(StringUtils.isEmpty(orgVo.getId())){
                orgVo.setInsertDate(new Date());
                orgVo.setInsertUserId(session.getAttribute("userId")==null?"guest":session.getAttribute("userId").toString());
                orgVo.setLeaf(true);
            }else {
                orgEntity = organizationService.getById(orgVo.getId());
                orgVo.setInsertDate(orgEntity.getInsertDate());
                orgVo.setInsertUserId(orgEntity.getInsertUserId());
            }
            orgVo.setUpDateDate(new Date());
            orgVo.setUpDateUserId(session.getAttribute("userId")==null?"guest":session.getAttribute("userId").toString());
            if(StringUtils.isEmpty(orgVo.getParentId()) || "root".equals(orgVo.getParentId())){
                orgVo.setParentId("0");
            }
            BeanUtils.copyProperties(orgVo,orgEntity);
            orgEntity = organizationService.save(orgEntity);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.ok(orgEntity);
    }
}
