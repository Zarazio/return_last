package zara.zio.turn.domain;

public class LikesVO {
	
	private int board_code; // ��ȣ
	private String user_id; // ���̵�
	private int cnt; // ����
	
	public int getBoard_code() {
		return board_code;
	}
	public void setBoard_code(int board_code) {
		this.board_code = board_code;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	public String toString() {
		return "board_code : " + board_code + 
			   " user_id : " + user_id +
			   " cnt : " + cnt;
	}
	
	
}
