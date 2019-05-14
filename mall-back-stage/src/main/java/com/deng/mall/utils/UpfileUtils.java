package com.deng.mall.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class UpfileUtils {
	final static Logger logger= LoggerFactory.getLogger(UpfileUtils.class);

	public static List<String> loadFileList(MultipartFile[] productImgs, String picPath,boolean saveRedis) {
		List<String> imgPaths = new ArrayList<>();
		String redisKey=null;
		BufferedOutputStream bos=null;
		File file=new File(picPath);
		if(!file.exists()) {
			file.mkdirs();
			logger.info("创建文件目录,路径名:{}",picPath);
		}else {
			logger.info("图片文件所在路径:{}",picPath);
		}
		
		try {
		for (MultipartFile productImg : productImgs) {
			file = new File(picPath, productImg.getOriginalFilename());
			byte imgBytes[] = productImg.getBytes();
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(imgBytes);
			imgPaths.add(productImg.getOriginalFilename());
			if (saveRedis){
				if (StringUtils.isEmpty(redisKey)){
					redisKey=productImg.getOriginalFilename();
					if (JedisUtils.LRANGE(redisKey,0,-1).size()>0){
						JedisUtils.LTRIM(redisKey,0,-1);
					}
				}
				JedisUtils.LPUSH(redisKey,productImg.getOriginalFilename());
			}
		}
		bos.close();
		}catch(Exception e) {
			logger.error("occur a exception msg:{}",e.getMessage());
		}
		return imgPaths;
	}

}
