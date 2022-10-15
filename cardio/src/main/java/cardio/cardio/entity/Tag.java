package cardio.cardio.entity;
import javax.persistence.*;

import lombok.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", unique = true, nullable = false)
    private Long tagId;

    @Column(length = 50, name = "tagname", unique = true, nullable = false)
    private String tagname;
}
