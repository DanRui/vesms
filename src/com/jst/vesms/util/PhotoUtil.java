
package com.jst.vesms.util;

import java.io.FileOutputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;

public class PhotoUtil {
	
	public static boolean generateFile(String image, String jpgName) {
		 //对字节数组字符串进行Base64解码并生成图片  
        if (image == null) //图像数据为空  
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(image);  
            for(int i = 0 ; i < b.length ; ++ i)  
            {  
                if(b[i] < 0)  
                {//调整异常数据  
                    b[i] += 256;  
                }  
            }  
            //生成jpeg图片  
            String imgFilePath = "d://image//"+jpgName;//新生成的图片  
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        }   
        catch (Exception e)   
        {  
            return false;  
        }  
	}
}

