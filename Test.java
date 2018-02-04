package json_to_csk;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
//		System.out.println(round(70.5999984741211));
		
		List<String> arr=new ArrayList<>();
		for(String s:arr){
			s="1";
		}
		System.out.println(arr.size());
		for(int i=0;i<arr.size();i++){
			System.out.println(arr.get(i));
		}
		
//		String string="1.10000";
//				System.out.println(!(string.equals("0")));
	}
	
public static double round(double initial) {  
		
		DecimalFormat df= new DecimalFormat("############0.0000");     
		double processed=Double.parseDouble(df.format(initial));
		return processed;
	     
	} 
	
}
