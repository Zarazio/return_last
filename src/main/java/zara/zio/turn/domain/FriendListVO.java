package zara.zio.turn.domain;

public class FriendListVO {
	
	private String user_id; // �������̵� 
	private String friend_id; // ģ�����̵� 
	private int friend_accept; // ģ��Ȯ��
   
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(String friend_id) {
		this.friend_id = friend_id;
	}
	public int getFriend_accept() {
		return friend_accept;
	}
	public void setFriend_accept(int friend_accept) {
		this.friend_accept = friend_accept;
	}
}