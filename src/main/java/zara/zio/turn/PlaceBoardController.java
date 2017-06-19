package zara.zio.turn;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zara.zio.turn.domain.PlaceInfoListVO;
import zara.zio.turn.domain.PlaceVO;
import zara.zio.turn.persistence.PlaceService;

@Controller
public class PlaceBoardController {
	
	@Inject
	private PlaceService service;
	
	@RequestMapping(value="/placeInfo", method = RequestMethod.GET)
	public String placeInfo() {
		
		return "placeBorad/placeInfo";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/listPaging", method = RequestMethod.POST)
	public String listPaging(PlaceInfoListVO InfoCount) throws Exception {
		
		int totalCount = service.getInfoCount(InfoCount);
		System.out.println("totalCount[" + totalCount + "]");
		
		
		return totalCount+"";
	}
	
	@ResponseBody
	@RequestMapping(value="/placeInfoList", method = RequestMethod.POST)
	public List<PlaceVO> placeInfoList(PlaceInfoListVO InfoList) throws Exception {
		
		List<PlaceVO> list = service.placeInfoList(InfoList);
		
		return list;
	}
	
	
	
}
