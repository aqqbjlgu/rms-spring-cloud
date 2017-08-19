package com.youngsun.organization.controller;

import com.youngsun.common.util.ErrorType;
import com.youngsun.common.util.ExceptionUtil;
import com.youngsun.common.util.Result;
import com.youngsun.organization.dto.OrgTypeAndRuleDto;
import com.youngsun.organization.entity.OrgTypeEntity;
import com.youngsun.organization.service.OrgTypeService;
import com.youngsun.vo.OrgTypeVo;
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
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by 国平 on 2016/10/23.
 */
@RestController
@RequestMapping("/orgType")
public class OrgTypeContorller {

    @Autowired
    private OrgTypeService orgTypeService;
    
    private static final Logger log = LoggerFactory.getLogger(OrgTypeContorller.class);

    @GetMapping(value = "/getAll")
    public Result getAllOrgType(int page, int limit){
        Page<OrgTypeEntity> pageReturn = null;
        try {
            PageRequest pageRequest = new PageRequest(page-1, limit);
            pageReturn = orgTypeService.getAll(pageRequest);
        }catch (Exception e){
            log.error("500",e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(pageReturn);
    }
    
    @GetMapping(value = "/getOrgTypeByPid/{pid}")
    public Result getOrgTypeByPid(@PathVariable String pid, int page, int limit){
        List<OrgTypeAndRuleDto> orgTypeEntities;
        try {
            orgTypeEntities = orgTypeService.getOrgTypeUsePid(pid);
        }catch (Exception e){
            log.error("500",e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(orgTypeEntities);
    }
    
    @GetMapping(value = "/getAllWithoutPage")
    public Result getAllWithoutPage(){
        List<OrgTypeEntity> result;
        try {
            result = orgTypeService.getAll();
        }catch (Exception e){
            log.error("500",e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(result);
    }
    
    @GetMapping(value = "/getOthers/{id}")
    public Result getOthers(@PathVariable String id){
        List<OrgTypeEntity> result;
        try {
            result = orgTypeService.getOthers(id);
        }catch (Exception e){
            log.error("500",e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(result);
    }

    @PostMapping(value = "/add")
    public Result add(@Valid OrgTypeVo orgTypeVo, BindingResult result, HttpSession session){
        OrgTypeEntity orgTypeEntity = new OrgTypeEntity();
        try {
            if (result.getErrorCount()>0){
                String errorMessage = "";
                for (FieldError error : result.getFieldErrors()){
                    errorMessage += error.getField()+ ":" + error.getDefaultMessage()+"</br>";
                }
                return Result.build(500, errorMessage, false, ErrorType.NormException.toString());
            }
            if(StringUtils.isEmpty(orgTypeVo.getId())){
                orgTypeVo.setInsertDate(new Date());
                orgTypeVo.setInsertUserId(session.getAttribute("userId")==null?"guest":session.getAttribute("userId").toString());
            }else{
                orgTypeEntity = orgTypeService.getById(orgTypeVo.getId());
                orgTypeVo.setInsertDate(orgTypeEntity.getInsertDate());
                orgTypeVo.setInsertUserId(orgTypeEntity.getInsertUserId());
            }
            orgTypeVo.setUpDateDate(new Date());
            orgTypeVo.setUpDateUserId(session.getAttribute("userId")==null?"guest":session.getAttribute("userId").toString());
            BeanUtils.copyProperties(orgTypeVo,orgTypeEntity);
            orgTypeEntity = orgTypeService.save(orgTypeEntity);
        }catch (Exception e){
            log.error("500",e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(orgTypeEntity);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable String id){
        OrgTypeEntity orgTypeEntity = new OrgTypeEntity();
        orgTypeEntity.setId(id);
        orgTypeService.delete(orgTypeEntity);
    }

    @PutMapping(value = "update")
    public Result update(OrgTypeEntity orgTypeEntity){
        return Result.ok(orgTypeService.save(orgTypeEntity));
    }
    
    @DeleteMapping(value = "deleteAll")
    public Result deleteAll(String[] ids){
        try {
            List idsList = CollectionUtils.arrayToList(ids);
            orgTypeService.delete(idsList);
        }catch (Exception e){
            log.error("500",e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.build(200,"删除成功",true);
    }
}
