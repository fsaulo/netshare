package com.var;

import java.sql.Date;

public class PostVar {

	private String content;
	private String imagePath;
	private String mood;
	private String place;
	private int user_id;
	private int feed_id;
	private int post_id;
	private int count_posts;
	private int count_reacts;
	private int count_comments;
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCountComments() {
		return count_comments;
	}

	public void setCountComments(int count) {
		this.count_comments = count;
	}

	public int getCountPosts() {
		return this.count_posts;
	}

	public void setCountPosts(int count) {
		this.count_posts = count;
	}

	public int getCountReacts() {
		return this.count_reacts;
	}

	public void setCountReacts(int count) {
		this.count_reacts = count;
	}

	public int getPostId() {
		return post_id;
	}

	public void setPostId(int idPost) {
		this.post_id = idPost;
	}

	public void setContent(String phrase) {
		this.content = phrase;
	}

	public String getContent() {
		return content;
	}

	public void setImagePath(String img) {
		this.imagePath = img;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setMood(String md) {
		this.mood = md;
	}

	public String getMood() {
		return this.mood;
	}

	public void setPlace(String plc) {
		this.place = plc;
	}

	public String getPlace() {
		return this.place;
	}

	public int getUserId() {
		return user_id;
	}

	public void setUserId(int idUser) {
		this.user_id = idUser;
	}

	public int getFeedId() {
		return feed_id;
	}

	public void setFeedId(int idFeed) {
		this.feed_id = idFeed;
	}
}
