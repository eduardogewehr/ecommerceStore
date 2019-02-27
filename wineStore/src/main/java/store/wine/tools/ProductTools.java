package store.wine.tools;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import store.wine.model.EntityManagerUtil;
import store.wine.model.Product;

/**
 * Classe que contém os métodos de consulta e inserção de produtos na aplicação
 * @author eduardo
 * @version 1.0
 */
public class ProductTools {
	
	/**
	 * Método responsável por retornar todos os produtos cadastrados na aplicação
	 * @return List - Lista de produtos
	 */
	public List<Product> getAllProducts (){
		
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		List<Product> products = null;
		
		try{
			Query query = em.createQuery("FROM Product ORDER BY id");
			products = query.getResultList();
		}catch (Exception e) {
			System.err.println("erro ao obter produtos: "+ e.toString());
		}finally{
			em.close();
		}

		return products;
	}
	
	/**
	 * Método responsável por retornar um produto a partir do id informado
	 * @param id - id do produto a ser retornado
	 * @return Product - Produto
	 */
	public Product getProductById(Integer id){
		
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		Product product = null;
		
		try{
			product = em.find(Product.class, id);
		}catch (Exception e) {
			System.err.println("erro ao obter produto: "+ e.toString());
		}finally{
			em.close();
		}

		return product;
	}
	
	/**
	 * Método responsável por criar um produto na aplicação
	 * @param product - objeto com as informações do produto a ser criado
	 * @return Product - Produto
	 */
	public boolean createProduct(Product product){
		
		if(product == null){
			return false;
		}
		
		EntityManager em = EntityManagerUtil.getEntityManager();
	
		try{
			em.getTransaction().begin();
			em.persist(product);
			em.getTransaction().commit();
		}catch (Exception e) {
			System.err.println("erro ao inserir product no banco: "+ e.toString());
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
