package com.youngsun.organization.service.impl;

import com.youngsun.organization.dao.OrganizationRuleRepository;
import com.youngsun.organization.entity.OrgRuleEntity;
import com.youngsun.organization.service.OrganizationRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by 国平 on 2016/10/21.
 */
@Service
public class OrganizationRuleServiceImpl extends BasicServiceImpl<OrgRuleEntity> implements OrganizationRuleService {
    
    @Autowired
    private OrganizationRuleRepository organizationRuleRepository;
    
    public List<OrgRuleEntity> getAllByOrgId(String orgId) {
        List<OrgRuleEntity> orgRuleEntities = organizationRuleRepository.getAllByOrgId(orgId);
        return orgRuleEntities;
    }
    
    public void delete(List<String> ids) throws Exception {
        organizationRuleRepository.delete(ids);
    }
    
    public void deleteByOrgId(String orgIds) {
        organizationRuleRepository.deleteByOrgId(orgIds);
    }
    
    public void deleteByOrgIds(List<String> orgIds) {
        organizationRuleRepository.deleteByOrgIds(orgIds);
    }
}
