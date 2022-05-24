import java.awt.Image;

public class Ironman extends Sprite{
		
		private SpiderGame game;
		
		public Ironman(SpiderGame game, Image image, int x, int y) {
			super(image, x, y);
			this.game = game;
			dx = 4;
		}
		@Override
		public void move() {
			super.move();
			if(x > 1100) { // 미사일이 화면 밖으로 나가는 경우 미사일 객체 제거
				game.removeSprite(this);
			}
		}
		@Override
		public void handleCollision(Sprite other) {
			//other 객체가 AlienSpriter 타입으로 형변환 가능한지 확인(총알이 적과 닿았는지 확인)
			if(other instanceof GreenGoblin ) {
				game.removeSprite(other);
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
			
			//필살기가 닥터랑 충돌했다면 닥터의 이미지만 삭제
			if(other instanceof DoctorOctopus ) {
				game.removeSprite(other);
				game.game_Score++;
				game.ultimate_Score++;
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
			//필살기가 닥터의 공격이랑 충돌했다면 닥터의 공격 이미지만 삭제
			if(other instanceof ShotPumkinSprite ) {
				game.removeSprite(other);
				game.game_Score++;
				game.ultimate_Score++;
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
			
			
		}

	}

