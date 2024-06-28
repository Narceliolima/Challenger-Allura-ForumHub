package br.com.example.ForumHub.domain.topics;

import java.time.LocalDateTime;

public record TopicResumeData(String title,
								String message,
								LocalDateTime creationDate,
								Status status,
								String authorName,
								Course course) {

	public TopicResumeData(Topic topic) {
		this(topic.getTitle(), topic.getMessage(), topic.getCreationDate(), topic.getStatus(), topic.getAuthor().getUsername(), topic.getCourse());
	}
}
