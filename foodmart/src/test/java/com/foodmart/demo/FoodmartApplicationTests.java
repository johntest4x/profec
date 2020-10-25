package com.foodmart.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.foodmart.app.FoodmartApplication;
import com.foodmart.app.data.Entity.Product;
import com.foodmart.app.data.Entity.Ptype;
import com.foodmart.app.web.FmartController;

//@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = com.foodmart.app.FoodmartApplication.class)
public class FoodmartApplicationTests  {


	
	
	//@Autowired
	private MockMvc mvc;
	
	 @InjectMocks
	    private FmartController gc;

	 @BeforeEach
	    public void setup() {
	        mvc = MockMvcBuilders.standaloneSetup(gc).build();
	        MockitoAnnotations.initMocks(this);
	    }
	
	@Test
	public void contextLoads() {		
		assert(true);		
	}
	
	@Test
	public void getAllEmployeesAPI() throws Exception 
	{
		
	//	mvc.perform(get("/testapi")).andExpect(status().isOk());		
		mvc.perform(get("/testapi")).andExpect(content().string("777777")); //("=============>>enda");
		

		
		
//		RestTemplate restTemplate = new RestTemplate();        
//        final String baseUrl = "http://localhost:"+9090+"/";
//        URI uri = new URI(baseUrl);
	     
	}
	
	
	
	

}
