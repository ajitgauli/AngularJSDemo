<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
 <script src="http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/sha256.js"></script>
<!--  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
 <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>  
 <script src="/AngularJSDemo/js/jquery-1.10.2.min.js"></script>-->
 <script src="/AngularJSDemo/js/angular.min.1.0.8.js"></script>
 <script src="/AngularJSDemo/js/jquery.ebcaptcha.js"></script>
<script src="/AngularJSDemo/js/app.js"></script>

<!--  <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.1/css/bootstrap.min.css">-->

<!--<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.1/js/bootstrap.min.js"></script>
<script src="/twitter-bootstrap/twitter-bootstrap-v2/js/bootstrap-popover.js"></script>
<script type="text/javascript" src="path/to/bootstrap-confirmation.js"></script>
<script src="/AngularJSDemo/js/bootstrap-modal.js"></script>
<script src="//twitter-bootstrap/twitter-bootstrap-v2/js/bootstrap-tooltip.js"></script>
<script src="/AngularJSDemo/js/bootbox.min.js"></script>-->
<script src="/AngularJSDemo/js/ng-grid-2.0.7.debug.js"></script>
<script src="/AngularJSDemo/js/ng-csv.min.js"></script>
<script src="/AngularJSDemo/js/angular-sanitize.min.js"></script>

<link rel="stylesheet" href="/AngularJSDemo/css/ng-grid.min.css">

 </head>
<div ng-app="demo">  
    <div ng-controller="DemoCtrl" class="row-fluid"  id="container"  ng-cloak>
    <div ng-hide="showDataTable">
    <div class="signin span6" ng-hide="showEditForm">
    <fieldset>
      <form name="signInForm" novalidate>
        <legend>Sign In to Edit/Unsubscribe</legend>     
        <div class="alert alert-error" ng-show="errorMessage">  
        <a class="close" data-dismiss="alert">×</a>  
        {{errorMessage}}  
        </div>
        <div class="alert alert-success" ng-show="successMessage">  
         <a class="close" data-dismiss="alert">×</a>  
            {{successMessage}}  
        </div> 

       <label>Email <span class="required" >*</span></label>
       
        <input type="email" ng-model="existingUser.email" required><br/>
        
        <label>Password <span class="required" >*</span></label>
        
        <input type="password" ng-model="existingUser.password" required><br/><br/>       
         
        <button ng-disabled="signInForm.$invalid" class="btn btn-primary" ng-click="signInUser($event)">Sign In</button>
      </form>
     </fieldset>
     <div class="alert alert-error" ng-show="errorLoadingRecords">  
        <a class="close" data-dismiss="alert">×</a>  
        {{errorLoadingRecords}} 
     </div> 
     <br/>
     <button class="btn btn-primary" ng-click="getAllUsers($event)" >View All Subscribers</button>
     </div>
    
     
     
     <div class="edit span6" ng-show="showEditForm">
    <fieldset>
      <form name="editForm" id="editForm" novalidate>
        <legend>Edit Information</legend>
        <div class="alert alert-error" ng-show="errorEditMessage">  
         <a class="close" data-dismiss="alert">×</a>  
            {{errorEditMessage}}  
        </div> 
        <label>First Name <span class="required" >*</span></label>        
        <input type="text" ng-model="existingUser.firstName" name="firstName" required><br/>
        
        <label>Last Name <span class="required">*</span></label>       
        <input type="text" ng-model="existingUser.lastName" name="lastName" required><br/>
        
        <label class="checkbox">
       <input type="checkbox" ng-model="editPassword" ng-click="toggle(editPassword)">
       Change Password.
       </label>  <br/>
       <div class="editPassword" ng-show="editPassword">
       <label>Password</label>        
        <input type="password" ng-model="existingUser.password" name="password" ng-minlength='3'><br/>        
        
        <label>New Password </label>        
        <input type="password" ng-model="existingUser.newPassword" name="newPassword" ng-minlength='3'><br/>
        <p class="help-block" ng-show="editForm.newPassword.$error.minlength || editForm.newPassword.$invalid">
        Password must be at least 3 characters.
        </p>
        
        <label>Confirm New Password</label>        
        <input type="password" ng-model="existingUser.newPasswordConfirm" name="newPasswordConfirm" ng-minlength='3' ng-invalid='existingUser.newPassword != existingUser.newPasswordConfirm'><br/>
        <p class="help-block" ng-show="existingUser.newPassword != existingUser.newPasswordConfirm">
        Passwords do not match !
        </p>
         </div>       
       <label>Email <span class="required" >*</span></label>      
        <input type="email" ng-model="existingUser.email" name="email" required><br/>
        
        <label>Phone</label>
        <input type="text" ng-model="existingUser.phone" name="phone" ng-pattern="/^\(?(\d{3})\)?[ .-]?(\d{3})[ .-]?(\d{4})$/"><br/>
        <p class="help-block" ng-show="registerForm.phone.$invalid">
        Phone must be a 10 digit number.
        </p> <br/>       
        <div class="button-holder">					
		<button ng-disabled="editForm.$invalid" class="btn btn-primary" ng-click="editUser($event)">Save</button>
		<button class="btn btn-primary" ng-click="goBack($event)">Cancel</button>
		<button data-toggle="modal"  data-target="#popup"class="btn btn-primary" id ="newsletter_unsubscribe">Unsubscribe</button>		
	    </div>
       
      </form>
     </fieldset>
    
     <div id="popup" class="modal hide fade in" style="display: none; ">  
<div class="modal-header">  
<a class="close" data-dismiss="modal">×</a>  
<h3>Warning</h3>  
</div>  
<div class="modal-body">  
<h4>Are you sure you want to unsubscribe?</h4>  
<p>Click yes to confirm.</p>                
</div>  
<div class="modal-footer">  
<button ng-click="deleteUser()" class="btn btn-primary" data-dismiss="modal">Yes</button>  
<button href="#" class="btn btn-primary" data-dismiss="modal">No</button>  
</div>  
</div>  

     </div>
     
    <div class="register span6" ng-show="!registered && !showEditForm">
    <fieldset>
      <form name="registerForm" id="registerForm" novalidate>
        <legend>Subscribe to Campus Newsletter</legend>
        <div class="alert alert-error" ng-show="errorRegisterMessage">  
         <a class="close" data-dismiss="alert">×</a>  
            {{errorRegisterMessage}}  
        </div> 
        <label>First Name <span class="required" >*</span></label>        
        <input type="text" ng-model="newUser.firstName" name="firstName" required><br/>
        
        <label>Last Name <span class="required">*</span></label>
       
        <input type="text" ng-model="newUser.lastName" name="lastName" required><br/>
        <label>Password <span class="required">*</span></label>
        
        <input type="password" ng-model="newUser.password" name="password" ng-minlength='3' required><br/>
        <p class="help-block" ng-show="registerForm.password.$error.minlength || registerForm.password.$invalid">
        Password must be at least 3 characters.
        </p>
              
       <label>Email <span class="required" >*</span></label>
      
        <input type="email" ng-model="newUser.email" name="email" required><br/>
        
        <label>Phone</label>
        <input type="text" ng-model="newUser.phone" name="phone" ng-pattern="/^\(?(\d{3})\)?[ .-]?(\d{3})[ .-]?(\d{4})$/"><br/>
        <p class="help-block" ng-show="registerForm.phone.$invalid">
        Phone must be a 10 digit number.
        </p> <br/>
        
       <label class="checkbox">
       <input type="checkbox" ng-model="newUser.terms" required>
       I accept TACC's <a href="#">Terms of Service</a>.
       </label>  <br/> 
        <button ng-disabled="registerForm.$invalid || !validCaptcha" class="btn btn-primary" ng-click="registerUser($event)">Submit</button>
      </form>
     </fieldset>
     </div>
     
     <script type="text/javascript">
      $(function(){
    $('#registerForm').ebcaptcha();
});
     </script>
      <div class="registerSuccess span6" ng-show="registered && !showEditForm" >
      <div class="alert alert-success">
      <a class="close" data-dismiss="alert">x</a>
    <h5> Congratulations {{newUser.firstName}} {{newUser.lastName}}! <br/>  
     You've successfully subscribed to our newsletter.<br/>
     You may now sign in to edit your informaton or unsubscribe !</h5>
      </div> 
      </div>
      </div>
      <div ng-show="showDataTable">
      <h4>Current Subscribers</h4><br/>
       <button  class="btn btn-medium" ng-csv="myData" csv-header="['FirstName', 'LastName', 'Email', 'Phone']" filename="TACC_Newsletter_Subscribers.csv">Download CSV</button><br/><br/>	  	
      <div class="gridStyle" ng-grid="gridOptions"></div>
      <div class="export-buttons" ng-show="loadingIsOn">
					<img src="/sample-theme/images/loading.gif" />			
	  </div>
      </div>
    
</div>
</div>