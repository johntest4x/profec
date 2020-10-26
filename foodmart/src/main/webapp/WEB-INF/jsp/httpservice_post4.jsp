<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular.min.js"> </script> 
</head>










<body style="background-color:#ecf4f3;">




<script>


var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope, $http) {
  $http.get("show/products")
  .then(function (response) {
	  $scope.companies = response.data.companies;
	  $scope.message = response.data.message;
	  }, function error(response) {

	 		$scope.message= "Error: "+response.data.message;

	 		 });

  var table = document.getElementsByTagName("table")[0];
  var tbody = table.getElementsByTagName("tbody")[0];
 
  tbody.onclick = function (e) {
      e = e || window.event;
     
      var data = [];
      var target = e.srcElement || e.target;
      while (target && target.nodeName !== "TR") {
          target = target.parentNode;
      }
      if (target) {

    	  var ocells =tbody.getElementsByTagName("td");
          for (var i = 0; i < ocells.length; i++) {
              //data.push(cells[i].innerHTML);
              ocells[i].style.color='black';
          }

    	  
          var cells = target.getElementsByTagName("td");
          for (var i = 0; i < cells.length; i++) {
              //data.push(cells[i].innerHTML);
              cells[i].style.color='blue';
          }

          
      }

      parent.document.getElementById('cid').value = cells[2].innerHTML;
      parent.document.getElementById('cid').style.color = "blue";
  };

  
});


</script>











<div ng-app="myApp" ng-controller="myCtrl">
<td style="vertical-align: top">
<h3 style="text-align:center;color:green"><p>{{message}}</p></h3>


						<table class="table">
							<tbody>
							
							<tr>
								<th>Type
								</th>
								<th>Category
								</th>
								<th>Id
								</th>
								
							</tr>
							
							<tr ng-repeat="Ptype in companies" >
							    <td>{{ Ptype.name }}</td>
							    <td>{{ Ptype.category }}</td>
    							<td>{{ Ptype.id }}</td>
							</tr><!-- end ngRepeat: company in companies -->
							
							
						</tbody></table>
					</td>
   
   
   </div>
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
</body>
</html>