package com.foodmart.app.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foodmart.app.data.DateUtility;
import com.foodmart.app.data.IQuery;
import com.foodmart.app.data.PorderQuery;
import com.foodmart.app.data.Entity.Porder;
import com.foodmart.app.data.Entity.Product;
import com.foodmart.app.data.Entity.Ptype;




/*
 * 	Get raw list list dbase;
 * 		-all 
 *  
 * 
 * 
 * 
 */
@Component
public class Calculator {
	
	
	
	

	@Autowired 
	private DateUtility dutil;
	@Autowired 
	private Calculator calculator;
	
	
	
	private static HashMap<Integer, Product> products = new HashMap<Integer,Product>();	
	
	private static HashMap<Integer, Ptype> ptypes = new HashMap<Integer,Ptype>();	
	
	private static HashMap<Integer, Porder> porders = new HashMap<Integer,Porder>();

	
	
	
	
	
	public  BigDecimal getProfit(IQuery qporder, String dateRangeFrom,String dateRangeTo) {
		
		
		BigDecimal profit = new BigDecimal("0");

		if (qporder == null)
			return profit;

		
		String odateRangeFrom = dutil.getFormatedDTStr(dateRangeFrom);
		Date dFr = dutil.strDatetoDate(odateRangeFrom, "EE MMM dd yyyy HH:mm:ss zz");
		
		String odateRangeTo = dutil.getFormatedDTStr(dateRangeTo);
		Date dTo = dutil.strDatetoDate(odateRangeTo, "EE MMM dd yyyy HH:mm:ss zz");



		List<Porder> Valid_lporders = new ArrayList<Porder>();

		List<Porder> lporders = ((PorderQuery) qporder).getPorders();

		//List<Entry<Integer, Porder>> listBuy = ((HashMap<Integer, Porder>) lporders).entrySet().stream().filter(
		
		List<Porder> listBuy = lporders.stream().filter(
				
				e -> (((e.getOdate().after(dFr) || e.getOdate().compareTo(dFr) == 0)
						
						&& (e.getOdate().before(dTo)) || e.getOdate().compareTo(dTo) == 0))

						&& e.getCategory().equals("BUY"/*Porder.movement.PURCHASED*/))
				
				.collect(Collectors.toList()
		);
		
		BigDecimal cost = listBuy.stream().map(x -> x.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		
		
		List<Porder>  listSell = lporders.stream().filter(
				
				e -> (((e.getOdate().after(dFr) || e.getOdate().compareTo(dFr) == 0)
						
						&& (e.getOdate().before(dTo)) || e.getOdate().compareTo(dTo) == 0))

						&& e.getCategory().equalsIgnoreCase("SELL"/*Porder.movement.PURCHASED*/))
				
				.collect(Collectors.toList()
		);
		
		BigDecimal sale = listSell.stream().map(x -> x.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
	
		
		
		listSell.addAll(listBuy);
		((PorderQuery) qporder).setPorders(listSell);
		
		
		BigDecimal prof=null;;
		if(sale.compareTo(cost) > 0 ) {
			prof = sale.subtract(cost);
			((PorderQuery) qporder).setProfOrLoss("Profit");
		}else if(sale.compareTo(cost) < 0) {
			
			prof = cost.subtract(sale);
			((PorderQuery) qporder).setProfOrLoss("LOSS");
		}else {
			prof=new BigDecimal(0);
			((PorderQuery) qporder).setProfOrLoss("Profit");
		}
		
		
		
		
		 
		 
		
		
		
		
		// TODO Auto-generated method stub
		return prof;
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
