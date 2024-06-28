package br.com.example.ForumHub.domain.topics;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.example.ForumHub.domain.user.User;
import br.com.example.ForumHub.domain.user.UserRepository;
import br.com.example.ForumHub.infra.exception.APIValidationException;
import jakarta.validation.Valid;

@Service
public class TopicService {
	
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private UserRepository userRepository;

	public Topic registerTopic(@Valid TopicRegistryData data) {

		User user = userRepository.getReferenceById(data.authorId());
		
		if(user==null) {
			throw new APIValidationException("O Usuário não existe");
		}
		
		Topic topic = new Topic(data, user);
		topicRepository.save(topic);
		
		return topic;
	}

	public Topic getTopic(Long id) {

		return topicRepository.getReferenceById(id);
	}

	public Page<TopicResumeData> getListOfTopics(Pageable pageable, TopicSearchData search) {

		if(search.course()!=null&&search.creationYear()!=null) {
			LocalDateTime initYear = LocalDateTime.parse(search.creationYear()+"-01-01T00:00:00");
			LocalDateTime endYear = LocalDateTime.parse(search.creationYear()+"-12-31T11:59:59");
			return topicRepository.findByCourseAndCreationDateBetween(pageable, search.course(), initYear, endYear).map(TopicResumeData::new);
		}
		else if(search.course()!=null) {
			return topicRepository.findByCourse(pageable, search.course()).map(TopicResumeData::new);
		}
		else if(search.creationYear()!=null) {
			LocalDateTime initYear = LocalDateTime.parse(search.creationYear()+"-01-01T00:00:00");
			LocalDateTime endYear = LocalDateTime.parse(search.creationYear()+"-12-31T11:59:59");
			return topicRepository.findByCreationDateBetween(pageable, initYear, endYear).map(TopicResumeData::new);
		}
		
		return topicRepository.findAll(pageable).map(TopicResumeData::new);
	}

	public Topic updateTopicInfo(TopicUpdatingData data, Long id) {
		
		Optional<Topic> optionalTopic = topicRepository.findById(id);
		User user = data.authorId()==null ? null : userRepository.getReferenceById(data.authorId());
		
		if(optionalTopic.isEmpty()) {
			throw new APIValidationException("Topico removido ou não existe");
		}
		if(data.authorId()!=null&&user==null) {
			throw new APIValidationException("Usuário não existe");
		}
		
		Topic topic = optionalTopic.get();
		topic.updateInfo(data, user);
		topicRepository.save(topic);
		
		return topic;
	}

	public void deleteTopic(Long id) {
		
		if(!topicRepository.existsById(id)) {
			throw new APIValidationException("Topico não existe");
		}
		
		topicRepository.deleteById(id);
	}
}
