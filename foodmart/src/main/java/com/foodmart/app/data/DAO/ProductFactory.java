package com.foodmart.app.data.DAO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foodmart.app.data.IQuery;
import com.foodmart.app.data.PorderQuery;
import com.foodmart.app.data.ProductQuery;
import com.foodmart.app.data.PtypeQuery;
import com.foodmart.app.data.Entity.Porder;
import com.foodmart.app.data.Entity.Product;
import com.foodmart.app.data.Entity.Ptype;


@Component
public class ProductFactory {

	
	@Autowired 
	private  ProductDAO productDAO;
	@Autowired 
	private PtypeDAO ptypeDAO;
	
	
	public  Product createProduct(String code,Ptype ptype){

		Product p =productDAO.createProduct(code,ptype);
				
		return p;
	}
	
	
	public  Ptype createProductType(String name,String category){

		
		Ptype p =ptypeDAO.createPtype(name, category);
		

		
		return p;
	}
	
	
	
	
	
	
	public void findProducts(ProductQuery q){

		List<Ptype> lpt=null;
		PtypeQuery ptq = (PtypeQuery)q.getQuery();
		if(ptq!=null)
			lpt = ptq.getPtypes();
		
		
		String [] codes = q.getCode();

		q.setProducts(productDAO.findProduct(codes,lpt));  
		
	}
	
	
	public void findPtypes(PtypeQuery q){


		q.setPtypes(ptypeDAO.findPtype(q.getId(), q.getNames(),q.getCategories()));
		
	}
	
	
	
	
	
}
