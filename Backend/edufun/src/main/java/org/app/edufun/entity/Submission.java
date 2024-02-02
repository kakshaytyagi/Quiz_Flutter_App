package org.app.edufun.entity;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.app.edufun.entity.common.InstantSerializer;
import org.app.edufun.entity.common.SecondsMillisSerializer;

import javax.persistence.*;
import java.sql.Time;
import java.time.Instant;

@Entity
@Table(name="submission")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class Submission {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submission_id;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private Long question_id;

    @Column(nullable = false)
    private String submissionTime;

    @JsonSerialize(using = InstantSerializer.class)
    @Column(nullable = false)
    private Instant dt;

    @Column(nullable = false)
    private Integer status;

}
