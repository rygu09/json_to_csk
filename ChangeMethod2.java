package json_to_csk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ChangeMethod2 {
	
	public static void main(String[] args) {
//		String path="C:\\Users\\admin\\Desktop\\4201.json";
//		String from="D:\\study_resouce\\项目\\15755141202_207\\15755141202\\4241\\4241.json";
//		String to="D:\\study_resouce\\项目\\15755141202_207\\15755141202\\4241\\4241.csk";

		
//		String from="F:\\05 iflytek\\study_resouce\\项目\\15755141202_207\\15755141202\\4241\\4241.json";
//		String to="F:\\05 iflytek\\study_resouce\\项目\\15755141202_207\\15755141202\\4241\\4241.csk";
		
		String from="C:/Users/GRY/Desktop/4241.json";
		String to="C:/Users/GRY/Desktop/4241.csk";		
		
		String content=change(from);
		writeDataToFile(content,to);
	}
	
	/*
	 * json 转 csk
	 * @param path
	 */
	public static String change(String path) {
		
		List<String> sX_Array=new ArrayList<>();
		List<String> sY_Array=new ArrayList<>();

		List<String> eX_Array=new ArrayList<>();
		List<String> eY_Array=new ArrayList<>();
		
		List<Integer> pressure_Array=new ArrayList<>();
		List<Long> t_Array=new ArrayList<>();
		
		int length;
		int num=1;
		int index=0;

		
		Boolean boolean_X,boolean_Y;
		
		StringBuilder stringBuilder=new StringBuilder();		
		
		Gson gson = new GsonBuilder().create();
		
		String message=getStringFromJsonFile(path);
		
		List<PointBean2> pointBeanList2 = gson.fromJson(message, new TypeToken<List<PointBean2>>() {}.getType());
		
		/**
		 * 遍历List，将各个元素分开来存到不同数组中
		 */
		for(PointBean2 p:pointBeanList2) {
			if(!(p.geteX().equals("0")) && !(p.geteY().equals("0")) ) {//通过判断x，y不为0，剔除掉其余josn对象
				
				sX_Array.add(p.getsX());
				sY_Array.add(p.getsY());
				
				eX_Array.add(p.geteX());
				eY_Array.add(p.geteY());
				
				pressure_Array.add(p.getPressure());
				t_Array.add(p.getT());
			}
		}
		
		length=eX_Array.size();
		
//		      循环外
//		    <PenData>  
//				<symbol label="" type="UNCLASSIFIED">
		stringBuilder
		.append("<PenData>")
		.append("\r\n")
		.append("  <symbol label=\"\" type=\"UNCLASSIFIED\">")
		.append("\r\n");
		
		/*
		 * 循环读取每一点
		 */
		for(int i=0;i<length;i++) {
			boolean_X=sX_Array.get(i).equals(eX_Array.get(i).toString());
			boolean_Y=sY_Array.get(i).equals(eY_Array.get(i).toString());
			System.out.println(boolean_X+"  "+boolean_Y);		
			
			/*
			 * 奇数次出现sX=eX sY=eY
			 * 加<stroke label="30" index="30">
			 * 再加点的信息
			 */						
			if(boolean_X && boolean_Y && num%2==1) {
				
				num++;
				index=(num+1)/2;
				//<stroke label="30" index="30">
				stringBuilder.append("    <stroke label=\""+index+"\" index=\""+index+"\">"+"\r\n");							 				
				
				stringBuilder
				.append("      ")
				.append("<point ")
//				.append("sY="+sY_Array.get(i)+" ")
//				.append("sX="+sX_Array.get(i)+" ")
				.append("y=\""+eY_Array.get(i)+"\" ")
				.append("x=\""+eX_Array.get(i)+"\" ")
				.append("pressure=\""+pressure_Array.get(i)+"\" ")
				.append("timestamp=\""+t_Array.get(i)+"\" />")
				.append("\r\n");
				
			}
			/*
			 * 偶数次出现sX=eX sY=eY
			 * 先加点的信息
			 * 再加</stroke>
			 */
			else if(boolean_X && boolean_Y && num%2==0){							
				
				/*
				 * 拼接成一个point
				 * <point y="73.3500" x="99.4875" pressure="124" timestamp="1273248698972" />
				 */
				stringBuilder
				.append("      ")
				.append("<point ")
//				.append("sY="+sY_Array.get(i)+" ")
//				.append("sX="+sX_Array.get(i)+" ")
				.append("y=\""+eY_Array.get(i)+"\" ")
				.append("x=\""+eX_Array.get(i)+"\" ")
				.append("pressure=\""+pressure_Array.get(i)+"\" ")
				.append("timestamp=\""+t_Array.get(i)+"\" />")
				.append("\r\n");
				
				//<stroke label="30" index="30">
				stringBuilder.append("    </stroke>"+"\r\n");				
				num++;
			}else {
				/*
				 * 拼接point
				 * <point y="73.3500" x="99.4875" pressure="124" timestamp="1273248698972" />
				 */
				stringBuilder
				.append("      ")
				.append("<point ")
//				.append("sY="+sY_Array.get(i)+" ")
//				.append("sX="+sX_Array.get(i)+" ")
				.append("y=\""+eY_Array.get(i)+"\" ")
				.append("x=\""+eX_Array.get(i)+"\" ")
				.append("pressure=\""+pressure_Array.get(i)+"\" ")
				.append("timestamp=\""+t_Array.get(i)+"\" />")
				.append("\r\n");				
			}				
		}
		
//		      循环外
//		</symbol>  
		stringBuilder
		.append("  </symbol>");
//		.append("\r\n");
		
		
		System.out.println(stringBuilder.toString());        
		System.out.println("一共有"+length+"行数据");
		
		return stringBuilder.toString();
	}
	
	/**
	 * 获取json文件，将其中内容写出到一个字符串，并返回
	 * @param path
	 * @return
	 */
	public static String getStringFromJsonFile(String path) {
		File file = new File(path);
		BufferedReader reader = null; 
        StringBuilder builder=null;           
         
        try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			int tempchar;
			builder=new StringBuilder();
			while ((tempchar = reader.read()) != -1) {  // 一次读一个字符 
				builder.append((char)tempchar);            	
			}             
		} catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  		        
		return builder.toString();        
	}
	
	public static void writeDataToFile(String content,String destination) {
		File file = new File(destination);		
//        StringBuilder builder=null; 
		PrintWriter printWriter=null;
		FileOutputStream fileOutputStream=null;
		try {
			if(!file.exists()) {
				file.createNewFile();			
			}
			fileOutputStream=new FileOutputStream(file);
	        printWriter=new PrintWriter(fileOutputStream);
	        printWriter.write(content);
	        printWriter.flush();			
	        fileOutputStream.close();         
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if(printWriter!=null) {
				printWriter.close();
			}			
		}     
	}
	
	public double Round(double initial) {  
		
		DecimalFormat df= new DecimalFormat("######0.0000");     
		double processed=Double.parseDouble(df.format(initial));
		return processed;
	     
	} 
}

