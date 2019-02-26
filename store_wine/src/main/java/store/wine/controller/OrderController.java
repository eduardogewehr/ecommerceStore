package store.wine.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import store.wine.model.dto.FreigthDTO;
import store.wine.model.Item;
import store.wine.model.dto.ItemDTO;
import store.wine.model.Order;
import store.wine.model.dto.OrderStateDTO;
import store.wine.tools.OrderTools;
import store.wine.model.Product;
import store.wine.tools.ProductTools;

/**
 * Classe responsável por mapear os serviços REST da aplicação
 * @author eduardo
 * @version 1.0
 */
@Path("/order")
public class OrderController {

	private ProductTools productTools = new ProductTools();
	private OrderTools orderTools = new OrderTools();
	
	/**
	 * Serviço que cria um novo pedido
	 * @param order - objeto com o o pedido a ser criado
	 * @return Response - OrderStore
	 */
	@POST
	@Path("/")
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response createOrder(Order order){
	 
	    boolean created = orderTools.createOrder(order);
	    
	    if(created){
	    	return Response.status( 200 ).entity(order).build();
	    }else{
			return Response.status(Status.NOT_FOUND).build();
	    }
	}
	
	/**
	 * Serviço que retorna um pedido a partir de um id
	 * @param id - id do pedido a ser retornado
	 * @return Response - OrderStore
	 */
	@GET
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response getOrderById(@PathParam("id") Integer id){
		
		Order order = orderTools.getOrderById(id);
		
		if(order != null){
			return Response.status( 200 ).entity(order).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	/**
	 * Serviço que adiciona um item no pedido
	 * @param orderProduct - objeto com as informações do pedido e item a ser adicionado
	 * @return Response - OrderProduct
	 */
	@POST
	@Path("/item")
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response addItemToOrder(ItemDTO itemDTO){
		
		Order order = orderTools.getOrderById(itemDTO.getOrderId());
		
		Product product = productTools.getProductById(itemDTO.getProductId());
		
		if(order == null || product == null){
			return Response.status(Status.NOT_FOUND).build();
		}
		
		boolean isCreateItem = true;
		
		Item item = new Item();

		for (Item itemOrder : order.getItems()) {
			if(itemOrder.getProduct().getProductId().equals(itemDTO.getProductId())){
				item = orderTools.getItemById(itemOrder.getItemId());
				isCreateItem = false;
			}
		}
		
		if(isCreateItem){
			item.setOrder(order);
			item.setProduct(product);		
		}
		
		item.setQuantity(itemDTO.getQuantity());
		item.setUnitPrice(product.getPrice());
		item.setTotalPrice(itemDTO.getQuantity()*product.getPrice());
		
	    boolean createdOrUpdated = false;
	    
	    if(isCreateItem){
	    	createdOrUpdated = orderTools.createItemOrder(item);
	    }else{
	    	createdOrUpdated = orderTools.updateItemOrder(item);
	    }
	    
	    if(createdOrUpdated){
	    	return Response.status( 200 ).entity(item).build();
	    }else{
			return Response.status(Status.NOT_FOUND).build();
	    }
	}
	
	/**
	 * Serviço que retorna o pedido atual
	 * @return Response - OrderStore
	 */
	@GET
	@Path("/current/")
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response getCurrentOrder(){
	 
		Order order = orderTools.getCurrentOrder();
		
		if(order != null){
			return Response.status( 200 ).entity(order).build();
		}else{
			
			order = new Order();
			
			order.setState("new");
			order.setTotalProductsValue(0D);
			order.setFreightValue(0D);
			order.setDistance(0D);
			
			boolean created = orderTools.createOrder(order);
			
			if(created){
				return Response.status( 200 ).entity(order).build();
			}else{
				return Response.status(Status.NOT_FOUND).build();
			}	
		}
	}
	
	/**
	 * Serviço que calcula o frete de um pedido
	 * @param freight - objeto com as informações de distancia e pedido a ter o frete calculado
	 * @return Response - OrderProduct
	 */
	@POST
	@Path("/freight")
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response calculateFreight(FreigthDTO freight){
		
		Double freightValue = orderTools.calculateFreight(freight);
		
		if(freightValue == null){
			return Response.status(Status.NOT_FOUND).build();
		}else{
			
			Order order = orderTools.getOrderById(freight.getOrderId());
			
			if(order != null){				
				
				order.setFreightValue(freightValue);
				order.setDistance(freight.getDistance());
				
				boolean updated = orderTools.updateOrder(order);
				
				if(updated){
					return Response.status( 200 ).entity(freightValue).build();
				}
			}
			
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
	
	/**
	 * Serviço que calcula o frete de um pedido
	 * @param freight - objeto com as informações de distancia e pedido a ter o frete calculado
	 * @return Response - OrderProduct
	 */
	@PUT
	@Path("/update")
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response updateOrderStatus(OrderStateDTO orderStatus){
		
		Order order = orderTools.getOrderById(orderStatus.getOrderId());
		    
	    if(order == null){
	    	return Response.status(Status.NOT_FOUND).build();
	    }
	    
	    order.setState(orderStatus.getState());
	    
	    boolean updated = orderTools.updateOrder(order);
		
		if(updated){
			return Response.status( 200 ).entity(order).build();
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
	
	/**
	 * Serviço que remove um item do pedido
	 * @param id - id do item a ser removido
	 * @return Response - OrderProduct
	 */
	@DELETE
	@Path("/item/{id}")
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response deleteItemOrder(@PathParam("id") Integer id){
		
		boolean removed = orderTools.deleteItemOrder(id);
		
		if(removed){
			return Response.status( 200 ).build();
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
	
	/**
	 * Serviço que retorna o último pedido finalizado
	 * @return Response - OrderStore
	 */
	@GET
	@Path("/last/")
	@Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response getLastFinishOrder(){
	 
		Order order = orderTools.getLastFinishOrder();
		
		if(order != null){
			return Response.status( 200 ).entity(order).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
