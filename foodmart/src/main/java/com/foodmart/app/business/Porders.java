package com.foodmart.app.business;

import java.math.BigDecimal;
import java.util.Date;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foodmart.app.data.IQuery;
import com.foodmart.app.data.PorderQuery;
import com.foodmart.app.data.ProductQuery;
import com.foodmart.app.data.PtypeQuery;
import com.foodmart.app.data.DAO.OrderFactory;
import com.foodmart.app.data.DAO.ProductFactory;

@Component
public class Porders {

	
	@Autowired 
	private OrderFactory orderFactory;
	@Autowired 
	private ProductFactory productFactory;
	
	
	public IQuery findPorders(TreeMap<String, String> qparams) {
								//String comparectyp,catg,String code,,String price String compare,String startdate,String enddate,


		IQuery qptype=null;
		IQuery qproduct=null;
		IQuery qporder=null;
		
		
		qporder= new PorderQuery(null,  (IQuery)qproduct,null,qparams.get("compare"), new BigDecimal(qparams.get("price")),null, null,qparams.get("startdate"));
		
		
		if(qparams.get("catg")!=null) {
			if(!qparams.get("catg").isEmpty())
				{
										
					String[] categories=qparams.get("catg").split(",");
					String[] names=qparams.get("catg").split(",");
					
				    if(qparams.get("comparectpe").equalsIgnoreCase("Category")) {
				    	names=null;
				    }else { 
				    	categories=null;
				    }
				    
					qptype = new PtypeQuery(null,null,names, categories );                     //create queryobj add these params
					productFactory.findPtypes((PtypeQuery) qptype);                            //find all ptypes using the params, ad them to list inside the queryobj
					
					//we had a criteria but found none in first wuery builder stage so dont continue
					if(   ((PtypeQuery) qptype).getPtypes().size()==0  )
						return qporder;//null;
					//if we 'HAD' any criteria but found no ptypes we can return! (search stops at level1
				}
		}//if we 'HAD NO' criterias (ie emty fileds )  then contue on passing null to next search level (on product codes)
		
		
		
		
		
		
		//ptypes if left blank its predicate is not implemented ie it is not used to filter products and we get list of all products using list prod codes
		// as filter only
				
		
		String [] codes=null;
		if(qparams.get("code")!=null) {
			if(!qparams.get("code").isEmpty())                        //if empty codes passing over creation
			{
				    codes=qparams.get("code").split(",");

	        }	    
		}//else get all products comparing (on any ptypes or none if was empty ie return every product) 
					//qproduct = new ProductQuery(null,qptype,codes );                           //create queryobj, add params codes, and 1st queryobject 
					//productFactory.findProducts( ((ProductQuery)qproduct));                    //find all products using 

					
					
		
	     //we had a criteria (list codes) expecting but found none - then return empty handed!
					qproduct = new ProductQuery(null,qptype,codes );                           //create queryobj, add params codes, and 1st queryobject 
					productFactory.findProducts( ((ProductQuery)qproduct));                    //find all products using 
					
					//there are no products based on the ptype queries Or product code    
					//(is bad code search create empty list or null, its predicate not added in order search criteria)		
					if(   ((ProductQuery) qproduct).getProducts().size()==0  )
							return qporder;//null;
					
		
		/*
		 *             categ or codes were entered (so if fund none return empty!
		 * 		1) if had code and got no products return emty handed
		 * 		2) but if no codes entered, and used category ret no products also return
		 * 
		 * 		3) if no categories & no codes entered 
		 *                    search only on price 
		 *                         then filter these on dates
		 *                         
		 *       Categories OR names are highest
		 *            then from this list (list prod codes) if we enter any codes.
		 *                   Then fom this list products filter on(price and dates)
		 *                              
		 *                         
		 *                         
		 *                         
		 *                         
		 */
			
		
		
		
		
			
					
					
					
					
		/*             Considerations on design,
		 * 
		 * 
		 * 
		 * 		        					OptionA: (alternate)
		 *              Create single table Raw entries NOSql style ie non relational.
		 *              Containing duplicate field references.
		 *              (not optimised for ORM style - as per request for jpa ...)
		 * 				
		 * 				Considerations:
		 * 					-larger table size
		 * 					-less flexible search (non Relational)
		 *                  -OOP classes More generic (ie contain many disparate fields) note this is valid approach all in one classes.  
		 * 
		 * 
		 *              					OptionB: (adopted)
		 *              
		 *              This aims to avoid many to many (duplication in tables) 
		 *              Normalisation (first order):
		 *              
		 *              	(Client order) (oo)---------------->1Porder oo----------------->1 Product oo----------------->1 Product type (categ/name)
		 *              
		 *              Create relational,
		 *              more Flexible update for new fields, tables   
		 *              ie can add manufacturer code, retail code and use natural language for UI readable eg WWFRUIT_APPL_2, application specific code
		 *              OOP - smaller specific classes reflected in database schema and relations.
		 *              
		 *              
		 *              
		 *              (Performace considerations at scale - Requires loadtesting)
		 *              
		 *              
		 * 				Option1:
		 * 						statement1 = Select types where names in {} and categ in {};
		 * 
		 * 					statement2= Select products where types in {statement1 and codes in {}};
		 * 
		 *              Select orders where O.products in (statment2);
		 *              
		 *              (will reach limit in query size usually around < 32k !!! - simplest to impliment for sample application)
		 *              
		 *               
		 *              Option2:
		 *              Use temp table 
		 *              create table t select ptypes into t where ...
		 *              create table t2 select products from t1 into t2 where ...  
		 *              
		 *              (database intensive)
		 *               
		 * 
		 * 				Option3: 
		 * 				use batch processing - cursor type query, can use stored procedures with parameters;  
		 * 					I have implemented this strategy successfully in the past previous applications.
		 * 
		 *              (Good for streaming)
		 * 
		 * 
		 *              Option4: 
		 *              store results in application system memory, iterate as individual requests
		 *              
		 *              for Each product in products, find orders that contain it (where orders = criteria)
		 *              update any calculation amount;
		 *              repeat;
		 *              
		 *              (most commmon used, also suits java 8+, streaming api)
         *
		 *              
		 *              
		 * 			
		 */
		

		//qporder= new PorderQuery(null,  (IQuery)qproduct,null,qparams.get("compare"), new BigDecimal(qparams.get("price")),null, null,qparams.get("startdate"));
		
		//
		((PorderQuery)qporder).setQuery((IQuery)qproduct);
		orderFactory.findPOrders( ((PorderQuery)qporder));
		
		
		
		return qporder;
		
	}
	
	
	
	
	
	
	
	
}
