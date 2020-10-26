<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TEST INTERFACE</title>
 <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular.min.js"> </script> 
</head>










<body ng-app="myApp" ng-controller="myCtrl">




<p>{{message}}</p>






<script>















var text       =/^\d{1,}$/;
var numbersOnly = /^\d+$/;
var decimalOnly = /^\s*-?[1-9]\d*(\.\d{1,2})?\s*$/;
var uppercaseOnly = /^[A-Z]+$/;
var lowercaseOnly = /^[a-z]+$/;
var stringOnly = /^[A-Za-z0-9]+$/;

function testInputData(myData, restrictionType) {
	 alert(""+myData);
 //var myData = document.getElementById(myfield).value;
 if(myData!=''){
  if(restrictionType.test(myData)){
   alert('It is GOOD!');
  }else{
   alert('Your data input is invalid!');
  }
 }else{
  alert('Please enter data!');
 }
 return;
    
}














var app = angular.module("myApp", []);


    app.controller("myCtrl", function($scope,$http) {


    	  $scope.startdate = {
    	         value: new Date(2020, 01, 01, 00, 00)
    	       };
    	 
	      $scope.myTxt = "";
	     
          $scope.addRow = function () {


            
            var data;
            var sendto;
            var retmsg;
            
            


                 //data = data ='&pnme=' + $scope.pnme + '&catg=' + $scope.catg + '&code=' + $scope.code + '&fid=' + $scope.fid + '&opcode=' + $scope.opcode+ '&compare=' + $scope.compare+    '&orderx=' + $scope.orderx + '&price=' + $scope.price + '&quantity=' + $scope.quantity  +'&startdate=' + $scope.startdate.value     +'&enddate=' + $scope.enddate.value;
                 data = '&comparectyp=' + $scope.comparectyp+ '&catg=' + $scope.catg + '&code=' + $scope.code + '&compare=' + $scope.compare+   '&price=' + $scope.price  +'&startdate=' + $scope.startdate.value     +'&enddate=' + $scope.enddate.value;
                 sendto = 'calc';
                 retmsg = 'Product Type Successfully Added';
                
           
           
           // $scope.myTxt = "You clicked submit!s";		
			 	

            $http({

            	method: 'POST',

            	url: sendto,//'addptype',
            	
                data: data,

                headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}

            	}).then(function success(response) {

            		 $scope.message = response.data.message;//"Success: Order Created";            		 
            		 $scope.companies = response;

            	}, function error(response) {

            		  $scope.message= "Error: "+response.data.message;

            	});

            	 
        	$scope.name='';
        	$scope.fid='';
        	$scope.duration='';
        	//$scope.startdate='';
        	

          }


               
          

    	
   
});
</script>

<style>
input {
  width: 100%;
}
<style>
table {
  width: 100%;
 
  
}
tr {
text-align: left;
}


td{
word-wrap:break-word;

}






label {
text-align: right;
}
button {
align:left
}

</style>

<form class="form-horizontal" role="form" ng-submit="addRow()"  id="fx">

<table>


<tr>


<td>
<br>
<br>
<br>
</td>

</tr>








<td>
   
      	<div class="form-group">
          <label class="col-md-2 control-label">Category/Name Product Type (Comma seperated list 'Fruit,Dairy' Or 'Milk,Apples')</label>
             <div class="col-md-4">
                 <select  id="cpselect" class="form-control" name="comparectyp"
                  ng-model="comparectyp" >
                      <option>Category</option>
                       <option>Name</option>
   					</select>
  				 </div>
     	</div>
</td>










<tr>
<td>

<div class="form-group">
          <!-- label class="col-md-2 control-label">categories</label -->
             <div class="col-md-4">
                 <input type="text" class="form-control" name=catg id="catgs" placeholder="Fruit,Dairy | Milk,Apples"
                  ng-model="catg" />
             </div>
     </div
     
     >
   <td>
 </td>
 
  <tr>
<td>       
     </div>
     <div class="form-group">
          <label class="col-md-2 control-label">products (by code)</label>
             <div class="col-md-4">
                 <input type="text" class="form-control" name="code" placeholder="WWAPPLE93,11SHIRTKMRT"
                  ng-model="code" />
             </div>
     </div>
     
 <td>
 </td>
     
     
     
 <tr>    
     <td>
     
     
     <Table>
     <tr>
     <td>  <label >price</label>
     </td>
     </tr>
     
     <tr>
     <td>
   
      	<div class="form-group">
          <label class="col-md-2 control-label"></label>
             <div class="col-md-4">
                 <select  id="cpselect" class="form-control" name="comp"
                  ng-model="compare" >
                      <option>greaterthan</option>
                       <option>lessthan</option>
						<option>equals</option>
   					</select>
  				 </div>
     	</div>
</td>
<td style="width: 362px; ">
        
          <div class="form-group" style="width: 282px; ">
          <label class="col-md-2 control-label"> </label>
             <div class="col-md-4">
                 <input type="text" class="form-control" name="price"    style="margin:0px 0 0; width:70px;"
                  ng-model="price" />  <a style="color:red">(manditory) '> 0 for any'</a>
             </div>
     </div>   
     
     </td>
     
     </table>
     </td>
 </tr>
     
     
     
     
     
     
     
     
     
     <tr>
<td>
     

     <div class="form-group">
     	<label class="col-md-2 control-label">Start date time</label>
     	<div>
   			<input type="datetime-local" id="exampleInput" name="startdate" ng-model="startdate.value"
   			placeholder="yyyy-MM-ddTHH:mm" min="2020-01-01T00:00" max="2099-12-31T00:00" required />
   			<div role="alert">
     			<span class="error" ng-show="myForm.input.$error.required">Required!</span>
     			<span class="error" ng-show="myForm.input.$error.date">Not a valid date!</span>
            </div>
            
      </div>
      </div>
    
    </tr>
</td>
    
    
           
           <tr>
<td>
            
     <div class="form-group">
     	<label class="col-md-2 control-label">end date time</label>
     	<div>
   			<input type="datetime-local" id="exampleInput" name="enddate" ng-model="enddate.value"
   			placeholder="yyyy-MM-ddTHH:mm" min="2020-01-01T00:00" max="2099-12-31T00:00" required />
   			<div role="alert">
     			<span class="error" ng-show="myForm.input.$error.required">Required!</span>
     			<span class="error" ng-show="myForm.input.$error.date">Not a valid date!</span>
        </div>
            
     </div>
  
    </div>
     
        </tr>
</td> 
     
         <tr>
<td>


     <div class="form-group">
           <div style="padding-right:150px ;  ">
              <input type="submit" value="Submit" class="btn btn-primary"  id="k3" />
           </div>
     </div>
     
     
     


</td>
</tr>
</table>


</form>







<div ng-app="myApp" ng-controller="myCtrl">
<td style="vertical-align: top">
<h2><h3><p>{{message}}</p></h3>

						<table class="table" style="table-layout:fixed;width:100%" >
							<tbody>
							<tr><td></td><td></td><td></td><td></td><td style="text-align:center">Report</td></tr>
							<tr>
								<th style="border-top: 1px solid lightgrey;border-left: 1px solid lightgrey;">Category &nbsp;&nbsp;&nbsp;&nbsp;
					 			</th>
								<th style="border-top: 1px solid lightgrey;border-left: 1px solid lightgrey;"> Name &nbsp;&nbsp;&nbsp;&nbsp;
								</th>
								<th style="border-top: 1px solid lightgrey;border-left: 1px solid lightgrey;"> categoryId &nbsp;&nbsp;&nbsp;&nbsp;
								</th>
								<th style="border-top: 1px solid lightgrey;border-left: 1px solid lightgrey;">Product code &nbsp;&nbsp;&nbsp;&nbsp;
								</th>
								<th style="border-top: 1px solid lightgrey;border-left: 1px solid lightgrey;">orders product code &nbsp;&nbsp;&nbsp;&nbsp;
								</th>
							     <th style="border-top: 1px solid lightgrey;border-left: 1px solid lightgrey;">Buy/Sell &nbsp;&nbsp;&nbsp;&nbsp;
								</th>
								<th style="border-top: 1px solid lightgrey;border-left: 1px solid lightgrey;">price &nbsp;&nbsp;&nbsp;&nbsp;
								</th>
								<th style="border-top: 1px solid lightgrey;border-left: 1px solid lightgrey;">quantity &nbsp;&nbsp;&nbsp;&nbsp;
								</th>
								<th style="border-right: 1px solid lightgrey;border-top: 1px solid lightgrey;border-left: 1px solid lightgrey;">order date &nbsp;&nbsp;&nbsp;&nbsp;
								</th>
							</tr>
							<tr><br>
							</tr>
							<tr ng-repeat="company in companies" >
							    <td style="border-left: 1px solid lightgrey;">{{ company.catg }}</td>
							    <td style="border-left: 1px solid lightgrey;">{{ company.pnme }}</td>
							    <td style="border-left: 1px solid lightgrey;">{{ company.fid }}</td>
							    <td style="border-left: 1px solid lightgrey;word-wrap:break-word;max-width:30px">{{ company.code }}</td> 
							    <td style="border-left: 1px solid lightgrey;">{{ company.opcode }}</td>			
							    <td style="border-left: 1px solid lightgrey;">{{ company.orderx }}</td>					    
							    <td style="border-left: 1px solid lightgrey;">{{ company.price }}</td>
							    <td style="border-left: 1px solid lightgrey;">{{ company.quantity }}</td>   							
							    <td style="border-left: 1px solid lightgrey;border-right: 1px solid lightgrey;">{{ company.startdate }}</td>
							</tr><!-- end ngRepeat: company in companies -->
							
						
						
						
							
						</tbody>
						
						
						</table>
					</td>
					
					
					
					 
   </div>
   
   
   
   
   
   
  
						
 
			  			
			

   
   
   
   
   
   
</body>
</html>