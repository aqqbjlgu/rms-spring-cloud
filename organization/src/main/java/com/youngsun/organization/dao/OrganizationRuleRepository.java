package com.youngsun.organization.dao;

import com.youngsun.organization.entity.OrgRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrganizationRuleRepository extends JpaRepository<OrgRuleEntity, String> {
    
    @Query("from OrgRuleEntity where orgId = :orgId ")
    List<OrgRuleEntity> getAllByOrgId(@Param("orgId") String orgId);
    
    @Modifying
    @Query("delete from OrgRuleEntity where id in(:ids)")
    void delete(@Param("ids") List<String> ids);
    
    @Modifying
    @Query("delete from OrgRuleEntity where orgId = :orgId")
    void deleteByOrgId(@Param("orgId") String orgId);
    
    @Modifying
    @Query("delete from OrgRuleEntity where orgId in (:orgIds)")
    void deleteByOrgIds(@Param("orgIds") List<String> orgIds);
    
}

