package ru.eddyz.telegramposterbrinkman.entities;


import jakarta.persistence.*;
import lombok.*;
import ru.eddyz.telegramposterbrinkman.util.enums.PostMode;
import ru.eddyz.telegramposterbrinkman.util.enums.PostType;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "post_name")
    private String postName;

    @Column(name = "post_text")
    private String postText;

    @Column(name = "status")
    private boolean status;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "count")
    private int count;

    @Column(name = "post_mode")
    @Enumerated(EnumType.STRING)
    private PostMode postMode;

    @Column(name = "post_type")
    @Enumerated(EnumType.STRING)
    private PostType postType;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
//    private List<Channel> channels;
}
