package com.foodmart.app.data.raw;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.foodmart.app.data.Entity.Product;
import com.foodmart.app.data.Entity.Ptype;

public class Testsetup {
	

	
	public String testA(EntityManager em) {

		Ptype t1 = new Ptype();
		t1.setName("Milk");
		t1.setCategory("Dairy");
		em.persist(t1);

		Product p1 = new Product();
		String l = "100";
		p1.setCode(l);
		p1.setPtype(t1);
		em.persist(p1);

		Product p2 = new Product();
		String m = "101";
		p2.setCode(m);
		p2.setPtype(t1);
		em.persist(p2);
		return "enda";
	}
	
	
	
	
	

}
