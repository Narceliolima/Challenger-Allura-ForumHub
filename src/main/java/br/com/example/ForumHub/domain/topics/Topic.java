package br.com.example.ForumHub.domain.topics;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.example.ForumHub.domain.response.Response;
import br.com.example.ForumHub.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "topics")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String title;
	private String message;
	
	@Column(name = "creation_date")
	private LocalDateTime creationDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User author;
	
	@Enumerated(value = EnumType.STRING)
	private Course course;
	
	@Enumerated(value = EnumType.STRING)
	private Status status;
	
	@OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Response> responseList = new ArrayList<Response>();

	public Topic(@Valid TopicRegistryData data, User user) {
		
		this.title = data.title();
		this.message = data.message();
		this.course = data.course();
		
		this.author = user;
		
		this.creationDate = LocalDateTime.now();
		this.status = Status.SEM_RESPOSTA;
	}
	
	public void updateInfo(TopicUpdatingData data, User author) {
		
		if(data.title()!=null) {
			this.title = data.title();
		}
		if(data.message()!=null) {
			this.message = data.message();
		}
		if(author!=null) {
			this.author = author;
		}
		if(data.course()!=null) {
			this.course = data.course();
		}
	}

	public Response answer(User user, String message) {

		this.status = Status.RESPONDIDO;
		Response response = new Response(this, user, message);
		responseList.add(response);
		return response;
	}

	public void deleteResponse(Response response) {
		
		this.responseList.remove(response);
	}
}
