package com.youngsun.user.mapper;

import com.youngsun.common.mapper.MyMapper;
import com.youngsun.user.dto.UserDto;
import com.youngsun.user.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper extends MyMapper<UserEntity> {

    List<UserEntity> getAllByPositoinId(@Param("positionIds") List<String> positionIds);
    List<UserDto> getAll();
}

