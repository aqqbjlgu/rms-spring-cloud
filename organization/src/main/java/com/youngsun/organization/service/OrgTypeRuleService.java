package com.youngsun.organization.service;


import com.youngsun.organization.entity.OrgTypeRuleEntity;

import java.util.List;

/**
 * Created by 国平 on 2016/10/21.
 */
public interface OrgTypeRuleService extends BasicService<OrgTypeRuleEntity> {
   
   void addOrgTypRule(String pid, String cid, Integer num);
   void deleteOrgTypRule(String pid, String cid);
   void updateOrgTypRule(String pid, String cid, Integer num);
   List<OrgTypeRuleEntity> getAllbyPid(String pid);
   void delete(List<String> ids);

}
