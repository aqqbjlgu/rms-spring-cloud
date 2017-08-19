package com.youngsun.organization.controller;

import com.youngsun.common.util.ErrorType;
import com.youngsun.common.util.ExceptionUtil;
import com.youngsun.common.util.Result;
import com.youngsun.organization.entity.OrgTypeRuleEntity;
import com.youngsun.organization.service.OrgTypeRuleService;
import com.youngsun.vo.OrgTypeRuleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/orgTypeRule")
public class OrgTypeRuleContorller {

    @Autowired
    private OrgTypeRuleService orgTypeRuleService;
    private static final Logger log = LoggerFactory.getLogger(OrgTypeRuleContorller.class);

    @PostMapping(value = "/add")
    public Result add(@Valid OrgTypeRuleVo orgTypeRuleVo, BindingResult result, HttpSession session){
        OrgTypeRuleEntity orgTypeRuleEntity = new OrgTypeRuleEntity();
        try {
            if (result.getErrorCount()>0){
                String errorMessage = "";
                for (FieldError error : result.getFieldErrors()){
                    errorMessage += error.getField()+ ":" + error.getDefaultMessage()+"</br>";
                }
                return Result.build(500, errorMessage, false, ErrorType.NormException.toString());
            }
            if(StringUtils.isEmpty(orgTypeRuleVo.getRid())){
                orgTypeRuleVo.setInsertDate(new Date());
                orgTypeRuleVo.setInsertUserId(session.getAttribute("userId")==null?"guest":session.getAttribute("userId").toString());
            }else{
                orgTypeRuleEntity = orgTypeRuleService.getById(orgTypeRuleVo.getRid());
                orgTypeRuleVo.setInsertDate(orgTypeRuleEntity.getInsertDate());
                orgTypeRuleVo.setInsertUserId(orgTypeRuleEntity.getInsertUserId());
            }
            orgTypeRuleVo.setUpDateDate(new Date());
            orgTypeRuleVo.setId(orgTypeRuleVo.getRid());
            orgTypeRuleVo.setUpDateUserId(session.getAttribute("userId")==null?"guest":session.getAttribute("userId").toString());
            BeanUtils.copyProperties(orgTypeRuleVo,orgTypeRuleEntity);
            orgTypeRuleEntity = orgTypeRuleService.save(orgTypeRuleEntity);
        }catch (Exception e){
            log.error("500", e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(orgTypeRuleEntity);
    }

    @DeleteMapping(value = "deleteAll")
    public Result deleteAll(String[] ids){
        try {
            List idsList = CollectionUtils.arrayToList(ids);
            orgTypeRuleService.delete(idsList);
        }catch (Exception e){
            log.error("500", e);
            if(e instanceof RuntimeException){
                return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
            }
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.NormException.toString());
        }
        return Result.build(200,"删除成功",true);
    }
}
