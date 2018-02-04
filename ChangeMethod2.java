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
//		String from="D:\\study_resouce\\��Ŀ\\15755141202_207\\15755141202\\4241\\4241.json";
//		String to="D:\\study_resouce\\��Ŀ\\15755141202_207\\15755141202\\4241\\4241.csk";

		
//		String from="F:\\05 iflytek\\study_resouce\\��Ŀ\\15755141202_207\\15755141202\\4241\\4241.json";
//		String to="F:\\05 iflytek\\study_resouce\\��Ŀ\\15755141202_207\\15755141202\\4241\\4241.csk";
		
		String from="C:/Users/GRY/Desktop/4241.json";
		String to="C:/Users/GRY/Desktop/4241.csk";		
		
		String content=change(from);
		writeDataToFile(content,to);
	}
	
	/*
	 * json ת csk
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
		 * ����List��������Ԫ�طֿ����浽��ͬ������
		 */
		for(PointBean2 p:pointBeanList2) {
			if(!(p.geteX().equals("0")) && !(p.geteY().equals("0")) ) {//ͨ���ж�x��y��Ϊ0���޳�������josn����
				
				sX_Array.add(p.getsX());
				sY_Array.add(p.getsY());
				
				eX_Array.add(p.geteX());
				eY_Array.add(p.geteY());
				
				pressure_Array.add(p.getPressure());
				t_Array.add(p.getT());
			}
		}
		
		length=eX_Array.size();
		
//		      ѭ����
//		    <PenData>  
//				<symbol label="" type="UNCLASSIFIED">
		stringBuilder
		.append("<PenData>")
		.append("\r\n")
		.append("  <symbol label=\"\" type=\"UNCLASSIFIED\">")
		.append("\r\n");
		
		/*
		 * ѭ����ȡÿһ��
		 */
		for(int i=0;i<length;i++) {
			boolean_X=sX_Array.get(i).equals(eX_Array.get(i).toString());
			boolean_Y=sY_Array.get(i).equals(eY_Array.get(i).toString());
			System.out.println(boolean_X+"  "+boolean_Y);		
			
			/*
			 * �����γ���sX=eX sY=eY
			 * ��<stroke label="30" index="30">
			 * �ټӵ����Ϣ
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
			 * ż���γ���sX=eX sY=eY
			 * �ȼӵ����Ϣ
			 * �ټ�</stroke>
			 */
			else if(boolean_X && boolean_Y && num%2==0){							
				
				/*
				 * ƴ�ӳ�һ��point
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
				 * ƴ��point
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
		
//		      ѭ����
//		</symbol>  
		stringBuilder
		.append("  </symbol>");
//		.append("\r\n");
		
		
		System.out.println(stringBuilder.toString());        
		System.out.println("һ����"+length+"������");
		
		return stringBuilder.toString();
	}
	
	/**
	 * ��ȡjson�ļ�������������д����һ���ַ�����������
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
			while ((tempchar = reader.read()) != -1) {  // һ�ζ�һ���ַ� 
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

