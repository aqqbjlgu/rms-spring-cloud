package com.youngsun.organization.dao;

import com.youngsun.organization.entity.OrgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrganizationRepository extends JpaRepository<OrgEntity, String> {
    
    @Query("from OrgEntity where typeId in (:typeIds)")
    List<OrgEntity> getAllByTypeIds(@Param("typeIds") List<String> typeIds);
    
    @Query("from OrgEntity where parentId = :parentId")
    List<OrgEntity> getAllByParentId(@Param("parentId") String parentId);
    
    @Query("from OrgEntity where parentId in (:parentIds)")
    List<OrgEntity> getAllByParentId(@Param("parentIds") List<String> parentIds);
    
    @Query("from OrgEntity where name like %:name%")
    List<OrgEntity> getAllByName(@Param("name") String name);
    
    @Query("from OrgEntity where id in (:ids)")
    List<OrgEntity> getAllByIds(@Param("ids") List<String> ids);
    
    @Modifying
    @Query("delete from OrgEntity where id in(:ids)")
    void delete(@Param("ids") List<String> ids);
    
    @Query("from OrgEntity o where o.id in (select ore.managerOrg from OrgRuleEntity ore where ore.orgId = :orgId)")
    List<OrgEntity> getOrgRuleByOrgId(@Param("orgId") String orgId);

}

