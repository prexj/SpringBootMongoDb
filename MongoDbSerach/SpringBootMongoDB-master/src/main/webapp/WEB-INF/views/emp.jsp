<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet"> 
	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
	<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<!-- CSS -->
	<style>
	Table.GridOne 
		{
		 padding: 3px;
		 margin: 0;
		 background: lightyellow;
		 border-collapse: collapse; 
		 width:35%;
		}
		Table.GridTwo 
		{
		 padding: 3px;
		 margin: 0;
		 background: lightyellow;
		 border-collapse: collapse; 
		 width:35%;
		}
	Table.GridOne Td 
		{ 
		 padding:2px;
		 border: 1px solid #ff9900;
		 border-collapse: collapse;
		} 
		Table.GridTwo Td 
		{ 
		 padding:2px;
		 border: 1px solid #ff9900;
		 border-collapse: collapse;
		} 
	</style>
	<script type="text/javascript">
	
	jQuery(document).ready(function() {
		$('#insertBut').hide();
		$('#updateBut').hide();
		
		showAll();
		
	    jQuery("#confirmationDialog").dialog({
	        autoOpen: false,
	        modal: true,
	        title:'insert Data',
	        width:600,
	        height:600,
	        open: function() {
	        	var id = $("#id").val();
	        	if(id == '' ){
	        		$('#insertBut').show();
	        		$('#updateBut').hide();
	        	}else{
	        		$('#insertBut').hide();
	        		$('#updateBut').show();
	        	}
	        },
	        close :function() {
	        	$("#id").val('');
    	    	$("#fname").val('');
    	    	$("#lname").val('');
    	    	$("#gen").val('');
    	    	$("#address").val('');
    	    	
	        	showAll();
	        } 
	    });
	});
	
	function insert(){
		$("#confirmationDialog").dialog("open");
	}
	
	function showAll(){
		alert('shioall');
	      $.ajax({
	              type:"GET",
	              url:"showAll",
	              dataType: "json",
	              success:function(data)
	              { 
	            	  $('#tblProducts').html('');
	            	  var rows = '';
	                  $.each( data , function( index, item ) {
	                	rows += '<tr><td>' + (index+1) + '</td>';
	          	  	  	rows += '<td>' + item.unique_word_name + '</td>';
	          	  	  	//rows += '<td>' + item.unique_word_language + '</td>';
	          	  	  	rows += '<td>';
	          	  	  	if(item.file_objects.length >0 ){
	          	  	  		$.each( item.file_objects , function( oIndex, oItem ) {
	          	  	  			rows += '<table class="GridTwo"><tr>';
	          	  	  			rows += '<td>' + oItem.object_name + '</td>';//object_name,count_word
	          	  	  			rows += '<td>&nbsp;&nbsp;&nbsp;count is:' + oItem.count_word + '</td>';
	          	  				rows += '</tr></table>';
	          	  	  		});
	          	  	  	}else{
	          	  	  		//rows += '</td></tr>';
	          	  	  	}
	          	  		rows +='</td></tr>';
	          	  	  	//rows += '<td>' + item.gender + '</td>';
	          	  		//rows += '<td>' + item.address + '</td>';
	          	  		//rows += '<td onclick="editAjaxData('+item.id+');" >edit</td>';
	          	  		//rows += '<td onclick="deleteAjaxData('+item.id+');" >Delete</td></tr>';
	          	  	  	
	          	  	  });
	          	  	  $('#tblProducts').html(rows);
	              },
	              error:function(xmlHttpRequest, textStatus, errorThrown)
	              {
	                     alert("error");
	              }
	      });
	}
	
	function insertAjaxData(){
    	//var fn = $("#fname").val();
  		//var ln = $("#lname").val();
  		//var gn = $("#gen").val();
  		//var add = $("#address").val();
  		//console.log("fn ::: "+fn+" ln ::: "+ln+" gn ::: "+gn+" add ::: "+add);
    	$.ajax({
	    	   type: "post",
	    	   url: "save",
	    	   dataType: "json",  
	    	   data:{},//fname: fn , lname: ln , gen: gn , address: add
	    	   success: function(data){
	    		  alert('success :::: '+data);
	    	    if(data == 1){
	    	    	
	    	    	alert('inserted');
	    	    	$('#confirmationDialog').dialog('close');
	    	    	showAll();
	    	    }else{
	    	    	alert('inserting fail');
	    	    	$('#confirmationDialog').dialog('close');
	    	    	showAll();
	    	    }
	    	   },
	    	   error: function(){      
	    	    alert('Error while request..');
	    	   }
	    });
    }
	function updateAjaxData(){
		var id = $("#id").val();
    	var fn = $("#fname").val();
  		var ln = $("#lname").val();
  		var gn = $("#gen").val();
  		var add = $("#address").val();
    	$.ajax({
	    	   type: "post",
	    	   url: "updateEmp",
	    	   dataType: "json",  
	    	   data:{id: id, fname: fn , lname: ln , gen: gn , address: add},
	    	   success: function(response){
	    	    if(response == 1){
	    	    	alert('updated');
	    	    	$('#confirmationDialog').dialog('close');
	    	    	showAll();
	    	    }else{
	    	    	alert('updated fail ');
	    	    	$('#confirmationDialog').dialog('close');
	    	    	showAll();
	    	    }
	    	   },
	    	   error: function(){      
	    	    alert('Error while request..');
	    	   }
	    });
    }
	
	function editAjaxData(id){
		alert('editAjaxData()'+id);
		
  	  $.ajax({
  	   type: "get",
  	   url: "editData",
  	   dataType: "json",  
  	   data:{id: id },
  	   success: function(response){
  		    alert(response);
  		    $("#id").val(response.id);
    		$("#fname").val(response.firstName);
    		$("#lname").val(response.lastName);
    		$("#gen").val(response.gender);
    		$("#address").val(response.address);
  	    	$("#confirmationDialog").dialog("open");
  	   },
  	   error: function(){      
  	    alert('Error while request..');
  	   }
  	  });
  	}
	
	function deleteAjaxData(id){
  	  $.ajax({
  	   type: "post",
  	   url: "deleteEmp",
  	   dataType: "json",  
  	   data:{id: id },
  	   success: function(response){
	  	   if(response == 1){
	  		   alert('data deleted success');
	  		   showAll();
	  	   }else{
	  		 alert('data deleted fail');
	  		   showAll();
	  	   }
  	   },
  	   error: function(){      
  	   		alert('Error while request..');
  	   }
  	  });
  	 }
	
	function searchName(){
		var firstname = $("#search").val();
	      $.ajax({
	              type:"GET",
	              url:"searchName",
	              dataType: "json",
	              data:{firstname: firstname },
	              success:function(data)
	              { 
	            	  var rows = '';
	            	  $('#tblProducts').html('');
	                  //$.each( data , function( index, item ) {
	                		rows += '<tr><td></td>';
	          	  	  	rows += '<td>' + data.unique_word_name + '</td>';
	          	  	  	//rows += '<td>' + item.unique_word_language + '</td>';
	          	  	  	rows += '<td>';
	          	  	  	if(data.file_objects.length >0 ){
	          	  	  		$.each( data.file_objects , function( oIndex, oItem ) {
	          	  	  			rows += '<table class="GridTwo"><tr>';
	          	  	  			rows += '<td>' + oItem.object_name + '</td>';//object_name,count_word
	          	  	  			rows += '<td>&nbsp;&nbsp;&nbsp;count is:' + oItem.count_word + '</td>';
	          	  				rows += '</tr></table>';
	          	  	  		});
	          	  	  	}else{
	          	  	  		//rows += '</td></tr>';
	          	  	  	}
	          	  		rows +='</td></tr>';

	                  //});
	          	  	  $('#tblProducts').html(rows);
	              },
	              error:function(xmlHttpRequest, textStatus, errorThrown)
	              {
	                     alert("error");
	              }
	      });
	}
	/* $("#btnSubmit").click(function(event){
		alert('hie ');
		event.preventDefault();
		submitClick();
	}); */
	function submitClicked(){
		alert('hie ');
		var form =$('#fileUploadForm')[0];
		var filename= $('#filesEditData').val();
		var ext = filename.split('.').pop();
		var data = new FormData(form);
		if(filename.length<0){
			alert('Plase choose file');
			return;
		}
		 $.ajax({
             type:"POST",
             url:"getUniqueWord",
             encrype:"multipart/form-data",
           //  dataType: "json",
             data:data,
             processData:false,
             contentType:false,
             cache:false,
             success:function(data)
             { 
            	 $('#filesEditData').val('');
            	 var rows = '';
                 $.each( data , function( index, item ) {
               		rows += '<tr><td>' + (index+1) + '</td>';
         	  	  	rows += '<td>' + item.unique_word_name + '</td>';
         	  	  	//rows += '<td>' + item.unique_word_language + '</td>';
         	  	  	rows += '<td>';
         	  	  	if(item.file_objects.length >0 ){
         	  	  		$.each( item.file_objects , function( oIndex, oItem ) {
         	  	  			rows += '<table class="GridTwo"><tr>';
         	  	  			rows += '<td>' + oItem.object_name + '</td>';//object_name,count_word
         	  	  			rows += '<td>&nbsp;&nbsp;&nbsp;count is:' + oItem.count_word + '</td>';
         	  				rows += '</tr></table>';
         	  	  		});
         	  	  	}else{
         	  	  		//rows += '</td></tr>';
         	  	  	}
         	  		rows +='</td></tr>';
         	  	  	//rows += '<td>' + item.gender + '</td>';
         	  		//rows += '<td>' + item.address + '</td>';
         	  		//rows += '<td onclick="editAjaxData('+item.id+');" >edit</td>';
         	  		//rows += '<td onclick="deleteAjaxData('+item.id+');" >Delete</td></tr>';
         	  	  	
         	  	  });
         	  	  $('#tblProducts').html(rows);
             },
             error:function(xmlHttpRequest, textStatus, errorThrown)
             {
            	 $('#filesEditData').val('');
                    alert("error");
             }
     });
	}
	</script>
</head>
<body>
	<!-- <button id="click" onclick="insert();"> Add New</button> -->
	<!-- <form method="POST" enctype="multipart/form-data" id="fileUploadForm">
	    <input type="file" name="filesData" id="filesEditData"/><br/><br/>
	    <input type="button"  value="send" id="btnSubmit" onclick="submitClicked()"/>
	</form>viewSerachUniqueKey,viewUniqueKey -->
	<a href="viewSerachUniqueKey">Search View</a>
	<button id="click" onclick="insertAjaxData();" style="display: none;"> insert</button>
	<!-- <input id="search" type="type" name="search"  value=""/>
	<button id="click" onclick="searchName();"> SEARCH</button> -->
				
	<table class="GridOne">
	  <thead>
	  	<tr>
		  <th> Id </th>
		  <th> word </th>
		  <!-- <th> lang </th> -->
		  <th> object </th>
		  <!-- <th> address </th> -->
		  <!-- <th>Action</th> -->
		</tr>
	  </thead>
	  <tbody id="tblProducts">
	  
	  </tbody>
	</table>

</body>
</html>