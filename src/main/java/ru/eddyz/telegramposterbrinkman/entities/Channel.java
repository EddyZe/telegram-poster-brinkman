package ru.eddyz.telegramposterbrinkman.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chanel")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "channel_id")
    private Long channelId;

    @Column(name = "title")
    private String title;

//    @ManyToOne
//    @JoinColumn(name = "chanel_posts", referencedColumnName = "id")
//    private Post post;

}
