package com.community.entity;

import java.sql.Timestamp;

public class ManagerEntity {
	private long id;
	private String username;
	private String password;
	private long community_id;
	private int is_super;
	private int state;
	private Timestamp time;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getCommunity_id() {
		return community_id;
	}
	public void setCommunity_id(long community_id) {
		this.community_id = community_id;
	}
	public int getIs_super() {
		return is_super;
	}
	public void setIs_super(int is_super) {
		this.is_super = is_super;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
	
}
