package br.com.example.ForumHub.domain.topics;

import com.fasterxml.jackson.annotation.JsonFormat;

public record TopicSearchData(Course course,
							@JsonFormat(pattern = "yyyy") String creationYear) {

}
