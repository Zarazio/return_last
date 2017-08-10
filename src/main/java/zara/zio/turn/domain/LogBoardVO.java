package zara.zio.turn.domain;

import java.sql.Timestamp;

public class LogBoardVO {
	
	private int board_code; // ��ȣ 
	private String board_title; // ���� 
	private String board_content; // ����
	private Timestamp board_date; // ��¥ 
	private int share_type; // ��ü����, �׷����, ����� 
	private int board_type_code; // ���� ī�װ�  default : log / 1
	private int viewCount; // ��ȸ�� 
	private String user_id; // �������� 
	private int step_log_code; // ���ܷα�����
	
	private Double log_longtitude; // �浵
	private Double log_latitude; // ���� 

	private String user_profile; // �����������̹���;	
	private int write_type; // �ۼ�Ÿ�� �� or ����� 
	private int reply_code; // �Խñ� ���Ÿ���ڵ�
	
	// �ؽ��±� �κ�
	private String [] hash_tag_content; // �ؽ��±�����
	// ���Ͼ��ε� ���� 
	private String [] file_content; // ���ε� ��������
	
	// �� �Խù� ���ƿ� ��
	private int like_count; 
	private int my_like;
	
	// ���°�ü
	private int states;
	// ��۾��̵� == ���Ǿ��̵� ���� 
	private int reply_state;
	// �������ڵ� ������
	private String onAddress;
	
	public String getOnAddress() {
		return onAddress;
	}
	public void setOnAddress(String onAddress) {
		this.onAddress = onAddress;
	}
	
	
	public void setStates(int states) {
		this.states = states;
	}
	public int getStates() {
		return states;
	}
	public int getReply_state() {
		return reply_state;
	}
	public void setReply_state(int reply_state) {
		this.reply_state = reply_state;
	}
	public String getUser_profile() {
		return user_profile;
	}
	public void setUser_profile(String user_profile) {
		this.user_profile = user_profile;
	}
	public int getWrite_type() {
		return write_type;
	}
	public void setWrite_type(int write_type) {
		this.write_type = write_type;
	}
	public String[] getHash_tag_content() {
		return hash_tag_content;
	}
	public void setHash_tag_content(String[] hash_tag_content) {
		this.hash_tag_content = hash_tag_content;
	}
	public int getReply_code() {
		return reply_code;
	}
	public void setReply_code(int reply_code) {
		this.reply_code = reply_code;
	}
	public String[] getFile_content() {
		return file_content;
	}
	public void setFile_content(String[] file_content) {
		this.file_content = file_content;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getMy_like() {
		return my_like;
	}
	public void setMy_like(int my_like) {
		this.my_like = my_like;
	}
	
	
	public Double getLog_longtitude() {
		return log_longtitude;
	}
	public void setLog_longtitude(Double log_longtitude) {
		this.log_longtitude = log_longtitude;
	}
	public Double getLog_latitude() {
		return log_latitude;
	}
	public void setLog_latitude(Double log_latitude) {
		this.log_latitude = log_latitude;
	}
	
	
	public int getBoard_code() {
		return board_code;
	}
	public void setBoard_code(int board_code) {
		this.board_code = board_code;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public Timestamp getBoard_date() {
		return board_date;
	}
	public void setBoard_date(Timestamp board_date) {
		this.board_date = board_date;
	}
	public int getShare_type() {
		return share_type;
	}
	public void setShare_type(int share_type) {
		this.share_type = share_type;
	}
	public int getBoard_type_code() {
		return board_type_code;
	}
	public void setBoard_type_code(int board_type_code) {
		this.board_type_code = board_type_code;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getStep_log_code() {
		return step_log_code;
	}
	public void setStep_log_code(int step_log_code) {
		this.step_log_code = step_log_code;
	}
	
	public String toString() {
		return "board_code : " + board_code + "\n"
				+ "board_title : " + board_title + "\n"
				+ "board_content : " + board_content + "\n"
				+ "share_type : " + share_type + "\n"
				+ "board_type_code : " + board_type_code + "\n"
				+ "viewCount : " + viewCount + "\n"
				+ "user_id : " + user_id + "\n"
				+ "step_log_code : " + step_log_code;
	}
	
}
