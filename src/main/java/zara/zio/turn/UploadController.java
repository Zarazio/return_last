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
		// 서버의 파일을 다운로드하기 위한 스트림
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		logger.info("Display FILE NAME : " + fileName);
		
		try {
			// 확장자를 추출하여 formatName에 저장
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			// 추출한 확장자를 MediaUtils클래스에서  이미지파일여부를 검사하고 리턴받아 mType에 저장
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			// 헤더 구성 객체(외부에서 데이터를 주고받을 때에는 header와 body를 구성해야하기 때문에)
			HttpHeaders headers = new HttpHeaders();
			
			 // InputStream 생성
			in = new FileInputStream(gifPath+fileName);
			
			if(mType != null) { // 이미지 파일일때 
				headers.setContentType(mType);
			} else { // 이미지파일이 아닐때
				fileName = fileName.substring(fileName.indexOf("_")+1);
				
				// 다운로드용 컨텐트 타입지정 application/octet-stream 
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				
				// 바이트배열을 스트링으로 : 
				// new String(fileName.getBytes("utf-8"),"iso-8859-1") * iso-8859-1 서유럽언어, 큰 따옴표 내부에  " \" 내용 \" "
                // 파일의 한글 깨짐 방지
				headers.add("Content-Disposition", "attachment; filename=\"" + 
					new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\""); 
				//headers.add("Content-Disposition", "attachment; filename='"+fileName+"'");
			}
			
			// 바이트 배열, 헤더, HTTP 상태코드 
			// 대상파일에서 데이터를 읽어내는 IOUtils의 toByteArray()메소드 
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED); 
				
		} catch(Exception e) {
			e.printStackTrace();
			
			// HTTP상태 코드()
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
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		logger.info("originalName:" + file.getOriginalFilename());
		logger.info("size:" + file.getSize());
		logger.info("contentType:" + file.getContentType());
		String savedName = UploadFileUtils.uploadFile(gifPath, file.getOriginalFilename(), file.getBytes());

		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}

	@ResponseBody // ������ ��ü�� ��ȯ @ResponseBody = @RestController( ������ �� ������ ������
					// �νĵȴ�. )
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
			System.out.println("����� �� ��!!!");
			
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
				System.out.println("����Դ�?");
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
		System.out.println("������� ����?");
		return entity;
	}
	
	// gif ����
	@ResponseBody
	@RequestMapping(value = "layersupmakeGif", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> makeGif() throws Exception {
		
		// gif �����
		File Gfolder = new File(gifPath);
		if (Gfolder.exists() == false) {
			Gfolder.mkdirs();
		}
		
		FileOutputStream fout = new FileOutputStream(gifPath + "/success.gif");

		File[] files = FileUtils.listFilesMatching(new File(gifPath), "s_.*.png");
		// ModifiedDateŬ������ ���ǵ� ������ ���� �����Ѵ�.
		// ��¥������ ���� ����
		Arrays.sort(files, new ModifiedDate());
		BufferedImage[] images = new BufferedImage[files.length];
		int[] delays = new int[images.length];

		for (int i = 0; i < files.length; i++) {
			FileInputStream fin = new FileInputStream(files[i]);
			BufferedImage image = javax.imageio.ImageIO.read(fin);
			images[i] = image;
			delays[i] = 80;
			fin.close();
		}

		GIFTweaker.writeAnimatedGIF(images, delays, fout);
		fout.close();
		
		// gif �����ֱ�
		String savedName = "/success.gif";
		System.out.println("gif: " + savedName);
		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "layersupdeleteFile", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteFile(@RequestBody String fileName) throws Exception {
		logger.info("delete file name: " + fileName);
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		MediaType mType = MediaUtils.getMediaType(ext); // MediaType�� null�� �ƴ϶��
														// �̹��� �����̶�� �ǹ�
														// �̹��� �����̸� �������ϰ� ����� ����
														// �ΰ����� �����������
//		String folderPath = fileName.substring(9, 29); // /��/��/��/ ����

		if (mType != null) { // image file �̶�� �ǹ�
			// ���� �̹��� ����
//			folderPath = folderPath.replaceAll("%2F", "/");
			String orgName = fileName.substring(22);
//			orgName = orgName.replaceAll("thumbNail_", "");
			System.out.println(orgName);
			// String orgName = fileName.substring(12 + "thumbNail_".length());
			File orgImgFile = new File(gifPath + "/" + orgName);
			System.out.println(orgImgFile);
			orgImgFile.delete();
		}

		// ����� �̹��� ���� �Ǵ� �̹����� �ƴ� ���� ����
//		folderPath = folderPath.replaceAll("%2F", "/");
		String orgName = fileName.substring(12);
		System.out.println(orgName);
		File orgFile = new File(gifPath + "/" + orgName);
		System.out.println(orgFile);
		orgFile.delete();
		ResponseEntity<String> entity = new ResponseEntity<String>("deleted", HttpStatus.OK);
		System.out.println("��");
		return entity;
	}

	@ResponseBody
	@RequestMapping(value = "layersupAlldelete", method = RequestMethod.POST)
	public String Alldelete() throws Exception {
		File file = new File(gifPath); // ������ ������ �迭�� �����´�.
		File[] tempFile = file.listFiles();
		if (tempFile.length > 0) {
			for (int i = 0; i < tempFile.length; i++) {
				if (tempFile[i].isFile()) {
					tempFile[i].delete();
				} else { // ����Լ�
					Alldelete();
				}
				tempFile[i].delete();
			}
			file.delete();
		}
		return "fin";
	}

	// Arrays.sort()�޼ҵ忡 ����ϱ� ���� Comparator�������̽� ����, ���ϴ� ��ü�� File��ü

	class ModifiedDate implements Comparator<File> {

		public int compare(File f1, File f2) {

			// ������¥�� ���� ��ũ�� -1����, -1�� �����ϸ� ù��°���� ������ ����.
			if (f1.lastModified() > f2.lastModified())
				return 1;

			// ������ 0
			if (f1.lastModified() == f2.lastModified())
				return 0;

			// ������ 1
			return -1;
		}
	}
	
	// ������ �湮 �� �ش������� �����ϴ��� Ȯ��
	// ���������ʴ´ٸ� ��������
	// �����Ѵٸ� ���������� �����
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
