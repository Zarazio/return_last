package zara.zio.turn.domain;

public class GroupApplicationVO {

	private String user_id; // �������̵� 
	private int group_Code; // �׷��ڵ� 
	private int group_apply; // �׷���� 
	private String invite_user; // �ʴ��Ѿ��̵�
   
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getGroup_Code() {
		return group_Code;
	}
	public void setGroup_Code(int group_Code) {
		this.group_Code = group_Code;
	}
	public int getGroup_apply() {
		return group_apply;
	}
	public void setGroup_apply(int group_apply) {
		this.group_apply = group_apply;
	}
	public String getInvite_user() {
		return invite_user;
	}
	public void setInvite_user(String invite_user) {
		this.invite_user = invite_user;
	}
   
   
}