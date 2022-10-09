package com.example.reddit.service;

import com.example.reddit.dto.SubredditDto;
import com.example.reddit.model.SubReddit;
import com.example.reddit.repository.SubRedditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubRedditServices {

    private final SubRedditRepository subRedditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        SubReddit save = subRedditRepository.save(mapSubredditDto(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
       return subRedditRepository.findAll().stream().map(this::mapToDto).collect(toList());
    }

    public SubReddit mapSubredditDto(SubredditDto subredditDto){
        return SubReddit.builder().nome(subredditDto.getName()).description(subredditDto.getDescription()).build();
    }

    private SubredditDto mapToDto(SubReddit subreddit) {
        return SubredditDto.builder()
               .name(subreddit.getNome())
                .id(subreddit.getId())
                .numberOfPosts(subreddit.getPosts().size())
               .build();
    }


}
