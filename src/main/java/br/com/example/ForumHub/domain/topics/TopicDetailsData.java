package br.com.example.ForumHub.domain.topics;

import java.time.LocalDateTime;
import java.util.List;

import br.com.example.ForumHub.domain.response.ResponseDetailsData;

public record TopicDetailsData(String title,
								String message,
								LocalDateTime creationDate,
								Status status,
								String authorName,
								Course course,
								List<ResponseDetailsData> responseList) {

	public TopicDetailsData(Topic topic) {
		this(topic.getTitle(), topic.getMessage(), topic.getCreationDate(), topic.getStatus(), topic.getAuthor().getUsername(), topic.getCourse(), topic.getResponseList().stream().map(ResponseDetailsData::new).toList());
	}
}
