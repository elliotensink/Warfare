package warfare;

public class PointCard extends Card{
	
	private int point;
	
	public PointCard(String name, int cost, String description, int point){
		super(name, cost, description);
		this.point = point;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	} 
}
