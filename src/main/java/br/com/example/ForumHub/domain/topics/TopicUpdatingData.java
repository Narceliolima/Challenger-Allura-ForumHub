package br.com.example.ForumHub.domain.topics;

public record TopicUpdatingData(String title,
								String message,
								Long authorId,
								Course course) {

}
