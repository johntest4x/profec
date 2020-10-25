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







var sumbitType;
function bt1(){
	//alert("button1 was clicked");
sumbitType='b1'
}
function bt2(){
	//alert("button2 was clicked");
sumbitType='b2'
}
function bt3(){
	//alert("button2 was clicked");
sumbitType='b3'
}










var app = angular.module("myApp", []);


    app.controller("myCtrl", function($scope,$http) {


    	 //$scope.orders = ["Order", "Purchase"];
    	  $scope.startdate = {
    	         value: new Date(2020, 01, 01, 00, 00)
    	       };
    	 
	      //$scope.myTxt = "--------";
	     
          $scope.addRow = function () {


			//if(document.getElementById("faid").value ===""){
			//	$scope.fid=0;
			//	alert("error insode faid?");
			//	}
            
            var data;
            var sendto;
            var retmsg;
            
            
            
            $scope.fid  = document.getElementById("faid").value;
            $scope.opcode  = document.getElementById("cid").value;
            

           
         //   alert("->"+$scope.orderx);
            
            if(sumbitType =='b1' ){                       
                 data ='&pnme=' + $scope.pnme + '&catg=' + $scope.catg;// + '&code=' + $scope.code + '&fid=' + $scope.fid + '&opcode=' + $scope.opcode+ '&orderx=' + $scope.orderx+ '&price=' + $scope.price + '&quantity=' + $scope.quantity  +'&startdate=' + $scope.startdate.value;
                 sendto = 'addptype';
         //        retmsg = 'Product Type Successfully Added';
         //        alert("1$scope.fid:"+$scope.fid);
            }else if(sumbitType =='b2' ){
                 data ='&pnme=' + $scope.pnme + '&catg=' + $scope.catg + '&code=' + $scope.code + '&fid=' + $scope.fid  + '&opcode=' + $scope.opcode+ '&orderx=' + $scope.orderx+ '&price=' + $scope.price + '&quantity=' + $scope.quantity  +'&startdate=' + $scope.startdate.value;
				 sendto = 'addptype2';
		//		 retmsg = 'New Product Code Successfully Added';
		//		 alert("2$scope.fid:"+$scope.fid);
		//		 alert("2$scope.fid:"+$scope.faid);
            }else if(sumbitType =='b3' ){
                 data ='&pnme=' + $scope.pnme + '&catg=' + $scope.catg + '&code=' + $scope.code + '&fid=' + $scope.fid  + '&opcode=' + $scope.opcode + '&orderx=' + $scope.orderx+ '&price=' + $scope.price + '&quantity=' + $scope.quantity  +'&startdate=' + $scope.startdate.value;
				 sendto = 'addptype3';
		//		 retmsg = 'New order Successfully Added';
		//		 alert("2$scope.fid:"+$scope.fid);
		//		 alert("2$scope.fid:"+$scope.faid);
            }
            
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
        	
        	document.getElementById('frame_id').contentDocument.location.reload(true);
			document.getElementById('frame_id2').contentDocument.location.reload(true);
          }


               
          

    	
   
});
</script>



<form class="form-horizontal" role="form" ng-submit="addRow()"  id="fx">




<table>

<tr>








<td style="border-top: 4px solid black;border-left: 4px solid black;border-right: 4px solid black;">



						<iframe id="frame_id" src="viewL1" width="96%"; height="100";>
   
   						</iframe>
</td>



   

<td style="border-top: 4px solid red;border-left: 4px solid red;border-right: 4px solid red;border-bottom: 4px solid red;">
New Product
    <div class="form-group">
        <label class="col-md-2 control-label">new name</label>
            <div class="col-md-4">
                <input type="text" class="form-control" name="pnme"
                 ng-model="pnme" />
            </div>
    </div>
    <div class="form-group">
        <label class="col-md-2 control-label">new category</label>
            <div class="col-md-4">
                <input type="text"  class="form-control" name="catg"
                 ng-model="catg" />
            </div>
    </div>
        <div class="form-group">
           <div style="padding-left:0px">
              <input type="submit" value="Submit" class="btn btn-primary" onclick="bt1();" id="koo"/>
           </div>
     </div>
 </td>

    
 <td style="width: 500px;">
     
     <a style="width: 496px;text-align:right;valign:center; height: 198px"  href="testapi2/a" target="_blank"><h2>Load Data (Tests)</h2></h3></a>
     <a style="width: 496px;text-align:right;valign:center; height: 198px"  href="calculations" target="_blank"><h2>calculations</h2></h3></a>
     
     </td>


  
</tr>






<tr>

<td style="border-bottom: 4px solid black;border-left: 4px solid black;border-right: 4px solid black;">
    <div class="form-group">
        <label class="col-md-2 control-label">product type Id</label>
            <div class="col-md-4">
                <input type="text" id="faid" class="form-control" name="fid" value="p"
                 ng-model="fid" />
            </div>
    </div>

     <div class="form-group">
        <label class="col-md-2 control-label">product code</label>
            <div class="col-md-4">
                 <input type="text" class="form-control" name="code"
                  ng-model="code" />
            </div>
    </div>
         <div class="form-group">
           <div style="padding-left:0px">
              <input type="submit" value="Submit" class="btn btn-primary" onclick="bt2()"; id="k2" />
           </div>
     </div>
</td>
<td>

</td>
<td>
</td>
</tr>



<tr>
<td>
<br>
<br>
<br>
</td>

</tr>






<tr>    

 <td>  
 <table>
<tr>
<td>
						<iframe id="frame_id2" src="viewL2" width="100%"; height="100";>
   
   						</iframe>
</td>
<td>
</table>
</tr>
</td>

<tr>


<td>

<div class="form-group">
          <label class="col-md-2 control-label">Product Code</label>
             <div class="col-md-4">
                 <input type="text" class="form-control" name="opcode" id="cid"
                  ng-model="opcode" />
             </div>
     </div>
     
          <div class="form-group">
          <label class="col-md-2 control-label">order type</label>
             <div class="col-md-4">
                 <select  id="otselect" class="form-control" name="ordert"
                  ng-model="orderx" >
                      <option>BUY</option>
    <option>SELL</option>

   </select>
             </div>
     </div>
     
     
     
     <div class="form-group">
          <label class="col-md-2 control-label">quantity</label>
             <div class="col-md-4">
                 <input type="text" class="form-control" name="quantity" 
                  ng-model="quantity" />
             </div>
     </div>
     
          <div class="form-group">
          <label class="col-md-2 control-label">price</label>
             <div class="col-md-4">
                 <input type="text" class="form-control" name="price"
                  ng-model="price" />
             </div>
     </div>
     

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
     
     
     
     


     <div class="form-group">
           <div style="padding-left:110px">
              <input type="submit" value="Submit" class="btn btn-primary" onclick="bt3()"; id="k3" />
           </div>
     </div>
     
     
     


</td>
</tr>
</table>


</form>







<div ng-app="myApp" ng-controller="myCtrl">
<td style="vertical-align: top">
<h2><h3><p>{{message}}</p></h3>

						<table class="table" >
							<tbody>
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
							    <td style="border-left: 1px solid lightgrey;">{{ company.code }}</td> 
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