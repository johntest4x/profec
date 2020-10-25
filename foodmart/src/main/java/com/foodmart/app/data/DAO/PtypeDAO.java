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
import org.springframework.util.CollectionUtils;

import com.foodmart.app.data.PorderQuery;
import com.foodmart.app.data.Entity.Porder;
import com.foodmart.app.data.Entity.Product;
import com.foodmart.app.data.Entity.Ptype;





@Repository
@Transactional
public class PtypeDAO implements IDAO<Ptype>{

	
	
	@PersistenceContext
	private  EntityManager em;	
	
	
	@Override
	public Optional<Ptype> get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ptype> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Ptype t) {
		// TODO Auto-generated method stub
		em.persist(t);
	}

	@Override
	public void update(Ptype t, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Ptype t) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	public Ptype createPtype(String name,String category) {
		
		Ptype t = new Ptype(name,category);
		save(t);
		
		return t;
		
		
	}
	
	
	
	
	
	/*
	 *  	PG  in any('{X,Y,Z}') 
	 * 
	 * 
	 * 		Returns	- Single Ptype on matching id
	 * 				- List Ptypes based on Ptype.names AND/OR Ptype.categories
	 * 
	 * 
	 */
	public List<Ptype> findPtype(Long id,String [] names,String [] categories) {
		

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Ptype> criteriaQuery = criteriaBuilder.createQuery(Ptype.class);			
		Root<Ptype> itemRoot = criteriaQuery.from(Ptype.class);

		criteriaQuery.select(itemRoot);
		List<Predicate> predicates = new ArrayList<>();
		
		
		if(id!=null)
		        predicates.add(criteriaBuilder.equal(itemRoot.get("id"),id));
		
		if(names!=null)
			if(!ArrayUtils.isEmpty(names))
		        predicates.add(itemRoot.get("name").in(names));
		
		if(categories!=null)
			if(!ArrayUtils.isEmpty(categories))
				predicates.add(itemRoot.get("category").in(categories));
		
		Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
		List<Ptype> list = em.createQuery(criteriaQuery.where(p)).getResultList();
		
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
