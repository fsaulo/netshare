package com.var;

public class FollowerVar extends UserVar {

	private int follower_id;
	private int user_id;
	private int count_follower;
	private int count_following;
	private String follower_name;

	public void setFollowerId(int id) {
		this.follower_id = id;
	}

	public int getFollowerId() {
		return this.follower_id;
	}

	public void setUserId(int uid) {
		this.user_id = uid;
	}

	public int getUserId() {
		return this.user_id;
	}

	public void setCountFollower(int count) {
		this.count_follower = count;
	}

	public int getCountFollower() {
		return this.count_follower;
	}

	public void setCountFollowing(int count) {
		this.count_following = count;
	}

	public int getCountFollowing() {
		return this.count_following;
	}

	public void setFollowerName(String name) {
		this.follower_name = name;
	}

	public String getFollowerName() {
		return this.follower_name;
	}
}
