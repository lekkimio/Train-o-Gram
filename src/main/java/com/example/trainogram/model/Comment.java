package com.example.trainogram.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "comment_author_id")
    private User commentAuthor;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "comment", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CommentLike> likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime commentPub;
    private String text;

    public Integer getLikes() {
        return Math.toIntExact(likes.stream().count());
    }


}
