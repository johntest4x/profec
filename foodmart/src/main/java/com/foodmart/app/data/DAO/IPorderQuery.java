package com.foodmart.app.data.DAO;

import java.math.BigDecimal;
import java.util.Date;

import com.foodmart.app.data.Entity.Porder;
import com.foodmart.app.data.Entity.Product;

public interface IPorderQuery {



	void setCategory(String string);

	void setQty(long l);

	void setOdate(Date date);

	void setOdatetxt(String string);

	void setPrice(BigDecimal bigDecimal);
	
	void setPorder(Porder o1);
	
	Long setId(long l);

	BigDecimal getPrice();

	String getCategory();

	Long getQty();

	Date getOdate();

	String getOdatetxt();

	long getId();

    Product getProduct();


	
	
	
	
	
	
	
	
	
	
	

}
