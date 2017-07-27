package zara.zio.turn.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class KmlParsingUtils {
	
	public static String kmlParse(String step_log_path, String fileName) {
		
		String path = step_log_path + "/" + fileName;
	
		try {
			
			FileInputStream in = new FileInputStream(path);
			InputStreamReader inputReader = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(inputReader);
			

			String lines = null;
			boolean flag = false;
			String inLinne = "";
			
			try {
				
				while((lines=br.readLine()) != null){ // 문자가 없을 때까지 1byte씩 읽음 
					
					int idx = lines.indexOf("<coordinates>");
					
					if (idx != -1 || flag) {
						 
				         int i = 0;
				         flag = true;
				         String temLine = lines;
				         temLine = temLine.replaceAll(",0","");
				         temLine = temLine.replaceAll(" ","");
				         temLine = temLine.replaceAll("<coordinates>", "").replaceAll("    ","");
				         temLine = temLine.replaceAll("</coordinates>", "");
				         if (temLine.contains("</LineString>")) {
				             break;
				         }
				         
				         inLinne += temLine + "●";
				         
				    }
					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return inLinne;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
//		System.out.println(inLinne); // KML 위도경도
	
	}
	
}
