var Cve = Cve || {};
var dtDefinition;
var dtOvalDefs;

Cve.init=function(){
	$("body").spin("modal");
	
	$.getJSON("/OvalDefTool/jsonrest/readcves.do", function(result){
		  $.each(result,function(idx,cve){
		    	$("select[name='cveIds']").append("<option>"+cve+"</option>");
		  }); 
		  $("select[name='cveIds']").chosen({ search_contains: true });
		  //$("select[name='ovalClass']").chosen({ search_contains: false });
	});
    

	$(".date-submission").datetimepicker();
	
	$("#msrcMetaInfo").toggle(1000,"linear");
	$("#mitreMetaInfo").toggle(1000,"linear");
	$("#nvdMetaInfo").toggle(1000,"linear");
	
	setTimeout(function() {
		$("body").spin("modal");
	}, 3000);
	
	dtDefinition=$("#tblSelectedDef").DataTable();
	dtOvalDefs=$("#tblOvalDefinitions").DataTable();
};

Cve.showModalNewDefintion=function(){
	$(".add-definition").click(function(){
		$("#modalNewDefinition").modal("show");
	});
};

Cve.showModalCriterion=function(){	
	$("#modalCriterion").modal("show")
};

Cve.showModalOvalId=function(){
	$("#modalSearchOval").modal("show")
};

Cve.processCveData=function(){		
	$(".process-cve-data").click(function(){
		$("body").spin("modal");
		$("#msrcMetaInfo").empty();
		$("#tblProduct tbody").empty();
		var cveId=$("select[name='cveIds']").chosen().val();
			var result=GlobalCve.nvdCveMap[cveId].msrcCve;
			console.log(result);
			if (typeof result != 'undefined'){
				if (result.length<=0)
					console.log("unable to find msrc cve meta....");
				else{
					$("input[name='cveTitle']").val(result.cveTitle + " - " + cveId);
					
					$("#msrcTitle").text(result.cveTitle);				
					var sHtml="<p><a target=_blank href=https://portal.msrc.microsoft.com/en-US/security-guidance/advisory/"+result.cveNumber+" source='MSRC'>"+result.cveNumber+"</a><input name='oval-input' holder-id='#msrcMeta' class='ptr-hand oval-input pull-right' type='radio' /></p>";
					sHtml=sHtml+"<p>"+result.cveTitle+"</p>";
					sHtml=sHtml+"<p>"+result.description+"</p>";
					$("#msrcMeta").html(sHtml);
					
					//Affected Products
					sHtml="";	
					$("#tblAffectedFamily tbody").empty();
					$.each(GlobalCve.nvdCveMap[cveId].affectedProducts, function(idx,ap){
						sHtml=sHtml+"<li>"+ap.name+"</li>";		
						Cve.plotAvailableDefinition(ap)
					});	
					
					$("#msrcMetaInfo").html("<p>Affected Products</p><ul>"+sHtml+"</ul>");
					$("#mitreMetaInfo").html("<p>Affected Products</p><ul>"+sHtml+"</ul>");
					$("#nvdMetaInfo").html("<p>Affected Products</p><ul>"+sHtml+"</ul>");
				}	
			}

		$.getJSON("/OvalDefTool/jsonrest/cvemitrenvdmeta.do?cveId="+cveId, function(result){
			//1st mitre 2nd nvd
			$.each(result,function(idx,cveElem){	
				var sHandler="#mitreMeta";
				var sSource="CVE"
				if (idx===1){
					sHandler="#nvdMeta";
					sSource="NVD";
				}
				
				var sHtml="<p><a target=_blank href="+cveElem.url+" source='"+sSource+"'>"+cveElem.cveNumber+"</a><input name='oval-input' holder-id='"+sHandler+"' class='ptr-hand oval-input pull-right' type='radio' source='' /></p>";
				sHtml=sHtml+"<p >"+$("#msrcTitle").text()+"</p>";
				sHtml=sHtml+"<p>"+cveElem.description+"</p>";

				
				$(sHandler).html(sHtml);
				sHtml="";

				Cve.plotReference(cveElem.source, cveElem.cveNumber,cveElem.url,cveElem.description );
			});//each
			$("body").spin("modal");
		});//getJson

	});
};



Cve.showCveInfo=function(){
	$(".cve-info").click(function(){
		var s=$(this).attr("cveIdInfo");
		$("#"+s).toggle(1000,"linear");
	});
}

Cve.showAffectedFamily=function(){
	$(".process-aff-family").click(function(){
		$("#modalAffectedFamily").modal();
	})
};

Cve.plotAffectedFamily=function(elem){
	var sName=elem.name.split("for");	
	var sDisplay="<tr><td>"+elem.name+"</td><td><input type='text' class='form-control' value='"+sName[0]+"' ></td></tr>";
	$("#tblAffectedFamily tbody").append(sDisplay);
}

Cve.plotAvailableDefinition=function(ap){
	var elem=ap.name;
	var platform=ap.platform;
	var sName=elem.split("for");	
	var sFamilies=$("#vr_product_family").html().toLowerCase().split(",");
	var sFamily="";
	var rowCount=$("#tblProduct tbody tr").length;
	if (platform == null){
		platform=sName[0];
	}
	
	var sLiFamily="<ul class='dropdown-menu'>"
	for(var i=0;i<sFamilies.length;i++){
		sLiFamily=sLiFamily + "<li><a refid='defFamily"+rowCount+"' onClick='Cve.select(this)'>"+sFamilies[i]+"</a></li>";
		if (sName[0].toLowerCase().includes(sFamilies[i])){
			sFamily=sFamilies[i];
		}
	}
	sLiFamily=sLiFamily+"</ul>";

	var sLiCpes="<ul class='dropdown-menu'>";
	var cveId=$("select[name='cveIds']").chosen().val();
	var Cpes=GlobalCve.extractCPEs(cveId);
	
	for(var i=0;i<Cpes.length;i++){
		sLiCpes=sLiCpes + "<li><a refid='defCpe"+rowCount+"' onClick='Cve.select(this)'>"+Cpes[i]+"</a></li>";
	}
	sLiCpes=sLiCpes+"</ul>";
	
	var sDisplay="<tr><td>"
						+"<div class='input-group input-group-sm'>" +
							"<span class='input-group-addon' >Title</span>" +
							"<input type='text' class='def-input form-control' value='"+elem+"'>"+
						"</div>" +
						"<div class='input-group input-group-sm'>" +
							"<span class='input-group-addon' id='ssFamily'>Family</span>" +
							"<input id='defFamily"+rowCount+"' type='text' class='def-input form-control' aria-describedby='ssFamily' value='"+sFamily+"'>"+
							"<div class='input-group-btn'>" +
								"<button type='button' class='def-input btn btn-default dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>Selection<span class='caret'></span></button>"	+
								sLiFamily +
							"</div>"+
						"</div>" +
						"<div class='input-group input-group-sm'>" +
							"<span class='input-group-addon' id='ssPlatform"+rowCount+"'>Platform</span>" +
							"<input type='text' class='def-input form-control' aria-describedby='ssPlatform' value='"+platform+"'>"+
						"</div>" +	
						"<div class='input-group input-group-sm'>" +
							"<span class='input-group-addon' >CPE Reference</span>" +
							"<input id='defCpe"+rowCount+"' type='text' class='def-input form-control' >"+
							"<div class='input-group-btn'>" +
							"<button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>Selection<span class='caret'></span></button>"	+
							sLiCpes +
						"</div>"+					
					"</div>" +
					"<div class='input-group input-group-sm'>" +
						"<span class='input-group-addon' id='ssDescription"+rowCount+"'>Description</span>" +
						"<input type='text' class='def-input form-control' aria-describedby='ssPlatform'>"+
					"</div>" +		
					"</td>" +
					"<td>" +
						"<input type='checkbox'>" +
					"</td></tr>";
	
	$("#tblProduct tbody").append(sDisplay);
}

Cve.plotReference=function(source, id, url, description){
	$("#tblReference tbody").append("<tr><td class='col-md-1'>"+source+"</td><td class='col-md-1'>"+id+"</td class='col-md-2'><td><a target='_blank' href="+url+" style='color:black'>"+url+"</a></td><td>"+description+"</td></tr>");
}

Cve.click=function(){
	$(".init-cves").click(function(){
		Cve.init();
	});	
	
	$("select[name='cveIds']").change(function(){
		var cveId=$("select[name='cveIds']").chosen().val();	
		GlobalCve.extractMsrcCve(cveId);
		$("#tblProduct tbody").empty();
		$("#msrcMeta").empty();
		$("#msrcMetaInfo").empty();
		$("#mitreMeta").empty();
		$("#mitreMetaInfo").empty();
		$("#nvdMeta").empty();
		$("#nvdMetaInfo").empty();
		$("input[name='cveTitle']").val("");	
	});

	$("#btnAddDef").click(function(){
		var cveId=$("select[name='cveIds']").chosen().val();	
		var htmlContent="";
		var content="";
		var ovalHandler=$("input[name='oval-input']:checked").attr("holder-id");
		console.log(ovalHandler);
		var ovalData=new Object();
		
		ovalData.title=$("input[name='cveTitle']").val();
		ovalData.ovalClass="vulnerability";
		ovalData.ref_id=cveId;
		ovalData.ref_url=$(ovalHandler+" p a").attr("href");
		ovalData.source=$(ovalHandler+" p a").attr("source");
		var ovalDescription="";

		
		
		$.each($(ovalHandler+" p"),function(idx, elem){
			if (idx>=2){
				ovalDescription=ovalDescription+$(elem).html();
			}
		})
		ovalData.description=ovalDescription;
		console.log(ovalData);
		$("input[name='ovalTitle']").val(ovalData.title);
		$("input[name='ovalClass']").val(ovalData.ovalClass);
		$("input[name='referenceId']").val(ovalData.ref_id);
		$("input[name='referenceUrl']").val(ovalData.ref_url);
		$("input[name='referenceSource']").val(ovalData.source);
		$("textarea[name='ovalDescription']").val(ovalData.description);
		
/*
		$.each($("input[type=checkbox]:checked"),function(idx, elem){
			console.log("cnt: " + idx);
			var def=new Object();
			def.title=$($(elem).parents().eq(1).find("td input.def-input")[0]).val();
			def.family=$($(elem).parents().eq(1).find("td input.def-input")[1]).val();
			def.platform=$($(elem).parents().eq(1).find("td input.def-input")[2]).val();
			def.cpe=$($(elem).parents().eq(1).find("td input.def-input")[3]).val();
			def.description=$($(elem).parents().eq(1).find("td input.def-input")[4]).val();			
			htmlContent="<tr>" +
							"<td>"+def.title+"</td>"+//Title: 
							"<td>"+def.family+"</td>"+//Family: 
							"<td>"+def.platform+"</td>"+//Platform: 
							"<td>"+def.cpe+"</li>"+//CPE: 
							"<td>"+ def.description+"</td>"+//Description: 
							"<td><i rowid='tr-"+idx+"'  onclick='Cve.deleteDef(this)' class='pull-right ptr-hand fa fa-trash fa-5' aria-hidden='true'></i></td>"
							"</tr>";
			console.log(htmlContent);
			//$("#tblSelectedDef tbody").append(htmlContent);
			var counter=1;
			dtDefinition.row.add( [
			            def.title,
			            def.family,
			            def.platform,
			            def.platform,
			            def.cpe,
			            "<span onclick='Cve.showModalCriterion()' class='btn btn-danger'>Criterion <span class='badge'>0</span></span>",
			            "<span rowid='tr-"+idx+"'  onclick='Cve.deleteDef(this)' class='pull-right ptr-hand btn btn-default fa fa-trash fa-5' aria-hidden='true'></span></td>"
			        ] ).draw( false );

		});//each
*/		
	});
	
	
	
	$("#btnSearchOval").click(function(){
		var ovalPram=new Object();
		ovalPram.definition_id=$("#definition_id").val();
		ovalPram.title=$("#title").val();
		ovalPram.description=$("#description").val();
		ovalPram.reference_id=$("#reference_id").val();
		
		ovalPram.platform=$("#platform").val();
		ovalPram.product=$("#product").val();
		ovalPram.aclass=$("#product").val();
		ovalPram.family=$("#family").val();
		ovalPram.status=$("#status").val();
		console.log(ovalPram);
		
		$.ajax({
			  url:"/OvalDefTool/jsonrest/ovaldefids.do",
			  type: "post",
			  dataType:'json',
			  contentType: 'application/json',
			  data:JSON.stringify(ovalPram),
			  success: function(definitions){
					dtOvalDefs.clear().draw();
					$.each(definitions,function(idx, def){
						dtOvalDefs.row.add( [
						                     def.definitionUrl,
						                     def.definitionClass,
						                     def.title,
						                     "<span oval-ref-id='"+def.definitionId+"' onclick='Cve.selectOvalDefinition(this)' title='select' class='btn btn-default fa fa-hand-o-up' data-dismiss='modal'></span>"
						                     ]).draw( false );
					})
			  },
			  error: function(data, status, err){
				console.log("error");		
			  }
		});
		
	});	
}


Cve.selectOvalDefinition=function(elem){
	//oval:org.cisecurity:def:807
	//console.log($(elem).attr("oval-ref-id"));
	$("input[name='ovalId']").val($(elem).attr("oval-ref-id"));
}

Cve.select=function(elem){
	$("#"+elem.getAttribute("refid")).val(elem.innerHTML);
}

Cve.deleteDef=function(elem){
	var trid=$(elem).attr("rowid");
	$("tr[class^="+trid).remove();
}

$(document).ready(function(){
	Cve.init();
	Cve.click();	
	Cve.showModalNewDefintion();
	Cve.processCveData();
	Cve.showCveInfo();
	Cve.showAffectedFamily();
});
