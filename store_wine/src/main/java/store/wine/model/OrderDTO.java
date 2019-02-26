package store.wine.model;

import java.util.List;

public class OrderDTO extends Order{

	private List<ProductDTO> products;

	public List<ProductDTO> getProductS() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	
}
