package store.wine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="product")
@XmlRootElement(name="product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product implements Serializable{
	
	@Id
	@Column(name = "\"product_id\"")
	private Integer productId;
	
	@Column(name = "\"product_name\"")
	private String productName;
	
	@Column(name = "\"product_type\"")
	private String productType;
	
	private Double weight;
	private Double price;
	
	public Product() {}	
	
	public Product(Integer productId, String productName, String productType, Double weight, Double price) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productType = productType;
		this.weight = weight;
		this.price = price;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
