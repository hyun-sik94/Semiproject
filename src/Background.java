import java.awt.Image;

public class Background extends Sprite{
	
	private SpiderGame game; 
	
	public Background(SpiderGame game, Image image, int x, int y) {
		//Sprite 클래스를 상속받고 있기에
		//Sprite 생성자에 super를 통해 값을 저장한다.
		//super : 상속받은 멤버와 자신의 멤버와 이름이 같을 때 super를 붙여서 구별할 수 있다
		//↓public Sprite(Image image, int x, int y) {
		//super(image, x, y);
		super(image, x, y);
		this.game = game;

	}
}
