package com.foodmart.app.data;

import java.math.BigDecimal;
import java.util.List;

import com.foodmart.app.data.Entity.Porder;
import com.foodmart.app.data.Entity.Ptype;

public class PtypeQuery extends Query implements IQuery{

    private Long id;
    private String [] names;
    private String [] categories;
    

    
    
    
	public PtypeQuery(Long id,IQuery query, String [] names, String [] categories) {
		super();
		this.setId(id);
		this.setNames(names);
		this.setCategories(categories);
		this.setQuery(query);		
	}
	
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String[] getNames() {
		
		if(names==null)
			return names;
		
		 String[] copy = new String[this.names.length];
		 System.arraycopy(this.names, 0, copy, 0, copy.length);
		    return copy;
		
		//return names;
	}
	public void setNames(String[] name) {
		
		if(name==null) {
			this.names=null;
		}else {
	    this.names = new String[name.length];
	    System.arraycopy(name, 0, this.names, 0, name.length);
		}
		//this.names = names;
	}
	public String[] getCategories() {

		if(categories==null)
			return categories;
		
		 String[] copy = new String[this.categories.length];
		 System.arraycopy(this.categories, 0, copy, 0, copy.length);
		    return copy;
		
	}
	public void setCategories(String [] categories) {
		if(categories==null) {
			this.categories=null;
		}else {
		this.categories = new String[categories.length];
	    System.arraycopy(categories, 0, this.categories, 0, categories.length);
		}
		
		//this.categories = categories;
	}



	//parent------------------------

	public IQuery getQuery() {
		return super.getQuery();
	}
	public void setQuery(IQuery query) {
		super.setQuery(query);
	}    
	public List<Ptype> getPtypes() {
		return super.getPtypes();    
	}	
	public void setPtypes(List<Ptype> ptypes){
		super.setPtypes(ptypes);
	}
    
    
	
	
}
