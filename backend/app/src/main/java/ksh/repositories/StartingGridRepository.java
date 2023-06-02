package ksh.repositories;

import org.springframework.data.repository.CrudRepository;

import ksh.entities.StartingGridEntity;

/**
 * simple crud repository for storing starting grid entities and generating ids
 */
public interface StartingGridRepository extends CrudRepository<StartingGridEntity, String> {
}
