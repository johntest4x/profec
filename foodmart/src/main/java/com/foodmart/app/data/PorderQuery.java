package com.foodmart.app.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.foodmart.app.data.Entity.Porder;
import com.foodmart.app.data.Entity.Product;




public class PorderQuery extends Query implements IQuery{



	
    private Long id;
    private Long qty;
    private BigDecimal price;
    private String profOrLoss;
    private String[] category;
    private Date odate;
    private String odatetxt;
    private String priceOper;

    
    
    
    
	

	public PorderQuery(Long id,IQuery query,Long qty, String priceOper, BigDecimal price,String[] category, Date odate,String odatetxt) {
		super();
		this.id = id;
		this.setQty(qty);
		this.setPrice(price);
		this.setCategory(category);
		this.setOdate(odate);
		this.setOdatetxt(odatetxt);
		this.setPriceOper(priceOper);
		this.setQuery(query);

	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String[] getCategory() {

		if(category==null)
			return category;
		
		String[] copy = new String[this.category.length];
		System.arraycopy(this.category, 0, copy, 0, copy.length);
		
		return copy;
	}
	public void setCategory(String[] category) {		
		
		 if(category==null) {
				this.category=null;
			}else {
				this.category = new String[category.length];
				System.arraycopy(category, 0, this.category, 0, category.length);
			}
		
		this.category = category;
	}
	public Date getOdate() {
		return odate;
	}
	public void setOdate(Date odate) {
		this.odate = odate;
	}
	public String getOdatetxt() {
		return odatetxt;
	}
	public void setOdatetxt(String odatetxt) {
		this.odatetxt = odatetxt;
	}
//	public List<Product> getProduct() {
//		return super.getQProduct();    
//	}
//	public void setProduct(List<Product> product) {
//		super.setQProduct(product);
//	}
	public String getPriceOper() {
		return priceOper;
	}

	public void setPriceOper(String priceOper) {
		
		if(priceOper.trim().toLowerCase().equals("equals"))
			this.priceOper = this.getPriceoperequal();
		if(priceOper.trim().toLowerCase().equals("greaterthan"))
			this.priceOper = this.getPriceopergreaterthan();
		if(priceOper.trim().toLowerCase().equals("lessthan"))
			this.priceOper = this.getPriceoperlessthan();
		
	}
	
	
	
	
    public String getProfOrLoss() {
		return profOrLoss;
	}

	public void setProfOrLoss(String profOrLoss) {
		
		
		if(profOrLoss.trim().equalsIgnoreCase("profit")){
			this.profOrLoss = this.getProfit();
		}else if(profOrLoss.trim().equalsIgnoreCase("loss")){
			this.profOrLoss = this.getLoss();
		}else {
			
		}
			
		
	}

	
	
	
	//parent------------------------------------
	

	public static String getQPriceoperequal() {
		return getPriceoperequal();
	}
    public static String getQPriceopergreaterthan() {
		return getPriceopergreaterthan();
	}
    public static String getQPriceoperlessthan() {
		return getPriceoperlessthan();
	}
	
	
	
	public IQuery getQuery() {
		return super.getQuery();
	}
	public void setQuery(IQuery query) {
		super.setQuery(query);
	}    
	public List<Porder> getPorders() {
		return super.getPorders();    
	}	
	public void setPorders(List<Porder> porders){
		super.setPorders(porders);
	}

	

    
    
    
    
    
    
	
	
}
