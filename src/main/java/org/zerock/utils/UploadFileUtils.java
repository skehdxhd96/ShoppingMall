package org.zerock.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;
import net.coobird.thumbnailator.Thumbnails;

public class UploadFileUtils {
  
 static final int THUMB_WIDTH = 300;
 static final int THUMB_HEIGHT = 300;
 
 	 public static UUID getuuid( ) {
 		 
 		 UUID uid = UUID.randomUUID();
 		 
 		 return uid;
 	 }
 
	 public static String fileUpload(String uploadPath,
	         String fileName,
	         byte[] fileData, String ymdPath) throws Exception {
	
	  //uuid 랜덤 생성
	  UUID uid = getuuid();
	  
	  //uuid + file이름으로 새로운 파일 이름 생성
	  String newFileName = uid + "_" + fileName;
	  
	  //이미지 경로 설정(매개로 받은 uploadPath + 이미지 업로드하는 날짜)
	  String imgPath = uploadPath + ymdPath;
	
	  //File생성자 : imgPath안에 newFileName이라는 파일에 대한 '객체' 생성
	  File target = new File(imgPath, newFileName);
	  
	  //fileData(실제데이터)를 target(객체)에 복사한다. 지금 객체에는 imgPath경로의 target이라는 File객체에는 fileData이라는 데이터가 들어있다.
	  FileCopyUtils.copy(fileData, target);
	  
	  //썸네일을 구분하기 위해 앞에s_라는 명칭 부여
	  String thumbFileName = "s_" + newFileName;
	  
	  //"imgPath/newFileName"에 해당되는 객체 생성 
	  File image = new File(imgPath + File.separator + newFileName);
	
	  //"imgPath/s/thumbFileName"에 해당되는 객체 생성
	  File thumbnail = new File(imgPath + File.separator + "s" + File.separator + thumbFileName);
	
	  if (image.exists()) { //image객체에 파일 존재 여부 검토하고
	   thumbnail.getParentFile().mkdirs(); //imgPath/s에 폴더 만들기
	   Thumbnails.of(image).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(thumbnail);//image로부터 썸네일을 얻고, 사이즈 조정하고, thumbnail객체를 파일로 만들고 반환
	  }
	  return newFileName;
	 }

	 public static String calcPath(String uploadPath) { //년/월/일 폴더 만드는 메소드
	  Calendar cal = Calendar.getInstance();
	  String yearPath = File.separator + cal.get(Calendar.YEAR);//년
	  String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);//월
	  String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));//일
	
	  makeDir(uploadPath, yearPath, monthPath, datePath);//폴더만들고
	  makeDir(uploadPath, yearPath, monthPath, datePath + "\\s");//썸네일폴더도 만들고
	
	  return datePath;
	 }

	 private static void makeDir(String uploadPath, String... paths) { //타고들어가면서 폴더만들기
	
	  if (new File(paths[paths.length - 1]).exists()) { return; }
	
	  for (String path : paths) {
	   File dirPath = new File(uploadPath + path);
	
	   if (!dirPath.exists()) {
	    dirPath.mkdir();
	   }
	  }
	 }
}