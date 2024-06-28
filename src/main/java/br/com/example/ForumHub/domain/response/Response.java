package br.com.example.ForumHub.domain.response;

import br.com.example.ForumHub.domain.topics.Topic;
import br.com.example.ForumHub.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "responses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Response {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String message;

	public Response(Topic topic, User user, String message) {

		this.topic = topic;
		this.user = user;
		this.message = message;
	}
	
	public void editMessage(String message) {
		this.message = message;
	}
}
