package store.wine.model.dto;

/**
 * Classe que recebe os dados do serviço REST de calcular frete
 * @author eduardo
 * @version 1.0
 *
 */
public class FreigthDTO {
	
	private Integer orderId;
	private Double distance;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}

}
