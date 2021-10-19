package string;

import java.util.Scanner;

public class RepeatationOfCharactersInString {

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
        System.out.println("Enter the String");
        String str=in.nextLine();
        int count=0;
        String temp="";
        for(int i=0;i < str.length();i++){
            char c1=str.charAt(i);
            for(int j=i;j < str.length();j++){
				char c2=str.charAt(j);  
                if(c1==c2 && temp.indexOf(c1)==-1){
                    count=count+1;
                }
            }
			if(temp.indexOf(c1)==-1){
				temp=temp+c1;
				System.out.println("The Character   " + c1 + "   has occurred   " + count + "    times");
			}  
		count=0;
        }
        
        RepeatationOfCharactersInString frc = new RepeatationOfCharactersInString();
		frc.mainLoop();
	}	
    void mainLoop() {
    	Scanner in=new Scanner(System.in);
        
    	String str = " ";
        System.out.println("Please enter a string: ");
        str = in.next();
        str = str.toLowerCase();
        for(int i = 0; i < str.length(); i++) {
            char character = str.charAt(i);
            int firstIndex = str.indexOf(character);
            int lastIndex = str.lastIndexOf(character);
            if(firstIndex != lastIndex) {
                System.out.println("The character '"+character+"' is repeated");
                break; 
            }
        }

	}


}