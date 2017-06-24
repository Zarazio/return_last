package zara.zio.turn.domain;

import java.sql.Timestamp;

public class LogBoardVO {
	
	private int board_code; // ��ȣ 
	private String board_title; // ���� 
	private String board_content; // ����
	private Timestamp board_date; // ��¥ 
	private int share_type; // ��ü����, �׷����, ����� 
	private int board_type_code; // ���� ī�װ���  default : log / 1
	private int viewCount; // ��ȸ�� 
	private String user_id; // �������� 
	private int step_log_code; // ���ܷα�����

	private String user_profile; // �����������̹���;	
	
	// �ؽ��±� �κ�
	private String [] hash_tag_content; // �ؽ��±�����
	
	// ���Ͼ��ε� ���� 
	private String [] file_content; // ���ε� ��������
	
	
	
	
	
	public String getUser_profile() {
		return user_profile;
	}
	public void setUser_profile(String user_profile) {
		this.user_profile = user_profile;
	}
	public String[] getHash_tag_content() {
		return hash_tag_content;
	}
	public void setHash_tag_content(String[] hash_tag_content) {
		this.hash_tag_content = hash_tag_content;
	}
	public String[] getFile_content() {
		return file_content;
	}
	public void setFile_content(String[] file_content) {
		this.file_content = file_content;
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