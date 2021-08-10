package com.sjsu.travelflare.repository;

import com.sjsu.travelflare.models.entity.IncidentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentsRepository extends MongoRepository<IncidentEntity, String> {

    List<IncidentEntity> getIncidentEntitiesByCity(final String city);
}
