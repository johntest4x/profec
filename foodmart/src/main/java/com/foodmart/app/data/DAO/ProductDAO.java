package com.foodmart.app.data.DAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Repository;

import com.foodmart.app.data.Entity.Porder;
import com.foodmart.app.data.Entity.Product;
import com.foodmart.app.data.Entity.Ptype;



@Repository
@Transactional
public class ProductDAO implements IDAO<Product>{

	
	
	@PersistenceContext
	private  EntityManager em;	
	
	
	
	@Override
	public Optional<Product> get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Product t) {
		// TODO Auto-generated method stub
		em.persist(t);
		
	}

	@Override
	public void update(Product t, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Product t) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	public Product createProduct(String code,Ptype ptype) {

		Product product = new Product(code,ptype);   //( product,  category,  odateTxt,  date,  price,  quantity);

		save(product);

		return product;
	}
	
	
	
	
	
	
	
	
	
	/*
	 *    in any('{X,Y.Z}') and in ()
	 * 
	 * 
	 */
	public List<Product> findProduct(String [] pcodes,List<Ptype> ptypes) {
		

	
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);			
		Root<Product> itemRoot = criteriaQuery.from(Product.class);

		criteriaQuery.select(itemRoot);
		List<Predicate> predicates = new ArrayList<>();
		
		
		if(pcodes!=null)
			if(!ArrayUtils.isEmpty(pcodes))
				predicates.add(itemRoot.get("code").in(pcodes));
		
		if(ptypes!=null)
			if(!ptypes.isEmpty())
				predicates.add(itemRoot.get("ptype").in(ptypes));
		
		

		Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
		List<Product> list = em.createQuery(criteriaQuery.where(p)).getResultList();

		
		return list;
	}
	
	
	
	
	
	
}
