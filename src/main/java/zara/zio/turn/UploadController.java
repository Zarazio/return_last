package zara.zio.turn;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.icafe4j.image.gif.GIFTweaker;
import com.icafe4j.util.FileUtils;

import zara.zio.turn.util.MediaUtils;
import zara.zio.turn.util.UploadFileUtils;

@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Resource(name = "gifPath")
	private String gifPath;
	@Resource(name = "resultgifPath")
	private String resultgifPath;
	

	@RequestMapping(value = "layersupuploadForm", method = RequestMethod.GET)
	public void uploadForm() {
		// logger.debug("uploadForm Requested");
		// logger.info("uploadForm Requested");
		// logger.warn("uploadForm Requested");
		// logger.error("uploadForm Requested");
	}

	
	@RequestMapping(value = "layersupupload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> upload(MultipartFile file) throws Exception {
		logger.info("originalName:" + file.getOriginalFilename());
		logger.info("size:" + file.getSize());
		logger.info("contentType:" + file.getContentType());
		logger.info("server FileName:" + file.getName());

		// String savedName = uploadFile(file.getOriginalFilename(),
		// file.getBytes());
		String savedName = UploadFileUtils.uploadFile(gifPath, file.getOriginalFilename(), file.getBytes());
		
		System.out.println("what?: " + savedName);
		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping("/displayGifFile") 
	public ResponseEntity<byte[]> displayGifFile(String fileName) throws Exception {
		// �꽌踰꾩쓽 �뙆�씪�쓣 �떎�슫濡쒕뱶�븯湲� �쐞�븳 �뒪�듃由�
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		logger.info("Display FILE NAME : " + fileName);
		
		try {
			// �솗�옣�옄瑜� 異붿텧�븯�뿬 formatName�뿉 ���옣
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			// 異붿텧�븳 �솗�옣�옄瑜� MediaUtils�겢�옒�뒪�뿉�꽌  �씠誘몄��뙆�씪�뿬遺�瑜� 寃��궗�븯怨� 由ы꽩諛쏆븘 mType�뿉 ���옣
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			// �뿤�뜑 援ъ꽦 媛앹껜(�쇅遺��뿉�꽌 �뜲�씠�꽣瑜� 二쇨퀬諛쏆쓣 �븣�뿉�뒗 header�� body瑜� 援ъ꽦�빐�빞�븯湲� �븣臾몄뿉)
			HttpHeaders headers = new HttpHeaders();
			
			 // InputStream �깮�꽦
			in = new FileInputStream(gifPath+fileName);
			
			if(mType != null) { // �씠誘몄� �뙆�씪�씪�븣 
				headers.setContentType(mType);
			} else { // �씠誘몄��뙆�씪�씠 �븘�땺�븣
				fileName = fileName.substring(fileName.indexOf("_")+1);
				
				// �떎�슫濡쒕뱶�슜 而⑦뀗�듃 ���엯吏��젙 application/octet-stream 
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				
				// 諛붿씠�듃諛곗뿴�쓣 �뒪�듃留곸쑝濡� : 
				// new String(fileName.getBytes("utf-8"),"iso-8859-1") * iso-8859-1 �꽌�쑀�읇�뼵�뼱, �겙 �뵲�샂�몴 �궡遺��뿉  " \" �궡�슜 \" "
                // �뙆�씪�쓽 �븳湲� 源⑥쭚 諛⑹�
				headers.add("Content-Disposition", "attachment; filename=\"" + 
					new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\""); 
				//headers.add("Content-Disposition", "attachment; filename='"+fileName+"'");
			}
			
			// 諛붿씠�듃 諛곗뿴, �뿤�뜑, HTTP �긽�깭肄붾뱶 
			// ���긽�뙆�씪�뿉�꽌 �뜲�씠�꽣瑜� �씫�뼱�궡�뒗 IOUtils�쓽 toByteArray()硫붿냼�뱶 
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED); 
				
		} catch(Exception e) {
			e.printStackTrace();
			
			// HTTP�긽�깭 肄붾뱶()
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}
	@ResponseBody
	@RequestMapping("/resultgifFile") 
	public ResponseEntity<byte[]> resultgifFile(String fileName) throws Exception {
		// �꽌踰꾩쓽 �뙆�씪�쓣 �떎�슫濡쒕뱶�븯湲� �쐞�븳 �뒪�듃由�
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		logger.info("Display FILE NAME : " + fileName);
		
		try {
			// �솗�옣�옄瑜� 異붿텧�븯�뿬 formatName�뿉 ���옣
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			// 異붿텧�븳 �솗�옣�옄瑜� MediaUtils�겢�옒�뒪�뿉�꽌  �씠誘몄��뙆�씪�뿬遺�瑜� 寃��궗�븯怨� 由ы꽩諛쏆븘 mType�뿉 ���옣
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			// �뿤�뜑 援ъ꽦 媛앹껜(�쇅遺��뿉�꽌 �뜲�씠�꽣瑜� 二쇨퀬諛쏆쓣 �븣�뿉�뒗 header�� body瑜� 援ъ꽦�빐�빞�븯湲� �븣臾몄뿉)
			HttpHeaders headers = new HttpHeaders();
			
			 // InputStream �깮�꽦
			in = new FileInputStream(resultgifPath+"/"+fileName);
			
			if(mType != null) { // �씠誘몄� �뙆�씪�씪�븣 
				headers.setContentType(mType);
			} else { // �씠誘몄��뙆�씪�씠 �븘�땺�븣
				fileName = fileName.substring(fileName.indexOf("_")+1);
				
				// �떎�슫濡쒕뱶�슜 而⑦뀗�듃 ���엯吏��젙 application/octet-stream 
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				
				// 諛붿씠�듃諛곗뿴�쓣 �뒪�듃留곸쑝濡� : 
				// new String(fileName.getBytes("utf-8"),"iso-8859-1") * iso-8859-1 �꽌�쑀�읇�뼵�뼱, �겙 �뵲�샂�몴 �궡遺��뿉  " \" �궡�슜 \" "
                // �뙆�씪�쓽 �븳湲� 源⑥쭚 諛⑹�
				headers.add("Content-Disposition", "attachment; filename=\"" + 
					new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\""); 
				//headers.add("Content-Disposition", "attachment; filename='"+fileName+"'");
			}
			
			// 諛붿씠�듃 諛곗뿴, �뿤�뜑, HTTP �긽�깭肄붾뱶 
			// ���긽�뙆�씪�뿉�꽌 �뜲�씠�꽣瑜� �씫�뼱�궡�뒗 IOUtils�쓽 toByteArray()硫붿냼�뱶 
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED); 
				
		} catch(Exception e) {
			e.printStackTrace();
			
			// HTTP�긽�깭 肄붾뱶()
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}
	
	@RequestMapping(value = "layersupuploadAjax", method = RequestMethod.GET)
	public void uploadAjax() {

	}

	@RequestMapping(value = "layersupuploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file, MultipartFile aa) throws Exception {
		System.out.println(aa);
		
		logger.info("originalName:" + file.getOriginalFilename());
		logger.info("size:" + file.getSize());
		logger.info("contentType:" + file.getContentType());
		String savedName = UploadFileUtils.uploadFile(gifPath, file.getOriginalFilename(), file.getBytes());

		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}

	@ResponseBody // 占쏙옙占쏙옙占쏙옙 占쏙옙체占쏙옙 占쏙옙환 @ResponseBody = @RestController( 占쏙옙占쏙옙占쏙옙 占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙
					// 占싸식된댐옙. )
	@RequestMapping("layersupdisplayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {

		ResponseEntity<byte[]> entity = null;

		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

		MediaType mediaType = MediaUtils.getMediaType(ext);
		InputStream in = null;
		InputStream on = null;

		logger.info("File Name : " + fileName);
		System.out.println(ext);

		HttpHeaders headers = new HttpHeaders();
		
		if(mediaType == MediaType.IMAGE_GIF) {	
			String filePath = gifPath.substring(0, 6);
			filePath = filePath + "/gif";
			System.out.println(filePath);
			System.out.println("占쏙옙占쏙옙占� 占쏙옙 占쏙옙!!!");
			
			try {
				on = new FileInputStream(filePath + fileName);
				if (mediaType != null) {
					headers.setContentType(mediaType);
				} else {
					fileName = fileName.substring(fileName.indexOf("_") + 1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					String fN = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
					headers.add("Content-Dispostion", "attachment; filename = \"" + fN + "\"");
				}
				
				byte[] data = IOUtils.toByteArray(on);
				entity = new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			} finally {
				on.close();
			}
		} else{
			try {
				System.out.println("占쏙옙占쏙옙都占�?");
				in = new FileInputStream(gifPath + fileName);
				if (mediaType != null) {
					headers.setContentType(mediaType);
				} else {
					fileName = fileName.substring(fileName.indexOf("_") + 1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					String fN = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
					headers.add("Content-Dispostion", "attachment; filename = \"" + fN + "\"");
				}
				byte[] data = IOUtils.toByteArray(in);
				entity = new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			} finally {
				in.close();
			}
		}
		System.out.println("占쏙옙占쏙옙占쏙옙占� 占쏙옙占쏙옙?");
		return entity;
	}
	
	// gif 占쏙옙占쏙옙
	@ResponseBody
	@RequestMapping(value = "layersupmakeGif", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> makeGif() throws Exception {
		
		// gif 占쏙옙占쏙옙占�
		File Gfolder = new File(resultgifPath);
		if (Gfolder.exists() == false) {
			Gfolder.mkdirs();
		}
		String savedName =System.currentTimeMillis() +"Make.gif";
		FileOutputStream fout = new FileOutputStream(resultgifPath+"/" + savedName);

		File[] files = FileUtils.listFilesMatching(new File(gifPath), "s_.*.jpg");
		// ModifiedDate클占쏙옙占쏙옙占쏙옙 占쏙옙占실듸옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占싼댐옙.
		// 占쏙옙짜占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙
		Arrays.sort(files, new ModifiedDate());
		BufferedImage[] images = new BufferedImage[files.length];
		int[] delays = new int[images.length];

		for (int i = 0; i < files.length; i++) {
			FileInputStream fin = new FileInputStream(files[i]);
			BufferedImage image = javax.imageio.ImageIO.read(fin);
			images[i] = image;
			delays[i] = 200;
			fin.close();
		}

		GIFTweaker.writeAnimatedGIF(images, delays, fout);
		fout.close();
		
		// gif 占쏙옙占쏙옙占쌍깍옙
		System.out.println("gif: " + savedName);
		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "layersupdeleteFile", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteFile(@RequestBody String fileName) throws Exception {
		logger.info("delete file name: " + fileName);
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		MediaType mType = MediaUtils.getMediaType(ext); // MediaType占쏙옙 null占쏙옙 占싣니띰옙占�
														// 占싱뱄옙占쏙옙 占쏙옙占쏙옙占싱띰옙占� 占실뱄옙
														// 占싱뱄옙占쏙옙 占쏙옙占쏙옙占싱몌옙 占쏙옙占쏙옙占쏙옙占싹곤옙 占쏙옙占쏙옙占� 占쏙옙占쏙옙
														// 占싸곤옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙占�
//		String folderPath = fileName.substring(9, 29); // /占쏙옙/占쏙옙/占쏙옙/ 占쏙옙占쏙옙

		if (mType != null) { // image file 占싱띰옙占� 占실뱄옙
			// 占쏙옙占쏙옙 占싱뱄옙占쏙옙 占쏙옙占쏙옙
//			folderPath = folderPath.replaceAll("%2F", "/");
			String orgName = fileName.substring(22);
//			orgName = orgName.replaceAll("thumbNail_", "");
			System.out.println(orgName);
			// String orgName = fileName.substring(12 + "thumbNail_".length());
			File orgImgFile = new File(gifPath + "/" + orgName);
			System.out.println(orgImgFile);
			orgImgFile.delete();
		}

		// 占쏙옙占쏙옙占� 占싱뱄옙占쏙옙 占쏙옙占쏙옙 占실댐옙 占싱뱄옙占쏙옙占쏙옙 占싣댐옙 占쏙옙占쏙옙 占쏙옙占쏙옙
//		folderPath = folderPath.replaceAll("%2F", "/");
		String orgName = fileName.substring(12);
		System.out.println(orgName);
		File orgFile = new File(gifPath + "/" + orgName);
		System.out.println(orgFile);
		orgFile.delete();
		ResponseEntity<String> entity = new ResponseEntity<String>("deleted", HttpStatus.OK);
		System.out.println("占쏙옙");
		return entity;
	}

	@ResponseBody
	@RequestMapping(value = "layersupAlldelete", method = RequestMethod.POST)
	public String Alldelete() throws Exception {
		File file = new File(gifPath); // 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占썼열占쏙옙 占쏙옙占쏙옙占승댐옙.
		File[] tempFile = file.listFiles();
		if (tempFile.length > 0) {
			for (int i = 0; i < tempFile.length; i++) {
				if (tempFile[i].isFile()) {
					tempFile[i].delete();
				} else { // 占쏙옙占쏙옙獨占�
					Alldelete();
				}
				tempFile[i].delete();
			}
			file.delete();
		}
		return "fin";
	}

	// Arrays.sort()占쌨소드에 占쏙옙占쏙옙歐占� 占쏙옙占쏙옙 Comparator占쏙옙占쏙옙占쏙옙占싱쏙옙 占쏙옙占쏙옙, 占쏙옙占싹댐옙 占쏙옙체占쏙옙 File占쏙옙체

	class ModifiedDate implements Comparator<File> {

		public int compare(File f1, File f2) {

			// 占쏙옙占쏙옙占쏙옙짜占쏙옙 占쏙옙占쏙옙 占쏙옙크占쏙옙 -1占쏙옙占쏙옙, -1占쏙옙 占쏙옙占쏙옙占싹몌옙 첫占쏙옙째占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙.
			if (f1.lastModified() > f2.lastModified())
				return 1;

			// 占쏙옙占쏙옙占쏙옙 0
			if (f1.lastModified() == f2.lastModified())
				return 0;

			// 占쏙옙占쏙옙占쏙옙 1
			return -1;
		}
	}
	
	// 占쏙옙占쏙옙占쏙옙 占썸문 占쏙옙 占쌔댐옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占싹댐옙占쏙옙 확占쏙옙
	// 占쏙옙占쏙옙占쏙옙占쏙옙占십는다몌옙 占쏙옙占쏙옙占쏙옙占쏙옙
	// 占쏙옙占쏙옙占싼다몌옙 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占�
	@ResponseBody
	@RequestMapping(value = "layersupfCheck", method = RequestMethod.POST)
	public void Check() throws Exception{
		File Path = new File(gifPath);
		if(Path.exists() == false){
			Path.mkdir();
		}else {
			String localPath = gifPath.substring(5);
			System.out.println(localPath);
			Alldelete();
			Path.mkdir();
		}
	}
}
