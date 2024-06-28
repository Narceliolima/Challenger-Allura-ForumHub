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

import br.com.example.ForumHub.domain.response.Response;
import br.com.example.ForumHub.domain.response.ResponseDetailsData;
import br.com.example.ForumHub.domain.response.ResponseRegistryData;
import br.com.example.ForumHub.domain.response.ResponseService;
import br.com.example.ForumHub.domain.response.ResponseTopicIdData;
import br.com.example.ForumHub.domain.response.ResponseUpdatingData;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("respostas")
@SecurityRequirement(name = "bearer-key")
public class ResponseController {

	@Autowired
	private ResponseService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity<ResponseDetailsData> create(@RequestBody @Valid ResponseRegistryData data, UriComponentsBuilder uriBuilder) {
		
		Response response = service.answerTopic(data);
		URI uri = uriBuilder.path("/respostas/{id}").buildAndExpand(response.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new ResponseDetailsData(response));
	}
	
	@GetMapping
	public ResponseEntity<Page<ResponseDetailsData>> list(@PageableDefault(size = 10, sort = {"id"}, direction = Direction.ASC) Pageable pageable, @RequestBody ResponseTopicIdData data){
		
		Page<ResponseDetailsData> listOfResponsesInTopic = service.getListOfResponses(pageable, data);
		
		return ResponseEntity.ok(listOfResponsesInTopic);
	}
	
	@PutMapping("{id}")
	@Transactional
	public ResponseEntity<ResponseDetailsData> update(@RequestBody ResponseUpdatingData data, @PathVariable Long id) {
		
		Response response = service.editResponseMessage(data, id);
		
		return ResponseEntity.ok(new ResponseDetailsData(response));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		
		service.deleteMessage(id);
		
		return ResponseEntity.noContent().build();
	}
 }
