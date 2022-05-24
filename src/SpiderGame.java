import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;
import javax.swing.JPanel;

 public class SpiderGame extends JPanel implements KeyListener {
	
	//게임의 진행 상태 
	private boolean running = true;
	//게임 졌을, 이겼을 때 나타나는 이미지
	boolean isLoseScreen,isWinScreen = false;
	//게임(캐릭터 움직임) 시작할 때 나타나는 이미지
	boolean isGameScreen = false;
	//게임 시작 시 처음 엔터 누르기 전 화면
	boolean isStartScreen = true;
	//게임 시작 후 3초간 게임 설명 나오는 화면 
	boolean isLoadingScreen = false;	
	
	private ArrayList sprites = new ArrayList();

	private Sprite spiderman, greengoblin,background, endbackground;

	private BufferedImage greengoblinImage; //왼쪽으로 움직이는 적1
	private BufferedImage doctoroctopusImage;//총알쏘는 적2
	private BufferedImage shotImage; // 총알
	private BufferedImage shotPumkinImage; // 적의 총알 
	private BufferedImage spidermanImage; //우주선 
	private BufferedImage backgroundImage;
	private BufferedImage loadingScreenImage,startScreenImage,loseScreenImage,winScreenImage ;
	private BufferedImage ironmanImage; // 궁극필살기
	
	Random random = new Random();
	
	public static final int SCREEN_WIDTH = 1100;
	public static final int SCREEN_HIGHT = 530;
	static boolean remove = false;
	int game_Score = 0; // 게임점수계산
	int player_Hitpoint = 20; // 플레이어캐릭터의체력
	int game_stage =1;
	int ultimate_Score = 0;
	static int addGoblinImage =15;
	//게임루프 쓰레드, 전체 캐릭터 움직이는 속도
	private int delay=15;
	private long pretime;
	//int count;//루프 카운트 
	int r=900; // 닥터가 나오는 시간 아래 쓰레드에서 사용
	

	public SpiderGame() {
		JFrame frame = new JFrame("Spider Game");
		
		frame.setSize(1100,530);
		frame.setSize(SCREEN_WIDTH,SCREEN_HIGHT);
		//add 없으면 아예 화면이 나오지 않는다.!
		frame.add(this);
		frame.setResizable(false); //창 크기 조절 불가
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		//프레임을 모니터 화면 정 중앙에 배치하기위한 좌표값 계산
		int f_xpos = (int) (screen.getWidth() / 2 - SCREEN_WIDTH / 2);
		int f_ypos = (int) (screen.getHeight() / 2 - SCREEN_HIGHT / 2);
		frame.setLocation(f_xpos, f_ypos); //프레임을해당위치에배치
		frame.setVisible(true);
		//프레임이 종료되면 프로그램 실행도 중지
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		try {
			
			//BufferedImage 객체에 이미지 파일 넣기
			ironmanImage = ImageIO.read(new File("ironman.png"));
			shotImage = ImageIO.read(new File("spiderwep.png"));
			shotPumkinImage = ImageIO.read(new File("pumkin.png"));
			spidermanImage = ImageIO.read(new File("spiderman.png"));
			doctoroctopusImage = ImageIO.read(new File("doctor.png"));
			greengoblinImage = ImageIO.read(new File("greengoblin.png"));
			backgroundImage = ImageIO.read(new File("space.jpg"));
			
			loseScreenImage = ImageIO.read(new File("lose.png"));
			startScreenImage = ImageIO.read(new File("startScreen.png"));
			loadingScreenImage = ImageIO.read(new File("loading.png"));
			winScreenImage = ImageIO.read(new File("win.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		this.requestFocus();
		//background가 다른 이미지보다 먼저 생성되야 한다.
		this.background();
		//처음 초기화면에 게임 캐릭터들이 등장한다.
		this.initCharacterSprites();
		this.GreenGoblininitSprites();	
		addKeyListener(this);

	
	}
		

	//스파이더맨, 닥터 초기 설정 메소드
	private void initCharacterSprites() {
		
		//↓ 아군(스파이더맨) 초기 설정 하기
		//스파이더맨 이미지를 넣고 위치는 x축 50, y축 200에서 생성된다.
		spiderman = new SpidermanSprite(this, spidermanImage, 50, 200);
		//ArrayList 배열 객체인 sprites에 spiderman을 저장한다.
		sprites.add(spiderman);
		
		//닥터 옥토퍼스 쓰레드 객체에 y축 위치를 랜덤으로 두고 start()
		////닥터의 생성 -> x축을 SCREEN_WIDT의 값에서 - 200하여 계산 값으로 설정함
		//  y축을 SCREEN_HIGHT - 닥터이미지의 높이로 계산하여 해당 범위의 난수로 설정
		DoctorOctopusThread docter = new DoctorOctopusThread(this,doctoroctopusImage, SCREEN_WIDTH-200, random.nextInt(SCREEN_HIGHT-doctoroctopusImage.getHeight()));
		docter.setPriority(10);
		docter.start();
		
	}
		
 	//그린고블린 초기 설정 메소드
	private void GreenGoblininitSprites() {	
		
				//↓ 적군(고블린) 초기 설정하 기
				//그린고블린의 수가 game_Stage에 맞춰 늘어난다.
				//1&2 단계 2행, 3&4&5 단계 3행, 6&7&8단계 4행, 9&10&11단계 5행  
    			for(int y=0; y<2+((int)(game_stage/3)); y++) {//초기는 2행
    			for(int x=0; x<11; x++) { //10열 
				
				//적 스프라이트를 생성한다.
				//100+(x*50) , (50) + y*30 : 적 사이의 간격 설정
				//x=0, y=0
				////100+(0*50) , (50) + 0*30 = 100, 50 : 0행 0열에 있는 적 
				//x=1, y=0  
				////100+(1*50) , (50) + 0*30 = 150, 50 : 0행 1열에 있는 적 
				//x=1, y=1
				////100+(1*50) , (50) + 1*30 = 150, 80 : 1행 1열에 있는 적 
				//즉 x축 간격은 50만큼, y축 간격은 30만큼
    			//여기서 y*50은 적들이 떨어진 만큼!! 즉 1행 적과 2행 적이 x 축으로 얼마큼 떨어져 있나.  
				
    				
		    	//Sprite 클래스의 객체 greengoblin 변수에 값을 저장한다.
		    	//GreenGoblin 생성자에 인자로 고블린 이미지, 초기 생성 될 x&y축을 전달한다.
				greengoblin = new GreenGoblin(this, greengoblinImage, 1000+((50) + y *70), 20+(x*40));

				//ArrayList 배열 객체인 sprites에 alien을 저장한다.
				sprites.add(greengoblin);
				
		    			}
		    		}
		    	}
	

	//닥터 구현하는 쓰레드를 상속받는 클래스
	class DoctorOctopusThread extends Thread{
		
		SpiderGame g;
		BufferedImage img;
		int x;
		int y;
		
		DoctorOctopusThread(SpiderGame g,BufferedImage img, int x ,int y){ 
			
			this.g=g;
			this.x=x;
			this.y=y;
			this.img=img;
		}
		
		public void run() {
			while(true) {
				try {
					//r은 나오는 시간 0~0.5초마다 랜덤으로 나온다
					//쓰레드가 실행된다.
					r+=new Random().nextInt(500);
					Thread.sleep(r);
					
				}catch(InterruptedException e) {}
				
				DoctorOctopus doctor = new DoctorOctopus(g, img, x, y);
				sprites.add(doctor);
				//아래 펌킨 쓰레드를 닥터의 위치에 맞춰 생성하게 만든다.
				new ShotPumkinThread(doctor, g,shotPumkinImage,x,y).start();
				
	 		}
		}
	
	
		//닥터의 공격 슛펌킨 쓰레드
		class ShotPumkinThread extends DoctorOctopusThread{
			DoctorOctopus obj;
			
			ShotPumkinThread(DoctorOctopus obj, SpiderGame g, BufferedImage img, int x, int y){
				super(g,img,x,y);
				this.obj=obj;
			}
			
			public void run() {
				//이 닥터옥토퍼스가 spiderwap에 맞아 없어지기 전까지
				while(obj.flag == false) {
					try {
						Thread.sleep(2000); // 2초 단위로
					}catch(InterruptedException e) {}
					//총알을 쏘게 된다. 
					sprites.add(new ShotPumkinSprite(obj,img));
				}
			}
		}
	}
	
	//엔터를 누르면 실행되는 메소드로 
	//플레이어가 실제로 시작하게 될 게임 화면
	private void startGameScreen() {
		//초기 화면이 사라지고 로딩 화면이 나온다.
		isStartScreen = false;
		isLoadingScreen = true;
		
		Timer loadingTimer = new Timer();
		TimerTask loadingTask = new TimerTask() {
			@Override
			public void run() {
				//3초 후 로딩 화면이 자동으로 사라지고 게임이 시작된다.
				isLoadingScreen = false;
				isGameScreen = true;
				//본격적인 게임이 실행되는 메소드
				gameLoop();
			}
		};
		//로딩배경 3초 동안 나온 후 GameScreen으로 넘어간다.
		loadingTimer.schedule(loadingTask, 3000);
	}
	
	
	//고블린을 추가로 프레임에 그리는 메소드
	private void addGreenGoblin() {
		GreenGoblininitSprites();
		 
	}
 	
 
	//ESC로 강제로 게임을 끝내면 실행되는 메소드
	public void endGame() {
		System.out.println("endGame!!");
		//
		System.exit(0);
	}
	
	//stage 끝내면 나타나는 메소드
	public void winGame() {
		isWinScreen = true;
		isGameScreen = false;
		Timer loadingTimer = new Timer();
		TimerTask loadingTask = new TimerTask() {
			@Override
			public void run() {
				System.out.println("winGame!!");
				System.exit(0);
			}
		};
		//win 화면이 3초 동안 나온 후 게임이 종료된다.
		loadingTimer.schedule(loadingTask, 3000);
		
	}
	
	//체력이 닳아 게임이 끝나면 실행되는 메소드
	public void loseGame() {
		isLoseScreen = true;
		isGameScreen = false;
		Timer loadingTimer = new Timer();
		TimerTask loadingTask = new TimerTask() {
			@Override
			public void run() {
				System.out.println("lose!!");
				System.exit(0);
			}
		};
		//lose 화면이 3초 동안 나온 후 게임이 종료된다.
		loadingTimer.schedule(loadingTask, 3000);
		
	}
	
	//스프라이트 제거
	//즉 충돌 시 이미지 삭제 
	public void removeSprite(Sprite sprite) {

		sprite.flag=true;
		sprites.remove(sprite);
		
	}
	
	// 필살기 메소드
	public void Ultimate() { 
		//스파이더맨의 위치를 가져와서 ctrl 누르면 그 위치에서 시작
		Ironman iron = new Ironman(this, ironmanImage, spiderman.getX() + 2, 
						spiderman.getY() - 5);
	      sprites.add(iron);
	   // 필사기 사용 후 게이지를 줄어들게 함
	      ultimate_Score -= 20; 
	   }
	
	//fire = 총알
	public void fire() {
		//this : 생성자의 이름으로 클래스이름 대신 this를 사용한다.
		//여기서 this : GalagaGame 클래스 
		//스파이더맨의 x 좌표와 y좌표를 받아오는데 
		//↓스파이더맨보다 오른쪽으로 10만큼 위로 30만큼 위치로 초기화 시킨다. 
		Sound("shot1.wav", false);
		ShotSprite shot = new ShotSprite(this, shotImage, spiderman.getX() +38, spiderman.getY()-10);
		sprites.add(shot);
	}
	
	
	//게임 시작 배경화면, 즉 우주 배경
	public void background() {

		background = new Background(this, backgroundImage, 0, 0);
		sprites.add(background); 		
	}
	
	//사운드재생용메소드
	//사운드 파일을 받아들여 해당사운드를 재생시킨다.
	public void Sound(String file, boolean Loop){
		Clip clip;
		try {
		AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
		clip = AudioSystem.getClip();
		clip.open(ais);
		clip.start();
		
		//소리 설정
		FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		//볼륨 조정
		gainControl.setValue(-8.0f);
		
		if (Loop) clip.loop(-1);
		//Loop 값이true면 사운드재생을무한반복시킵니다.
		//false면 한번만재생시킵니다.
		} catch (Exception e) {
		e.printStackTrace();
		}
		}

	
	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		
		//처음 시작 시 나오는 spiderman game 화면 
		if(isStartScreen) {
			g.drawImage(startScreenImage, 0, 0, this);
			repaint();
		}
		//enter 누르면 나오고 3초 후 사라지는 게임 조작 설명 화면
		if(isLoadingScreen) {
			g.drawImage(loadingScreenImage, 0, 0, this);
			repaint();
		}
		
		//메인 인 게임 스크린
		//ArrayList에 저장한 sprites 객체들에 값을 모두 가져와
		//for 문을 통해 새롭게 그린다.
		//왼쪽 상단 game_state 표지판도 생성
		if(isGameScreen){
		
		for(int i=0; i<sprites.size(); i++) {
			Sprite sprite = (Sprite) sprites.get(i);
			sprite.draw(g);
		}
		
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Default",Font.BOLD,15));
		//점수를 표시한다.
		g.drawString("Score : " +game_Score,10,30);
		//체력을 표시한다. 
		g.drawString("HitPoint : " + player_Hitpoint, 10, 55);
		//단계를 표시한다. 
		g.drawString("Stage : " + game_stage, 10, 80);
		//필살기 점수를 표시한다.
		g.drawString("Ironman : "+ ultimate_Score, 10, 105);
		}
		
		//hitpoint가 0이 되어 게임 실패 시 나오는 lose 화면
		if(isLoseScreen) {
			g.drawImage(loseScreenImage, 0, 0, this);
		}

		//stage가 12단계가 되어 게임 complete 시 나오는 win 화면
		if(isWinScreen)	{
			g.drawImage(winScreenImage, 0, 0, this);
			
		}
	
	}
	
	
	//플레이어를 제외한 모든 오브젝트를 이동시킨다. 
	public void gameLoop() {

		//소리 재생, 로딩 화면이 지나고 메인 게임이 시작되면 실행
		Sound("spiderman.wav", true);
		//고블린 나타나게 하기 위한 boolean형 변수 addenermy
		boolean addenermy = false;
		
		//running이 true이면 무한으로 반복한다.
		while(running) {
			
			for(int i=0; i<sprites.size(); i++) {
				//ArrayList의 모든 요소를 가져와서 Sprite 타입(슈퍼클래스)로 변환한다.
				Sprite sprite = (Sprite)sprites.get(i);
				sprite.move();
			}
			//두 스프라이트의 충돌을 확인한다. 
			for(int p=0; p<sprites.size(); p++) {
				for(int s = p+1; s<sprites.size(); s++) {
					
					//↓s번째 인덱스의 sprites를 가져온다. 
					//즉 ArrayList에 저장되어 있는 객체 정보
					Sprite me= (Sprite)sprites.get(p);
					
					//↓s번째 +1번째 인덱스부터의 스프라이트를 가져옴 
					Sprite other = (Sprite)sprites.get(s);
					
					//checkCollision : 충돌 발생 현황을 확인한다. 
					//boolean형으로 충돌하면 true
					if(me.checkCollision(other)) {
						//handleCollision: 충돌 발생 시 처리하는 메소드 
						me.handleCollision(other);
						other.handleCollision(me);
					}
				}
			}
			//필수! 새롭게 화면을 다시 그린다.
			repaint();
			
			pretime = System.currentTimeMillis();
			if(System.currentTimeMillis()-pretime<delay) {
				
				try {
					Thread.sleep(delay-System.currentTimeMillis() + pretime);	
			}catch(Exception e) {}
			}
		
			 
			//점수가 15점의 배수-1이 되면 고블린이 초기 위치에 다시 생성된다. 
			for(int i=1; i<1000;i++) {
				if(game_Score+addGoblinImage==addGoblinImage*i)
					addenermy=false;
				//addenermy가 true가 되야 생성되는 것.
				//upStageScore = 15의 배수에서 -1 일 때
				//즉) 14 29 44 59 74 89 104 119 134 149 164 179 ,,, 등등인데 
				//여기서 짝수일 때만 고블린이 다시 생겨난다. 
				if(addenermy==false && (game_Score+1) % addGoblinImage==0 && (game_Score%2==0)){
					//(game_Score+1)%2==0)
					addGreenGoblin(); 
					addenermy = true; 	
			  	}
			}
		}
	}
	
	
	@Override
	//키보드를 눌렀을 때
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) //← 키보드
			spiderman.setDx(-3);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)//→ 키보드
			spiderman.setDx(+3);
		if(e.getKeyCode() == KeyEvent.VK_UP)//↑ 키보드
			spiderman.setDy(-3);
		if(e.getKeyCode() == KeyEvent.VK_DOWN)//↓ 키보드
			spiderman.setDy(+3);
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)//esc 키보드
			endGame();
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			startGameScreen();
	}

		
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	//키보드를 눌렀다 뗐을 때 
	@Override
	public void keyReleased(KeyEvent e) {
		//스페이스 키보드
		//꾹 눌렀을 때 지속적으로 미사일이 발사되는 것을 발생하기 위해 키보드가 눌렀다가 떼진 경우 실행 되도록 함

		if(e.getKeyCode() == KeyEvent.VK_SPACE) 
			fire();
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			spiderman.setDx(0);
		if(e.getKeyCode()== KeyEvent.VK_RIGHT)
			spiderman.setDx(0);
		if(e.getKeyCode() == KeyEvent.VK_UP)
			spiderman.setDy(0);
		if(e.getKeyCode()== KeyEvent.VK_DOWN)
			spiderman.setDy(0);
		// 궁극기(필살기) 점수가 20 이상일 경우 컨트롤을 눌러 메소드 실행
		if(ultimate_Score >= 20) { //컨트롤 키보드
			if (e.getKeyCode() == KeyEvent.VK_CONTROL)
				Ultimate();
		}
		
	}
	
	public static void main(String[] args) {
		
		SpiderGame g = new SpiderGame();

	}

}
