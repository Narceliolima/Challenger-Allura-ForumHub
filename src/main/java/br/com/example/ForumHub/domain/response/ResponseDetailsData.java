package br.com.example.ForumHub.domain.response;

public record ResponseDetailsData(Long id,
									Long topicId,
									Long authorId,
									String message) {

	public ResponseDetailsData(Response response) {
		this(response.getId(), response.getTopic().getId(), response.getUser().getId(), response.getMessage());
	}
}
