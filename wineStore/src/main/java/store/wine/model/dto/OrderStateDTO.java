package store.wine.model.dto;

/**
 * Classe que recebe os dados do serviço REST de atualizar status do pedido
 * @author eduardo
 * @version 1.0
 *
 */
public class OrderStateDTO {

	private Integer orderId;
	private String state;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
