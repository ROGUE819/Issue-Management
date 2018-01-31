function login() {
	var loginValue = $('#loginField').val();
	var passValue = $('#passField').val();
	
	$.ajax({
		url: 'tracker',
		type: 'GET',
		data: {
			login : loginValue,
			pass: passValue,
			action: 'login'
		},
		success: function(response) {
			if(response == ""){
				alert("Incorrect LoginID or Password");
			}
			else{
				var responseObject = JSON.parse(response);
				if (responseObject.firstName){
					window.location.href='main.html';
				}
				else{
					alert("Incorrect LoginID or Passoword");
				}	
			}				
		},
		failure: function() {
			alert('Unable to login');
		}		
	});
}