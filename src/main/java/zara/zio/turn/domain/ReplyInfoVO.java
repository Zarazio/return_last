package zara.zio.turn.domain;

import java.sql.Timestamp;

public class ReplyInfoVO {
	
	private int board_code; // ��ȣ 
	private String board_content; // �������
	private Timestamp board_date; // ��¥ 
	private int board_type_code; // ���� ī�װ�  6�� ���
	private String user_id; // �������� 
	private int place_reply_code; // �ش� ��ҰԽù� Ÿ��
	private int place_score; // ��� �������ھ�
	private String user_profile; // �����̹��� 
	
	public int getBoard_code() {
		return board_code;
	}
	public void setBoard_code(int board_code) {
		this.board_code = board_code;
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
	public int getBoard_type_code() {
		return board_type_code;
	}
	public void setBoard_type_code(int board_type_code) {
		this.board_type_code = board_type_code;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getPlace_reply_code() {
		return place_reply_code;
	}
	public void setPlace_reply_code(int place_reply_code) {
		this.place_reply_code = place_reply_code;
	}
	public int getPlace_score() {
		return place_score;
	}
	public void setPlace_score(int place_score) {
		this.place_score = place_score;
	}
	public String getUser_profile() {
		return user_profile;
	}
	public void setUser_profile(String user_profile) {
		this.user_profile = user_profile;
	}
	
	
	
}
