package com.foodmart.app.data.Entity;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Calendar;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.foodmart.app.data.DAO.IEntity;

@Entity
@Table(name = "porder")
public class Porder implements java.io.Serializable,IEntity{

	public enum movement {
	    BUY,
	    SOLD
	}
	
	
	private static final long serialVersionUID = 1L; 
    private Long id;
    private Long qty;
    private BigDecimal price;
    private String category;
    @Temporal(TemporalType.DATE)
    private Date odate;
    private String odatetxt;
    
	public Porder() {
	}
	public Porder(Product product,String category,String odateTxt,Date date,BigDecimal price,Long quantity) {
		
		this.product=product;
		this.category=category;
		this.price=price;
		this.odate=date;
		this.qty=quantity;
		this.odatetxt=odateTxt;	
		
	}
    
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "porder_id", unique = true, nullable = false)
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(name = "qty",unique = false, nullable = false)
	public Long getQty() {
		return qty;
	} 

	public void setQty(Long qty) {
		this.qty = qty;
	}
	
	@Column(name = "price",unique = false, nullable = false)
	public BigDecimal getPrice() {
		return price;
	} 

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Column(name = "category",unique = false, nullable = false)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	@Column(name = "odate",unique = false, nullable = false)
	public Date getOdate() {
		return odate;
	}

	public void setOdate(Date odate) {
		this.odate = odate;
	}
	
	@Column(name = "odatetxt",unique = false, nullable = false)
	public String getOdatetxt() {
		return odatetxt;
	}

	public void setOdatetxt(String odatetxt) {
		this.odatetxt = odatetxt;
	}
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="product_id",unique = false,nullable = false,referencedColumnName = "product_id")
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	private Product product;

	
	
	
	
	@Override
	@Transient 
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	@Transient 
	public String getCode() {
		// TODO Auto-generated method stub
		return null;
	}

 
	
	
	
	
}
