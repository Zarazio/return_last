package zara.zio.turn.domain;

import java.sql.Timestamp;

public class ReplyInfoVO {
	
	private int board_code; // ��ȣ 
	private String board_content; // �������
	private Timestamp board_date; // ��¥ 
	private int board_type_code; // ���� ī�װ�  6�� ���
	private String user_id; // �������� 
	private String user_profile; // �����̹��� 
	
	private int place_reply_code; // �ش� ��ҰԽù� Ÿ�� ====== ������� =====
	private int place_score; // ��� �������ھ� ===== ������� =====
	
	private int reply_code; // �ش� Ŀ�°Խù� Ÿ�� ��ȣ ===== Ŀ�´�Ƽ ���� ===== 
	private int count; // ��õī��Ʈ ===== Ŀ�´�Ƽ ���� ===== 
	private String confirm_id; // ��õ����Ȯ�� ===== Ŀ�´�Ƽ ���� ===== 
	
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
	public String getUser_profile() {
		return user_profile;
	}
	public void setUser_profile(String user_profile) {
		this.user_profile = user_profile;
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
	public int getReply_code() {
		return reply_code;
	}
	public void setReply_code(int reply_code) {
		this.reply_code = reply_code;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getConfirm_id() {
		return confirm_id;
	}
	public void setConfirm_id(String confirm_id) {
		this.confirm_id = confirm_id;
	}
	

}
