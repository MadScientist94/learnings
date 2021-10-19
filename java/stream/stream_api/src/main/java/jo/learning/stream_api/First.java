package jo.learning.stream_api;

import java.util.ArrayList;
import java.util.List;




public class First {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		First f = new First();
		f.init();
		
	}

	
	
	
	
	
	
	
	
	void init() {
		
		
		
		List<widget> widgets= new ArrayList();
		widgets.add(new widget("red",10));
		widgets.add(new widget("red",10));
		widgets.add(new widget("red",10));
		widgets.add(new widget("red",10));
		widgets.add(new widget("red",10));
		widgets.add(new widget("red",10));
		int sum = widgets.stream()
                .filter(b -> b.getColor() == "red")
                .mapToInt(b -> b.getWeight())
                .sum();
//		System.out.println(sum);
		List<Integer> numb= new ArrayList();
		numb.add(10);
		numb.add(10);
		numb.add(10);
		numb.add(5);
		numb.add(5);
		numb.add(5);
//		int sum1 = numb.stream().filter(n-> n>8).mapToInt(n-> n).sum();
//		int sum1 = numb.stream().
//		int sum1 = numb.stream().filter(n-> n>8).mapToInt(n-> n).sum();

//		System.out.println(sum1);
	}
}
class widget{
	String color="";

	int weight=0;
	widget(){
		
	}
	widget(String c, int n){
		color=c;
		weight= n;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
}
