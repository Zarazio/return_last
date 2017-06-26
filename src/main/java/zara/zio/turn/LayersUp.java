package zara.zio.turn;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LayersUp {
	
	@RequestMapping(value="/layersUp", method = RequestMethod.GET)
	public String layersUp() {
		
		return "LayersPage/layers";
	}

}
