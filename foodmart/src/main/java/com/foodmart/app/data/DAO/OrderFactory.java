package com.foodmart.app.data.DAO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foodmart.app.data.DateUtility;
import com.foodmart.app.data.IQuery;
import com.foodmart.app.data.PorderQuery;
import com.foodmart.app.data.ProductQuery;
import com.foodmart.app.data.Entity.CustomerOrder;
import com.foodmart.app.data.Entity.Porder;
import com.foodmart.app.data.Entity.Product;



@Component
public  class OrderFactory {
	
	@Autowired 
	private  PorderDAO porderDAO;
	@Autowired
	private DateUtility du;

	
		/*
		 *  TODO:
		 * 
		 */
		public CustomerOrder createCustomerOrder() {
			
			
			return null;
		}
		

		
		
		/*
		 *  
		 *  Returns Porder object, (pass it around - but has restricted access!)
		 * 
		 */
		public  Porder createPorder(Product product,String category,String odateTxt,BigDecimal price,Long quantity){


			
			if(product==null || category==null || odateTxt ==null || price==null ||quantity ==null )
			    return null;
			
			Porder p =porderDAO.createPorder(product, category,du.getFormatedDTStr(odateTxt), du.strDatetoDate(odateTxt,"EE MMM dd yyyy HH:mm:ss zz"), price, quantity);
			
			
			return p;
		}
	

		//TODO:
		
		/*
		 *   Generic find, any fields
		 * 
		 *   TODO: query object!
		 * 
		 */
		public void findPOrders(PorderQuery q){
			
			
			List<Product> p = null;
			 //from orderquery get prod query
			ProductQuery pq=(ProductQuery)q.getQuery();
			if(pq!=null)
				 p= pq.getProducts();   //if(p!=null) {System.out.println("FOUND PROD"+p.size()+p.get(0));}else {System.out.println("====>>>>NOT FOUND PROD");}
			
			BigDecimal price =q.getPrice();
			String [] category = q.getCategory();
			Long quantity = q.getQty();
			Date date = q.getOdate();
			String odateTxt = q.getOdatetxt();
			Long id = q.getId();
			String priceOper=q.getPriceOper();
			

			q.setPorders(porderDAO.findPorder(p,  category,  odateTxt,  date,  price, priceOper,  quantity));

		}
		
		
	

}
