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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.foodmart.app.data.DAO.IEntity;


@Entity
@Table(name = "product")
public class Product implements java.io.Serializable,IEntity{
    
	private static final long serialVersionUID = 1L; 
    private Long id;                                            //Integer
    private String code;
    
    
	public Product() {
	}
    
    
	public Product(String code, Ptype ptype) {
		super();
		this.code = code;
		this.ptype = ptype;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", unique = true, nullable = false)
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(name = "code",unique = true, nullable = false)
	public String getCode() {
		return code;
	} 

	public void setCode(String code) {
		this.code = code;
	}
	
	

	
//	@JoinColumn(name="ptype_id",nullable = false)
//	public Ptype getPtype() {
//		return this.ptype;
//	}
//
//	public void setPtype(Ptype ptype) {
//		this.ptype = ptype;
//	}
//	private Ptype ptype;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "ptype_id",unique = true,referencedColumnName = "ptype_id")
	private Ptype ptype;
	public Ptype getPtype() {
		return this.ptype;
	}

	public void setPtype(Ptype ptype) {
		this.ptype = ptype;
	}


	
	
	
	@Override
	@Transient 
	public String getName() {
		// TODO Auto-generated method stub
		return this.ptype.getName();
	}


	@Override
	@Transient 
	public String getCategory() {
		// TODO Auto-generated method stub
		return this.ptype.getCategory();
	}
	
	

	
	
	
	
	


}