package com.var;

public class InteractionVar {

	private int interaction_id;
	private int user_id;
	private int follower_id;

	public void setInteractionId(int intId) {
		this.interaction_id = intId;
	}

	public int getInteractionId() {
		return this.interaction_id;
	}

	public void setUserId(int uId) {
		this.user_id = uId;
	}

	public int getUserId() {
		return this.user_id;
	}

	public void setFollowerId(int fId) {
		this.follower_id = fId;
	}

	public int getFollowerId() {
		return this.follower_id;
	}
}
