package zara.zio.turn;


import zara.zio.turn.domain.MessageVO;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;

 
public class SocketController {
	
    private ArrayList messageList = new ArrayList();    //메시지 목록
   
    //최신 메시지 번호 조회
    public int getLastMessageNo() {
        return messageList.size();
    }
   
    //메시지 보내기
    public synchronized void sendMessage(MessageVO message) {
        //org.springframework.beans.BeanUtils를 이용하여 복사
        MessageVO msg = new MessageVO();
        BeanUtils.copyProperties(message, msg);
 
        //메시지 목록 길이 + 1을 새로운 메시지 번호로 설정
        msg.setMessageNo(messageList.size()+1);
        messageList.add(msg);
    }
   
    //메시지 받기
    public List receiveMessage(MessageVO message) {
       
        //받은 메시지 목록
        List receivedMessageList = new ArrayList();
       
        //사용자의 최신 메시지 번호로부터 채팅방의 최신 메시지 번호까지의 메시지를
        //받은 메시지 목록에 저장
        if (messageList != null && !messageList.isEmpty()) {
            for (int i=message.getMessageNo(); i<messageList.size(); i++) {
                receivedMessageList.add(messageList.get(i));
            }
        }
       
        return receivedMessageList;
    }
}
