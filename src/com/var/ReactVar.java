package com.var;

public class ReactVar {

	private int react_id;
	private int post_id;
	private int comment_id;
	private int user_id;
	private String hash;

	public int getUserId() {
		return this.user_id;
	}

	public void setUserId(int id) {
		this.user_id = id;
	}

	public void setReactId(int reactId) {
		this.react_id = reactId;
	}

	public int getReactId() {
		return this.react_id;
	}

	public void setPostId(int postId) {
		this.post_id = postId;
	}

	public int getPostId() {
		return this.post_id;
	}

	public void setCommentId(int commentId) {
		this.comment_id = commentId;
	}

	public int getCommentId() {
		return this.comment_id;
	}

	public void setHash(String hs) {
		this.hash = hs;
	}

	public String getHash() {
		return this.hash;
	}
}
