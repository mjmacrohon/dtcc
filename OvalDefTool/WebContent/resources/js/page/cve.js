var Cve = Cve || {};

Cve.init=function(){
	$("body").spin("modal");
	
    $.each(GlobalCve.nvdCve.CVE_Items,function(idx,cve){
    	$("select[name='cveIds']").append("<option>"+cve.cve.CVE_data_meta.ID+"</option>");
    });  
  
    
    $("select[name='cveIds']").chosen({ search_contains: true });

	$(".date-submission").datetimepicker();
	
	$("#msrcMetaInfo").toggle(1000,"linear");
	$("#mitreMetaInfo").toggle(1000,"linear");
	$("#nvdMetaInfo").toggle(1000,"linear");
	
	$(".dependent").toggle(1000,"linear");
	$(".parent").click(function(){
		console.log($("."+ $(this).attr("rowid")).next().toggle(1000,"linear"));
	});
	setTimeout(function() {
		$("body").spin("modal");
	}, 3000);
	
};

Cve.showModalNewDefintion=function(){
	$(".add-definition").click(function(){
		$("#modalNewDefinition").modal("show");
	});
};
Cve.processCveData=function(){		
	$(".process-cve-data").click(function(){
		$("body").spin("modal");
		$("#msrcMetaInfo").empty();
		$("#tblProduct tbody").empty();
		var cveId=$("select[name='cveIds']").chosen().val();
//		$.getJSON("/OvalDefTool/jsonrest/cvemsrcmeta.do?cveId="+cveId, function(result){
		
			var result=GlobalCve.nvdCveMap[cveId].msrcCve;
			if (result.length<=0)
				console.log("unable to find msrc cve meta....");
			else{
				
				$("#msrcTitle").text(result.cveTitle);
				$("input[name='cveTitle']").val(result.cveTitle + " - " + cveId);
				var sHtml="<p><a target=_blank href=https://portal.msrc.microsoft.com/en-US/security-guidance/advisory/"+result.cveNumber+">"+result.cveNumber+"</a></p>";
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
//		});	//getJson	
		
		$.getJSON("/OvalDefTool/jsonrest/cvemitrenvdmeta.do?cveId="+cveId, function(result){
			//1st mitre 2nd nvd
			$.each(result,function(idx,cveElem){				
				var sHandler="#mitreMeta";
				var sHtml="<p><a target=_blank href="+cveElem.url+">"+cveElem.cveNumber+"</a></p>";
				sHtml=sHtml+"<p>"+$("#msrcTitle").text()+"</p>";
				sHtml=sHtml+"<p>"+cveElem.description+"</p>";
				if (idx===1){
					sHandler="#nvdMeta";
				}
				
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
	var sDisplay="<tr><td>"+elem.name+"</td><td><input type='text' class='form-control'value='"+sName[0]+"' ></td></tr>";
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
							"<input type='text' class='form-control' value='"+elem+"'>"+
						"</div>" +
						"<div class='input-group input-group-sm'>" +
							"<span class='input-group-addon' id='ssFamily'>Family</span>" +
							"<input id='defFamily"+rowCount+"' type='text' class='form-control' aria-describedby='ssFamily' value='"+sFamily+"'>"+
							"<div class='input-group-btn'>" +
								"<button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>Selection<span class='caret'></span></button>"	+
								sLiFamily +
							"</div>"+
						"</div>" +
						"<div class='input-group input-group-sm'>" +
							"<span class='input-group-addon' id='ssPlatform'>Platform</span>" +
							"<input type='text' class='form-control' aria-describedby='ssPlatform' value='"+platform+"'>"+
						"</div>" +	
						"<div class='input-group input-group-sm'>" +
							"<span class='input-group-addon' >CPE Reference</span>" +
							"<input id='defCpe"+rowCount+"' type='text' class='form-control' >"+
							"<div class='input-group-btn'>" +
							"<button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>Selection<span class='caret'></span></button>"	+
							sLiCpes +
						"</div>"+
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
}

Cve.select=function(elem){
	$("#"+elem.getAttribute("refid")).val(elem.innerHTML);
}

$(document).ready(function(){
	Cve.click();	
	Cve.showModalNewDefintion();
	Cve.processCveData();
	Cve.showCveInfo();
	Cve.showAffectedFamily();
});
