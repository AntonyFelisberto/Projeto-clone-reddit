package com.example.reddit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Proxy(lazy=false)
public class SubReddit {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "o valor do nome é requerido")
    private String nome;
    @NotBlank(message = "o valor da mensagem é requerido")
    private String description;
    @OneToMany(fetch = LAZY)
    private List<Post> posts;
    private Instant createdDate;
    @ManyToOne(fetch = LAZY)
    private User user;

}
