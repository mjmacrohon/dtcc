var UploadCSV = UploadCSV || {};

var header;
var content;

var uFile = new Array();
var tUpload="";

/*
UploadCSV.selected = function(){
   $.ajax({
     url:"uploadCSV.do",
	 type: "post",
	 dataType:'json',
	 data: {},
	 success: function(uploadedFile){ 
		 $("#modal-preview").modal('show');
		 //console.log("Create Table");
		
		
	},
				 
	error: function(data, status, err){
		console.log(data);				
	}		
  });	  	
};
*/

UploadCSV.upload=function(){
		$('#formUploaderData').ajaxSubmit({
				 dataType:'json',
				 data: {field: $('#fieldUploadSeparator').val(), text: $('#textUploadSeparator').val(), tableUpload: $('#tableUpload').val()},
				 success:  function(uploadedFile){
					$("#modal-preview").modal('show');
					UploadCSV.createTableDisplay($('#tableUpload').val(), uploadedFile);
				 },
				 error: function(result){
					
				 }
		});
};

UploadCSV.createTableDisplay = function(tableUpload, uploadedFile){
	$('.modal-body').empty();
	//console.log(tableUpload)
	switch (tableUpload) {
		case "Attribut":
			$('.modal-body').append(
			 "<table class='table table-bordered' >" +
		       "<thead>" +
		         "<tr class='info'>" +	
		         	"<th>attributID</th>" +		         
		         	"<th>lagunageCode</th>" +
		         	"<th>attributText</th>" +
		         	"<th>sorting</th>" +			           
		         "</tr>" +
		       "</thead>" +
		       "<tbody>" +
		         UploadCSV.plotPreviewAttribut(uploadedFile) +
		       "</tbody>" +
		     "</table>" );
			 UploadCSV.getDuplicatesEntryAttribut();
		break;
		
		case "AttributSet":
			$('.modal-body').append(
					 "<table class='table table-bordered' >" +
				       "<thead>" +
				         "<tr class='info'>" +	
				         	"<th>attributSetID</th>" +
				         	"<th>attributSetItemID</th>" +
				         	"<th>attributID</th>" +
				         	"<th>attributValueID</th>" +
				         	"<th>parentItemID</th>" +		           
				         "</tr>" +
				       "</thead>" +
				       "<tbody>" +
				         UploadCSV.plotPreviewAttributSet(uploadedFile) +
				       "</tbody>" +
				     "</table>" );
					 UploadCSV.getDuplicatesEntryAttributSet();
		break;
		
		case "AttributValue":
			$('.modal-body').append(
					 "<table class='table table-bordered' >" +
				       "<thead>" +
				         "<tr class='info'>" +	
				         	"<th>attributValueID</th>" +
				         	"<th>languageCode</th>" +
				         	"<th>attributValueText</th>" +
				         	"<th>sorting</th>" +           
				         "</tr>" +
				       "</thead>" +
				       "<tbody>" +
				         UploadCSV.plotPreviewAttributValue(uploadedFile) +
				       "</tbody>" +
				     "</table>" );
					 UploadCSV.getDuplicatesEntryAttributValue();
		break;
		
		case "ElementRestoration":
			$('.modal-body').append(
					 "<table class='table table-bordered' >" +
				       "<thead>" +
				         "<tr class='info'>" +	
				         	"<th>restorationIDofHead</th>" +
				         	"<th>restorationID</th>" +
				         	"<th>attributSetID</th>" +
				         	"<th>locationCode</th>" + 
				         	"<th>factor</th>" + 
				         	"<th>sapMaterialNumber</th>" + 
				         	"<th>MaterialDescriptionID</th>" + 
				         	"<th>materialTypeID</th>" +
				         	"<th>sorting</th>" +
				         "</tr>" +
				       "</thead>" +
				       "<tbody>" +
				         UploadCSV.plotPreviewElementRestoration(uploadedFile) +
				       "</tbody>" +
				     "</table>" );
					 UploadCSV.getDuplicatesEntryElementRestoration();
		break;

		case "MainRestoration":
			$('.modal-body').append(
					 "<table class='table table-bordered' >" +
				       "<thead>" +
				         "<tr class='info'>" +	
				         	"<th>restorationID</th>" +
				         	"<th>materialID</th>" +
				         	"<th>attributSetID</th>" +
				         	"<th>locationCode</th>" + 
				         	"<th>typeID</th>" +
				         	"<th>elementCount</th>" +
				         	"<th>sapMaterialNumber</th>" +
				         	"<th>minElementsAllowed</th>" +
				        	"<th>maxElementsAllowed</th>" +
				        	"<th>sorting</th>" +
				         "</tr>" +
				       "</thead>" +
				       "<tbody>" +
				         UploadCSV.plotPreviewMainRestoration(uploadedFile) +
				       "</tbody>" +
				     "</table>" );
					 UploadCSV.getDuplicatesEntryMainRestoration();
	    break;
	    
		case "Material":
			$('.modal-body').append(
					 "<table class='table table-bordered' >" +
				       "<thead>" +
				         "<tr class='info'>" +	
				         	"<th>materialID</th>" +
				         	"<th>materialText</th>" +
				         	"<th>sorting</th>" +
				         "</tr>" +
				       "</thead>" +
				       "<tbody>" +
				         UploadCSV.plotPreviewMaterial(uploadedFile) +
				       "</tbody>" +
				     "</table>" );
					 UploadCSV.getDuplicatesEntryMaterial();
		break;
		
		case "MaterialDescription":
			$('.modal-body').append(
					 "<table class='table table-bordered' >" +
				       "<thead>" +
				         "<tr class='info'>" +	
				         	"<th>materialDescID</th>" +
				         	"<th>languageCode</th>" +
				         	"<th>Description</th>" +
				         "</tr>" +
				       "</thead>" +
				       "<tbody>" +
				         UploadCSV.plotPreviewMaterialDescription(uploadedFile) +
				       "</tbody>" +
				     "</table>" );
					 UploadCSV.getDuplicatesEntryMaterialDescription();
		break;
		
		case "MaterialType":
			$('.modal-body').append(
					 "<table class='table table-bordered' >" +
				       "<thead>" +
				         "<tr class='info'>" +	
				         	"<th>materialTypeId</th>" +
				         	"<th>materialType</th>" +
				         "</tr>" +
				       "</thead>" +
				       "<tbody>" +
				         UploadCSV.plotPreviewMaterialType(uploadedFile) +
				       "</tbody>" +
				     "</table>" );
					 UploadCSV.getDuplicatesEntryMaterialType();
		break;
			
		case "Restoration":
			$('.modal-body').append(
					 "<table class='table table-bordered' >" +
				       "<thead>" +
				         "<tr class='info'>" +	
				         	"<th>restorationID</th>" +
				         	"<th>languageCode</th>" +
				         	"<th>restorationText</th>" +
				         "</tr>" +
				       "</thead>" +
				       "<tbody>" +
				         UploadCSV.plotPreviewRestoration(uploadedFile) +
				       "</tbody>" +
				     "</table>" );
					 UploadCSV.getDuplicatesEntryRestoration();
		break;
		
		case "Type":
			$('.modal-body').append(
					 "<table class='table table-bordered' >" +
				       "<thead>" +
				         "<tr class='info'>" +	
				         	"<th>typeId</th>" +
				         	"<th>languageCode</th>" +
				         	"<th>typeCode</th>" +
				         	"<th>typeText</th>" +
				         	"<th>sorting</th>" +
				         	"<th>isUploadRequired</th>" +
				         "</tr>" +
				       "</thead>" +
				       "<tbody>" +
				         UploadCSV.plotPreviewType(uploadedFile) +
				       "</tbody>" +
				     "</table>" );
					 UploadCSV.getDuplicatesEntryType();
		break;
		
	    default:
		break;
	}
};

UploadCSV.plotPreviewAttribut = function(uploadedFile){
	var tblRow="";
	$.each(uploadedFile, function(key, attribut){
		if(key<10){
			tblRow = tblRow + "<tr>" +
			        "<td>"+attribut.attributID+"</td>" +
			        "<td>"+attribut.languageCode+"</td>" +
			        "<td>"+attribut.attributText+"</td>" +
			        "<td>"+attribut.sorting+"</td>" +
					"</tr>";
		}
	});
	return tblRow;
};


UploadCSV.getDuplicatesEntryAttribut =  function(){
	//console.log("Tinawag");
	var warnings = "";
	$.ajax({
	     url:"getDuplicates.do",
		 type: "post",
		 dataType:'json',
		 contentType:"application/json",
		 async: 'false',
		 success: function(duplicatesEntriesToDisplay){
			 if(duplicatesEntriesToDisplay.length>0){
				 $('.upload-continue').prop('disabled', true); 
			 }else{
				 $('.upload-continue').prop('disabled', false); 
			 }
			 $.each(duplicatesEntriesToDisplay, function(key, dEntry){
				 warnings = warnings + "<p class='bg-danger note-prgh'><i class='fa fa-exclamation-triangle'></i>&nbsp;Entries with key/s of <b>attributID : " + dEntry.attributID+ "</b> and <b>languageCode : " + dEntry.languageCode + "</b> is duplicated! <br/></p>";
			 });
			 $('.modal-body').prepend(warnings);
		 },
					 
		 error: function(data, status, err){
			//console.log(data);				
		 }		
	  });	  	
};

/////////////////////////////////////////////////////////////////////////////

 UploadCSV.plotPreviewAttributSet = function(uploadedFile){
	var tblRow="";
	$.each(uploadedFile, function(key, attributSet){
		if(key<10){
			tblRow = tblRow + "<tr>" +
			        "<td>"+attributSet.attributSetID+"</td>" +
			        "<td>"+attributSet.attributSetItemID+"</td>" +
			        "<td>"+attributSet.attributID+"</td>" +
			        "<td>"+attributSet.attributValueID+"</td>" +
			        "<td>"+attributSet.parentItemID+"</td>" +
					"</tr>";
		}
	});
	return tblRow;
};


UploadCSV.getDuplicatesEntryAttributSet =  function(){
	//console.log("Tinawag");
	var warnings = "";
	$.ajax({
	     url:"getDuplicates.do",
		 type: "post",
		 dataType:'json',
		 contentType:"application/json",
		 async: 'false',
		 success: function(duplicatesEntriesToDisplay){
			 if(duplicatesEntriesToDisplay.length>0){
				 $('.upload-continue').prop('disabled', true); 
			 }else{
				 $('.upload-continue').prop('disabled', false); 
			 }
			 $.each(duplicatesEntriesToDisplay, function(key, dEntry){
				 warnings = warnings + "<p class='bg-danger note-prgh'><i class='fa fa-exclamation-triangle'></i>&nbsp;Entries with key/s of <b>attributSetID : " + dEntry.attributSetID+ "</b> and <b>attributSetItemID : " + dEntry.attributSetItemID + "</b> is duplicated! <br/></p>";
			 });
			 $('.modal-body').prepend(warnings);
			 $('.upload-continue').prop('disabled', true);
		 
		 },
					 
		 error: function(data, status, err){
			//console.log(data);				
		 }		
	  });	  	
};

//////////////////////////////////////////////////////////////////////////////////////////////////////////

UploadCSV.plotPreviewAttributValue = function(uploadedFile){
	var tblRow="";
	$.each(uploadedFile, function(key, attributValue){
		if(key<10){
			tblRow = tblRow + "<tr>" +
			        "<td>"+attributValue.attributValueID+"</td>" +
			        "<td>"+attributValue.languageCode+"</td>" +
			        "<td>"+attributValue.attributValueText+"</td>" +
			        "<td>"+attributValue.sorting+"</td>" +
					"</tr>";
		}
	});
	return tblRow;
};


UploadCSV.getDuplicatesEntryAttributValue =  function(){
	//console.log("Tinawag");
	var warnings = "";
	$.ajax({
	     url:"getDuplicates.do",
		 type: "post",
		 dataType:'json',
		 contentType:"application/json",
		 async: 'false',
		 success: function(duplicatesEntriesToDisplay){
			 console.log('duplicatesEntriesToDisplay' + duplicatesEntriesToDisplay);
			 if(duplicatesEntriesToDisplay.length>0){
				 $('.upload-continue').prop('disabled', true); 
			 }else{
				 $('.upload-continue').prop('disabled', false); 
			 }
			 $.each(duplicatesEntriesToDisplay, function(key, dEntry){
				 warnings = warnings + "<p class='bg-danger note-prgh'><i class='fa fa-exclamation-triangle'></i>&nbsp;Entries with key/s of <b>attributValueID : " + dEntry.attributValueID+ "</b> and <b>languageCode : " + dEntry.languageCode + "</b> is duplicated! <br/></p>";
			 });
			 $('.modal-body').prepend(warnings);
			
			 
			 
		 },
					 
		 error: function(data, status, err){
			//console.log(data);				
		 }		
	  });	  	
};
///////////////////////////////////////////////////////////////////////////////////////////

UploadCSV.plotPreviewElementRestoration = function(uploadedFile){
	var tblRow="";
	$.each(uploadedFile, function(key, er){
		if(key<10){
			tblRow = tblRow + "<tr>" +
			        "<td>"+er.restorationIDofHead+"</td>" +
			        "<td>"+er.restorationID+"</td>" +
			        "<td>"+er.attributSetID+"</td>" +
			        "<td>"+er.locationCode+"</td>" +
			        "<td>"+er.factor+"</td>" +
			        "<td>"+er.sapMaterialNumber+"</td>" +
			        "<td>"+er.MaterialDescriptionID+"</td>" +
			        "<td>"+er.materialTypeID+"</td>" +
			        "<td>"+er.sorting+"</td>" +
					"</tr>";
		}
	});
	return tblRow;
};


UploadCSV.getDuplicatesEntryElementRestoration =  function(){
	//console.log("Tinawag");
	var warnings = "";
	$.ajax({
	     url:"getDuplicates.do",
		 type: "post",
		 dataType:'json',
		 async: 'false',
		 contentType:"application/json",
		 success: function(duplicatesEntriesToDisplay){
			 if(duplicatesEntriesToDisplay.length>0){
				 $('.upload-continue').prop('disabled', true); 
			 }else{
				 $('.upload-continue').prop('disabled', false); 
			 }
			 $.each(duplicatesEntriesToDisplay, function(key, dEntry){
				 warnings = warnings + "<p class='bg-danger note-prgh'><i class='fa fa-exclamation-triangle'></i>&nbsp;Entries with key/s of <b>restorationIDofHead : " + dEntry.restorationIDofHead+ "</b> and <b>restorationID : " + dEntry.restorationID + "</b> and <b>attributSetID : " + dEntry.attributSetID + "</b> and <b>locationCode : " + dEntry.locationCode + "</b> is duplicated! <br/></p>";
			 });
			 $('.modal-body').prepend(warnings);
			
			
			
		 },
					 
		 error: function(data, status, err){
			//console.log(data);				
		 }		
	  });	  	
};

/////////////////////////////////////////////////////////////////////////////////////////////////////

UploadCSV.plotPreviewMainRestoration = function(uploadedFile){
	var tblRow="";
	$.each(uploadedFile, function(key, mr){
		if(key<10){
			tblRow = tblRow + "<tr>" +
			        "<td>"+mr.restorationID+"</td>" +
			        "<td>"+mr.materialID+"</td>" +
			        "<td>"+mr.attributSetID+"</td>" +
			        "<td>"+mr.locationCode+"</td>" +
			        "<td>"+mr.typeID+"</td>" +
			        "<td>"+mr.elementCount+"</td>" +
			        "<td>"+mr.sapMaterialNumber+"</td>" +
			        "<td>"+mr.minElementsAllowed+"</td>" +
			        "<td>"+mr.maxElementsAllowed+"</td>" +
			        "<td>"+mr.sorting+"</td>" +
					"</tr>";
		}
	});
	return tblRow;
};


UploadCSV.getDuplicatesEntryMainRestoration =  function(){
	//console.log("Tinawag");
	var warnings = "";
	$.ajax({
	     url:"getDuplicates.do",
		 type: "post",
		 dataType:'json',
		 contentType:"application/json",
		 async: 'false',
		 success: function(duplicatesEntriesToDisplay){
			 if(duplicatesEntriesToDisplay.length>0){
				 $('.upload-continue').prop('disabled', true); 
			 }else{
				 $('.upload-continue').prop('disabled', false); 
			 }
			 $.each(duplicatesEntriesToDisplay, function(key, dEntry){
				 warnings = warnings + "<p class='bg-danger note-prgh'>" +
				 		"<i class='fa fa-exclamation-triangle'></i>&nbsp;Entries with key/s of " +
				 		"<b>restorationID : " + dEntry.restorationID+ "</b> and " +
				 		"<b>materialID : " + dEntry.materialID + "</b> and " +
				 		"<b>attributSetID : " + dEntry.attributSetID + "</b> and " +
				 		"<b>locationCode : " + dEntry.locationCode + "</b> " +
				 		"<b>typeID : " + dEntry.typeID + "</b> " +
				 		"<b>elementCount : " + dEntry.elementCount + "</b> " +
				 		"is duplicated! <br/></p>";
			 });
			 $('.modal-body').prepend(warnings);
		 },
					 
		 error: function(data, status, err){
			//console.log(data);				
		 }		
	  });	  	
};

//////////////////////////////////////////////////////////////////////////////////////////////
//--MATERIAL--
UploadCSV.plotPreviewMaterial = function(uploadedFile){
	var tblRow="";
	$.each(uploadedFile, function(key, m){
		if(key<10){
			tblRow = tblRow + "<tr>" +
			        "<td>"+m.materialID+"</td>" +
			        "<td>"+m.materialText+"</td>" +
			        "<td>"+m.sorting+"</td>" +
					"</tr>";
		}
	});
	return tblRow;
};


UploadCSV.getDuplicatesEntryMaterial =  function(){
	//console.log("Tinawag");
	var warnings = "";
	$.ajax({
	     url:"getDuplicates.do",
		 type: "post",
		 dataType:'json',
		 contentType:"application/json",
		 async: 'false',
		 success: function(duplicatesEntriesToDisplay){
			 if(duplicatesEntriesToDisplay.length>0){
				 $('.upload-continue').prop('disabled', true); 
			 }else{
				 $('.upload-continue').prop('disabled', false); 
			 }
			 $.each(duplicatesEntriesToDisplay, function(key, dEntry){
				 warnings = warnings + "<p class='bg-danger note-prgh'>" +
				 		"<i class='fa fa-exclamation-triangle'></i>&nbsp;Entries with key/s of " +
				 		"<b>materialID : " + dEntry.materialID+ "</b> is duplicated! <br/></p>";
			 });
			 $('.modal-body').prepend(warnings);
		 },
					 
		 error: function(data, status, err){
			//console.log(data);				
		 }		
	  });	  	
};

////////////////////////////////////////////////////////////////////////////////////////////////////
//--MaterialDescription--//
 
UploadCSV.plotPreviewMaterialDescription = function(uploadedFile){
	var tblRow="";
	$.each(uploadedFile, function(key, matD){
		if(key<10){
			tblRow = tblRow + "<tr>" +
			        "<td>"+matD.matDescID+"</td>" +
			        "<td>"+matD.languageCode+"</td>" +
			        "<td>"+matD.Description+"</td>" +
					"</tr>";
		}
	});
	return tblRow;
};


UploadCSV.getDuplicatesEntryMaterialDescription =  function(){
	//console.log("Tinawag");
	var warnings = "";
	$.ajax({
	     url:"getDuplicates.do",
		 type: "post",
		 dataType:'json',
		 contentType:"application/json",
		 async: 'false',
		 success: function(duplicatesEntriesToDisplay){
			 if(duplicatesEntriesToDisplay.length>0){
				 $('.upload-continue').prop('disabled', true); 
			 }else{
				 $('.upload-continue').prop('disabled', false); 
			 }
			 $.each(duplicatesEntriesToDisplay, function(key, dEntry){
				 warnings = warnings + "<p class='bg-danger note-prgh'>" +
				 		"<i class='fa fa-exclamation-triangle'></i>&nbsp;Entries with key/s of " +
				 		"<b>matDescID : " + dEntry.matDescID+ "</b> and " +
				 		"<b>languageCode : " + dEntry.languageCode + "</b> is duplicated! <br/></p>";
			 });
			 $('.modal-body').prepend(warnings);
		 },
					 
		 error: function(data, status, err){
			//console.log(data);				
		 }		
	  });	  	
};

//////////////////////////////////////////////////////////////////////////////////////////////////
///--MaterialType--////

UploadCSV.plotPreviewMaterialType = function(uploadedFile){
	var tblRow="";
	$.each(uploadedFile, function(key, matT){
		if(key<10){
			tblRow = tblRow + "<tr>" +
			        "<td>"+matT.materialTypeID+"</td>" +
			        "<td>"+matT.materialType+"</td>" +
					"</tr>";
		}
	});
	return tblRow;
};


UploadCSV.getDuplicatesEntryMaterialType =  function(){
	//console.log("Tinawag");
	var warnings = "";
	$.ajax({
	     url:"getDuplicates.do",
		 type: "post",
		 dataType:'json',
		 contentType:"application/json",
		 async: 'false',
		 success: function(duplicatesEntriesToDisplay){
			 if(duplicatesEntriesToDisplay.length>0){
				 $('.upload-continue').prop('disabled', true); 
			 }else{
				 $('.upload-continue').prop('disabled', false); 
			 }
			 $.each(duplicatesEntriesToDisplay, function(key, dEntry){
				 warnings = warnings + "<p class='bg-danger note-prgh'>" +
				 		"<i class='fa fa-exclamation-triangle'></i>&nbsp;Entries with key/s of " +
				 		"<b>materialTypeID : " + dEntry.materialTypeID+ "</b> is duplicated! <br/></p>";
			 });
			 $('.modal-body').prepend(warnings);
		 },
					 
		 error: function(data, status, err){
			//console.log(data);				
		 }		
	  });	  	
};

//////////////////////////////////////////////////////////////////////////////////////////////////////
//--Restoration--
UploadCSV.plotPreviewRestoration = function(uploadedFile){
	var tblRow="";
	$.each(uploadedFile, function(key, res){
		if(key<10){
			tblRow = tblRow + "<tr>" +
			        "<td>"+res.restorationId+"</td>" +
			        "<td>"+res.languageCode+"</td>" +
			        "<td>"+res.restorationText+"</td>" +
					"</tr>"
		}
	});
	return tblRow
};


UploadCSV.getDuplicatesEntryRestoration =  function(){
	//console.log("Tinawag");
	var warnings = "";
	$.ajax({
	     url:"getDuplicates.do",
		 type: "post",
		 dataType:'json',
		 contentType:"application/json",
		 async: 'false',
		 success: function(duplicatesEntriesToDisplay){
			 if(duplicatesEntriesToDisplay.length>0){
				 $('.upload-continue').prop('disabled', true); 
			 }else{
				 $('.upload-continue').prop('disabled', false); 
			 }
			 $.each(duplicatesEntriesToDisplay, function(key, dEntry){
				 warnings = warnings + "<p class='bg-danger note-prgh'>" +
				 		"<i class='fa fa-exclamation-triangle'></i>&nbsp;Entries with key/s of " +
				 		"<b>restorationID : " + dEntry.restorationId+ "</b> and " +
				 		"<b>languageCode : " + dEntry.languageCode + "</b> is duplicated! <br/></p>";
			 });
			 $('.modal-body').prepend(warnings);
		 },
					 
		 error: function(data, status, err){
			//console.log(data);				
		 }		
	  });	  	
};

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//--Type--//
UploadCSV.plotPreviewType = function(uploadedFile){
	var tblRow="";
	$.each(uploadedFile, function(key, t){
		if(key<10){
			tblRow = tblRow + "<tr>" +
			        "<td>"+t.typeId+"</td>" +
			        "<td>"+t.languageCode+"</td>" +
			        "<td>"+t.typeCode+"</td>" +
			        "<td>"+t.typeText+"</td>" +
			        "<td>"+t.sorting+"</td>" +
			        "<td>"+t.isUploadRequired+"</td>" +
					"</tr>"
		}
	});
	return tblRow
};


UploadCSV.getDuplicatesEntryType =  function(){
	//console.log("Tinawag");
	var warnings = "";
	$.ajax({
	     url:"getDuplicates.do",
		 type: "post",
		 dataType:'json',
		 contentType:"application/json",
		 async: 'false',
		 success: function(duplicatesEntriesToDisplay){
			 if(duplicatesEntriesToDisplay.length>0){
				 $('.upload-continue').prop('disabled', true); 
			 }else{
				 $('.upload-continue').prop('disabled', false); 
			 }
			 $.each(duplicatesEntriesToDisplay, function(key, dEntry){
				 warnings = warnings + "<p class='bg-danger note-prgh'>" +
				 		"<i class='fa fa-exclamation-triangle'></i>&nbsp;Entries with key/s of " +
				 		"<b>typeId : " + dEntry.typeId+ "</b> and " +
				 		"<b>languageCode : " + dEntry.languageCode + "</b> is duplicated! <br/></p>";
			 });
			 $('.modal-body').prepend(warnings);
		 },
					 
		 error: function(data, status, err){
			//console.log(data);				
		 }		
	  });	  	
};

UploadCSV.continueWrite =  function(){
	$.ajax({
	     url:"continueWrite.do",
		 type: "post",
		 dataType:'json',
		 success: function(message){
			
			
				
		 },
					 
		 error: function(data, status, err){
			//console.log(data);				
		 }		
	  });
	alert("Uploading of Entries was successful!");
	$("#tableUpload option:selected").prop("selected", false);
	var control = $(".csvfile");
	control.replaceWith( control = control.clone( true ) );
	$('.upload-csv-file-init').addClass('disabled')

}

UploadCSV.controls = function(){
	$('#tableUpload').change(function() {
		$('.upload-csv-file-init').removeClass('disabled')
	});
}

UploadCSV.cancelExport = function(){
	$("#tableUpload option:selected").prop("selected", false);
	var control = $(".csvfile");
	control.replaceWith( control = control.clone( true ) );
	$('.upload-csv-file-init').addClass('disabled')
}

 

$(document).ready(function(){
	header=$("meta[name='_csrf_header']").attr("content");
	content=$("meta[name='_csrf']").attr("content");
	//UploadCSV.selected();
	UploadCSV.controls();
});