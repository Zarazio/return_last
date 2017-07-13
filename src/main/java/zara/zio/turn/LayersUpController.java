package zara.zio.turn;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.net.URL;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zara.zio.turn.persistence.LayersService;


@Controller
public class LayersUpController {
	
	@Resource(name = "gifPath")
	private String gifPath;
	
	@Inject 
	private LayersService service ;
	
	@RequestMapping(value="/layersUp", method = RequestMethod.GET)
	public String layersUp() {
		
		
		return "LayersPage/layers";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/gif_image_list", method = RequestMethod.POST)
	public List<String> gif_image_list(HttpSession session) throws Exception{
		
		String mem = (String) session.getAttribute("mem") ;
		
		List<String> list = service.gif_image_list(mem);

		return list;
	}

	
	@ResponseBody
	@RequestMapping(value="/gif_list", method = RequestMethod.POST)
	public void gif_list(String imagesA) throws Exception{

	
			//System.out.println("imagesA : "  + imagesA);
			//String fileNm = imagesA.substring(imagesA.lastIndexOf("/") + 1) ;
			//String imagePath = "C:/saveImage/" + fileNm ;
			//System.out.println("imagesA : "  + imagePath);
			
			BufferedImage image = null ;
			
			image = ImageIO.read(new URL(imagesA)) ;
			
		
			int width = image.getWidth() ;
			int height = image.getHeight() ;
			BufferedImageOp op = createImageOp();

		
			
			
			BufferedImage bufferedImage =  new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR) ;
				
			bufferedImage.createGraphics().drawImage(image, op, 0,  0) ;
			String fileNm = imagesA.substring(imagesA.lastIndexOf("/") + 1) ;
			if(!fileNm.matches(".*s_.*")){
				fileNm = "s_" + fileNm ;
			}
			System.out.println("filenNm :  " + fileNm);
				
			//�빐�떦 寃쎈줈�뿉 誘몄�瑜� ���옣�븿.
			ImageIO.write(bufferedImage, "jpg", new File(gifPath+"/"+ fileNm)) ;
			
			
		

	}

	private BufferedImageOp createImageOp() {
		// TODO Auto-generated method stub
		return null;
	}


	

}
