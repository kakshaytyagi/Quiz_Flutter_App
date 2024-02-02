package org.app.edufun.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.app.edufun.entity.common.InstantSerializer;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class User {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(unique = false, nullable = false)
	private String password;
	@Column(unique = false)
	private String email;
	@Column(unique = false)
	private String contact;
	@Column
	private String avatar;
	@Column
	private String firstname;
	@Column
	private String lastname;
	@JsonSerialize(using = InstantSerializer.class)
	@Column
	private Instant dob;
	@Column(nullable = false)
	private int question;
	@JsonSerialize(using = InstantSerializer.class)
	@Column(nullable = false)
	private Instant created_at;
	@JsonSerialize(using = InstantSerializer.class)
	@Column
	private Instant updated_at;
	@Column(nullable = false)
	private String created_by;
	@Column(nullable = false)
	private Integer status;
}
