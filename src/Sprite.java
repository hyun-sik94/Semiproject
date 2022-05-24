import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//Sprite: 화면 전체가 아닌 개체의 움직임만 제어하는 하드웨어 기능을 지칭하는 용어
//소프트웨어적인 이미지 개체의 움직임도 포함하는 것으로 의미가 확장되었다.
//각 요소들(캐릭터, 총알 등)의 이미지를 받는 클래스 

public class Sprite {
	
	//protected : 패키지, 클래스, 자손클래스에서 접근을 허용하는 접근제어자
	protected int x;  //현재 위치의 x좌표
	protected int y; // 현재 위치의 y좌표
	protected int dx; //단위시간에 움직이는 x방향 거리
	protected int dy; //단위시간에 움직이는 y방향 거리
	private Image image; //스프라이트가 가지고 있는 이미지
	public boolean flag= false;
	
	
	
	//생성자 
	//이미지와 x,y축 촤표를 매개변수로 받는다.
	public Sprite(Image image, int x, int y) {
		//↓ this : 생성자 매개변수와 필드 멤버변수의 변수명이 같을 때 사용
		this.image=image;
		this.x=x;
		this.y=y;
	}
	//스프라이트(이미지)의 가로 길이를 반환한다.
	//int형으로 반환값을 줬기에 return 필요
	public int getWidth() {
		return image.getWidth(null);
	}
	//스프라이트(이미지)의 세로 길이를 반환한다.
	public int getHeight() {
		return image.getHeight(null);
	}
	//스프라이트를 화면에 그린다.
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
		
	}
	//스프라이트를 움직인다. 
	public void move() {
		//기본적으로 move 메소드가 실행되면
		//x, y값이 변경된다. 
		x+=dx;
		y+=dy;
	}
	//dx를 설정한다. 
	//keyEvent에서 사용
	//set을 통해 인자로 전달받은 값을 받아서 저장한다. 
	public void setDx(int dx) {
		this.dx = dx;
	}
	//dy를 설정한다. 
	//keyEvent에서 사용
	//set을 통해 인자로 전달받은 값을 받아서 저장한다. 
	public void setDy(int dy) {
		this.dy = dy;
	}
	//dx를 반환한다. 
	//get을 통해 저장된 값을 받아서 내보낸다. 
	//내보내는 return값을 int형으로 지정
	public int getDx() {
		return dx;
	}
	//dy를 반환한다.
	//get을 통해 저장된 값을 받아서 내보낸다. 
	//내보내는 return값을 int형으로 지정
	public int getDy() {
		return dy;
	}
	//x를 반환한다.
	public int getX() {
		return x;
	}
	//y를 반환한다.
	public int getY() {
		return y;
	}
	
	//다른 스프라이트와의 충돌 여부를 계산한다.
	//충돌이면 true를 반환한다. 
	//other는 내가 아닌 상대방 즉) Ailen(적)은 shot(총)이 other 
	//객체(나).checkCollision(other) 이렇게 사용
	public boolean checkCollision(Sprite other) {
		
		Rectangle myRect = new Rectangle();
		Rectangle otherRect = new Rectangle();
		//↓ 나의 위치에 사각형 영역 위치시킴
		//↓ setBounds : 위치와 크기를 지정해준다. 
		myRect.setBounds(x, y, getWidth(),getHeight());
		//↓ 적의 위치에 사각형 영역 위치시킴
		//↓ setBounds : 위치와 크기를 지정해준다. 
		otherRect.setBounds(other.getX(), other.getY(), other.getWidth(), other.getHeight());
		//myRect가 otherRect와 교차하는지 확인 (교차 = 충돌)
		//↓ intersects : Rectangle을 이 구조체와 지정된 Rectangle의 교차 부분으로 바꾼다. 
		//otherRect를 myRect의 지정된 Rectangle의 교차 부분으로 바꾼다는 뜻.
		return myRect.intersects(otherRect);
		
	}
	//충돌 이벤트 처리 메소드,, 충돌을 처리한다.
	public void handleCollision(Sprite other) {
		
	}
//	public void setImage(BufferedImage read) {
//		// TODO Auto-generated method stub
//		
//	}
	public void setImage(BufferedImage read) {
		// TODO Auto-generated method stub
		
	}
	


	
}
