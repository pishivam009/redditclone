package com.piyush.springboot.reditclone.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.piyush.springboot.reditclone.dto.SubredditDto;
import com.piyush.springboot.reditclone.exceptions.SpringRedditException;
import com.piyush.springboot.reditclone.mapper.SubredditMapper;
import com.piyush.springboot.reditclone.model.Subreddit;
import com.piyush.springboot.reditclone.repository.SubredditRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

	private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
//    	Subreddit subreddit = mapSubredditDto(subredditDto);
//		Subreddit save = subredditRepository.save(subreddit);
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
    	// return
    			// subredditRepository.findAll().stream().map(this::mapToDto).collect(toList());
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
