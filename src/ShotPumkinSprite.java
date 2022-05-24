import java.awt.Image;

//적의 총알  
public class ShotPumkinSprite extends Sprite{
	
	DoctorOctopus obj;
	ShotPumkinSprite(DoctorOctopus obj,Image image){
		super(image,obj.x,obj.y);
		dx=-3;  

	}
	
}
