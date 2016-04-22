
public class TestEnum {

	public static void main(String[] args) {
		
		Methods enums = Enums.METHOD;
		
		enums.getI();
		
		enums.setI(11212);
		
		enums.getI();
		
		enums = Enums.METHOD;
		
		enums.getI();

	}

}

class Methods {
	
	private int i;
	
	public Methods() {
		
		System.out.println("Init");
		i = 0;
	}
	
	public void setI(int newI){
		i = newI;
	}
	
	public void getI(){
		System.out.println(i);
	}
}


class Enums{
	public static final Methods METHOD = new Methods();
}