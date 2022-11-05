package cardio.cardio.entity;

import lombok.*;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name = "message")
@Table(name = "message")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", unique = true, nullable = false)
    private Long messageId;

    @Column(length = 1000, name = "content", nullable = false)
    private String content;
            
    @Column(name="unread", nullable = false)
    private Long unread;

    @Column(name="type", nullable = false)
    private Boolean type;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name="sender_id", referencedColumnName="user_id")
    private User sender;

    @ManyToOne
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name="receiver_id", referencedColumnName="user_id")
    private User receiver;

    @ManyToOne
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name="team_id")
    private Team team;

    
}
