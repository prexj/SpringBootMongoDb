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
	            	  if(data.length > 0 && data !=null){
	            		  $.each( data , function( index, item ) {
	  	                	rows += '<tr><td>' + (index+1) + '</td>';
	  	          	  	  	rows += '<td>' + item.unique_word_name + '</td>';
	  	          	  	  	rows += '<td>' + item.unique_word_language + '</td>';
	  	          	  	  	rows += '<td>';
	  	          	  	  	if(item.file_objects.length >0 ){
	  	          	  	  		//var count='';
	  	          	  	  		$.each( item.file_objects , function( oIndex, oItem ) {
	  	          	  	  			rows += '<table class="GridTwo"><tr>';
	  	          	  	  			rows += '<td>' + oItem.object_name + '</td>';//object_name,count_word
	  	          	  	  			rows += '<td>&nbsp;&nbsp;&nbsp;count is:' + oItem.count_word + '</td>';
	  	          	  				rows += '</tr></table>';
	  	          	  				//count =oItem.count_word+count;
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
	            	  }else{
	            		  rows +='<tr></td colspan="3"><td></tr>';
	            	  }
	                  
	          	  	  $('#tblProducts').html(rows);
	              },
	              error:function(xmlHttpRequest, textStatus, errorThrown)
	              {
	                     alert("error");
	              }
	      });
	}
	</script>
</head>
<body>
	
	<a href="viewUniqueKey">back</a>
	<input id="search" type="type" name="search"  value=""/>
	<button id="click" onclick="searchName();"> SEARCH</button>
				
	<table class="GridOne">
	  <thead>
	  	<tr>
		  <th> Id </th>
		  <th> word </th>
		  <th> lang </th>
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