package org.app.edufun.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.app.edufun.entity.common.InstantSerializer;
import org.app.edufun.entity.common.QuestionType;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="questions")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class Questions {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long question_id;
    @Column(nullable = false)
    private Long user_id;
    @Column(nullable = false, unique = true)
    private String question;
    @Column
    private String option1;
    @Column
    private String option2;
    @Column
    private String option3;
    @Column
    private String option4;
    @Column
    private Integer answer;
    @Column(nullable = false)
    private Integer isoption;
    @Column
    private String statement1;
    @Column
    private String statement2;
    @Column
    private String statement3;
    @Column
    private String image;
    @Column(nullable = false)
    private String created_by;
    @JsonSerialize(using = InstantSerializer.class)
    @Column(nullable = false)
    private Instant dt;
    @Column(unique = false, nullable = false)
    private Integer status;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private QuestionType category;
}
