package com.youngsun.user.service;


import com.youngsun.user.entity.PositionEntity;

import java.util.List;

/**
 * Created by 国平 on 2016/10/21.
 */
public interface PositionService extends BasicService<PositionEntity> {
    
    void delete(List<String> ids) throws Exception;
}
