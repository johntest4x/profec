package com.foodmart.app.data;

import java.util.List;

import com.foodmart.app.data.Entity.Product;
import com.foodmart.app.data.Entity.Ptype;

public class ProductQuery extends Query implements IQuery{

    private Long id;                                         //integer
    private String [] code;
    

	public ProductQuery(Long id, IQuery query, String [] code) {
		super();
		this.id = id;
		this.code = code;
		this.setQuery(query);
	}
	

	public ProductQuery() {
		// TODO Auto-generated constructor stub
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String [] getCode() {
		
		
		if(code==null)
			return code;
		
		String[] copy = new String[this.code.length];
		System.arraycopy(this.code, 0, copy, 0, copy.length);
		
		return copy;
	}
	public void setCode(String[] code) {

		    if(code==null) {
				this.code=null;
			}else {
				this.code = new String[code.length];
				System.arraycopy(code, 0, this.code, 0, code.length);
			}
		    
	}
	
	
	
	//parent------------------------

		public IQuery getQuery() {
			return super.getQuery();
		}
		public void setQuery(IQuery query) {
			super.setQuery(query);
		}    
		public List<Product> getProducts() {
			return super.getProducts();    
		}	
		public void setProducts(List<Product> products){
			super.setProducts(products);
		}
	
	
}
