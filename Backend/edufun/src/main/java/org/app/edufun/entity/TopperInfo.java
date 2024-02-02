package org.app.edufun.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class TopperInfo {
    @Id
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String avatar;
    @Column(unique = false)
    private String submissionTime;
}
