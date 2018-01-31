$(document).ready(function(){
   render();
   
});
function render(){
	$.ajax({
		url: 'tracker',
		data: {
			action: 'getLoggedInUserData'
		},
		success: function(response) {
			var responseObject = JSON.parse(response.trim());
			var html = '<div class="contentHeader"><div class="headerData"><span class="alignLeft">Tracker</span>';
			html += '<span class="userName">Welcome ' + responseObject.userName + '</span></div><div class="menu">';
			
			html += '<span onClick="loadInbox()">Inbox&nbsp;&nbsp;|&nbsp;&nbsp;</span>';
			
			if(responseObject.userRole == 'tester') {
				html += '<span onClick="createIssue()">Add Issue&nbsp;&nbsp;|&nbsp;&nbsp;</span>';
			}
			
			html += '<span onClick="viewAllIssues()"> View All Issues </span>'
				+ '<span class="logout" onClick="logout()">Logout</span></div>';
			
			html += '</div>';
			$('#wrapper').html(html);
			getIssuesForLoggedInUser();
		},
		failure: function() {
			alert('There was some error');
		}		
	});
}
function viewAllIssues(){
	$('table').remove();
	$.ajax({
		url: 'tracker',
		data: {
			action: 'getAllIssues'
		},
		success: function(response) {						
			var issueArray = JSON.parse(response);

			var tableStart = '<table id="listTable" border = "1" style = "border-collapse:collapse"> <thead>';
			var tableEnd = '</tbody> </table>';
			var tableRowHead = '<tr><td width="10%">ID</td><td width="30%">Name</td><td width="15%">Descripiton</td>' 
					+'<td width="15%">Priority</td><td width="15%">Resolution</td><td width="10%">Resolved</td>' 
					+ '<td width="15%">Creation Date</td><td width="15%">Show Stopper</td>' 
					+ '<td width="15%">Overall Status</td></tr>';

			var table = tableStart + tableRowHead + '</thead>' + '<tbody>';

			for(var i = 0; i < issueArray.length; i++) {
				var row = issueArray[i];
				table += '<tr issueId = "' + row.issueID + '" onClick = "loadIssue(event)"><td width="10%">' + row.issueID + '</td>'
				+ '<td width="30%" title="'+ row.name + '">' + row.name + '</td><td width="15%" title="'+ row.description + '">' 
				+ row.description + '</td><td width="15%">' + row.priority + '</td><td width="15%">' + row.resolution 
				+ '</td><td width="15%">' + row.resolved + '</td><td width="15%">' + row.dateIdentified	
				+ '</td><td width="15%">' + row.showStopper	+ '</td> <td width="15%">' + row.overallStatus + '</td></tr>';
			}

			table += tableEnd;

			$('#wrapper').append(table);
		},
		failure: function() {
			alert('There was some error...');
		}		
	});
}
function getIssuesForLoggedInUser(){
	$('table').remove();
	$.ajax({
		url: 'tracker',
		data: {
			action: 'getIssuesForLoggedInUser'
		},
		success: function(response) {				
			var issueArray = JSON.parse(response);

			var tableStart = '<table id="listTable" border = "1" style = "border-collapse:collapse"> <thead>';
			var tableEnd = '</tbody> </table>';
			var tableRowHead = '<tr><td width="10%">ID</td><td width="30%">Name</td><td width="15%">Priority</td>'
				+ '<td width="15%">Creation Date</td><td width="15%">Show Stopper</td><td width="15%">Overall Status</td></tr>';

			var table = tableStart + tableRowHead + '</thead>' + '<tbody>';

			for(var i = 0; i < issueArray.length; i++) {
				var row = issueArray[i];
				table += '<tr issueId = "' + row.issueID + '" onClick = "loadIssue(event)"><td width="10%">' + row.issueID + '</td><td width="30%">' + row.name + '</td><td width="15%">' 
				+ row.priority + '</td><td width="15%">' + row.dateIdentified + '</td><td width="15%">' + row.showStopper 
				+ '</td> <td width="15%">' + row.overallStatus + '</td></tr>';
			}

			table += tableEnd;

			$('#wrapper').append(table);
		},
		failure: function() {
			alert('There was some error...');
		}		
	});
}
function loadIssue(event){
	var issueID = parseInt($(event.currentTarget).attr('issueId'));
	$.ajax({
		url: 'tracker',
		data: {
			issueId : issueID,
			action: 'getIssue'
		},
		success: function(response) {
			var issue = JSON.parse(response);
			if(issue) {
				createForm(issue);
			}
		},
		failure: function() {
			alert('');
		}		
	});
}
function logout(){
	$.ajax({
		url: 'tracker',
		data: {
			action: 'logout'
		},
		success: function(response) {
			window.location.href='index.html';
		},
		failure: function() {
			alert('');
		}		
	});
}
function createForm(issue) {
	$('#listTable').remove();
	var html = '<table id="issueForm" class="issueForm">';
	for(var i = 0; i < issue.length; i++) {
		var issueData = issue[i];
		html += '<tr><td>';
		html += '<span>' + issueData.fieldName + '</span></td>';
		html += '<td>';
		
		if(issueData.type == 'label' || !issueData.editable) {
			html += '<span name="' + issueData.id + '">' + (issueData.value || issueData.value == 0 ? issueData.value : '' ) + '</span>';
		}
		else if(issueData.type == 'textbox') {
			html += '<input type="text" value="' + issueData.value + '" name="' + issueData.id + '" />';
		}
		else if(issueData.type == 'textarea') {
			html += '<textarea name="' + issueData.id + '">' + issueData.value + '</textarea>';
		}
		else if(issueData.type == 'dropdown') {
			html += '<select name="'+ issueData.id + '">';
				for(var j = 0; j < issueData.comboValues.length; j++) {
					html += '<option value="' + issueData.comboValues[j].value + '"' + 
						(issueData.value == issueData.comboValues[j].value ? ' selected ' : '') + '>'  
						+ issueData.comboValues[j].name + '</option>';
				}
			html += '</select>';
		}
		else if(issueData.type == 'checkbox') {
			html += '<input type="checkbox" value="' + issueData.value + '" name="' + issueData.id + '" ' 
					+ (issueData.value == 'Y' ? ' checked ' : '' ) + '/>';
		}
		
		html += "</td></tr>";
	}
	
	html += '<tr><td>&nbsp;</td></tr>';
	html += '<tr><td>&nbsp;</td></tr>';
	html += '<tr><td colspan="2" style="text-align:center;"><button onclick="saveIssue();">Save</button></td></tr>'
	html += "</table>";
	
	$('.wrapper').append(html);
}
function saveIssue() {
	var dataFields = $('#issueForm').find('input, select, textarea');
	var params = {};
	for(var i = 0; i < dataFields.length; i++) {
		var dataValue = dataFields[i].value;
		
		if(dataFields[i].type == 'checkbox' && dataFields[i].checked) {
			dataValue = 'Y';
		}
		params[dataFields[i].name] = dataValue;
	}
	
	$.ajax({
		url: 'tracker',
		data: {
			fieldData: JSON.stringify(params),
			action: 'updateIssue',
			issueId: $('span[name="issueId"]').text()
		},
		success: function(response) {
			alert('saved');
		},
		failure: function() {
			alert('There was some error');
		}		
	});
}
function createIssue() {
	$.ajax({
		url: 'tracker',
		data: {
			action: 'getIssue',
		},
		success: function(response) {
			var issue = JSON.parse(response);
			createForm(issue);
		},
		failure: function() {
			alert('There was some error');
		}		
	});
}
function loadInbox() {
	getIssuesForLoggedInUser();
}