package com.example.reddit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;



@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Proxy(lazy=false)
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;
    @NotBlank(message = "Post não pode ser vazio ou nulo")
    private String postName;
    @Nullable
    private String url;
    @Nullable
    @Lob
    private String description;
    private Integer voteCount = 0;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private SubReddit subReddit;

}
