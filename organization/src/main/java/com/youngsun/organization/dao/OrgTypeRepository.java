package com.youngsun.organization.dao;

import com.youngsun.organization.dto.OrgTypeAndRuleDto;
import com.youngsun.organization.entity.OrgTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrgTypeRepository extends JpaRepository<OrgTypeEntity, String>, JpaSpecificationExecutor<OrgTypeEntity> {
    
    @Modifying
    @Query("delete from OrgTypeEntity where id in(:ids)")
    void delete(@Param("ids") List<String> ids);
    
    @Query("select new com.youngsun.organization.dto.OrgTypeAndRuleDto(otr.id, otr.pid, otr.cid, ot.name, ot.sn, otr.num) from OrgTypeEntity ot, OrgTypeRuleEntity otr where ot.id = otr.cid and otr.pid = :pid ")
    List<OrgTypeAndRuleDto> getOrgTypeUsePid(@Param("pid") String pid);
    
    @Query("from OrgTypeEntity where id != :id")
    List<OrgTypeEntity> getOthers(@Param("id") String id);
        

}

