var module = angular.module('demo',['ngGrid','ngCsv']);
module.controller("DemoCtrl",function ($scope,$http) {	 
//
  $scope.myData=[];
	$scope.gridOptions = { 
			data: 'myData',
			showFooter: true,
			showFilter: true,
			showHeader: true,
			showColumnMenu: false,			
			showGroupPanel: false,
			showSelectionCheckbox: false,
			enablePaging: false,
			enableHighlighting: true,
			enableRowReordering: true,
			enableColumnResize: true,					
	        columnDefs: [
	                    { field: "firstName", width: 100 , displayName : "First Name" },
	                    { field: "lastName", width: 100, displayName : "Last Name"  },
	                    { field: "email", width: 100, displayName : "Email"  },
	                    { field: "phone", width: 100, displayName : "Phone"  }
	                    ]
				};
$scope.registered = false;
$scope.validCaptcha = false;
$scope.showEditForm = false;
$scope.editPassword = false;
$scope.errorMessage = null;
$scope.successMessage = null;
$scope.showDataTable = false;
$scope.allRecords;
$scope.data;
  $scope.newUser={
		  firstName:'',
		  lastName:'',
		  password:'',
		  email:'',
		  phone:'',
		  terms:false
  };
  $scope.existingUser={
		  firstName:'',
		  lastName:'',
		  password:'',
		  email:'',
		  phone:''
  };
  
  //service call to register a new user
  $scope.registerUser = function(event){
	  console.log("register");
     
  $http({
	    method: "POST",
	    url: "/AngularJSDemo/rest/service/saveOrUpdateUser",
	    headers: {'Content-Type':'application/json'},
	    data: {
	    	firstName:$scope.newUser.firstName,
	    	lastName:$scope.newUser.lastName,
	    	email:$scope.newUser.email,
	    	password:CryptoJS.SHA256($scope.newUser.password).toString(CryptoJS.enc.Base64),
	    	//password:hashedPasswordNewUser,	    	
	    	phone:$scope.newUser.phone
	    	
	    	}
	}).success(function(data, status, headers, config) {	    
	    if(data=='EMAIL ALREADY EXISTS'){
			$scope.errorRegisterMessage="This email address is already taken !"	;
			}			
		else if(data=='SUCCESS'){
				$scope.errorMessage=null;				
				$scope.data = data;	    	    
				$scope.registered=true;
			}
		else{
			$scope.errorRegisterMessage=data;	
			}
	}).error(function(data, status, headers, config) {
	    $scope.status = status;
	    console.log("status: "+status);
	});
  };
  
  //service call to sign existing user in
  $scope.signInUser = function(event){
	  console.log("Signing in...");
	 
	  $scope.successMessage='';
  $http({
	    method: "POST",
	    url: "/AngularJSDemo/rest/service/login",
	    headers: {'Content-Type':'application/json'},
	    data: {	    	
	    	email:$scope.existingUser.email,
	    	//password:$scope.existingUser.password,
	    	password:CryptoJS.SHA256($scope.existingUser.password).toString(CryptoJS.enc.Base64),   	
	    	}
	}).success(function(data, status, headers, config) {
		
		if(data=="NULL_USER"){
			$scope.errorMessage="This email does not exist. Please register to subscribe \n";
		}
		else if(data=="INVALID_PASSWORD"){
			$scope.errorMessage="Invalid password. Please try again! \n";
		}		
		else if(data != null){	   
		console.log(data);
	    $scope.existingUser = data;	
	    $scope.editPassword = false;
	    $scope.showEditForm=true;
	    $scope.errorMessage=null;
	    $scope.errorEditMessage='';
	   
		}
		else{			
			$scope.errorMessage="Server Error. Please try again! \n";
		}
	}).error(function(data, status, headers, config) {
	    $scope.status = status;
	    console.log("status: "+status);
	});
  };
  
  //service call to edit user info
  $scope.editUser = function(event){
	  console.log("edit");
	  console.log($scope.existingUser.password);
	  console.log($scope.existingUser.newPassword);
	  console.log($scope.existingUser.newPasswordConfirm);
	  console.log(CryptoJS.SHA256($scope.existingUser.password).toString(CryptoJS.enc.Base64));
	  console.log(CryptoJS.SHA256($scope.existingUser.newPassword).toString(CryptoJS.enc.Base64)),
	  console.log(CryptoJS.SHA256($scope.existingUser.newPasswordConfirm).toString(CryptoJS.enc.Base64)),
  
  $http({
	    method: "POST",
	    url: "/AngularJSDemo/rest/service/saveOrUpdateUser",
	    headers: {'Content-Type':'application/json'},
	    data: {
	    	firstName:$scope.existingUser.firstName,
	    	lastName:$scope.existingUser.lastName,
	    	email:$scope.existingUser.email,
	    	password:CryptoJS.SHA256($scope.existingUser.password).toString(CryptoJS.enc.Base64),
	    	newPassword:CryptoJS.SHA256($scope.existingUser.newPassword).toString(CryptoJS.enc.Base64),
	    	newPasswordConfirm:CryptoJS.SHA256($scope.existingUser.newPasswordConfirm).toString(CryptoJS.enc.Base64),
	    	phone:$scope.existingUser.phone,
	    	id:$scope.existingUser.id
	    	}
	}).success(function(data, status, headers, config) {
		if(data=='EMAIL ALREADY EXISTS'){
		$scope.errorEditMessage="This email address is already taken !"	;
		}		
		else if(data=='SUCCESS'){
			$scope.errorMessage=null;
			$scope.successMessage="Your profile changes were successfully saved.";
			$scope.data = data;
			$scope.existingUser.password='';
		    $scope.showEditForm=false;
			}
	    else{
			$scope.errorEditMessage=data;	
			}
	    
	}).error(function(data, status, headers, config) {
	    $scope.status = status;
	    console.log("status: "+status);
	});
  };
  
//service call to delete user 
  $scope.deleteUser = function(event){
	  console.log("delete");
  
  $http({
	    method: "POST",
	    url: "/AngularJSDemo/rest/service/removeUser",
	    headers: {'Content-Type':'application/json'},
	    data: {	    	
	    	id:$scope.existingUser.id
	    	}
	}).success(function(data, status, headers, config) {
		if(data=='SUCCESS'){
		$scope.errorMessage="";
		$scope.errorRegisterMessage="";
		$scope.registered=false;
	    $scope.showEditForm=false;
	    $scope.existingUser=null;
		$scope.successMessage="Your have successfully unsubscribed!";
			}
	    
	}).error(function(data, status, headers, config) {
	    $scope.status = status;
	    console.log("status: "+status);
	});
  };
  
//service call to get all subscribers 
  $scope.getAllUsers = function(event){
	  console.log("getAllSubscribers()");
	  $scope.showDataTable = true;
	  $scope.loadingIsOn = true;
  $http({
	    method: "GET",
	    url: "/AngularJSDemo/rest/service/getAllUsers",
	    headers: {'Content-Type':'application/json'}
	    
	}).success(function(data, status, headers, config) {
		 if(data=='ERROR'){
			$scope.errorLoadingRecords="There was an error loading the records !";	
			}
		else{				
            $scope.errorLoadingRecords=null;	    
		    $scope.myData=data;	
           // $scope.myData=[{"firstName":"p","lastName":"p","email":"p@P.com","phone":"1111111111"},{"firstName":"p","lastName":"p","email":"p@P.com","phone":"1111111111"}];
		    console.dir(JSON.stringify(data));		
		    $scope.loadingIsOn = false;
		    
		   // $scope.$apply();	  
			}
	    
	}).error(function(data, status, headers, config) {
	    $scope.status = status;
	    console.log("status: "+status);
	});
  
  };
  
  $scope.goBack = function(event){
	  $scope.showEditForm=false;
	  $scope.existingUser=null;
		  };		  
  

$scope.isOutOfFocus = function(event){
	console.log(event);   
	  };
	  
$scope.toggle = function(flag){
	flag = !flag;
	return flag;
	};

$scope.alertConfirmation = function(){
		var answer = confirm("Are you sure");
		console.log("Answer: "+answer);
		};
		 	 
		
});