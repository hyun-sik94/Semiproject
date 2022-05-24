import java.awt.Image;

//아군, 플레이어가 움직일 우주선 
public class SpidermanSprite extends Sprite {
	
	//↓ Spiderman 클래스 속성을 받는 game 멤버 변수 생성
	private SpiderGame game;
	
	
	public SpidermanSprite(SpiderGame game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		dx=0;
		dy=0;
	}
	
	@Override
	public void move() {
		//스파이더맨이 frame에서 벗어나지 못하게 설정한다.
		if((dx < 0) && (x <10)) {
			return;
		}

		if ((dx>0) && (x>SpiderGame.SCREEN_WIDTH-getWidth())) {
			return;
		}
		if((dy < 0) && (y <10)) {
			return;
		}
		//이미지의 크기만큼 스크린 창에서 빼줘야 한다. 
		if ((dy>0) && (y>SpiderGame.SCREEN_HIGHT-getHeight())) {
			return;
		}
		super.move();
	}
	
	@Override
	//적과 스파이더맨 충돌했는지를 확인한다. 
	public void handleCollision(Sprite other) { //other = Sprite
		//즉 알렌 스프라이트 형태로 other의 형변환이 가능하다면 
		//↓플레이어가 적과 충돌했을 때 player_Hitpoint 감소
		if( other instanceof GreenGoblin) {
			game.removeSprite(other);
			game.player_Hitpoint--;
			//player_Hitpoint가 감소되면 나오는 sound
			game.Sound("collision.wav", false);
			
		}
		
		//↓플레이어가 적과 충돌했을 때 player_Hitpoint 감소
		//적과 충돌한다면 적의 이미지를 지운다.
		if( other instanceof DoctorOctopus) {
			game.removeSprite(other);
			game.player_Hitpoint--;
			game.Sound("collision.wav", false);
			
		}
		
		//↓플레이어가 적의 공격과 충돌했을 때 player_Hitpoint 감소
		//적과 충돌한다면 적 공격의 이미지를 지운다.
		if( other instanceof ShotPumkinSprite) {
			game.removeSprite(other);
			game.player_Hitpoint--;
			game.Sound("collision.wav", false);

		}
		
		//player_Hitpoint가 0이 되면 게임 종료
		//lose 화면이 나온다.
		if(game.player_Hitpoint == 0) {
			game.loseGame();
		}
		
	}

}
