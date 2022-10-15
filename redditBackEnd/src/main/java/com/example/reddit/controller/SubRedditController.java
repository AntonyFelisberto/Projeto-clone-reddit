package com.example.reddit.controller;

import com.example.reddit.dto.SubredditDto;
import com.example.reddit.service.SubRedditServices;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubRedditController {

    private SubRedditServices subRedditServices;

    @PostConstruct
    public void createSubreddit(@RequestBody SubredditDto subredditDto){
        ResponseEntity.status(HttpStatus.CREATED).body(subRedditServices.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
        return ResponseEntity.status(HttpStatus.OK).body(subRedditServices.getAll());
    }

}
