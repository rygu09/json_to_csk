package json_to_csk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ChangeMethod {
	
	public static void main(String[] args) {
		String path="C:\\Users\\admin\\Desktop\\4201.json";
		change(path);
	}
	
	public static void change(String path) {
		
		List<Float> eX_Array=new ArrayList<>();
		List<Float> eY_Array=new ArrayList<>();
		List<Integer> pressure_Array=new ArrayList<>();
		List<Long> t_Array=new ArrayList<>();
		
		int length;
		StringBuilder stringBuilder=new StringBuilder();
		
//		List<PointBean> pointBeanList=new ArrayList<>();
		
		Gson gson = new GsonBuilder().create();
		
		String message=getStringFromJsonFile(path);
//		System.out.println(message);
		
		List<PointBean> pointBeanList = gson.fromJson(message, new TypeToken<List<PointBean>>() {}.getType());
		
		/**
		 * 遍历List，将各个元素分开来存到不同数组中
		 */
		for(PointBean p:pointBeanList) {
			eX_Array.add(p.geteX());
			eY_Array.add(p.geteY());
			pressure_Array.add(p.getPressure());
			t_Array.add(p.getT());

//			System.out.print(p.getPressure()+"  ");
//			System.out.print(p.geteX()+"  ");
		}
		
		length=eX_Array.size();
		
		for(int i=0;i<length;i++) {
			stringBuilder.append("<point ")
						 .append("y=\"")
						 .append(eY_Array.get(i))
						 .append("\" x=\"")
						 .append(eX_Array.get(i))
						 .append("\" pressure=\"")
						 .append(pressure_Array.get(i))
						 .append("\" timestamp=\"")
						 .append(t_Array.get(i))
						 .append("\" />")
						 .append("\r\n");
		}
		System.out.println(stringBuilder);
		
		
//		JsonReader reader = new JsonReader(new StringReader(message));
//		reader.setLenient(true);		
//		PointBean pointBean= gson.fromJson(reader, PointBean.class);
		
		
//		pointBeanList= gson.fromJson(reader, PointBean.class);
//		for(PointBean p:pointBeanList) {
//			System.out.println(p);
//		}

//		JsonParser jsonParser=new JsonParser();
//        JsonArray jsonArray=jsonParser.parse(path).getAsJsonArray();
//        for(int i=1;i<jsonArray.size();i++){
//            JsonObject jsonObject=jsonArray.get(i).getAsJsonObject();
//           
//            PointBean pointBean=gson.fromJson(jsonObject,PointBean.class);
//            pointBeanList.add(pointBean);
//        }
        
	}
	
	public static String getStringFromJsonFile(String path) {
		File file = new File(path);
		BufferedReader reader = null; 
        StringBuilder builder=null;        
         
        System.out.println("以字符为单位读取文件内容，一次读一个字节：");  
        // 一次读一个字符  
        try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			int tempchar;
			builder=new StringBuilder();
			while ((tempchar = reader.read()) != -1) {  
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

}
