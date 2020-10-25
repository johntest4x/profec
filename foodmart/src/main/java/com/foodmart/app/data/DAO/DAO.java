package com.foodmart.app.data.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.foodmart.app.data.Entity.Ptype;


/*
 * 
 * 		A product type can be a product
 * 
 * 
 * 
 *    Data modification:
 *    
 *    -create product
 *           -add product (product can exist without a type).
 *           -get a product type to add to the product
 *           
 *    -create 'product type'
 *    		 -optional: set it as a product
 *    
 *    -create order
 *           -link order to product
 *           
 *           
 *           
 *    -delete product       
 *    -delete 'product type'
 *    
 *    -modify product
 *    -modify 'product type'       
 *           
 *           
 * 
 * 
 *    Data query:
 * 
 * 
 * 
 * 
 * 
 *    Data integrity:
 *    		-check no duplicate product type: test unique 'name and category'
 * 
 * 
 * 
 */

@Transactional
@Component
public  class DAO {

	@PersistenceContext
	private EntityManager em;	
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////

//	
//	public List<Ptype> getAllPtype(){
//		
//		Query query2 = em.createQuery("Select t.ptype from Product t"    ); 
//		
//	return query2.getResultList();   
//	}
//	
//	
//	public List<Ptype> findPtype(String ptype, String category){
//		System.out.print("xxxxxxxxxxxxxxxinside"+ptype+category);
//		Query query2 = em.createQuery(	"Select t.ptype from Product t where t.ptype.name = :name and t.ptype.category= :category"    ); 
//		query2.setParameter("name",""+ptype);
//		query2.setParameter("category",""+category);
//		
//		List<Ptype> lptype;
//		if(query2.getResultList()!=null)
//			lptype =query2.getResultList();   
//		else
//			lptype = new ArrayList<Ptype>();
//		
//	    return lptype;
//	}
//	
//	
//	
//	
//	
//	public List<Ptype> findPOrders(String ptype, String category){
//		System.out.print("xxxxxxxxxxxxxxxinside"+ptype+category);
//		Query query2 = em.createQuery(	"Select t.ptype from Product t where t.ptype.name = :name and t.ptype.category= :category"    ); 
//		query2.setParameter("name",""+ptype);
//		query2.setParameter("category",""+category);
//		
//		List<Ptype> lptype;
//		if(query2.getResultList()!=null)
//			lptype =query2.getResultList();   
//		else
//			lptype = new ArrayList<Ptype>();
//		
//	    return lptype;
//	}

	
	
	
	
}
