package store.wine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="item")
@XmlRootElement(name="item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
	
	@Id
	@SequenceGenerator(name="SEQ_ITEM", sequenceName="item_item_id_seq", allocationSize=1)
    @GeneratedValue(generator="SEQ_ITEM",strategy= GenerationType.SEQUENCE)
	@Column(name = "\"item_id\"")
	private Integer itemId;
	
	private Integer quantity;

	@Column(name = "\"unit_price\"")
	private Double unitPrice;

	@Column(name = "\"total_price\"")
	private Double totalPrice;
	
	@ManyToOne
	@JoinColumn(name="order_id", referencedColumnName="order_id", nullable=false)
	@JsonIgnore
	private Order order;
	
	@ManyToOne
	@JoinColumn(name="product_id", referencedColumnName="product_id", nullable=false)
	private Product product;

	
	public Integer getItemId() {
		return itemId;
	}
	
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
