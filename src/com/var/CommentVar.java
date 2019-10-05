package com.var;

public class CommentVar {

	private String content;
	private int user_id;
	private int post_id;
	private int comment_id;
	private int count_reacts;

	public int getCountReacts() {
		return this.count_reacts;
	}

	public void setCountReacts(int count) {
		this.count_reacts = count;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String cnt) {
		this.content = cnt;
	}

	public int getUserId() {
		return this.user_id;
	}

	public void setUserId(int idUser) {
		this.user_id = idUser;
	}

	public int getPostId() {
		return this.post_id;
	}

	public void setPostId(int idPost) {
		this.post_id = idPost;
	}

	public int getCommentId() {
		return this.comment_id;
	}

	public void setCommentId(int idComment) {
		this.comment_id = idComment;
	}
}
