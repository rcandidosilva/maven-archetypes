package com.project.jpa.sample;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.project.util.jpa.BaseEntity;
import com.project.util.security.model.Group;
import com.project.util.security.model.Permission;
import com.project.util.security.model.Role;
import com.project.util.security.model.User;

@Entity
public class SampleEntity extends BaseEntity<Long> implements User {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String email;
	private String login;
	private String password;
	private String role;
	private String permission;
	
	public SampleEntity() {
		super();
	}
	
	public SampleEntity(String name, String email, String login, String password, String role, String permission) {
		this.name = name;
		this.email = email;
		this.login = login;
		this.password = password;
		this.role = role;
		this.permission = permission;
	}

	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}


	@Override
	public String getUserId() {
		return login;
	}

	@Override
	public String getUserName() {
		return login;
	}

	@Override
	public String getUserPassword() {
		return password;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public List<? extends Role> getRoles() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role() {
			@Override
			public String getRoleName() {
				return role;
			}
		});
		return roles;
	}

	@Override
	public List<? extends Group> getGroups() {
		return null;
	}

	@Override
	public List<? extends Permission> getPermissions() {
		List<Permission> permissions = new ArrayList<Permission>();
		permissions.add(new Permission() {
			@Override
			public String getTarget() {
				return "sample";
			}
			@Override
			public String getPermission() {
				return permission;
			}
		});
		return permissions;
	}
	
}