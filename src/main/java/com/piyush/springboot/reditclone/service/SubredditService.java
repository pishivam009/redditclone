package com.piyush.springboot.reditclone.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.piyush.springboot.reditclone.dto.SubredditDto;
import com.piyush.springboot.reditclone.exceptions.SpringRedditException;
import com.piyush.springboot.reditclone.mapper.SubredditMapper;
import com.piyush.springboot.reditclone.model.Subreddit;
import com.piyush.springboot.reditclone.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.util.stream.Collectors.toList;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;

//	@Transactional
//	public SubredditDto save(SubredditDto subredditDto) {
//		Subreddit subreddit = mapSubredditDto(subredditDto);
//		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
//		subredditDto.setId(save.getId());
//		return subredditDto;
//	}

	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
		subredditDto.setId(save.getId());
		return subredditDto;
	}

//	private Subreddit mapSubredditDto(SubredditDto subredditDto) {
//		return Subreddit.builder().name(subredditDto.getName())
//							.description(subredditDto.getDescription()).build();
//		
//	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto).collect(toList());
	}

	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id).orElseThrow(()-> new SpringRedditException("No subreddit found with the given id "+id));
		return subreddit.mapSubredditToDto(subreddit);
	}

//	private SubredditDto mapToDto(Subreddit subreddit) {
//		return SubredditDto.builder().name(subreddit.getName())
//				.id(subreddit.getId())
//				.numberOfPosts(subreddit.getPosts().size())
//				.build();
//	}
}
