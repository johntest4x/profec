
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
public class FmarController {
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
	
	
	

	@GetMapping(value = "/{code}", produces = MediaType.TEXT_PLAIN_VALUE)
	public String index(@PathVariable("code") String code, @RequestParam(value = "v1", required = false) String v1,
			@RequestParam(value = "v2", required = false) String v2) {

		String oup = "The output to test debug1";

	
		if ("c".equals(code))
			oup = testC();
		if ("d".equals(code))
			oup = testD();
		if ("e".equals(code))
			oup = testE();
		
		

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

	

	

	@RequestMapping(value = "/testapi", method = RequestMethod.GET)
	public @ResponseBody String  tester() {
		
		return "777777";
	}
	
	
	
	
	
}