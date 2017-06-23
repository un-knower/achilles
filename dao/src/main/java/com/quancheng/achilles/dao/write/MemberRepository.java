package com.quancheng.achilles.dao.write;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.quancheng.achilles.dao.modelwrite.Member;

/**
 * Created by XZW on 2016/9/7 0007.
 */
public interface MemberRepository  extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> 
{

}
