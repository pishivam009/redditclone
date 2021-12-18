package com.piyush.springboot.reditclone.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.piyush.springboot.reditclone.dto.SubredditDto;
import com.piyush.springboot.reditclone.exceptions.SpringRedditException;
import com.piyush.springboot.reditclone.model.Subreddit;
import com.piyush.springboot.reditclone.repository.SubredditRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
    	Subreddit subreddit = mapSubredditDto(subredditDto);
		Subreddit save = subredditRepository.save(subreddit);
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
		return Subreddit.builder().name(subredditDto.getName())
							.description(subredditDto.getDescription()).build();
		
	}
    
    @Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll()
		.stream().map(this::mapToDto)
		.collect(toList());
	}

    private SubredditDto mapToDto(Subreddit subreddit) {
		return SubredditDto.builder().name(subreddit.getName())
				.id(subreddit.getId())
				.numberOfPosts(subreddit.getPosts().size())
				.build();
	}
}
