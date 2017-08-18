var GlobalCve = GlobalCve || {};

GlobalCve.nvdCve;
GlobalCve.nvdCveMap=[];

GlobalCve.init=function(){
	$.getJSON("/OvalDefTool/jsonrest/cves.do", function(result){
		GlobalCve.nvdCve=result;
		 $.each(result.CVE_Items,function(idx,cve){
			 GlobalCve.nvdCveMap[cve.cve.CVE_data_meta.ID]=cve;
		 });
	});
}

GlobalCve.extractCveObject=function(cveId){
	var cve=GlobalCve.nvdCveMap[cveId];	
	return cve;
};

GlobalCve.extractCPEs=function(cveId){
	var arrCpe=[];
	var arrCpeAll=[];

	var arrNode=GlobalCve.nvdCveMap[cveId].configurations.nodes;
	for(iCtr=0;iCtr<arrNode.length;iCtr++){
		if (arrNode[iCtr].operator=="AND"){
			console.log("AND");
			arrCpe=GlobalCve.AndCpes(arrNode[iCtr]);
		}else{
			console.log("OR");
			arrCpe=GlobalCve.OrCpes(arrNode[iCtr]);
		}
	}
	return arrCpe;
};

GlobalCve.AndCpes=function(node){
	var arrCpeAnd=[];
	var children=node.children;
	for(iAnd=0; iAnd<children.length ;iAnd++){
		arrCpeAnd=GlobalCve.OrCpes(children[iAnd]);
	}
	return arrCpeAnd;
}

GlobalCve.OrCpes=function(node){
	var arrCpeOr=[];
	var cpes=node.cpe;
	for(iOr=0; iOr<cpes.length;iOr++ ){		
		arrCpeOr[iOr]=cpes[iOr].cpeMatchString;
	}
	return arrCpeOr;
}


GlobalCve.extractMsrcCve=function(cveId){
	$("body").spin("modal");
	var affectedProducts=[]; //prepare affected products
	var platform=[]; //prepare platform
	$.getJSON("/OvalDefTool/jsonrest/cvemsrcmeta.do?cveId="+cveId, function(result){
		GlobalCve.nvdCveMap[cveId].msrcCve=result;
		$.each(result.affectedProducts,function(idx,ap){
			affectedProducts.push(ap);
		});
		$("body").spin("modal");
	});
	GlobalCve.nvdCveMap[cveId].affectedProducts=affectedProducts;
	GlobalCve.nvdCveMap[cveId].platforms=platform;
	return GlobalCve.nvdCveMap[cveId].msrcCve;
}

GlobalCve.extractAffectedProducts=function(cveId){
	if(typeof GlobalCve.nvdCveMap[cveId].msrcCve === "undefined") {
		setTimeout(GlobalCve.extractMsrcCve(cveId), 10000)
	}
	console.log(GlobalCve.nvdCveMap[cveId].affectedProducts);
}


$(document).ready(function(){	
	GlobalCve.init();
});