package com.crunch.crunch_server.domain.crew.repository;

import java.util.List;

import com.crunch.crunch_server.domain.crew.entity.State;
import com.crunch.crunch_server.domain.crew.entity.WriterCrewIdentity;
import com.crunch.crunch_server.domain.crew.entity.WritersCrew;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterCrewRepository extends JpaRepository<WritersCrew, WriterCrewIdentity> {

	// WritersCrew findByWriterCrewIdentityUserId(int userId);

	WritersCrew findByWriterCrewIdentityUserIdAndWriterCrewIdentityProjectId(int userId, int id);

	List<WritersCrew> findByWriterCrewIdentityProjectId(int project_id);

	List<WritersCrew> findByWriterCrewIdentityUserId(int userId);

	List<WritersCrew> findByWriterCrewIdentityProjectIdAndState(int intValue, State selected);

	List<WritersCrew> findByState(State ing);

	// List<WritersCrew> findByStateAndProjectId(State ing, int intValue);

	// WritersCrew findByMainornotAndProjectId(int i, int intValue);

	List<WritersCrew> findByStateAndWriterCrewIdentityProjectId(State ing, int intValue);

	WritersCrew findByMainornotAndWriterCrewIdentityProjectId(int i, int intValue);

	List<WritersCrew> findByWriterCrewIdentityUserIdAndState(int userId, State selected);

	WritersCrew findByWriterCrewIdentityProjectIdAndWriterCrewIdentityUserId(int projectId, int userId);

	// List<WritersCrew> findByWriterCrewIdentityProjectIdAndState(int intValue,
	// String string);

}
