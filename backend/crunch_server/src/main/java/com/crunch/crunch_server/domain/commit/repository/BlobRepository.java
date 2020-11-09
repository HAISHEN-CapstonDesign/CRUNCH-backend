package com.crunch.crunch_server.domain.commit.repository;

import java.util.List;

import com.crunch.crunch_server.domain.commit.entity.Commit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlobRepository extends JpaRepository<Commit, Integer>{

    @Query("select * from post where project_id= :projectId and index_id= :indexId")
    List<Commit> findByProject_IdAndIndex_Id(@Param("projectId") Integer projectId, @Param("indexId") Integer indexId);


}
