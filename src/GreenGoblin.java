import java.awt.Image;

//적
//GreenGoblin 클래스는 Sprite의 상속을 받는다. 
public class GreenGoblin extends Sprite {
	//↓ Spiderman 클래스 속성을 받는 game 멤버 변수 생성
	private SpiderGame game; 
	
	//매개변수를 사용한 초기화, 생성자 
	//Spiderman 클래스에서 아래의 인자를 받는다.
	public GreenGoblin(SpiderGame game, Image image, int x, int y) {
		//Sprite 클래스를 상속받고 있기에
		//Sprite 생성자에 super를 통해 값을 저장한다.
		//super : 상속받은 멤버와 자신의 멤버와 이름이 같을 때 super를 붙여서 구별할 수 있다
		//↓public Sprite(Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		//단위시간에 움직이는 x방향 거리를 -1으로 초기화한다.
		dx = -1;

		//고블린 속도를 증가시키는데
		//1,2단계에선 -1, 3&4&5 단계에선 -2, 6&7&8 단계에선 -3만큼
		for(int i=1; i<10; i++) {
			if(game.game_stage==3*i||game.game_stage==(3*i)+1||game.game_stage==(3*i)+2)
				dx-=i;
			}
		}
	
	@Override
	public void move() {
		//↓ x+=dx; y+=dy;
		super.move();
	}

}
