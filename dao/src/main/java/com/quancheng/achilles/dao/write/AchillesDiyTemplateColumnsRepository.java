package com.quancheng.achilles.dao.write;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplateColumns;

public interface AchillesDiyTemplateColumnsRepository extends JpaRepository<AchillesDiyTemplateColumns, Long>, JpaSpecificationExecutor<AchillesDiyTemplateColumns> {
    
//    @Query("select t from AchillesDiyTemplateColumns t where t.templateId =:templateId")
//    public List<AchillesDiyTemplateColumns> queryByTableName(@Param("templateId")Long templateId);
    
}
