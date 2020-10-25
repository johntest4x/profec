package com.foodmart.app.data.DAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.foodmart.app.data.PorderQuery;
import com.foodmart.app.data.Entity.Porder;
import com.foodmart.app.data.Entity.Product;




@Repository
@Transactional
public class PorderDAO  implements IDAO<Porder>{
	

	@PersistenceContext
	private  EntityManager em;	
	

		public PorderDAO() {
			super();
			
		}


		@Override
		public Optional<Porder> get(long id) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public List<Porder> getAll() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public void save(Porder t) {
			// TODO Auto-generated method stub
			em.persist(t);
		}


		@Override
		public void update(Porder t, String[] params) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void delete(Porder t) {
			// TODO Auto-generated method stub
			
		}
		
		
		
	
		
		public Porder createPorder(Product product, String category, String odateTxt, Date date, BigDecimal price, Long quantity) {

			Porder porder = new Porder( product,  category,  odateTxt,  date,  price,  quantity);

			save(porder);

			return porder;
		}
		
		
		
		
		
		
		
		
		
		/*
		 * 
		 *      list 'Products' (filtered by ptype.category or ptype.name)
		 *      
		 *      list 'Porder categories' (BUY/SELL) empy means no criteria so default selects all. 
		 *      
		 *      price 'Porder price'
		 *      
		 *      //default
		 *      	quantity is any
		 * 			id is any
		 *          date is any
		 * 
		 * 		-use to find all BUY then pass list to time filter then SELL pass to time filter Then do calculation
		 * 
		 * 
		 *       Date range could be queriesd against database, using the Date epoch value type in java.util.date.
		 *       OR in application using Calender functions.
		 * 
		 * 
		 * 
		 * 
		 */
	public List<Porder> findPorder(List<Product> products, String[] categories, String odateTxt, Date date,
									BigDecimal price, String priceOperatn, Long quantity) {

		//System.out.println("----------------------" + categories[0] + price + priceOperatn);

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Porder> criteriaQuery = criteriaBuilder.createQuery(Porder.class);
		Root<Porder> itemRoot = criteriaQuery.from(Porder.class);

		criteriaQuery.select(itemRoot);

		List<Predicate> predicates = new ArrayList<>();

		if (categories != null)
			if (!ArrayUtils.isEmpty(categories))
				predicates.add(itemRoot.get("category").in(categories));

		if ((products != null))
			if (!products.isEmpty())
				predicates.add(itemRoot.get("product").in(products));

		if (price != null) {

			if (priceOperatn.equals(PorderQuery.getQPriceoperequal()))
				predicates.add(criteriaBuilder.equal(itemRoot.get("price"), price));

			if (priceOperatn.equals(PorderQuery.getQPriceopergreaterthan()))
				predicates.add(criteriaBuilder.greaterThan(itemRoot.get("price"), price));

			if (priceOperatn.equals(PorderQuery.getQPriceoperlessthan()))
				predicates.add(criteriaBuilder.lessThan(itemRoot.get("price"), price));

		}
			

		Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
		

		List<Porder> list = em.createQuery(criteriaQuery.where(p)).getResultList();

		
		return list;
		
		}
		
		
		
		
		
		
		
		
		
		
		
	

}
