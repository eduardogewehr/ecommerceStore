package store.wine.model.dto;

/**
 * Classe que recebe os dados do serviço REST de inserir e remover item do pedido
 * @author eduardo
 * @version 1.0
 *
 */
public class ItemDTO {

	private Integer orderId;
	private Integer productId;
	private Integer quantity;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
