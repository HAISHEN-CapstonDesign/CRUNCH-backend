package com.crunch.crunch_server.domain.commit.repository;

import java.util.List;

import com.crunch.crunch_server.domain.commit.entity.PostLineDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitDetailRepository extends JpaRepository<PostLineDetail,Integer> {

	@Query(value = "SELECT * FROM postLineDetail AS p1, (SELECT lineNum, max(id) AS id FROM postLineDetail WHERE postId = :postId GROUP BY lineNum limit :afterPostLength)"
	+"AS p2 WHERE p1.lineNum = p2.lineNum AND p1.id = p2.id"
	, nativeQuery = true)
	List<PostLineDetail> findAllByIdOrderedByLineNum(@Param("postId") int postId, @Param("afterPostLength") int afterPostLength);


    
}
