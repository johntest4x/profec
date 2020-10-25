package com.foodmart.app.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.foodmart.app.data.DAO.IPorderQuery;
import com.foodmart.app.data.Entity.Porder;
import com.foodmart.app.data.Entity.Product;
import com.foodmart.app.data.Entity.Ptype;

public  class Query {
	
    private List<Product> qproducts=null;    
	protected List<Product> getProducts() {
		return qproducts;
	}
	protected void setProducts(List<Product> products) {
		this.qproducts = products;
	}
	

	private List<Ptype> qptypes;
	protected List<Ptype> getPtypes() {
		return this.qptypes;
	}

	protected void setPtypes(List<Ptype> ptypes) {
		this.qptypes = ptypes;
	}

	
	private List<Porder> qporders;
	protected List<Porder> getPorders() {
		return this.qporders;
	}

	protected void setPorders(List<Porder> porders) {
		this.qporders = porders; 
	}
	
    
    private static final String priceOperEqual="EQUAL";
    private static final String priceOperGreaterThan="GREATERTHAN";
    private static final String priceOperLessThan="LESSTHAN";
	protected static String getPriceoperequal() {
		return priceOperEqual;
	}
	protected static String getPriceopergreaterthan() {
		return priceOperGreaterThan;
	}
	protected static String getPriceoperlessthan() {
		return priceOperLessThan;
	}
	
	
	//private static final String neutral="";
	private static final String profit="PROFIT";
	private static final String loss="LOSS";	
	protected static String getProfit() {
		return profit;
	}
	protected static String getLoss() {
		return loss;
	}

	
	////////////////////////////////////////////////////


	/*
	 *  Store copy of itself where list Porder,Ptype,Product are kept.
	 *  
	 *  Create a PtypeQuery, store 'list of found Ptypes as List<Ptype> qptype;, pass PTypeQuery as (IQuery object to new ProductQuery(IQuery...,...,)
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	private IQuery query;			
	protected IQuery getQuery() {
		return query;
	}
	protected void setQuery(IQuery query) {
		this.query = query;
	}
	
	
	
	
	
	
	

}
