package com.youngsun.organization.service;


import com.youngsun.organization.entity.OrgRuleEntity;

import java.util.List;

/**
 * Created by 国平 on 2016/10/21.
 */
public interface OrganizationRuleService extends BasicService<OrgRuleEntity> {
    
    List<OrgRuleEntity> getAllByOrgId(String orgId);
    void delete(List<String> ids) throws Exception;
    void deleteByOrgId(String orgIds);
    void deleteByOrgIds(List<String> orgIds);
}
