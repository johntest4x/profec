package com.foodmart.app.data.Entity;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.foodmart.app.data.DAO.IEntity;



@Entity
@Table(name = "ptype")
public class Ptype extends BaseType implements java.io.Serializable,IEntity{
    
	private static final long serialVersionUID = 1L; 
    private Long id;
    private String name;
    private String category;
    
	public Ptype() {
	}
    
    
	public Ptype(String name, String category, Product product) {
		super();
		this.name = name;
		this.category = category;
		this.product = product;
	}
	public Ptype(String name, String category) {
		super();
		this.name = name;
		this.category = category;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ptype_id", unique = true, nullable = false)
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(name = "name",nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "category",nullable = false)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	
//	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name = "product_id",unique = true)
//	private Product product;
//	public Product getProduct() {
//		return this.product;
//	}
//	public void setProduct(Product product) {
//		this.product = product;
//	}
	

    @JoinColumn(name = "product_id",unique = true, nullable = false,referencedColumnName = "product_id")
	private Product product;
	public Product getProduct() {
		return this.product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
	
	
	
	
	@Override
	@Transient 
	public String getCode() {
		// TODO Auto-generated method stub
		return ""+this.getId();
	}
	
	
}