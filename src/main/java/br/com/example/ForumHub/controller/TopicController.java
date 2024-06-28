package br.com.example.ForumHub.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.example.ForumHub.domain.topics.Topic;
import br.com.example.ForumHub.domain.topics.TopicDetailsData;
import br.com.example.ForumHub.domain.topics.TopicResumeData;
import br.com.example.ForumHub.domain.topics.TopicRegistryData;
import br.com.example.ForumHub.domain.topics.TopicSearchData;
import br.com.example.ForumHub.domain.topics.TopicService;
import br.com.example.ForumHub.domain.topics.TopicUpdatingData;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

	@Autowired
	private TopicService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicResumeData> register(@RequestBody @Valid TopicRegistryData data, UriComponentsBuilder uriBuilder) {
		
		Topic topic = service.registerTopic(data);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topic.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicResumeData(topic));
	}
	
	@GetMapping
	public ResponseEntity<Page<TopicResumeData>> list(@PageableDefault(size = 10, sort = {"creationDate"}, direction = Direction.ASC) Pageable pageable, @RequestBody TopicSearchData search){
		
		Page<TopicResumeData> pageOfTopicList = service.getListOfTopics(pageable, search);
		
		return ResponseEntity.ok(pageOfTopicList);
	}
	
	@PutMapping("{id}")
	@Transactional
	public ResponseEntity<TopicResumeData> update(@RequestBody TopicUpdatingData data, @PathVariable Long id) {
		
		Topic topic = service.updateTopicInfo(data, id);
		
		return ResponseEntity.ok(new TopicResumeData(topic));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		
		service.deleteTopic(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<TopicDetailsData> detail(@PathVariable Long id) {
		
		Topic topic = service.getTopic(id);
		
		return ResponseEntity.ok(new TopicDetailsData(topic));
	}
 }
