package com.example.trainogram.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private Set<PostLike> likes;

    public Integer getLikes() {
        return Math.toIntExact(likes.stream().count());
    }

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToOne()
    @JoinColumn(name = "post_author_id")
    private User postAuthor;

    private String postText;

    private LocalDateTime pubDate;


    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Column(name = "picture", nullable = false)
    @CollectionTable(name = "post_picture", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> picture;
}