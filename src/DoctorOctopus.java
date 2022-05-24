import java.awt.Image;

public class DoctorOctopus extends Sprite{
	
		//↓ Spiderman 클래스 속성을 받는 game 멤버 변수 생성
		private SpiderGame game; 
		
		//매개변수를 사용한 초기화, 생성자 
		public DoctorOctopus(SpiderGame game, Image image, int x, int y) {
			//Sprite 클래스를 상속받고 있기에
			//Sprite 생성자에 super를 통해 값을 저장한다.
			//super : 상속받은 멤버와 자신의 멤버와 이름이 같을 때 super를 붙여서 구별할 수 있다
			//↓public Sprite(Image image, int x, int y) {
			super(image, x, y);
			this.game = game;
			//단위시간에 움직이는 y방향 거리를 -3으로 초기화한다.
			dy = -2;
			
			}
		
		@Override
		public void move() {
				

			// y축의 값이 0보다 작거나 400보다 클 경우
			//dx의 값을 -로 변경하여 반대로 이동하게 함
			if(y < 0 || y > 400)
				dy = -dy;	
			
			//↓ x+=dx; y+=dy;
			super.move();
		}

	}
//}
	
	
	


