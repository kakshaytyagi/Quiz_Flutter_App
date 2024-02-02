package org.app.edufun.entity;


import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.app.edufun.entity.common.InstantSerializer;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class UserMaster {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(unique = false, nullable = false)
	private String password;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
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
	@Column(unique = false, nullable = false)
	private String created_by;
	@Column(nullable = false)
	private Integer status;

}
