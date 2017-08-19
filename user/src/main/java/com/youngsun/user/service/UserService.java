package com.youngsun.user.service;


import com.github.pagehelper.PageInfo;
import com.youngsun.user.dto.UserDto;
import com.youngsun.user.entity.UserEntity;
import com.youngsun.vo.UserVo;

import java.util.List;

/**
 * Created by 国平 on 2016/10/21.
 */
public interface UserService extends BasicService<UserEntity> {
    
    void delete(List<String> ids) throws Exception;
    
    List<UserEntity> getAllByPositoinId(List<String> positionIds);

    PageInfo<UserDto> getAllWithOidAndPid(Integer page, Integer rows, String belongTo);

    PageInfo<UserDto> getByUserVo(Integer page, Integer rows, UserVo userVo);

    List<UserDto> getAllBybelongTo(Integer page, Integer rows, String belongeTo);

    UserEntity getByEmail(String email);
    
    UserEntity getByIdCard(String idCard);
    
    UserEntity getByPhone(String phone);
    
    UserEntity getByNickName(String nickName);
    
    UserEntity save(UserDto UserDto);
    
    
}
