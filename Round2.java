package json_to_csk;

import java.math.BigDecimal;

public class Round2 {
	public static void main(String[] args) {
		double aa=round(504.70001220703125);
		System.out.println(aa);
		Double a = 1.000000;
		System.out.printf("%1.6f",a);
		System.out.println("");
		String b="504.70001220703125";
//        int afterDot=b.length()-1-b.lastIndexOf('.');
        System.out.println(b.substring(0,b.lastIndexOf('.')+5));
		
	}
	
	public static double round(double value) { 
		
    	BigDecimal bd = new BigDecimal(value);  
        String sbd=bd.toString();
        int afterDot=sbd.length()-1-sbd.lastIndexOf('.');
        if(afterDot>4) {
        	value=subround(value,4,BigDecimal.ROUND_HALF_UP);
        }else{
        	switch (afterDot) {
    		case 1:
    			value=Double.parseDouble(new StringBuilder(sbd).append("000").toString());
    			break;
    		case 2:
    			value=Double.parseDouble(new StringBuilder(sbd).append("00").toString());
    			break;
    		case 3:
    			value=Double.parseDouble(new StringBuilder(sbd).append("0").toString());
    			break;
    		case 4:		
    		default:
    			break;
    		}
        }
        value=addZero(value);
        return value;  
    }
	
	public static double subround(double value, int scale, int roundingMode) {  
        BigDecimal bd = new BigDecimal(value);  
        bd = bd.setScale(scale, roundingMode);  
        double d = bd.doubleValue();  
        bd = null;  
        return d;  
    }  

	public static double addZero(double value) {  
		String sFormatValue=String.valueOf(value);
		int afterDot=sFormatValue.length()-1-sFormatValue.lastIndexOf('.');      
        
        	switch (afterDot) {
    		case 1:
    			value=Double.parseDouble(new StringBuilder(sFormatValue).append("000").toString());
    			break;
    		case 2:
    			value=Double.parseDouble(new StringBuilder(sFormatValue).append("00").toString());
    			break;
    		case 3:
    			value=Double.parseDouble(new StringBuilder(sFormatValue).append("0").toString());
    			break;
    		case 4:	    					
    		default:
    			break;
    		}
        return value;
    }      
} 
