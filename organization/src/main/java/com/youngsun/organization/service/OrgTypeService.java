package com.youngsun.organization.service;


import com.youngsun.organization.dto.OrgTypeAndRuleDto;
import com.youngsun.organization.entity.OrgTypeEntity;

import java.util.List;

/**
 * Created by 国平 on 2016/10/21.
 */
public interface OrgTypeService extends BasicService<OrgTypeEntity> {
   
   List<OrgTypeAndRuleDto> getOrgTypeUsePid(String pid);

   void delete(List<String> ids) throws Exception;
   
   List<OrgTypeEntity> getOthers(String id);

}
