package com.foodmart.app.data.raw;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.foodmart.app.data.Entity.Porder;
import com.foodmart.app.data.Entity.Product;
import com.foodmart.app.data.Entity.Ptype;

public class Testsetup {
	

	
	
	
	public String testA(EntityManager em) {
		//-------------------------------------------------------------- setup prod types
		
		Ptype t1a = new Ptype();             //Milks
		t1a.setName("LF_Milk");
		t1a.setCategory("Dairy");
		em.persist(t1a);
		
		Ptype t1b = new Ptype();
		t1b.setName("FC_Milk");
		t1b.setCategory("Dairy");
		em.persist(t1b);
		
		Ptype t1c = new Ptype();
		t1c.setName("LL_Milk");
		t1c.setCategory("Dairy");
		em.persist(t1c);
		//----------------------------------------
		Ptype t2 = new Ptype();               //creams
		t2.setName("Cream");                        //Dairy
		t2.setCategory("Dairy");                 
		em.persist(t2);

		Ptype t3 = new Ptype();                     //Confectionary
		t3.setName("Cream");
		t3.setCategory("Confectionary");
		em.persist(t3);
        //-------------------------------------------
		Ptype t4 = new Ptype();               //Apple
		t4.setName("Apple");                        //Fruit
		t4.setCategory("Fruit");
		em.persist(t4);

		Ptype t5 = new Ptype();               //Orange
		t5.setName("Orange");                        //Fruit
		t5.setCategory("Fruit");
		em.persist(t5);
		//-----------------------------------------------------------------create products using the types
		String l = "100";
		Product p1a = new Product();
		l = "100";
		p1a.setCode(l);                          //product code 100 ---->>(LF_Milk/Dairy)
		p1a.setPtype(t1a);
		em.persist(p1a);

		t1a.setProduct(p1a);   //----test set ptype as product (reverse) OK 
		em.persist(t1a);

		
		Product p1b = new Product();
		l = "100-FC";
		p1b.setCode(l);                          //product code 100-FC ---->>(FC_Milk/Dairy)
		p1b.setPtype(t1b);
		em.persist(p1b);
		
		Product p1c = new Product();
		l = "100-LL";
		p1c.setCode(l);                          //product code 100-LL ---->>(LL_Milk/Dairy)
		p1c.setPtype(t1c);
		em.persist(p1c);
		
		
		
		
		
		
		Product p2 = new Product();
		l = "101";
		p2.setCode(l);                           //product code 101 ---->>(Cream/Dairy)
		p2.setPtype(t2);
		em.persist(p2);

		Product p3 = new Product();
		l = "102";
		p3.setCode(l);                           //product code 102 ---->>(Cream/Confectionary)
		p3.setPtype(t3);
		em.persist(p3);

		Product p4 = new Product();
		l = "103";
		p4.setCode(l);                           //product code 103 ---->>(Apple/Fruit)  
		p4.setPtype(t4);
		em.persist(p4);

		Product p5 = new Product();              //product code 104 ---->>(Orange/Fruit) 
		l = "104";
		p5.setCode(l);
		p5.setPtype(t5);
		em.persist(p5);
		
											//--------------products -use same prod types but using (reader friendly codes) 
		
		Product p6 = new Product();              //product code WOWMILK_LF1 ---->>(LF_Milk/Dairy)
		l = "WOWMILK_LF1";
		p6.setCode(l);
		p6.setPtype(t1a);
		em.persist(p6);
		
		
		Product p7 = new Product();              //product code ALDImilk3L ---->>(FC_Milk/Dairy)
		String m = "ALDImilk3L";
		p7.setCode(m);
		p7.setPtype(t1b);
		em.persist(p7);
		
		Product p8 = new Product();              //product code COL-milkLL402 ---->>(LL_Milk/Dairy)
		m = "COL-milkLL402";
		p7.setCode(m);
		p7.setPtype(t1c);
		em.persist(p7);
		
		
		
		//--------------------------------------------------------------------------create orders
		Porder o1 = new Porder();
		o1.setCategory("BUY" );
		o1.setPrice(new BigDecimal((long) 50.00));
		o1.setQty((long) 300);
		o1.setOdate(setDateAsAppStyle("02-04-2020 11:35:00"));
		o1.setProduct(p1a);
		em.persist(o1);
		
		Porder o2 = new Porder();
		o2.setCategory("SOLD" );
		o2.setPrice(new BigDecimal((long) 60.00));
		o2.setQty((long) 100);
		o2.setOdate(setDateAsAppStyle("04-04-2020 11:35:00"));
		o2.setProduct(p1a);
		em.persist(o2);
		
		Porder o3 = new Porder();
		o3.setCategory("SOLD" );
		o3.setPrice(new BigDecimal((long) 60.00));
		o3.setQty((long) 100);
		o3.setOdate(setDateAsAppStyle("05-04-2020 11:35:00"));
		o3.setProduct(p1a);
		em.persist(o3);
		
		
		//---------------------------------------------------------------------------
		
		Porder o4 = new Porder();
		o4.setCategory("BUY" );
		o4.setPrice(new BigDecimal((long) 50.00));
		o4.setQty((long) 300);
		o4.setOdate(setDateAsAppStyle("02-04-2020 11:35:00"));
		o4.setProduct(p1b);
		em.persist(o4);
		
		Porder o5 = new Porder();
		o5.setCategory("SELL" );
		o5.setPrice(new BigDecimal((long) 50.00));
		o5.setQty((long) 300);
		o5.setOdate(setDateAsAppStyle("02-04-2020 11:35:00"));
		o5.setProduct(p1c);
		em.persist(o5);
		
		
		
		
		
		

		return "Sample Data Added";

	}
	
	
	
	/* 
	 * Accepts:
	 * Style "02-04-2013 11:35:42"  ===>>> 
	 * Produces:
	 * "02-04-2013 11:35:00"
	 * 
	 * 
	 *   Returns null on bad 
	 * 
	 */
	private Date setDateAsAppStyle(String date) {
		
		 SimpleDateFormat dateformat2 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		 //String strdate2 = "02-04-2013 11:35:42";
		 Date newdate=null;
		 try {
		 newdate = dateformat2.parse(date);

		 } catch (ParseException e) {
			 
		 //date returnd null if incorrect format entered	 
		 e.printStackTrace();
		 
		 }
		return newdate;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
