package org.app.edufun.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class TopPeformer {
    @Id
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String avatar;
    @Column(unique = false)
    private Integer question;
}