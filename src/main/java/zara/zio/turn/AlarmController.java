package zara.zio.turn;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.persistence.GroupTravelService;

@Controller
public class AlarmController {
	
	private static final Logger logger =
	         LoggerFactory.getLogger(AlarmController.class);
	
	@Inject
	private GroupTravelService service;
	
	@ResponseBody
	@RequestMapping(value="invite", method = RequestMethod.GET)
	public List<GroupApplicationVO> groupAlarm(Model model, String user_id) throws Exception {
		
		List<GroupApplicationVO> invite = service.group_travel_alarm();
		
		return invite;
	}
}
