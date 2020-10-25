
package com.foodmart.app.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.foodmart.app.business.Calculator;
import com.foodmart.app.business.Porders;
import com.foodmart.app.data.DateUtility;
import com.foodmart.app.data.IQuery;
import com.foodmart.app.data.PorderQuery;
import com.foodmart.app.data.ProductQuery;
import com.foodmart.app.data.PtypeQuery;
import com.foodmart.app.data.DAO.DAO;
import com.foodmart.app.data.DAO.IEntity;
import com.foodmart.app.data.DAO.IPorderQuery;
import com.foodmart.app.data.Entity.BaseType;
import com.foodmart.app.data.Entity.Porder;
import com.foodmart.app.data.Entity.Product;
import com.foodmart.app.data.Entity.Ptype;
import com.foodmart.app.data.raw.Testsetup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUtils;
import javax.swing.UIManager;

import org.apache.commons.io.IOUtils;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.hibernate.cfg.Configuration;
//import org.hsqldb.TransactionManager;
//import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.foodmart.app.data.DAO.OrderFactory;
import com.foodmart.app.data.DAO.PorderDAO;
import com.foodmart.app.data.DAO.ProductFactory;

@Transactional
@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*") /*
													 * see web.xml commented out the javaconfig cors implementation
													 * class
													 */
public class FmartController {
	@PersistenceContext
	@Autowired
	private EntityManager em;
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	@Autowired
	private DAO dao;
	@Autowired
	private DateUtility dutil;

	@Autowired
	private OrderFactory orderFactory;
	@Autowired
	private ProductFactory productFactory;

	@Autowired
	private Porders porders;
	@Autowired
	private Calculator calculator;
	

	
	private boolean testhasrun=false;
	
	
	
	
	
	

	@RequestMapping("/")
	public String home(Map<String, Object> model) {
		model.put("message", "---");
		
		return "index";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewL1", method = RequestMethod.GET)
	public ModelAndView httpServicePostExample3(ModelMap model) {
		return new ModelAndView("httpservice_post3");
	}

	@RequestMapping(value = "/viewL2", method = RequestMethod.GET)
	public ModelAndView httpServicePostExample4(ModelMap model) {
		return new ModelAndView("httpservice_post4");
	}

	@RequestMapping(value = "/show/{code}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> showList(@PathVariable("code") String code) {

		String view = code;
		String viewDescr = "";
		JSONArray ja = null;
		HttpStatus httpstatus = null;

		try {

			IQuery qview = null;
			List list2 = null;

			if (view.equalsIgnoreCase("ptypes")) {

				viewDescr = "Types of product";
				qview = new PtypeQuery(null, null, null, null);        //new qview all fields null
				productFactory.findPtypes((PtypeQuery) qview);
				list2 = ((PtypeQuery) qview).getPtypes();

			} else if (view.equalsIgnoreCase("products")) {

				viewDescr = "Products";
				qview = new ProductQuery(null, null, null);
				productFactory.findProducts(((ProductQuery) qview));
				list2 = ((ProductQuery) qview).getProducts();

			} else {
				throw new Exception("API request not found");
			}

			HashMap<Integer, Object> custList = new HashMap<Integer, Object>();

			if (list2 != null) {
				if (!list2.isEmpty()) {

					for (/* Ptype */Object j : list2) {
						int id = UUID.randomUUID().hashCode();
						custList.put(id, j);
						// System.out.print("-->>"+j.getName());
					}
				} else {
					throw new Exception("list data not found (try entering some data)");
				}
			} else {
				throw new Exception("list data not found (try entering some data)");
			}

			List<Entry<Integer, Object/* Ptype */>> customers = (List<Entry<Integer, Object/* Ptype */>>) custList
					.entrySet().stream().collect(Collectors.toList());

			Iterator<Entry<Integer, Object/* Ptype */>> itx = customers.iterator();

			JSONObject out = null;
			// JSONArray ja = null;
			try {
				Map<String, Object> myString = new HashMap<String, Object>();
				ja = new JSONArray();

				while (itx.hasNext()) {
					IEntity/* Ptype */ c = (IEntity) itx.next().getValue();
					myString.put("name", ((IEntity) c).getName());
					myString.put("category", ((IEntity) c).getCategory());
					myString.put("id", ((IEntity) c).getCode());

					out = new JSONObject(myString);
					ja.put(out);
				}

			} catch (Exception e) {

				System.out.print(e);
				throw new Exception("could not populate list");
			}

			httpstatus = HttpStatus.OK;
		} catch (Exception e) {
			viewDescr = "Issue - " + e.getMessage();
			httpstatus = HttpStatus.BAD_REQUEST;
		}

		JSONObject tosend = new JSONObject();
		try {
			tosend.put("companies", ja);
			tosend.put("message", viewDescr);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(tosend.toString(), httpstatus);

	}

	@RequestMapping(value = "/addptype", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> addPtype(@RequestParam("pnme") String pnme, // for Product type: name of
																							// product
			@RequestParam("catg") String catg // for Product type: category of product
	// @RequestParam("code") String code, //for Product: Code to label a product
	// @RequestParam("fid") String fid, //for Product: id from product type selected
	// from 'product type list'
	// @RequestParam("opcode") String opcode, //for Porder: adding order i want
	// opcode (not code)
	// @RequestParam("orderx") String orderx, //for Porder: BUY/SELL
	// @RequestParam("price") String price, //for Porder:price qty
	// @RequestParam("quantity") String quantity, //for Porder:quantity
	// @RequestParam("startdate") String startdate //for Porder:date entered
	// @RequestParam("enddate") String enddate

	) {

		String viewDescr = "";
		JSONArray ja = null;
		HttpStatus httpstatus = null;

		IEntity ie = null;

		try {

			ie = productFactory.createProductType(pnme, catg);
			if (ie == null)
				throw new Exception("could not create product type");

			httpstatus = HttpStatus.OK;
			viewDescr = "Created Product type:";
		} catch (Exception e) {

			viewDescr = " issue - " + e.getMessage();
			httpstatus = HttpStatus.BAD_REQUEST;
		}

		JSONObject out = null;
		try {
			String pname = ie.getName();
			String pcat = ie.getCategory();
			Map<String, Object> myString = new HashMap<String, Object>();
			myString.put("pnme", "" + pname);
			myString.put("catg", "" + pcat);
			myString.put("fid", "" + ie.getId());
			myString.put("code", "-");
			myString.put("opcode", "-");
			myString.put("orderx", "-");
			myString.put("price", "-");
			myString.put("quantity", "-");
			myString.put("startdate", "-");
			myString.put("message", viewDescr);
			out = new JSONObject(myString);
		} catch (Exception e) {

			System.out.print(e);
		}

		return new ResponseEntity<String>(out.toString(), httpstatus);

	}

	@RequestMapping(value = "/addptype2", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> saveCompany2(
			// @RequestParam("pnme") String pnme, //for Product type: name of product
			// @RequestParam("catg") String catg, //for Product type: category of product
			@RequestParam("code") String pcode, // for Product: Code to label a product
			@RequestParam("fid") String fid // for Product: id from product type selected from 'product type list'
	// @RequestParam("opcode") String opcode, //for Porder: adding order i want
	// opcode (not code)
	// @RequestParam("orderx") String orderx, //for Porder: BUY/SELL
	// @RequestParam("price") String price, //for Porder:price qty
	// @RequestParam("quantity") String quantity, //for Porder:quantity
	// @RequestParam("startdate") String startdate //for Porder:date entered
	// @RequestParam("enddate") String enddate

	) {

		String viewDescr = "";
		JSONArray ja = null;
		HttpStatus httpstatus = null;

		IEntity ie = null;
		IQuery iq = null;

		try {
			Long id = Long.parseLong(this.validateInput(fid));
			String code = this.validateInput(pcode);

			iq = new PtypeQuery(id, null, null, null);
			productFactory.findPtypes(((PtypeQuery) iq));

			if (((PtypeQuery) iq).getPtypes() == null) {
				throw new Exception("could not find product type for id:" + id);
			} else if (((PtypeQuery) iq).getPtypes().isEmpty()) {
				throw new Exception("could not find product type for id:" + id);
			}

			IEntity foundPtype = ((PtypeQuery) iq).getPtypes().get(0);

			if (foundPtype == null)
				throw new Exception("issue retrieving product type for id:" + id);

			ie = productFactory.createProduct(code, (Ptype) foundPtype);

			if (ie == null)
				throw new Exception("could not create product");

			httpstatus = HttpStatus.OK;
			viewDescr = "Created Product:";

		} catch (Exception e) {

			viewDescr = " issue - " + e.getMessage();
			httpstatus = HttpStatus.BAD_REQUEST;
		}

		JSONObject out = null;
		try {
			// String pcode=ie.getCode();
			// Long pid=ie.getId();
			Map<String, Object> myString = new HashMap<String, Object>();
			myString.put("pnme", "-");
			myString.put("catg", "-");
			myString.put("code", "");
			myString.put("fid", "");
			myString.put("opcode", "-");
			myString.put("orderx", "-");
			myString.put("price", "-");
			myString.put("quantity", "-");
			myString.put("startdate", "-");
			myString.put("message", viewDescr);
			out = new JSONObject(myString);
		} catch (Exception e) {

			System.out.print(e);
		}

		return new ResponseEntity<String>(out.toString(), httpstatus);

	}

	@RequestMapping(value = "/addptype3", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> saveCompany3(
			// @RequestParam("pnme") String pnme, //for Product type: name of product
			// @RequestParam("catg") String catg, //for Product type: category of product
			// @RequestParam("code") String code, //for Product: Code to label a product
			// @RequestParam("fid") String fid, //for Product: id from product type selected
			// from 'product type list'
			@RequestParam("opcode") String opcode, // for Porder: adding order i want opcode (not code)
			@RequestParam("orderx") String orderx, // for Porder: BUY/SELL
			@RequestParam("price") String price, // for Porder:price qty
			@RequestParam("quantity") String quantity, // for Porder:quantity
			@RequestParam("startdate") String startdate // for Porder:date entered
	// @RequestParam("enddate") String enddate

	) {

		// System.out.print("iiiiiiiiiiiiiiiiiiiptypeid =
		// fid:"+fid+"cat:"+catg+"pnme:"+pnme+"quantity:"+quantity+"startdate:"+startdate+"code:"+code
		// +"opcode:"+opcode+"orderx:"+orderx+"price:"+price+"quantity:"+quantity);

		String viewDescr = "";
		JSONArray ja = null;
		HttpStatus httpstatus = null;

		IEntity ie = null;
		IEntity iep = null;
		IQuery iq = null;

		String[] codes = null;
		BigDecimal bdprice = null;
		Long lquantity = null;

		Product p = null;

		try {
			if (this.validateInput(opcode) != null) {
				codes = opcode.split(",");

			} else {
				throw new Exception("Invalid Code Entered");
			}

			if (this.validateInput(price) != null) {
				bdprice = new BigDecimal(price);
			} else {
				throw new Exception("Invalid price Entered");
			}

			if (this.validateInput(quantity) != null) {
				lquantity = Long.valueOf(quantity);
			} else {
				throw new Exception("Invalid quantity Entered");
			}

			iq = new ProductQuery(null, null, codes);
			productFactory.findProducts(((ProductQuery) iq));

			if (((ProductQuery) iq).getProducts() != null) {

				if (((ProductQuery) iq).getProducts().size() == 1) {
					iep = ((ProductQuery) iq).getProducts().get(0);
					if (iep == null) {
						throw new Exception("No Products found to associate with order");
					}

				} else {
					throw new Exception("Multiple Products found to associate with order");
				}

			} else {
				throw new Exception("Unable to get Products for order");
			}

			ie = orderFactory.createPorder((Product) iep, orderx, startdate, bdprice, lquantity);

			httpstatus = HttpStatus.OK;
			viewDescr = "Created Product Order:";

		} catch (Exception e) {
			viewDescr = " issue - " + e.getMessage();
			httpstatus = HttpStatus.BAD_REQUEST;

		}

		System.out.print("ssssssssssssssssss");
		JSONObject out = null;
		try {

			//TODO: -->>>> move to generateReport(IQuery<PorderQuery> iq)
			String ptypeName = ((Porder) ie).getProduct().getPtype().getName();
			String ptypeCategory = ((Porder) ie).getProduct().getPtype().getCategory();
			Long qtyId = ((Porder) ie).getId();
			String pcode = ((Porder) ie).getProduct().getCode();
			Long pid = ((Porder) ie).getProduct().getId();
			String buysellCat = ((Porder) ie).getCategory();
			String oprice = "" + ((Porder) ie).getPrice();
			Long qty = ((Porder) ie).getQty();
			String pdate = dutil.getLocalDateConversion(((Porder) ie).getOdate());
			//---------------------------------------------------------------------

			Map<String, Object> myString = new HashMap<String, Object>();
			myString.put("pnme", "" + ptypeName);
			myString.put("catg", "" + ptypeCategory);
			myString.put("code", iep.getCode());
			myString.put("fid", "" + qtyId);
			myString.put("opcode", pcode);
			myString.put("orderx", buysellCat);
			myString.put("price", oprice);
			myString.put("quantity", qty);
			myString.put("startdate", "" + pdate);
			myString.put("message", viewDescr);
			out = new JSONObject(myString);
		} catch (Exception e) {

			System.out.print(e);
		}

		return new ResponseEntity<String>(out.toString(), HttpStatus.OK);

	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/calculations", method = RequestMethod.GET)
	public ModelAndView httpServicePostExample2(ModelMap model) {
		return new ModelAndView("calculate");
	}

	public String validateInput(String inputs) {

		if (inputs.isEmpty()) {
			inputs = null;
		} else if (inputs.trim().equalsIgnoreCase("undefined")) {
			inputs = null;
		} else if (inputs.trim().toLowerCase().equals(",")) {
			inputs = null;
		} // TODO: validate non char

		return inputs;

	}

	@RequestMapping(value = "/calc", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> calculator(
			// @RequestParam("pnme") String pnme,
			@RequestParam("comparectyp") String comparectyp, 
			@RequestParam("catg") String catg,
			@RequestParam("code") String code,
			// @RequestParam("fid") String fid,
			// @RequestParam("opcode") String opcode,
			@RequestParam("compare") String compare, //
			// @RequestParam("orderx") String orderx,
			@RequestParam("price") String price,
			// @RequestParam("quantity") String quantity,
			@RequestParam("startdate") String startdate, 
			@RequestParam("enddate") String enddate, String startDate

	) {

		String viewDescr = "";
		JSONArray ja = null;
		HttpStatus httpstatus = null;

		IQuery iq = null;
		TreeMap<String, String> inputs=null;

		try {

			// String catg,String pnme,String code,String price,String compare, String
			// quantity,String startdate,String enddate,
			inputs = new TreeMap<String, String>();
			inputs.put("comparectpe", comparectyp);
			inputs.put("catg", this.validateInput(catg));
			inputs.put("code", this.validateInput(code));
			inputs.put("compare", compare);
			inputs.put("price", price);
			inputs.put("startdate", startdate);
			inputs.put("enddate", enddate);

			
			iq = porders.findPorders(inputs);
			String dateRangeTo = inputs.get("enddate");
			String dateRangeFrom = inputs.get("startdate");
			BigDecimal profit = calculator.getProfit(iq, dateRangeFrom,dateRangeTo);

			
			httpstatus = HttpStatus.OK;
			viewDescr = ""+((PorderQuery)iq).getProfOrLoss()+":"+  profit;

		} catch (Exception e) {

			viewDescr = " issue - " + e.getMessage();
			httpstatus = HttpStatus.BAD_REQUEST;
		}

		// myString.put("enddate",enddate);

		JSONObject out = null;
		try {

			try {
				
					inputs.clear();
					inputs = generateReport(iq);  //propogate exception back to here!
			
			}catch (Exception e) {
				
				viewDescr = " issue - " + e.getMessage();
				httpstatus = HttpStatus.BAD_REQUEST;
			}
			
			
			Map<String, Object> myString = new HashMap<String, Object>();
			myString.put("pnme", "-");
			myString.put("catg", "-");
			myString.put("code", "-");
			myString.put("fid", "-");
			myString.put("opcode", ""+inputs.get("code"));
			myString.put("compare", compare);
			myString.put("orderx", ""+inputs.get("orderx"));
			myString.put("price", ""+inputs.get("price"));
			myString.put("quantity", ""+inputs.get("qty"));
			myString.put("startdate",""+inputs.get("date"));
			myString.put("daterange", enddate);			
			myString.put("message", viewDescr);
			out = new JSONObject(myString);
		} catch (Exception e) {

			System.out.print(e);
		}

		return new ResponseEntity<String>(out.toString(), HttpStatus.OK);

	}

	///////////////////////////////////////////////////////////////////////////

	
	
	
	
	/**
	 * 
	 *   TODO:// string buffer line seperations
	 * 
	 * 
	 * @param iq
	 * @return
	 */
	private TreeMap<String, String> generateReport(IQuery<PorderQuery> iq) throws Exception{
		
		
		TreeMap<String, String> outputs = new TreeMap<String, String>();
		
//
//		IQuery ptyQuery = ((PorderQuery) iq).getQuery();
//		String []c=((PtypeQuery) ptyQuery).getCategories();	
//		List l = Arrays.asList(c);
//		outputs.put( "catg",String.join(",", l));
//		
//		
//		String compare = Arrays.toString(((PorderQuery) iq).getCategory());  
//		outputs.put( "compare",compare);
		
	

	//String outcode = "" + ((PorderQuery) iq).getPorders();//.get(0).getProduct().getCode(); list prodcodes as string NO 
	
	//String pocodes="";
	String code="";
	String price="";
	String qty="";
	String date="";
	String orderx="";
	
	
	try {
	
	List<Porder> lpo = ((PorderQuery) iq).getPorders();		
	if(lpo!=null) {
		for (Object o:lpo) {
			
			code+=((Porder)o).getProduct().getCode()+",\n";
			//get Products associated Ptype name & categ
			price+=((Porder)o).getPrice()+",\n";
			qty+=((Porder)o).getQty()+",\n";
			date+= dutil.getLocalDateConversion( ((Porder)o).getOdate())+",\n";
			orderx+=((Porder)o).getCategory()+",\n";   
		}
	}
		
	}catch(Exception e) {
		
		throw new Exception(" problem creating report");
	}
	
	outputs.put("code",code);
	outputs.put("price",price);
	outputs.put("qty",qty);
	outputs.put("date",date);
	outputs.put("orderx",orderx);
		
		return outputs;
	}
	
	
	
	
//	private IQuery findOrders(TreeMap<String, String> inputs) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@RequestMapping(value = "/calcold", method = RequestMethod.POST)
//	public @ResponseBody ResponseEntity<String> calculatorold(@RequestParam("pnme") String pnme,
//			@RequestParam("catg") String catg, @RequestParam("code") final String code, @RequestParam("fid") String fid,
//			@RequestParam("opcode") String opcode, @RequestParam("compare") String compare, // price
//																							// equal/greater,lessthan
//			@RequestParam("orderx") String orderx, @RequestParam("price") String price,
//			@RequestParam("quantity") String quantity, @RequestParam("startdate") String startdate,
//			@RequestParam("enddate") String enddate
//
//	) {
//
//		System.out.print("iiiiiiiiiiiiiiiiiiiptypeid = fid:" + fid + "cat:" + catg + "pnme:" + pnme + "catg:" + catg
//				+ "startdate:" + startdate + "code:" + code + "opcode:" + opcode + "orderx:" + orderx + "price:" + price
//				+ "quantity:" + quantity + "compare:" + compare);
//
//		BigDecimal bdprice = null;
//		Long lquantity = null;
//		String scatg = null;
//		try {
//			bdprice = new BigDecimal(price);
//			lquantity = Long.valueOf("98"/* quantity */);
//			if (!catg.trim().isEmpty())
//				scatg = catg;
//
//		} catch (Exception e) {
//			System.out.println("Error type conversion:" + e);
//		}
//
//		// com.foodmart.app.data.PorderQuery cannot be cast to
//		// com.foodmart.app.data.DAO.IPorderQuery
//		// IQuery qorder = new PorderQuery();
//		// IQuery qptype = new PorderQuery();
//		// IQuery qproduct = new PorderQuery();
//
//		// em.find(Product.class, arg1)
//
//		// PorderQuery qorder = new PorderQuery();
//
//		String priceOper = compare;
//
//		// (use cat for ) test filtering BUY/SELL
//		PorderQuery qorder = new PorderQuery(null, (IQuery) new ProductQuery(), lquantity, priceOper, bdprice,
//				scatg.split(","), null, startdate);
//
//		System.out.println("         lquantity     " + lquantity + "  Qty   " + qorder.getQty() + "   Price     "
//				+ qorder.getPrice() + "  CAT    " + qorder.getCategory());
//
//		// (( IPorderQuery)qproduct).setProduct();
//		qorder.setPrice(new BigDecimal(price));
//		// (( IPorderQuery)qorder).setCategory("SOLD");
//		qorder.setQty(lquantity);
//		qorder.setPriceOper(priceOper);
//		// (( IPorderQuery)qorder).setOdate(new Date());
//		// (( IPorderQuery)qorder).setOdatetxt("datestring");
//		// (( IPorderQuery)qorder).setId((long)6);
//
//		// List<Porder> l = orderFactory.findPOrders(qorder);
//		//
//		//
//		//
//		// // catg=l.get(0).getProduct().getPtype().getCategory();
//		// // String codeout=l.get(0).getProduct().getCode();
//		//
//		// compare=l.get(0).getCategory();
//		// price=""+l.get(0).getPrice();
//		// quantity=""+l.get(0).getQty();
//		// startdate=""+l.get(0).getOdate();
//		// //myString.put("enddate",enddate);
//		//
//
//		JSONObject out = null;
//		try {
//			Map<String, Object> myString = new HashMap<String, Object>();
//			myString.put("pnme", "RESULT");
//			myString.put("catg", catg);
//			myString.put("code", "codeout");
//			myString.put("fid", "RESULT");
//			myString.put("opcode", "RESULT");
//			myString.put("compare", compare);
//			myString.put("orderx", orderx);
//			myString.put("price", price);
//			myString.put("quantity", quantity);
//			myString.put("startdate", startdate);
//			myString.put("enddate", enddate);
//			out = new JSONObject(myString);
//		} catch (Exception e) {
//
//			System.out.print(e);
//		}
//
//		return new ResponseEntity<String>(out.toString(), HttpStatus.OK);
//
//	}

	@GetMapping(value = "/{code}", produces = MediaType.TEXT_PLAIN_VALUE)
	public String index(@PathVariable("code") String code, @RequestParam(value = "v1", required = false) String v1,
			@RequestParam(value = "v2", required = false) String v2) {

		String oup = "The output to test debug1";

		if ("a".equals(code))
			oup = testA();
		if ("b".equals(code))
			oup = testB();
		if ("c".equals(code))
			oup = testC();
		if ("d".equals(code))
			oup = testD();
		if ("e".equals(code))
			oup = testE();
		if ("f".equals(code))
			oup = testF();
		

		return "=============>>" + oup;
	}
	
	
	
	@RequestMapping(value = "/testapi2/{code}", method = RequestMethod.GET,produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String  tester1(@PathVariable( "code" ) String code) {

		//String ret = "xxx "+testF();

		String put1="";
		
		if(this.testhasrun==false) {
		com.foodmart.app.data.raw.Testsetup tsup = new Testsetup();
		String ret1="Data setup ran"+tsup.testA(em);
		put1="<HTML><BODY>" +ret1+ "<script>alert('Please refresh your application page to view the data')</script></BODY></HTML>";
		}
		//ModelAndView view =new ModelAndView("index","message", ret+"");//("index.jsp"); // = <property name="suffix" value=".jsp"/> else value = ""
		
        return put1;
	}

	
	
	
	
	

	/*
	 * Many prod to one type
	 */
	public String testA() {

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

	

	public String testB() {

		Ptype t1 = new Ptype();
		t1.setName("Milk");
		t1.setCategory("Dairy");

		Product p1 = new Product();
		String l = "100xx";
		p1.setCode(l);
		p1.setPtype(t1);
		em.persist(p1);

		Ptype t2 = new Ptype();
		t2.setName("SMilk");
		t2.setCategory("Dairy");

		t2.setProduct(p1);
		em.persist(t2);

		return "endb";

	}

	public String testE() {

		Porder o1 = new Porder();
		o1.setCategory("SOLD"/* ""+Order.movement.PURCHASED */);
		o1.setPrice(new BigDecimal((long) 50.00));
		o1.setQty((long) 400);

		Query query2 = em.createQuery("Select t from Product t"); // works as bidir relation is setup
		// query2.setParameter("urlName",urlNamex/* "placeholder"*/);
		@SuppressWarnings("unchecked")
		String out = "";
		List<Product> list2 = query2.getResultList();
		Product p = (Product) list2.get(list2.size() - 1);

		// TODO://test valid entry or empty
		if (list2 != null) {
			if (!list2.isEmpty()) {

				o1.setProduct((Product) p);
			}
		}
		em.persist(o1);

		String code = p.getCode();
		String[] criteria = { code };

		// IQuery qorder = new PorderQuery();
		// IQuery qprodtype = new PorderQuery();

		// //(( IPorderQuery)qproduct).setPorder(o1);
		// (( IPorderQuery)qorder).setPrice(new BigDecimal((long) 50.00));
		// (( IPorderQuery)qorder).setCategory("SOLD");
		// (( IPorderQuery)qorder).setQty((long)400);
		// (( IPorderQuery)qorder).setOdate(new Date());
		// (( IPorderQuery)qorder).setOdatetxt("datestring");
		// (( IPorderQuery)qorder).setId((long)6);
		//
		//
		//
		// orderFactory.findPOrders(qorder);

		// List<Porder> l = porderdao.findPOrders(criteria,p,qproduct);

		return " created order" + "found:";// +found.getCategory()+found.getPrice();

	}

	public String testD() {

		Product p1 = new Product();
		String l = "100";
		p1.setCode(l);
		// p1.setPtype(t1);
		// em.persist(p1);

		Product p2 = new Product();
		String m = "101";
		p2.setCode(m);
		// p2.setPtype(t1);
		// em.persist(p2);

		Ptype t1 = new Ptype();
		t1.setName("SMilk");
		t1.setCategory("Dairy");
		t1.setProduct(p1);
		em.persist(t1);
		t1.setProduct(p2);
		em.persist(t1);

		return "endb";

	}

	public String testC() {

		// Query query3 = em.createQuery("Select t from Table1
		// t",Product.class).setMaxResults(5);
		// @SuppressWarnings("unchecked")
		// List<Product> list2 =query3.getResultList();

		Query query2 = em.createQuery("Select t.ptype from Product t"); // works as bidir relation is setup
		// query2.setParameter("urlName",urlNamex/* "placeholder"*/);
		@SuppressWarnings("unchecked")
		String out = "";
		List<Ptype> list2 = query2.getResultList();

		// TODO://test valid entry or empty
		if (list2 != null) {
			if (!list2.isEmpty()) {

				for (Ptype j : list2) {
					out += j.getName();
				}
			}
		}

		return "endc  " + out;

	}

	public void test1() {

		/*
		 * Prodcode Must have a ptype (to begin with) -it can have same ptype -but we
		 * can associate more types with it when creating a ptype product now needs at
		 * least one type,
		 */

		Ptype t1 = new Ptype();
		t1.setName("Milk");
		t1.setCategory("Dairy");
		// //em.persist(t1);
		// //em.unwrap(Session.class).update(t1);
		//
		// Product p1 = new Product();
		// long l=100;
		// p1.setCode(l);
		// p1.setPtype(t1);
		// em.persist(p1);

		Product p2 = new Product(); // also has same type
		// //p2.setCode(l);//err test
		String m = "101"; // 100 test
		p2.setCode(m);
		// p2.setPtype(t1);
		// em.persist(p2);
		//
		//
		//
		//
		// ///persists tpe
		Ptype t2 = new Ptype();
		t2.setName("Milk2");
		t2.setCategory("Dairy2");
		// t2.addProduct(p2);
		//
		em.persist(t2);
		//

	}

	public String testF() {
		Ptype t1 = new Ptype();
		t1.setName("Milk");
		t1.setCategory("Dairy");
		em.persist(t1);

		Ptype t2 = new Ptype();
		t2.setName("Cream");
		t2.setCategory("Dairy");
		em.persist(t2);

		Ptype t3 = new Ptype();
		t3.setName("Cream");
		t3.setCategory("Confectionary");
		em.persist(t3);

		Ptype t4 = new Ptype();
		t4.setName("Apple");
		t4.setCategory("Fruit");
		em.persist(t4);

		Ptype t5 = new Ptype();
		t5.setName("Orange");
		t5.setCategory("Fruit");
		em.persist(t5);

		String l = "100";
		Product p1 = new Product();
		l = "100";
		p1.setCode(l);
		p1.setPtype(t1);
		em.persist(p1);

		t1.setProduct(p1);
		em.persist(t1);

		Product p2 = new Product();
		l = "101";
		p2.setCode(l);
		p2.setPtype(t2);
		em.persist(p2);

		Product p3 = new Product();
		l = "102";
		p3.setCode(l);
		p3.setPtype(t3);
		em.persist(p3);

		Product p4 = new Product();
		l = "103";
		p4.setCode(l);
		p4.setPtype(t4);
		em.persist(p4);

		Product p5 = new Product();
		l = "104";
		p5.setCode(l);
		p5.setPtype(t1);
		em.persist(p4);

		return "endF";

	}
//
//	public String testG(String ptype, String category) {
//
//		System.out.print("==================" + ptype + category);
//		List<Ptype> ptypes = findPtype(ptype, category);
//
//		String out = "";
//
//		// TODO://test valid entry or empty
//		if (ptypes != null) {
//			if (!ptypes.isEmpty()) {
//
//				for (Ptype j : ptypes) {
//					out += j.getName() + j.getCategory();
//				}
//			}
//		}
//
//		return "endc  " + out;
//
//	}

//	// findProductsPtype
//	// findProductsForType
//	// findAllPtypes
//	// findProdByCode
//	public List<Ptype> findPtype(String ptype, String category) {
//
//		// Query query2 = em.createQuery("Select t from Product t where t.code=101 " );
//		// // and t.ptype.category='Dairy'
//
//		Query query2 = em.createQuery("Select t from Product t where t.ptype.category  = 'Dairy' and t.code=101  "); // works
//																														// as
//																														// bidir
//																														// relation
//																														// is
//																														// setup
//		// query2.setParameter("urlName",urlNamex/* "placeholder"*/);
//		@SuppressWarnings("unchecked")
//		String out = "";
//		List<Ptype> list2 = query2.getResultList();
//
//		// TODO://test valid entry or empty
//		if (list2 != null) {
//			if (!list2.isEmpty()) {
//
//				for (Ptype j : list2) {
//					if ("Milk".equals("" + j.getName()))
//						out += j.getCategory();
//				}
//			}
//		}
//
//		System.out.print("vvvvvvvvvvvvvvvvvvvvvvvvv" + out);
//
//		return list2;
//
//		//
//		// System.out.print("xxxxxxxxxxxxxxxinside"+ptype+category);
//		// //Query query2 = em.createQuery("Select 1 from Table1 t where t.table2.data1
//		// = :urlName" );
//		// Query query2 = em.createQuery( "Select t.ptype from Product t where
//		// t.ptype.name = Milk");// and t.ptype.category= :category" );
//		// // query2.setParameter("name",""+ptype);
//		// //query2.setParameter("category",""+category);
//		//
//		// List<Ptype> lptype;
//		// if(query2.getResultList()!=null)
//		// lptype =query2.getResultList();
//		// else
//		// lptype = new ArrayList<Ptype>();
//		//
//		// return lptype;
//	}
//
//	
//	
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private String[] getDatePieces(String startdate, String duration) {
//
//		String[] pieces = startdate.split("\\s");
//		String[] time = pieces[4].split(":");
//
//		System.out.println("--------------->>Peices: " + pieces[0] + "   " + pieces[1] + "   " + pieces[2] + "   "
//				+ pieces[3] + "   " + pieces[4] + "   " + pieces[5] + "   " + pieces[6]);
//		System.out.println("--------------->>time: " + time[0] + "   " + time[1] + "   " + time[2]);
//
//		String[] segments = new String[] { pieces[0], pieces[1], pieces[2], pieces[3], time[0], time[1], time[2],
//				pieces[5], pieces[6] };
//
//		return segments;
//	}
//
//	private String getFormatedTime(String startdate) {
//
//		String pc[] = this.getDatePieces(startdate, "");
//		System.out.print("hhhhhhhhhhhhhh" + pc[0] + " " + pc[1] + " " + pc[2] + " " + pc[3] + " " + pc[4] + ":" + pc[5]
//				+ ":" + pc[6] + " " + pc[7]);// +" "+pc[8]);
//
//		String fdate = "";
//		fdate = pc[0] + " " + pc[1] + " " + pc[2] + " " + pc[3] + " " + pc[4] + ":" + pc[5] + ":" + pc[6] + " " + pc[7]
//				+ " " + pc[8];// strip TZ offset, TZ abreviation
//		return fdate;
//	}
//
//	/*
//	 * Date String format; Sat Oct 17 2020 18:05:00 GMT 1100 (Australian Eastern
//	 * Daylight Time)
//	 */
//	private Date toDate(String startdate, String date_format) {
//		Date senddate = null;
//		SimpleDateFormat readFormat = new SimpleDateFormat(date_format, Locale.ENGLISH);// zz"); // Z");
//		readFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//		try {
//			// {pieces[0]+pieces[1]+pieces[2]+pieces[3]+time[0]+time[1]+time[3]+pieces[5]+pieces[6]};
//			String pc[] = this.getDatePieces(startdate, "");
//			System.out.print("hhhhhhhhhhhhhh" + pc[0] + " " + pc[1] + " " + pc[2] + " " + pc[3] + " " + pc[4] + ":"
//					+ pc[5] + ":" + pc[6] + " " + pc[7] + " " + pc[8]);
//
//			String fdate = "";
//			fdate = pc[0] + " " + pc[1] + " " + pc[2] + " " + pc[3] + " " + pc[4] + ":" + pc[5] + ":" + pc[6] + " "
//					+ pc[7];// +" "+pc[8]; strip TZ offset, TZ abreviation
//			senddate = readFormat.parse(fdate);
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		return senddate;
//	}
//
//	private Date getDate(String startdate, String duration) {
//
//		String[] pieces = startdate.split("\\s");
//		String[] time = pieces[4].split(":");
//
//		System.out.println("-------x-------->>Peices: " + pieces[0] + "   " + pieces[1] + "   " + pieces[2] + "   "
//				+ pieces[3] + "   " + pieces[4] + "   " + pieces[5] + "   " + pieces[6]);
//		System.out.println("--------------->>time: " + time[0] + "   " + time[1] + "   " + time[2]);
//
//		// calculate date for end water service
//		int HOUR_OF_DAY = Integer.parseInt(time[0].trim());
//		if (duration != null) {
//			if (!duration.equals(""))
//				HOUR_OF_DAY += Integer.parseInt(duration);
//		}
//
//		int year = Integer.parseInt(pieces[3].trim());
//		int month = getMonthForName(pieces[1].trim());
//		int dom = Integer.parseInt(pieces[2].trim());
//		int dow = getDayForName(pieces[0].trim());
//		int hod = HOUR_OF_DAY;
//		int min = Integer.parseInt(time[1].trim());
//
//		System.out.println("================>>>>aftersplit: " + "year: " + year + "month: " + month + "dom: " + dom
//				+ "dow: " + dow + "hod: " + hod + "min: " + min);
//
//		Calendar calendar = Calendar.getInstance();
//		// calendar.setTimeZone(TimeZone.getTimeZone(pieces[5].trim() +
//		// pieces[6].trim()));
//		calendar.set(Calendar.YEAR, year);// Integer.parseInt(pieces[3].trim()));
//		calendar.set(Calendar.MONTH, month);// getMonthForName(pieces[1].trim()) );
//		calendar.set(Calendar.DAY_OF_MONTH, dom);// Integer.parseInt(pieces[2].trim()) );
//		calendar.set(Calendar.DAY_OF_WEEK, dow);// getDayForName(pieces[0].trim()) );
//		calendar.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);
//		calendar.set(Calendar.MINUTE, min);// Integer.parseInt(time[1].trim()) );
//		calendar.set(Calendar.SECOND, 0);
//
//		Date alarmTime = calendar.getTime();
//
//		System.out.println(" tolocalstr: " + alarmTime.toLocaleString());
//		System.out.println(" toGMTStr: " + alarmTime.toGMTString());
//		System.out.println(" toUTCStr: " + alarmTime.UTC(alarmTime.getYear(), alarmTime.getMonth(), alarmTime.getDate(),
//				alarmTime.getHours(), alarmTime.getMinutes(), alarmTime.getSeconds()));
//
//		System.out.println(" tolocalstr: " + alarmTime);
//
//		return alarmTime;
//
//	}
//
//	private int getMonthForName(String name) {
//
//		int month = 0;
//
//		switch (name) {
//		case "Jan":
//			month = Calendar.JANUARY;
//			break;
//		case "Feb":
//			month = Calendar.FEBRUARY;
//			break;
//		case "Mar":
//			month = Calendar.MARCH;
//			break;
//		case "Apr":
//			month = Calendar.APRIL;
//			break;
//		case "May":
//			month = Calendar.MAY;
//			break;
//		case "Jun":
//			month = Calendar.JUNE;
//			break;
//		case "Jul":
//			month = Calendar.JULY;
//			break;
//		case "Aug":
//			month = Calendar.AUGUST;
//			break;
//		case "Sep":
//			month = Calendar.SEPTEMBER;
//			break;
//		case "Oct":
//			month = Calendar.OCTOBER;
//			break;
//		case "Nov":
//			month = Calendar.NOVEMBER;
//			break;
//		case "Dec":
//			month = Calendar.DECEMBER;
//			break;
//		default:
//			month = Calendar.JANUARY;
//			break;
//
//		}
//
//		return month;
//	}
//
//	private int getDayForName(String name) {
//
//		int day = 0;
//
//		switch (name) {
//		case "Mon":
//			day = 1;
//			break;
//		case "Tue":
//			day = 2;
//			break;
//		case "Wed":
//			day = 3;
//			break;
//		case "Thu":
//			day = 4;
//			break;
//		case "Fri":
//			day = 5;
//			break;
//		case "Sat":
//			day = 6;
//			break;
//		case "Sun":
//			day = 7;
//			break;
//
//		default:
//			day = 0;
//			break;
//
//		}
//
//		return day;
//	}


	
	

	@RequestMapping(value = "/testapi", method = RequestMethod.GET)
	public @ResponseBody String  tester() {
		
		return "777777";
	}
	
	
	
	
	
}