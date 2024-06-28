package br.com.example.ForumHub.domain.topics;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicRegistryData(@NotBlank String title,
								@NotBlank String message,
								@NotNull Long authorId,
								@NotNull Course course) {

}
