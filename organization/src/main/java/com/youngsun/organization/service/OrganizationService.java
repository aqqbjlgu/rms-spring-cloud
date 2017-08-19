package com.youngsun.organization.service;


import com.youngsun.organization.dto.OrganizationDto;
import com.youngsun.organization.entity.OrgEntity;

import java.util.List;

/**
 * Created by 国平 on 2016/10/21.
 */
public interface OrganizationService extends BasicService<OrgEntity> {
    
    List<OrgEntity> getAllByTypeId(List<String> typeIds);
    List<OrgEntity> getAllByParentId(String node);
    List<OrgEntity> getAllByName(String name);
    List<OrganizationDto> getAllByParentId();
    List<OrgEntity> getAllByRule(String orgId);
    List<OrgEntity> getOrgRuleByOrgId(String orgId);
    void delete(List<String> ids) throws Exception;
    OrgEntity save(OrgEntity orgEntity);
}
