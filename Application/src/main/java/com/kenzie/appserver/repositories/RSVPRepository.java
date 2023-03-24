package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.RSVPCompositeId;
import com.kenzie.appserver.repositories.model.RSVPRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface RSVPRepository extends CrudRepository<RSVPRecord, RSVPCompositeId> {
}
