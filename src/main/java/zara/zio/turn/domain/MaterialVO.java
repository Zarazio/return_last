package zara.zio.turn.domain;

public class MaterialVO {
	
   private int material_code; // �غ��ڵ� 
   private String material_type_code; // �غ�Ÿ�� 
   private String material_name; // �غ��̸�
   private int manager_check; // ������Ȯ�� 
   private String user_id; // �������̵� 
   private int group_Code; // �׷��ڵ� 
   private int material_check; // �غ�üũ
   
   public int getMaterial_code() {
      return material_code;
   }
   public void setMaterial_code(int material_code) {
      this.material_code = material_code;
   }
   public String getMaterial_type_code() {
      return material_type_code;
   }
   public void setMaterial_type_code(String material_type_code) {
      this.material_type_code = material_type_code;
   }
   public String getMaterial_name() {
      return material_name;
   }
   public void setMaterial_name(String material_name) {
      this.material_name = material_name;
   }
   public int getManager_check() {
      return manager_check;
   }
   public void setManager_check(int manager_check) {
      this.manager_check = manager_check;
   }
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
   public int getMaterial_check() {
      return material_check;
   }
   public void setMaterial_check(int material_check) {
      this.material_check = material_check;
   }
   
   

}