package com.youngsun.user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youngsun.user.dto.UserDto;
import com.youngsun.user.dto.UserOrgPosDto;
import com.youngsun.user.entity.UserEntity;
import com.youngsun.user.entity.UserOrgPosEntity;
import com.youngsun.user.mapper.UserMapper;
import com.youngsun.user.service.UserOrgPosService;
import com.youngsun.user.service.UserService;
import com.youngsun.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by 国平 on 2016/10/21.
 */
@Service
public class UserServiceImpl extends BasicServiceImpl<UserEntity> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserOrgPosService userOrgPosService;
    //private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);
    
    @Transactional(rollbackFor=Exception.class)
    public void delete(List<String> ids) throws Exception {
        Example example = new Example(UserEntity.class);
        example.createCriteria().andIn("id", ids);
        userMapper.deleteByExample(example);
        userOrgPosService.deletByPid(ids);
    }
    
    public List<UserEntity> getAllByPositoinId(List<String> positionIds) {
        return userMapper.getAllByPositoinId(positionIds);
    }

    public PageInfo<UserDto> getAllWithOidAndPid(Integer page, Integer rows, String belongTo) {
        PageHelper.startPage(page, rows);
        UserEntity userEntity = new UserEntity();
        userEntity.setBelongTo(belongTo);
        List<UserEntity> userEntities = userMapper.select(userEntity);
        List<String> userIds = userEntities.stream().map(UserEntity :: getId).collect(Collectors.toList());
        Example example = new Example(UserOrgPosEntity.class);
        example.createCriteria().andIn("pId",userIds);
        List<UserOrgPosEntity> userOrgPosEntities = userOrgPosService.getAllByExample(example);
        List<UserDto> newUserDtos = userEntities.stream().map(e -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(e, userDto);
            if (!CollectionUtils.isEmpty(userOrgPosEntities)) {
                List<UserOrgPosDto> userOrgPosDtos = userOrgPosEntities.stream()
                        .filter(e1 -> e1.getpId().equals(e.getId()))
                        .map(e2 -> {
                            UserOrgPosDto userOrgPosDto = new UserOrgPosDto();
                            BeanUtils.copyProperties(e2, userOrgPosDto);
                            return userOrgPosDto;
                        }).collect(Collectors.toList());
                userDto.setUserOrgPosDtos(userOrgPosDtos);
            }
            return userDto;
        }).collect(Collectors.toList());
        PageInfo<UserDto> returnPage = new PageInfo<>(newUserDtos);
        Integer count = userMapper.selectCount(null);
        Integer pages = count%rows==0 ? count/rows : (count/rows) + 1;
        returnPage.setTotal(count);
        returnPage.setPages(pages);
        return returnPage;
    }

    @Override
    public PageInfo<UserDto> getByUserVo(Integer page, Integer rows, UserVo userVo) {
        PageHelper.startPage(page, rows);
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isEmpty(userVo.getName())) {
            userVo.setName(null);
        } else {
            criteria.andLike("name", "%"+userVo.getName()+"%");
        }
        if (StringUtils.isEmpty(userVo.getNickName())) {
            userVo.setNickName(null);
        } else {
            criteria.andLike("nickName", "%"+userVo.getNickName()+"%");
        }
        if (StringUtils.isEmpty(userVo.getEmail())) {
            userVo.setEmail(null);
        } else {
            criteria.andLike("email", "%"+userVo.getEmail()+"%");
        }
        if (StringUtils.isEmpty(userVo.getIdCard())) {
            userVo.setIdCard(null);
        } else {
            criteria.andLike("idCard", "%"+userVo.getIdCard()+"%");
        }
        if (StringUtils.isEmpty(userVo.getSex())) {
            userVo.setSex(null);
        } else {
            criteria.andEqualTo("sex", userVo.getSex());
        }
        if (StringUtils.isEmpty(userVo.getStatus())) {
            userVo.setStatus(null);
        } else {
            criteria.andEqualTo("status", userVo.getStatus());
        }
        if (StringUtils.isEmpty(userVo.getPhone())) {
            userVo.setPhone(null);
        } else {
            criteria.andLike("phone", "%"+userVo.getPhone()+"%");
        }
        criteria.andEqualTo("belongTo",userVo.getBelongTo());
        List<UserEntity> userEntities = userMapper.selectByExample(example);
        List<UserDto> userDtos = userEntities.stream().map(e -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(e, userDto);
            return userDto;
        }).collect(Collectors.toList());
        PageInfo<UserDto> returnPage = new PageInfo<>(userDtos);
        Integer count = userMapper.selectCountByExample(example);
        Integer pages = count%rows==0 ? count/rows : (count/rows) + 1;
        returnPage.setTotal(count);
        returnPage.setPages(pages);
        return returnPage;
    }

    @Override
    public List<UserDto> getAllBybelongTo(Integer page, Integer rows, String belongeTo) {
        UserEntity userEntity = new UserEntity();
        userEntity.setBelongTo(belongeTo);
        List<UserEntity> userEntities = userMapper.select(userEntity);
        List<UserDto> newUserDtos = userEntities.stream().map(e -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(e, userDto);
            return userDto;
        }).collect(Collectors.toList());
        return newUserDtos;
    }

    public UserEntity getByEmail(String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        return userMapper.selectOne(userEntity);
    }
    
    public UserEntity getByIdCard(String idCard) {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdCard(idCard);
        return userMapper.selectOne(userEntity);
    }
    
    public UserEntity getByPhone(String phone) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(phone);
        return userMapper.selectOne(userEntity);
    }
    
    public UserEntity getByNickName(String nickName) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(nickName);
        return userMapper.selectOne(userEntity);
    }
    
    @Transactional(rollbackFor=Exception.class)
    public UserEntity save(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto,userEntity);
        super.save(userEntity);
        List<UserOrgPosEntity> userOrgPosEntities = new ArrayList<UserOrgPosEntity>();
        List<String> pIds = new ArrayList<String>();
        pIds.add(userEntity.getId());
        userOrgPosService.deletByPid(pIds);
        if (!CollectionUtils.isEmpty(userDto.getUserOrgPosDtos())) {
            for (UserOrgPosDto userOrgPosDto : userDto.getUserOrgPosDtos()){
                UserOrgPosEntity userOrgPosEntity = new UserOrgPosEntity();
                //userOrgPosEntity.setId(null);
                userOrgPosEntity.setpId(userEntity.getId());
                userOrgPosEntity.setId(userDto.getUserOrgPositionId());
                userOrgPosEntity.setOrgId(userOrgPosDto.getOrgId());
                userOrgPosEntity.setPosId(userOrgPosDto.getPosId());
                userOrgPosEntity.setInsertUserId(userDto.getInsertUserId());
                userOrgPosEntity.setBelongTo(userDto.getBelongTo());
                userOrgPosEntity.setUpDateDate(userDto.getUpDateDate());
                userOrgPosEntity.setInsertDate(userDto.getInsertDate());
                userOrgPosEntity.setUpDateUserId(userDto.getUpDateUserId());
                userOrgPosEntities.add(userOrgPosEntity);
            }
            userOrgPosService.save(userOrgPosEntities);
        }
        return userEntity;
    }
}
