package com.slyadz.misy2.userssevice.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "users")
public class User {
		public interface Views {
		interface idName {}
	}
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@JsonView(Views.idName.class)
	private Long id;
	@JsonView(Views.idName.class)
	private String name;

	public User() {}
	public User(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) &&
				Objects.equals(name, user.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}

}
