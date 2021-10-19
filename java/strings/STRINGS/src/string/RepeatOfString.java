package string;

import java.util.Arrays;
import java.util.Scanner;

public class RepeatOfString {

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
        System.out.println("Enter the String");
        String str=in.nextLine();
        int count=0;
        String[] s1= str.split(" ");
        String[] temp=new String[s1.length] ;
        StringBuilder c2=new StringBuilder();
        for(int i=0;i < s1.length;i++){
            StringBuilder c1=new StringBuilder(s1[i]);
            for(int j=i;j < s1.length;j++){
            	c2=new StringBuilder(s1[j]); 
                if(c1==c2 && !Arrays.asList(temp).contains(c2.toString()) ){
                    count=count+1;
                }
            }
			if(!Arrays.asList(temp).contains(c2.toString()) ){
				temp[temp.length]=c1.toString();
				System.out.println("The Character   " + c1.toString() + "   has occurred   " + count + "    times");
			}  
		count=0;
        }

	}

}
