package store.wine.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import store.wine.model.OrderTools;
import store.wine.model.Product;
import store.wine.model.ProductTools;

/**
 * Classe respons�vel por mapear os servi�os REST da aplica��o
 * @author eduardo
 * @version 1.0
 */
@Path("/product")
public class ProductController {

	private ProductTools productTools = new ProductTools();
	private OrderTools orderTools = new OrderTools();

	/**
	 * Servi�o que retorna todos os produtos cadastrados
	 * @return Response - List Products
	 */
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response getAllProducts(){
		
		List<Product> products = productTools.getAllProducts();

		GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
		
		return Response.status( 200 ).entity(entity).build();
		
	}
	
	/**
	 * Servi�o que retorna um produto a partir de um id
	 * @param id - id do produto a ser retornado
	 * @return Response - Product
	 */
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response getProductById(@PathParam("id") Integer id){
		
		Product product = productTools.getProductById(id);
		
		if(product != null){
			return Response.status( 200 ).entity(product).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	/**
	 * Servi�o que cria um produto
	 * @param product - objeto com o produto a ser criado
	 * @return Response - Product
	 */
	@POST
	@Path("/")
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response createProduct(Product product){
	 
	    boolean created = productTools.createProduct(product);
	    
	    if(created){
	    	return Response.status( 200 ).entity(product).build();
	    }else{
			return Response.status(Status.NOT_FOUND).build();
	    }
	}
	}
