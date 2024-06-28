package br.com.example.ForumHub.domain.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResponseRegistryData(@NotNull Long topicId,
									@NotNull Long authorId,
									@NotBlank String message) {

}
