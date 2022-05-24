import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//총알  
public class ShotSprite extends Sprite{
	
	//↓ Spiderman 클래스 속성을 받는 game 멤버 변수 생성
	private SpiderGame game;
	
	//매개변수를 사용한 초기화, 생성자 
	//Spiderman 클래스에서 아래의 인자를 받는다.
	//↓ ShotSprite(this, shotImage, spiderman.getX() +10, spiderman.getY()-30);
	public ShotSprite(SpiderGame game, Image image, int x, int y) {
		//super : 상속받은 멤버와 자신의 멤버와 이름이 같을 때 super를 붙여서 구별할 수 있다
		//↓public Sprite(Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		//단위시간에 움직이는 x방향 거리를 8으로 초기화한다.
		//dx를 크게 주면 슈팅 속도가 빨라지는 것.
		dx = 8;
	}
	//상속받고 있는 Sprite클래스에 move() 메소드를 오버라이딩한다.
	//즉) 새롭게 move()메소드를 정의한다는 것!
	@Override
	public void move() {
		//↓ x+=dx; y+=dy;
		super.move();
		//게임 창의 가로를 벗어난다면 사라진다.
		if(x>game.SCREEN_WIDTH +100) {
			game.removeSprite(this);			
		}
	}
	
	@Override
	//총알과 적이 충돌했는지 확인한다.
	//↓ spiderman 클레스에서 이렇게 사용 me.handleCollision(other);
	public void handleCollision(Sprite other) {
		//instanceof는 객체 타입을 확인하는 연산자이다.
		//형변환 가능 여부를 확인하며 true/false로 결과를 반환한다.
		//other 객체가 GreenGoblin 타입으로 형변환 가능한지 확인 
		//즉 other 객체가 GreenGoblin 클래스이거나 이 클래스의 상속을 받던가.
		//(총알이 적과 닿았는지 확인)
		if(other instanceof GreenGoblin) {
			try {
				other.setImage(ImageIO.read(new File("collisionImage.png")));
				SpiderGame.remove = true;
			}catch(IOException e) { //파일을 읽어올 수 없으면 상세한 내용 print
				e.printStackTrace();
			}

			//SpiderGame.remove true이면 
			if(SpiderGame.remove) {
			game.removeSprite(other);
			game.removeSprite(this); // this 는 ShotSprite
			}
			
			game.ultimate_Score++;
			game.game_Score++;
			
			for(int i=0; i <100; i++) {
				//점수가 20점의 배수이면 stage가 올라간다.
				if(game.game_Score == 20 *i) {
				game.game_stage++;
				
				//만약 game_stage가 12점이 되면 게임이 종료된다.
				if(game.game_stage==12 )
					game.winGame();
					//game.endGame();
			}		
			} 
		}
		
		
		if(other instanceof DoctorOctopus) {
			
			game.removeSprite(other);
			game.removeSprite(this);
			game.ultimate_Score++;
			game.game_Score++;
			for(int i=0; i <100; i++) {
				//점수가 20점의 배수이면 stage가 올라간다.
				if(game.game_Score == 20 *i) {
				game.game_stage++;
				
				//만약 game_stage가 12점이 되면 게임이 종료된다.
				if(game.game_stage==12 )
					game.winGame();
					//game.endGame();
				}		
			}
		}
		
		if(other instanceof ShotPumkinSprite) {
					
			game.removeSprite(other);
			game.removeSprite(this);
			//game.ultimate_Score+=3;
			game.ultimate_Score++;
			game.game_Score++;
			for(int i=0; i <100; i++) {
				//점수가 20점의 배수이면 stage가 올라간다.
				if(game.game_Score == 20 *i) {
				game.game_stage++;
				//만약 game_stage가 12점이 되면 게임이 종료된다.
				if(game.game_stage==7 )
					game.winGame();
					//game.endGame();
				}		
			}
		}
		
	}
	

}
