package store.wine.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="order_store")
@XmlRootElement(name="orderStore")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order implements Serializable{
	
	@Id
	@SequenceGenerator(name="SEQ", sequenceName="order_store_order_id_seq", allocationSize=1)
    @GeneratedValue(generator="SEQ",strategy= GenerationType.SEQUENCE)
	@Column(name = "\"order_id\"")
	private Integer orderId;
	
	@Column(name = "\"freight_value\"", nullable = true)
	private Double freightValue;
	
	@Column(name = "\"total_products_value\"", nullable = true)
	private Double totalProductsValue;
	
	private String state;
	
	private Double distance;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
	private Set<Item> items;
	
	public Order() {}	
	
	public Order(Integer orderId, Double freightValue, Double totalProductsValue) {
		super();
		this.orderId = orderId;
		this.freightValue = freightValue;
		this.totalProductsValue = totalProductsValue;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Double getFreightValue() {
		return freightValue;
	}

	public void setFreightValue(Double freightValue) {
		this.freightValue = freightValue;
	}

	public Double getTotalProductsValue() {
		return totalProductsValue;
	}

	public void setTotalProductsValue(Double totalProductsValue) {
		this.totalProductsValue = totalProductsValue;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

}
