package com.github.cbuschka.gcp_examples.cloudrun_with_cloudsql;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<MessageEntity, Long>
{
	@Query(nativeQuery = true, value = "select * from message limit 1")
	Optional<MessageEntity> findAny();
}
