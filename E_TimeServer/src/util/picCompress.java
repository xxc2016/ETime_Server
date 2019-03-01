package util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class picCompress {
	
	public static  String  compressPicBySize(String srcPath,String desPath,long desFileSize,double accuracy)
	{
		if(srcPath ==null ||  desPath==null)
		{
			return null;
		}
		File srcFile = new File(srcPath);
		if(!srcFile.exists())
		{
			return null;
		}
		try {
			long originSize = srcFile.length();
			System.out.println("源文件："+srcPath+" 大小："+originSize/1024 +"kb");
			
			Thumbnails.of(srcPath).scale(1f).toFile(desPath);//转jpg图片
			compressPic(desPath,desFileSize,accuracy);
			
			File desFile = new File(desPath);
			System.out.println("目标:"+desPath+" 大小："+desFile.length()/1024+"kb");
			return desPath;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private static void compressPic(String desPath,long desFileSize,double accuracy) throws IOException
	{
		File srcFile = new File(desPath);
		long srcSize = srcFile.length();
		if(srcSize <=desFileSize*1024)
		{
			return;
		}
		
		BufferedImage bim = ImageIO.read(srcFile);
		int srcWdith  =bim.getWidth();
		int srcHeight = bim.getHeight();
		
		int desWidth = new BigDecimal(srcWdith).multiply(new BigDecimal(accuracy)).intValue();
		int desHeight = new BigDecimal(srcHeight).multiply(new BigDecimal(accuracy)).intValue();
		
		Thumbnails.of(desPath).size(desWidth, desHeight).outputQuality(accuracy).toFile(desPath);
		compressPic(desPath,desFileSize,accuracy);
		
	}
	

}
