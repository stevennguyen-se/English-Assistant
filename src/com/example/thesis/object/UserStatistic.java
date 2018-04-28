package com.example.thesis.object;

public class UserStatistic {
	protected int total_num_of_view_word_detail; 
	protected int total_num_of_favorite_word;
	protected int total_num_of_adding_favorite_word;
	protected int total_num_of_removing_favorite_word;
	protected int total_num_of_hearing_voice_of_word;
	
	
	public int getTotal_num_of_view_word_detail() {
		return total_num_of_view_word_detail;
	}
	public void setTotal_num_of_view_word_detail(int total_num_of_view_word_detail) {
		this.total_num_of_view_word_detail = total_num_of_view_word_detail;
	}
	public int getTotal_num_of_favorite_word() {
		return total_num_of_favorite_word;
	}
	public void setTotal_num_of_favorite_word(int total_num_of_favorite_word) {
		this.total_num_of_favorite_word = total_num_of_favorite_word;
	}
	public int getTotal_num_of_adding_favorite_word() {
		return total_num_of_adding_favorite_word;
	}
	public void setTotal_num_of_adding_favorite_word(
			int total_num_of_adding_favorite_word) {
		this.total_num_of_adding_favorite_word = total_num_of_adding_favorite_word;
	}
	public int getTotal_num_of_removing_favorite_word() {
		return total_num_of_removing_favorite_word;
	}
	public void setTotal_num_of_removing_favorite_word(
			int total_num_of_removing_favorite_word) {
		this.total_num_of_removing_favorite_word = total_num_of_removing_favorite_word;
	}
	public int getTotal_num_of_hearing_voice_of_word() {
		return total_num_of_hearing_voice_of_word;
	}
	public void setTotal_num_of_hearing_voice_of_word(
			int total_num_of_hearing_voice_of_word) {
		this.total_num_of_hearing_voice_of_word = total_num_of_hearing_voice_of_word;
	}
	
	public void increase_total_num_of_view_word_detail() {
		++total_num_of_view_word_detail;
	}
	public void increase_total_num_of_favorite_word() {
		++total_num_of_favorite_word;
	}
	public void increase_total_num_of_adding_favorite_word() {
		++total_num_of_adding_favorite_word;
	}
	public void increase_total_num_of_removing_favorite_word() {
		++total_num_of_removing_favorite_word;
	}
	public void increase_total_num_of_hearing_voice_of_word() {
		++total_num_of_hearing_voice_of_word;
	}
	
	public void decrease_total_num_of_favorite_word() {
		--total_num_of_favorite_word;
	}
	
	public UserStatistic() {
		// TODO Auto-generated constructor stub
		this.total_num_of_view_word_detail = 0;
		this.total_num_of_favorite_word = 0;
		this.total_num_of_adding_favorite_word = 0;
		this.total_num_of_removing_favorite_word = 0;
		this.total_num_of_hearing_voice_of_word = 0;
	}
}
