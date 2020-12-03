package com.crunch.crunch_server.domain.community.repository;

import java.util.List;

import com.crunch.crunch_server.domain.community.entity.Chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer>{

	List<Chat> findAllByCommunityId(int communityId);

    
}
