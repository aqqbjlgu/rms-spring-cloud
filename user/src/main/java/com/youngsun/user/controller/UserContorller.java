package com.youngsun.user.controller;

import com.github.pagehelper.PageInfo;
import com.youngsun.common.util.ErrorType;
import com.youngsun.common.util.ExceptionUtil;
import com.youngsun.user.dto.UserDto;
import com.youngsun.user.dto.UserOrgPosDto;
import com.youngsun.user.entity.UserEntity;
import com.youngsun.user.service.UserService;
import com.youngsun.vo.UserOrgPosVo;
import com.youngsun.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.youngsun.common.util.Result;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 国平 on 2016/10/23.
 */
@RestController
@RequestMapping("/user")
public class UserContorller {

    @Autowired
    private UserService userService;
    
    private static final Logger log = LoggerFactory.getLogger(UserContorller.class);

    @GetMapping(value = "/getAll")
    public Result getAll(int page, int limit, String belongTo){
        log.info("page================="+page);
        PageInfo<UserDto> pageInfo = null;
        try {
            List list = userService.getAllBybelongTo(page, limit, belongTo);
            pageInfo = new PageInfo<UserDto>(list);
        }catch (Exception e){
            log.error("500", e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(pageInfo);
    }

    @GetMapping(value = "/getAllByBelongTo/{belongTo}")
    public Result getAllByBelongTo(int page, int limit, @PathVariable String belongTo){
        log.info("page================="+page);
        PageInfo<UserDto> pageInfo = null;
        try {
            pageInfo = userService.getAllWithOidAndPid(page, limit, belongTo);
        }catch (Exception e){
            log.error("500", e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(pageInfo);
    }

    @GetMapping(value = "/getByUserVo")
    public Result getByUserVo(int page, int limit, UserVo userVo){
        PageInfo<UserDto> pageInfo = null;
        try {
            PageInfo list = userService.getByUserVo(page, limit, userVo);
        }catch (Exception e){
            log.error("500", e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(pageInfo);
    }
    
    @GetMapping(value = "/getByEmail")
    public Result getByEmail(String email){
        UserEntity userEntity = null;
        try {
            userEntity = userService.getByEmail(email);
        }catch (Exception e){
            log.error("500", e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(userEntity);
    }
    
    @GetMapping(value = "/getByIdCard/{idCard}")
    public Result getByIdCard(@PathVariable String idCard){
        UserEntity userEntity = null;
        try {
            userEntity = userService.getByIdCard(idCard);
        }catch (Exception e){
            log.error("500", e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(userEntity);
    }
    
    @GetMapping(value = "/getByPhone/{phone}")
    public Result getByPhone(@PathVariable String phone){
        UserEntity userEntity = null;
        try {
            userEntity = userService.getByPhone(phone);
        }catch (Exception e){
            log.error("500", e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(userEntity);
    }
    
    @GetMapping(value = "/getByNickName/{nickName}")
    public Result getByNickName(@PathVariable String nickName){
        UserEntity userEntity = null;
        try {
            userEntity = userService.getByNickName(nickName);
        }catch (Exception e){
            log.error("500", e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(userEntity);
    }

    @PostMapping(value = "/add")
    public Result add(@Valid UserVo userVo, BindingResult result, HttpSession session){
        UserDto userDto = new UserDto();
        if (StringUtils.isEmpty(userVo.getId())) {
            userVo.setId(null);
        }
        try {
            if (result.getErrorCount()>0){
                String errorMessage = "";
                for (FieldError error : result.getFieldErrors()){
                    errorMessage += error.getField()+ ":" + error.getDefaultMessage()+"</br>";
                }
                return Result.build(500, errorMessage, false, ErrorType.NormException.toString());
            }
            UserEntity userEntity = userService.getById(userVo.getId());
            if(StringUtils.isEmpty(userVo.getId())){
                userVo.setInsertDate(new Date());
                userVo.setInsertUserId(session.getAttribute("userId")==null?"guest":session.getAttribute("userId").toString());
            }else{
                userVo.setInsertDate(userEntity.getInsertDate());
                userVo.setInsertUserId(userEntity.getInsertUserId());
            }
            userVo.setUpDateDate(new Date());
            userVo.setUpDateUserId(session.getAttribute("userId")==null?"guest":session.getAttribute("userId").toString());
            BeanUtils.copyProperties(userVo,userDto);
            if (!CollectionUtils.isEmpty(userVo.getUserOrgPosVos())) {
                List<UserOrgPosVo> userOrgPosVos = userVo.getUserOrgPosVos().stream().filter(e -> !StringUtils.isEmpty(e.getOrgId())).collect(Collectors.toList());
                List<UserOrgPosDto> userOrgPosDtos = userOrgPosVos.stream().map(e -> {
                    UserOrgPosDto userOrgPosDto = new UserOrgPosDto();
                    BeanUtils.copyProperties(e, userOrgPosDto);
                    return userOrgPosDto;
                }).collect(Collectors.toList());
                userDto.setUserOrgPosDtos(userOrgPosDtos);
            }
            userService.save(userDto);
        }catch (Exception e){
            log.error("500", e);
            return Result.build(500, ExceptionUtil.getStackTrace(e), false, ErrorType.RuntimeException.toString());
        }
        return Result.ok(userDto);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable String id){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userService.delete(userEntity);
    }

    @PutMapping(value = "update")
    public Result update(UserEntity userEntity){
        return Result.ok(userService.save(userEntity));
    }
    
    @RequestMapping(value = "deleteAll",method = RequestMethod.DELETE)
    public @ResponseBody
    Result deleteAll(String[] ids){
        try {
            List idsList = CollectionUtils.arrayToList(ids);
            userService.delete(idsList);
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
