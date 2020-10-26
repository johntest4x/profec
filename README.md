# https://drive.google.com/file/d/1APn6uD_3By4AEqkOPQp-dkrj2txGJunD/view?usp=sharing   applic download(~60M)     https://drive.google.com/file/d/1k3V1VZmwWplPL-EsnRPStSai5uSkFtey/view?usp=sharing     VisC-2013 redistr

********************************************************************
Update:                                                            *
New application link (~60M) more recent lighter postgress          * 
New link to VC2-013 redistr package (resolves MFC classes issues)  *
********************************************************************


There is Issue installing pg-embedded some versions windows (see):  

https://github.com/opentable/otj-pg-embedded------ 
If you experience difficulty running otj-pg-embedded tests on Windows, make sure you've installed the appropriate MFC redistributables.

Microsoft Site
Github issue discussing this  https://github.com/opentable/otj-pg-embedded/issues/65 
---------------------------------------------------    


Resolution:
https://support.microsoft.com/en-us/help/4032938/update-for-visual-c-2013-redistributable-package  
  
English - United States     https://aka.ms/highdpimfc2013x64enu  <<-----------download this or use the supplied download   





																												.   
																												.  
sample application:																							.   
.																												.   
.																												.  
.																												.  
Run the application:                                                                            .    
java - jar {applicationname}.war                    													.  
Ctr^C to stop                                                                                   .    
.																												.  
.																												.  
.																												.  
Buuild from source:                                                                             .  
eclipse:mvn  clean                                                                              .  
eclipse:mvn  package spring-boot:repackage                                                      .    
.																												.  
.																												.  
.																												.  
Dependencies:																								   .    
Tomcat embedded																								.
Postgress embedded																						        
  .																												.  
  .																												.  
User Instruction:   
  .																												.   
  .																												.   
Setup test:-----------------   																			.  
goto http://localhost:8080/   																				.  
select link 'load data(Tests)'     																		.  
select link '$ calculations'   																			.  
    price: greaterthan 0, end date time: 'today'   														.  
.																										       .   
.																										       .   
OR        
.																										       .     
Manual:----------------------    .													                     .   
.																										       .       
restart application (    java - jar {applicationname}.war )  
goto http://localhost:8080/  
1) Add product types:     
	enter product type data (name eg milk,category eg Dairy) repeat.      

2) Add products:  
	click on (product type list) to select any one  
     -enter new product code  eg "WW_LLMilk32"  
       
3) Add Product orders:    
	click on product list to select a product code       
     -enter (quantity,price,date)  
     
select link '$ calculations'    
    price: greaterthan 0, end date time: 'today'     
    Or Filter search as required       
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
       
         
    
    
    
    
    
    
    





