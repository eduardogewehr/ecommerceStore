package store.wine.tools;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import store.wine.model.EntityManagerUtil;
import store.wine.model.dto.FreigthDTO;
import store.wine.model.Item;
import store.wine.model.Order;

/**
 * Classe que cont�m os m�todos de consulta e inser��o de pedidos na aplica��o
 * @author eduardo
 * @version 1.0
 *
 */
public class OrderTools {
	
	/**
	 * M�todo respons�vel por retornar um pedido a partir do id informado
	 * @param id - id do pedido a ser retornado
	 * @return Order - Pedido
	 */
	public Order getOrderById(Integer id){
		
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		Order order = null;
		
		try{
			order = em.find(Order.class, id);
		}catch (Exception e) {
			System.err.println("erro ao obter order: "+ e.toString());
		}finally{
			em.close();
		}

		return order;
	}
	
	/**
	 * M�todo respons�vel por retornar o pedido atual - 
	 * OBS (Como n�o existe controle de pedidos por usu�rio, o m�todo retornar� o �ltimo pedido criado no banco, que ser� o pedido atual a ser fechado
	 * @return Order - Pedido
	 */
	public Order getCurrentOrder(){
		
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		Order order = null;
		
		try{
			Query query = em.createNativeQuery("SELECT order_id  FROM order_store WHERE state = 'new' ORDER BY order_id DESC LIMIT 1");
			
			Integer id = (Integer) query.getSingleResult();
			
			if(id != null){
				order = getOrderById(id);
			}
			
		}catch (Exception e) {
			System.err.println("erro ao obter order: "+ e.toString());
		}finally{
			em.close();
		}

		return order;
	
	}
	
	/**
	 * M�todo respons�vel por o �ltimo pedido finalizado 
	 * OBS (Como n�o existe controle de pedidos por usu�rio, o m�todo retornar� o �ltimo pedido criado no banco, que ser� o pedido atual a ser fechado
	 * @return Order - Pedido
	 */
	public Order getLastFinishOrder(){
		
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		Order order = null;
		
		try{
			Query query = em.createNativeQuery("SELECT order_id  FROM order_store WHERE state = 'finished' ORDER BY order_id DESC LIMIT 1");
			
			Integer id = (Integer) query.getSingleResult();
			
			if(id != null){
				order = getOrderById(id);
			}
			
		}catch (Exception e) {
			System.err.println("erro ao obter order: "+ e.toString());
		}finally{
			em.close();
		}

		return order;
	
	}
		
	/**
	 * M�todo respons�vel por criar um pedido
	 * @param order - objeto com o o pedido a ser criado 
	 * @return Order - Pedido
	 */
	public boolean createOrder(Order order){
		
		if(order == null){
			return false;
		}
		
		EntityManager em = EntityManagerUtil.getEntityManager();
	
		try{
			em.getTransaction().begin();
			em.persist(order);
			em.getTransaction().commit();
		}catch (Exception e) {
			System.err.println("erro ao inserir order no banco: "+ e.toString());
			try{
				em.getTransaction().rollback();
			}catch (Exception ex) {
				System.err.println("erro ao dar roolback: "+ ex.toString());
			}
			return false;
		}finally{
			em.close();
		}
		
		return true;
	}
	
	/**
	 * M�todo respons�vel por adicionar um produto no pedido
	 * @param orderProduct - objeto com as informa��es do pedido e produto a ser adicionado
	 * @return Order 
	 */
	public boolean createItemOrder(Item item){
		
		if(item == null){
			return false;
		}

		EntityManager em = EntityManagerUtil.getEntityManager();

		try{
			em.getTransaction().begin();
			em.persist(item);
			em.getTransaction().commit();
			
			Order order = item.getOrder();
			
			Double orderTotal = 0D;
			
			for (Item itemF : order.getItems()) {
				orderTotal = orderTotal + itemF.getTotalPrice();
			}
			
			orderTotal = orderTotal + item.getTotalPrice();
			
			order.setTotalProductsValue(orderTotal);
			
			Double weightTotal = 0D;
			Double freightValue = 0D;
			
			if(order.getDistance() > 0){
				
				for (Item itemF : order.getItems()) {
					weightTotal = weightTotal + (itemF.getProduct().getWeight() * itemF.getQuantity());
				}
				
				weightTotal = weightTotal + (item.getProduct().getWeight() * item.getQuantity());
				
				if(order.getDistance() > 100){
					freightValue = (weightTotal * order.getDistance())/100;
				}else{
					freightValue = weightTotal * 5;
				}
				
			}
			
			
			order.setFreightValue(freightValue);
			
			updateOrder(order);
			
		}catch (Exception e) {
			System.err.println("erro ao inserir item no banco: "+ e.toString());
			try{
				em.getTransaction().rollback();
			}catch (Exception ex) {
				System.err.println("erro ao dar roolback: "+ ex.toString());
			}
			return false;
		}finally{
			em.close();
		}
		
		return true;
	}
	
	/**
	 * M�todo respons�vel por atualizar um pedido
	 * @param order - objeto com as informa��es do pedido a ser atualizado
	 * @return Order 
	 */
	public boolean updateOrder(Order order){
		
		if(order == null){
			return false;
		}
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			
			em.getTransaction().begin();
			em.merge(order);
			em.getTransaction().commit();
		}catch (Exception e) {
			
			System.err.println("erro ao alterar pedido no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		return true;
	}
	
	
	/**
	 * M�todo respons�vel calcular o frete do pedido
	 * @param freight - objeto com as informa��es do pedido e frete
	 * @return Double - valor do frete 
	 */
	public Double calculateFreight(FreigthDTO freight){
		
		if(freight == null){
			return null;
		}
		
		Order order = getOrderById(freight.getOrderId());
		
		if(order == null){
			return null;
		}
		
		Double weightTotal = 0D;
		Double freightValue = 0D;
		
		if(freight.getDistance() > 0){
			
			for (Item item : order.getItems()) {
				weightTotal = weightTotal + (item.getProduct().getWeight() * item.getQuantity());
			}

			if(freight.getDistance() > 100){
				freightValue = (weightTotal * freight.getDistance())/100;
			}else{
				freightValue = weightTotal * 5;
			}
		}

		return freightValue;
	}
	
	/**
	 * M�todo respons�vel por atualizar um item do pedido
	 * @param item - objeto com as informa��es do item a ser atualizado
	 * @return boolean 
	 */
	public boolean updateItemOrder(Item item){
		
		if(item == null){
			return false;
		}
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(item);
			em.getTransaction().commit();
			
			Order order = item.getOrder();
			
			Double orderTotal = 0D;
			
			for (Item itemF : order.getItems()) {
				orderTotal = orderTotal + itemF.getTotalPrice();
			}

			order.setTotalProductsValue(orderTotal);
			
			Double weightTotal = 0D;
			Double freightValue = 0D;
			
			if(order.getDistance() > 0){
				
				for (Item itemF : order.getItems()) {
					weightTotal = weightTotal + (itemF.getProduct().getWeight() * itemF.getQuantity());
				}
				
				if(order.getDistance() > 100){
					freightValue = (weightTotal * order.getDistance())/100;
				}else{
					freightValue = weightTotal * 5;
				}
				
			}
						
			order.setFreightValue(freightValue);
			
			updateOrder(order);
			
		}catch (Exception e) {
			
			System.err.println("erro ao alterar item no banco: "+ e.toString());
			em.getTransaction().rollback();
			return false;
		}finally{
			em.close();
		}
		return true;
	}
	
	/**
	 * M�todo respons�vel por retornar um item do pedido a partir do id informado
	 * @param id - id do pedido a ser retornado
	 * @return Item - item
	 */
	public Item getItemById(Integer id){
		
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		Item item = null;
		
		try{
			item = em.find(Item.class, id);
		}catch (Exception e) {
			System.err.println("erro ao obter item: "+ e.toString());
		}finally{
			em.close();
		}

		return item;
	}

	/**
	 * M�todo respons�vel por remover um item do pedido
	 * @param id - id do item a ser removido
	 * @return boolean 
	 */
	public boolean deleteItemOrder(Integer id){
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			Item item = getItemById(id);
						
			if(item != null){
				em.getTransaction().begin();
				
				em.createQuery("DELETE FROM Item WHERE itemId = :itemId").setParameter("itemId", item.getItemId()).executeUpdate();

				em.getTransaction().commit();
				
				Order order = getOrderById(item.getOrder().getOrderId()); 
				
				Double orderTotal = 0D;
				
				for (Item itemF : order.getItems()) {
					orderTotal = orderTotal + itemF.getTotalPrice();
				}
					
				order.setTotalProductsValue(orderTotal);
			
				Double weightTotal = 0D;
				Double freightValue = 0D;
				
				if(order.getDistance() > 0){
					
					for (Item itemF : order.getItems()) {
						
						weightTotal = weightTotal + (itemF.getProduct().getWeight() * itemF.getQuantity());
					}
									
					if(order.getDistance() > 100){
						freightValue = (weightTotal * order.getDistance())/100;
					}else{
						freightValue = weightTotal * 5;
					}					
				}
				
				order.setFreightValue(freightValue);
				
				updateOrder(order);
			}
		}catch (Exception e) {
			System.err.println("erro ao excluir: "+ e.toString());
			try{
				em.getTransaction().rollback();
			}catch (Exception ex) {
				System.err.println("erro ao dar roolback: "+ ex.toString());
			}
			return false;
		}finally{
			em.close();
		}
		return true;
	}
}
