(function($){

	jQuery.fn.ebcaptcha = function(options){

		var element = this; 
		var checkbox = $(this).find('.checkbox');
		$('<span id="ebcaptchatext"></span><input type="text" class="required" id="ebcaptchainput" data-valid="false" style="border-left-width:1px;width:3em;height:10px" required/><br/><br/>').insertBefore(checkbox);
		
		var input = this.find('#ebcaptchainput'); 
		var label = this.find('#ebcaptchatext'); 
		
		$(element).find('button').attr('ng-disabled','true'); 

		
		var randomNr1 = 0; 
		var randomNr2 = 0;
		var totalNr = 0;


		randomNr1 = Math.floor(Math.random()*10);
		randomNr2 = Math.floor(Math.random()*10);
		totalNr = randomNr1 + randomNr2;
		var texti = "What is "+randomNr1+" + "+randomNr2 +" ?"+"<span class='required'>* </span>";
		$(label).html(texti);
		
	
		$(input).keyup(function(){

			var nr = $(this).val();
			if(nr==totalNr)
			{
				//$(element).find('#ebcaptchainput').attr('data-valid','true');
				console.log("true");
				 var scope = angular.element($("#container")).scope();
				    scope.$apply(function(){
				        scope.validCaptcha = true;
				    });
			}
			else{
				//$(element).find('#ebcaptchainput').attr('data-valid','false');
				console.log("false");
				var scope = angular.element($("#container")).scope();
			    scope.$apply(function(){
			        scope.validCaptcha = false;
			    });
			}
			
		});

		$(document).keypress(function(e)
		{
			if(e.which==13)
			{
				if((element).find('button').is(':disabled')==true)
				{
					e.preventDefault();
					return false;
				}
			}

		});

	};

})(jQuery);