package DTO;

public class WorkTimeDTO extends RouteDTO{

	private Double allDistanceSum;
	
	public WorkTimeDTO(){
		
	}

	public Double getAllDistanceSum() {
		return allDistanceSum;
	}

	public void setAllDistanceSum(Double allDistanceSum) {
		this.allDistanceSum = allDistanceSum;
	}
	
}
