package com.var;

import java.time.LocalDate;
import java.time.Period;

public class UserVar {

	private int user_id;
	private int age;
	private int user_mode;
	private boolean status_session;
	private boolean status_email;
	private String firstName;
	private String lastName;
	private String fullName;
	private String gender;
	private String email;
	private String email_hash;
	private String password;
	private String phone_number;
	private LocalDate birthday;
	private String pin;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String name) {
		this.fullName = name;
	}

	public void setLastName(String lname) {
		this.lastName = lname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setUserId(int id) {
		this.user_id = id;
	}

	public int getUserId() {
		return user_id;
	}

	public void setStatusSession(boolean sts) {
		this.status_session = sts;
	}

	public boolean isStatusSession() {
		return status_session;
	}

	public void setHashEmail(String hash) {
		this.email_hash = hash;
	}

	public String getHashEmail() {
		return email_hash;
	}

	public boolean isStatusEmail() {
		return status_email;
	}

	public void setStatusEmail(boolean semail) {
		this.status_email = semail;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fname) {
		this.firstName = fname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserMode() {
		return user_mode;
	}

	public void setUserMode(int user_mode) {
		this.user_mode = user_mode;
	}

	public String getPhoneNumber() {
		return phone_number;
	}

	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return Period.between(birthday, LocalDate.now()).getYears();
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setAge() {
		this.age = Period.between(birthday, LocalDate.now()).getYears();
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getPin() {
		return pin;
	}
}
