package com.youngsun.user.service.impl;

import com.youngsun.common.exception.BusinessException;
import com.youngsun.user.entity.PositionEntity;
import com.youngsun.user.entity.UserEntity;
import com.youngsun.user.mapper.PositionMapper;
import com.youngsun.user.service.PositionService;
import com.youngsun.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * Created by 国平 on 2016/10/21.
 */
@Service
public class PositionServiceImpl extends BasicServiceImpl<PositionEntity> implements PositionService {
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private UserService userService;
    @Transactional
    public void delete(List<String> ids) throws Exception{
        List<UserEntity> personEntities = userService.getAllByPositoinId(ids);
        if(CollectionUtils.isEmpty(personEntities)){
            Example example = new Example(PositionEntity.class);
            example.createCriteria().andIn("id", ids);
            positionMapper.deleteByExample(example);
        }else{
            throw new BusinessException(500,"请先删除该岗位下的人员");
        }
        
    }
}
