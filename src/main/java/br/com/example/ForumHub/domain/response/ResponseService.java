package br.com.example.ForumHub.domain.response;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.example.ForumHub.domain.topics.Topic;
import br.com.example.ForumHub.domain.topics.TopicRepository;
import br.com.example.ForumHub.domain.user.User;
import br.com.example.ForumHub.domain.user.UserRepository;
import br.com.example.ForumHub.infra.exception.APIValidationException;
import jakarta.validation.Valid;

@Service
public class ResponseService {

	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private UserRepository userRepository;
	
	public Response answerTopic(@Valid ResponseRegistryData data) {
		
		Optional<Topic> optionalTopic = topicRepository.findById(data.topicId());
		Optional<User> optionalUser = userRepository.findById(data.authorId());
		
		if(optionalTopic.isEmpty()) {
			throw new APIValidationException("Deve ser passado um id valido para topico");
		}
		if(optionalUser.isEmpty()) {
			throw new APIValidationException("Author inexistente");
		}
		
		Topic topic = optionalTopic.get();
		User user = optionalUser.get();
		Response response = topic.answer(user, data.message());
		topicRepository.save(topic);
		
		return response;
	}

	public Page<ResponseDetailsData> getListOfResponses(Pageable pageable, ResponseTopicIdData data) {

		return topicRepository.getResponseListByTopic(pageable, data.topicId()).map(ResponseDetailsData::new);
	}


	public Response editResponseMessage(ResponseUpdatingData data, Long id) {

		Response response = topicRepository.getResponseById(id);
		
		if(response==null) {
			throw new APIValidationException("Resposta removido ou não existe");
		}
		
		Topic topic = response.getTopic();
		response.editMessage(data.message());
		topicRepository.save(topic);
		
		return response;
	}

	public void deleteMessage(Long id) {
		
		Response response = topicRepository.getResponseById(id);
		
		if(response==null) {
			throw new APIValidationException("Resposta removido ou não existe");
		}
		
		Topic topic = response.getTopic();
		topic.deleteResponse(response);
		topicRepository.save(topic);
	}
}
