package com.poly.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {

	@ManyToMany (mappedBy = "roles", fetch = FetchType.EAGER)
	@LazyCollection (LazyCollectionOption.TRUE)
	private Set<User> users = new HashSet<>();

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;

	private String roleName;

	public Role () {
	}

	public Role (Long id, String roleName) {
		this.id = id;
		this.roleName = roleName;
	}

	public Set<User> getUsers () {
		return users;
	}

	public void setUsers (Set<User> users) {
		this.users = users;
	}

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public String getRoleName () {
		return roleName;
	}

	public void setRoleName (String roleName) {
		this.roleName = roleName;
	}
}
