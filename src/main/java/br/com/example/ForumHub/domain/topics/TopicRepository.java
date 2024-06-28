package br.com.example.ForumHub.domain.topics;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.example.ForumHub.domain.response.Response;

public interface TopicRepository extends JpaRepository<Topic, Long>{

	Page<Topic> findByCourseAndCreationDateBetween(Pageable pageable, Course course, LocalDateTime creationDateInit, LocalDateTime creationDateEnd);

	Page<Topic> findByCourse(Pageable pageable, Course course);

	Page<Topic> findByCreationDateBetween(Pageable pageable, LocalDateTime creationDateInit, LocalDateTime creationDateEnd);

	@Query("SELECT response FROM Topic topic JOIN topic.responseList response WHERE topic.id=:topicId")
	Page<Response> getResponseListByTopic(Pageable pageable, Long topicId);

	@Query("SELECT response FROM Topic topic JOIN topic.responseList response WHERE response.id=:responseId")
	Response getResponseById(Long responseId);
}
