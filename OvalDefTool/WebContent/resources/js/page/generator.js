var Gen = Gen || {};

var dtDefinition;
var dtOvalDefs;
var dtDownloadableArticles;
var parent_node;
var selected_node;
var selected_node_id="1";
var parent_node_id="1";//0 no selected node
var prev_parent_node_id="1";
var msrc;
var microsoftApi;

//var rowObject;

Gen.init=function(){
	dtOvalDefs=$("#tblOvalDefinitions").DataTable();
	dtDownloadableArticles=$("#tblDownloadableArticles").DataTable();
	dtDefinition=$("#tblDefinition").DataTable({
		"columns":[
		           {
		                "className":      'details-control',
		                "orderable":      false,
		                "data":           null,
		                "defaultContent": ''
		           },
		           null,
		           null,
		           null,
		           null,
		           null,
		           null,
		           null,
		           null
		           ]
	});
	
    $('#tblDefinition tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = dtDefinition.row( tr );
 
        if (!tr.hasClass("init-once")){//true
        	tr.addClass("init-once");
        	var cve=$(tr[0]).find("td p:first").attr("cveId").replace(/-/g,"");
            row.child( Gen.CveTableDetail(cve) ).show();            
           	$("#tbl"+cve).treetable({
        		expandable:true,
        		force:true
        	}).on("mousedown", "tr", function() {
        		$(".selected").not(this).removeClass("selected");
        		$(this).toggleClass("selected");
        		if ($(this).attr("data-tt-id")!=="1"){
        			selected_node_id=$(this).attr("data-tt-id");
        		}
        	});            	
        }
        
        if ( row.child.isShown() ) {
        	row.child.hide();
        	tr.removeClass('shown');
        }else{
        	row.child.show();
        	tr.addClass('shown');
        }
    } );

/*
	$("#tblCriteria").treetable({
		expandable:true,
		force:true
	}).on("mousedown", "tr", function() {
		alert("World");
		$(".selected").not(this).removeClass("selected");
		$(this).toggleClass("selected");
		parent_node_id=$(this).attr("data-tt-id");
		//parent_node=$("#tblCriteria").treetable("node",parent_node_id);
		//console.log(parent_node_id);
	});
*/	
	//for automatic submit of selected xml for online editing
	$("#xmlfile").change(function(){
		$("#uploadForm").submit();
	});
};

Gen.showModalReadDefintion=function(){
	$(".add-definition").click(function(){
		$("#modalNewDefinition").modal("show");
	});
};

Gen.showModalNewFamily=function(){	
	$("#modalNewFamily").modal("show");
};

Gen.showModalNewPlatform=function(){
	$("#platformFamily").val($("select[name='family']").val());
	$("#modalNewPlatform").modal("show");
};

Gen.showModalNewCriteria=function(tblCriteria){
	$("#btnSaveNewCriteria").attr("tblCriteria",tblCriteria);
	$("#modalNewCriteria").modal('show');
};


Gen.showModalNewTest=function(elem){
	if ($(elem).attr("id")==="newTest"){}
	else{
		$("input[name='testComment']").val($(elem).closest("tr").find(" td:nth-child(1)").html().split("</span>")[1]);
		$("input[name='tetRefId']").val($(elem).closest("tr").find(" td:nth-child(4)").html());
		$("input[name='testStateReference']").val($("input[name='tetRefId']").val().replace("tst","ste"));
	}
	$("#modalNewTest").modal('show');
};


Gen.showModalNewState=function(elem){
	if ($(elem).attr("id")==="newState"){}
	else{
		var tds=$(elem.closest("tr")).find("td");
		$("input[name='stateId']").val($(tds[5]).html());
		$("input[name='stateComment']").val($(tds[0]).html());
	}
	$("#modalNewState").modal("show");
};


Gen.showModalOvalId=function(){
	$("#modalSearchOval").modal("show")
};

Gen.showDownloadableArticles=function(elem){
	//var md=$(elem).next().clone();
	var md=$("#row"+$(elem).attr("cveId").replace(/-/g,""));
	$("#btn-dlg-add-criteria").attr("tblcriteria","tbl"+$(elem).attr("cveId").replace(/-/g,""));
	$("#modalDownloadableArticles").attr("cveId",$(elem).attr("cveId"));
	$("#modalDownloadableArticles").find(".modal-title").html("List of downloadable Articles [" +$(elem).attr("cveId")+"]");
	
	dtDownloadableArticles.clear().draw(false);
	dtOvalDefs.clear().draw(false);
	$.each(JSON.parse($(md).html()),function(idx,art){
		//$("#tblDownloadableArticles tbody").append("<tr><td>"+art.product+"</td><td>"+art.platform+"</td><td>"+art.downloadTitle+"</td><td><a href='"+art.downloadUrl+"'>"+art.article+"</a></td></tr>");
		//https://support.microsoft.com/en-gb/help/4019204/title
		var dUrl=art.downloadUrl;
		if (dUrl===""){
			dUrl="https://support.microsoft.com/en-gb/help/"+art.article+"/title";
		}
		if (art.downloadTitle!=="Monthly Rollup")
		dtDownloadableArticles.row.add([
		                                art.product,
		                                art.platform,
		                                art.downloadTitle,
		                                "<a target='_blank' href='"+dUrl+"'>"+art.article+"</a>",
		                                "ddvcx"]).draw(false);
	});
	
	$("#modalDownloadableArticles").modal("show");
};

Gen.searchPossibleCriteria=function(elem){
	var md=$("#row"+$(elem).attr("cveId")).html();
	$("body").spin("modal");
	$.ajax({
		  url:"/OvalDefTool/jsonrest/ajaxSearchPosibleCriterion.do?sFilter="+$("#search-text").val(),
		  type: "post",
		  contentType: 'application/json',
		  data:md,
		  success: function(result){
			  dtDownloadableArticles.clear().draw(false);
			  $.each(result,function(idx, res){
				  console.log(res);
				  var fversion="";
				  var dUrl=res.downloadUrl;
				  var sContent="";
				  if (res.fileVersion.length>0){
					  var s=res.fileVersion[0].split(",");
					  sContent=s[0] + " " + s[1];
				  }
//				  $.each(res.fileVersion,function(fidx,fres){
//					  var s=fres.split(",");
//					  fversion=fversion+"<br>"+s[0] +" "+ s[1];
//				  });
				  if (res.downloadTitle!=="Monthly Rollup")
				  dtDownloadableArticles.row.add([
				                                  res.product,
				                                  res.platform,
				                                  res.downloadTitle,
				                                  "<a target='_blank' href='"+dUrl+"'>"+res.article+"</a>",
				                                  sContent
				                                  ]).draw(false);;
			  });
			  
			  $("body").spin("modal");
		  },
		  error: function(data, status, err){
			console.log("error");		
			$("body").spin("modal");
		  }
	});		
	
};

Gen.readCveData=function(){		
	$(".process-cve-data").click(function(){
		$("body").spin("modal");
		$("#tblFamily tbody").empty();
		var urltemplate=$("input[name='referenceUrl']").attr('urltemplate');;
		
		var cveId=$("input[name='cveId']").val();
		$("input[name='referenceUrl']").val(urltemplate.replace("${cveid}",cveId));
		//msrsc
		$.getJSON("/OvalDefTool/jsonrest/cvemsrcmeta.do?cveId="+cveId, function(result){
			//console.log(result.affectedProducts[0].name);//products............
			msrc=result;
			Gen.processProducts(msrc.affectedProducts);
			$("input[name='cveTitle']").val(result.cveTitle + " - " + cveId);
			
			$("#msrcTitle").text(result.cveTitle);				
			var sHtml="<p><a target=_blank href=https://portal.msrc.microsoft.com/en-US/security-guidance/advisory/"+result.cveNumber+" source='MSRC'>"+result.cveNumber+"</a><input name='oval-input' checked=checked holder-id='#msrcMeta' class='hide ptr-hand oval-input pull-right' type='radio' /></p>";
			sHtml=sHtml+"<p>"+result.cveTitle+"</p>";
			sHtml=sHtml+"<p>"+result.description+"</p>";
			$("#msrcMeta").html(sHtml);
		}).fail(function(){
			$("#msrcTitle").text("");
			var sHtml="<p><a target=_blank href=https://portal.msrc.microsoft.com/en-US/security-guidance/advisory/"+cveId+" source='MSRC'>"+cveId+"</a><input name='oval-input' checked=checked holder-id='#msrcMeta' class='hide ptr-hand oval-input pull-right' type='radio' /></p>";
			sHtml=sHtml+"<p>"+""+"</p>";
			sHtml=sHtml+"<p>"+""+"</p>";
			$("#msrcMeta").html(sHtml);
			alert("No MSRC information....");
		});
		
		
		$.getJSON("/OvalDefTool/jsonrest/cvemitrenvdmeta.do?cveId="+cveId, function(result){
			//1st mitre 2nd nvd
			$.each(result,function(idx,cveElem){	
				var sHandler="#mitreMeta";
				var sSource="CVE"
				if (idx===1){
					sHandler="#nvdMeta";
					sSource="NVD";
				}
				
				var sHtml="<p><a target=_blank href="+cveElem.url+" source='"+sSource+"'>"+cveElem.cveNumber+"</a><input name='oval-input' holder-id='"+sHandler+"' class='hide ptr-hand oval-input pull-right' type='radio' source='' /></p>";
				sHtml=sHtml+"<p >"+$("#msrcTitle").text()+"</p>";
				sHtml=sHtml+"<p>"+cveElem.description+"</p>";

				
				$(sHandler).html(sHtml);
				sHtml="";

			});//each
			$("body").spin("modal");
		});//getJson
		
	});//$(".process-cve-data")
};

Gen.click=function(){
	
	//save family
	$("#btnSaveNewFamily").click(function(){
		$("body").spin("modal");
		var family=new Object();
		family.name=$("#familyName").val();
		family.description=$("#familyDescription").val();
		$.ajax({
			  url:"/OvalDefTool/db/ajaxSaveFamily.do",
			  type: "post",
			  contentType: 'application/json',
			  data:JSON.stringify(family),
			  success: function(result){
				  $("select[name='family']").append("<option>"+$("#familyName").val()+"</option>");
				  $("body").spin("modal");
			  },
			  error: function(data, status, err){
				console.log("error");		
				$("body").spin("modal");
			  }
		});		
	});//btnSaveNewFamily
	

	//save family
	$("#btnSaveNewPlatform").click(function(){
		$("body").spin("modal");
		var platform=new Object();
		platform.name=$("#platformName").val();
		platform.family=$("#platformFamily").val();
		platform.description=$("#platformDescription").val();
		$.ajax({
			  url:"/OvalDefTool/db/ajaxSavePlatform.do",
			  type: "post",
			  //contentType: 'application/json',
			  data:JSON.stringify(platform),
			  success: function(result){
				  $("select[name='cplatform']").append("<option>"+$("#platformName").val()+"</option>");
				  $("select[name='cplatform']").selectpicker("refresh");
				  $("body").spin("modal");
			  },
			  error: function(data, status, err){
				console.log("error");		
				$("body").spin("modal");
			  }
		});		
	});//btnSaveNewFamily
	
	$("#btnSaveNewCriteria").click(function(){	
		var criteria=new Object();
		criteria.tblCriteria=$(this).attr("tblCriteria");
		criteria.type=$("select[name='criteriaType']").val();
		criteria.operator=$("select[name='criteriaOperator']").val();
		criteria.comment=$("input[name='criteriaComment']").val();
		criteria.refId=$("input[name='criteriaRefId']").val();
		
		var newid=Math.floor(Math.random() * 10000000);
		
		if ($("select[name='criteriaType']").val()!=="Criteria"){
			operator="";
		}
		
		var pr_node_id=parent_node_id;
		var pr_node;
		if ($("input[name=activeNode]:checked").attr("node") ==="sn"){
			pr_node_id=selected_node_id;
		}
		
		console.log("parent_node_id: " + parent_node_id);
		console.log("parent_node: " + parent_node);
		console.log("selected_node_id: " + selected_node_id);
		

		Gen.plotCriteria(criteria, pr_node_id);
		$("input[name='criteriaRefId']").val("");
	});//btnSaveNewCriteria
	
	
	$("#btn-dlg-add-criteria").click(function(){	
		var criteria=new Object();
		criteria.tblCriteria=$(this).attr("tblCriteria");
		criteria.type=$("select[name='criteriaType1']").val();
		criteria.operator=$("select[name='criteriaOperator1']").val();
		criteria.comment=$("input[name='criteriaComment1']").val();
		criteria.refId=$("input[name='criteriaRefId1']").val();
		
		var newid=Math.floor(Math.random() * 10000000);
		
		if ($("select[name='criteriaType1']").val()!=="Criteria"){
			operator="";
		}
				
		var pr_node_id=parent_node_id;
		var pr_node;
		if ($("input[name=activeNode1]:checked").attr("node") ==="sn"){
			pr_node_id=selected_node_id;
		}
		
		console.log("parent_node_id: " + parent_node_id);
		console.log("parent_node: " + parent_node);
		console.log("selected_node_id: " + selected_node_id);

		Gen.plotCriteria(criteria, pr_node_id);
		$("input[name='criteriaRefId1']").val("");
	});//btn-dlg-add-criteria
	
	
	$("#btnSaveNewTest").click(function(){
		var test=new Object();
		test.check=$("select[name='testCheck']").val();
		test.checkExistence=$("select[name='testCheckExistence']").val();
		test.comment=$("input[name='testComment']").val();
		test.testId=$("input[name='tetRefId']").val();
		test.objectReference=$("input[name='tetObjectReference']").val();
		test.stateReference=$("input[name='testStateReference']").val();

		var newid=Math.floor(Math.random() * 10000000);
		$("#tblTest tbody").append("<tr id='"+newid+"'>" +
				"<td>"+test.comment+"</td>" +	
				"<td>"+test.check+"</td>" +	
				"<td>"+test.checkExistence+"</td>" +	
				"<td>"+test.testId+"</td>" +	
				"<td>"+test.objectReference+"</td>" +	
				"<td>"+test.stateReference+"</td>" +	
				"<td>" +
					"<span class='fa fa-desktop ptr-hand' onclick='Gen.showModalNewState(this)'></span>" +
					"<span class='fa fa-trash ptr-hand' onclick='Gen.deleteTest("+newid+")'></span>" +
				"</td>" +
			"</tr>");
	});
	
	$("#btnSaveNewState").click(function(){
		var newid=Math.floor(Math.random() * 10000000);
		var state=new Object();
		state.comment=$("input[name=stateComment]").val();
		state.type=$("select[name=stateType").val();
		state.id=$("input[name=stateId]").val();
		state.operation=$("input[name=stateOperation]").val();
		state.operationValue=$("input[name=stateOperationValue]").val();
		state.version=$("input[name=stateVersion]").val();		
		console.log(state);
		$("#tblState tbody").append("<tr><td>"+state.type+"</td><td>"+state.comment+"</td><td>"+state.id+"</td><td>"+state.operation+"</td><td>"+state.operationValue+"</td><td>"+state.version+"</td></tr>");
	});	
	
	
	//add definition
	$("#btnAddDef").click(function(){
		$("body").spin("modal");
		
		//clear fields
		$("input[name='ovalTitle']").val("");
		//$("input[name='ovalClass']").val(ovalData.ovalClass);
		$("input[name='referenceId']").val("");
		//$("input[name='referenceUrl']").val(ovalData.ref_url);
		//$("input[name='referenceSource']").val(ovalData.source);
		$("textarea[name='ovalDescription']").val("");
		
		//definition
		var cveId=$("input[name='cveId']").val();
		var ovalHandler=$("input[name='oval-input']:checked").attr("holder-id");
//		console.log(ovalHandler);
		var ovalData=new Object();
		
		ovalData.title=$("input[name='cveTitle']").val();
		ovalData.ovalClass="vulnerability";
		ovalData.ref_id=cveId;
		ovalData.ref_url=$(ovalHandler+" p a").attr("href");
		ovalData.source=$(ovalHandler+" p a").attr("source");
		var ovalDescription="";
		
		$.each($("#nvdMeta p"),function(idx, elem){
			if (idx>=2){
				ovalDescription=ovalDescription+$(elem).html();
			}
		})
		ovalData.description=ovalDescription;
		
		//platform/product
		var platform="";
		var platformCtr=0;
		var badgePlatformColor="btn-danger";
		var product="";
		var productCtr=0;
		var badgeProductColor="btn-danger";
		$.each($(".chk-platform:checked"),function(idx, item){
			if ($(item).attr("itemtype")==="platform"){
				platform=platform+$(item).attr("item") + ",";
				platformCtr++;
				badgePlatformColor="btn-primary";
			}else{
				product=product+$(item).attr("item") + ",";
				productCtr++;
				badgeProductColor="btn-primary";
			}
				
		});
		
		
		//for articles needed for criteria analysis
		
		var arrArticle=[];
		var bodyCount;
		//console.log(msrc.cveNumber);
		$.each(msrc.affectedProducts,function(idx,ap){
			var downloadUrl1="https://support.microsoft.com/en-gb/help/4019204/title";
			var downloadUrl2="";
			var downloadUrl3="";
			var downloadUrl4="";
			//console.log("********************************************************************");
			//console.log("Product: " + ap.name);
			//console.log("Platform: " + ap.platform);

			
			
			if (ap.articleUrl1!==null){
				var article=new Object();
				article.product=ap.name
				article.platform=ap.platform;
				
				microsoftApi=Gen.getFromMicrosoftFileInfo(ap.articleTitle1);
				article.article=ap.articleTitle1;
				article.downloadUrl=Gen.getKbDownloadUrl(microsoftApi.details.body);
				article.downloadTitle=ap.downloadTitle1;
				arrArticle.push(article);
			}
			
			if (ap.articleUrl2!==null){
				var article=new Object();
				article.product=ap.name
				article.platform=ap.platform;
				
				microsoftApi=Gen.getFromMicrosoftFileInfo(ap.articleTitle2);
				article.article=ap.articleTitle2;
				article.downloadUrl=Gen.getKbDownloadUrl(microsoftApi.details.body);
				article.downloadTitle=ap.downloadTitle2;
				arrArticle.push(article);
			}
			
			if (ap.articleUrl3!==null){
				var article=new Object();
				article.product=ap.name
				article.platform=ap.platform;
				
				microsoftApi=Gen.getFromMicrosoftFileInfo(ap.articleTitle3);
				article.article=ap.articleTitle3;
				article.downloadUrl=Gen.getKbDownloadUrl(microsoftApi.details.body);
				article.downloadTitle=ap.downloadTitle3;
				arrArticle.push(article);
			}
			
			if (ap.articleUrl4!==null){
				var article=new Object();
				article.product=ap.name
				article.platform=ap.platform;
				
				microsoftApi=Gen.getFromMicrosoftFileInfo(ap.articleTitle4);
				article.article=ap.articleTitle4;
				article.downloadUrl=Gen.getKbDownloadUrl(microsoftApi.details.body);
				article.downloadTitle=ap.downloadTitle4;
				arrArticle.push(article);
			}
			
			console.log(article);
	
		});
		var articles=Gen.cleanseArticle(arrArticle);
		
		dtDefinition.row.add(["",
		                     "<p id=hlCveId cveId='"+ovalData.ref_id+"'><input type=radio checked criteria='' cveId='"+ovalData.ref_id+"' name='ts' onclick=Gen.clickCveOption(this) onblur=Gen.clickLostOption(this) />"+ovalData.ref_id+"</p>"+
		                     "<p id=hlCveSource class='hide'>CVE</p>"+
		                     "<p id=hlFamily class='hide'>windows</p>"+
		                     "<p id=hlClass class='hide'>vulnerability</p>"+
		                     "<p id=hlTitle class='hide'>"+ovalData.title+"</p>"+
		                     "<p id=hlPlatform class='hide'>"+platform+"</p>"+
		                     "<p id=hlProduct class='hide'>"+product+"</p>"+
		                     "<p id=hlRefId class='hide'>"+ovalData.ref_id+"</p>"+
		                     "<p id=hlRefUrl class='hide'>http://cve.mitre.org/cgi-bin/cvename.cgi?name="+ovalData.ref_id+"</p>"+
		                     "<p id=hlRefSource class='hide'>CVE</p>"+
		                     "<p id=hlDescription class='hide'>"+ovalData.description+"</p>",
		                     ovalData.title,
		                      "<p title='"+ovalData.description+"'>"+ovalData.description.substr(0,50)+"...</p>",
		                      "<button type='button' class='btn "+badgePlatformColor+"'><span class='badge' platfor="+platform+">"+platformCtr+"</span></button>",
		                      "<button type='button' class='btn "+badgeProductColor+"'><span class='badge' product="+product+">"+productCtr+"</span></button>",
		                      "oval:com.xxxx:def:"+ Math.floor((Math.random() * 1000) + 300) ,
		                      "<span title='show downloadable articles' cveId="+cveId+" class='btn btn-default fa fa-search' onclick='Gen.showDownloadableArticles(this)'></span>" +
		                      "<div class='row hide'>"+JSON.stringify(articles)+"</div>" +
		                      "<span title='search criterion' cveId="+cveId+" class='btn btn-default fa fa-binoculars' onclick='Gen.searchPossibleCriteria(this)'></span>"+
		                      "<div id=row"+ovalData.ref_id.replace(/-/g,"")+" class='row hide'>"+JSON.stringify(arrArticle)+"</div>",
		                      "<span title='edit' class='btn btn-default fa fa-edit'></span>" +
		                      "<span title='delete' onclick='Gen.deleteDefinition(this)' class='btn btn-default fa fa-trash'></span>"]).draw(false);
		
		$("#search-btn").attr("cveId",ovalData.ref_id.replace(/-/g,""));
//		console.log(ovalData);
		$("input[name='ovalTitle']").val(ovalData.title);
		$("input[name='ovalClass']").val(ovalData.ovalClass);
		$("input[name='referenceId']").val(ovalData.ref_id);
		//$("input[name='referenceUrl']").val(ovalData.ref_url);
		//$("input[name='referenceSource']").val(ovalData.source);
		$("textarea[name='ovalDescription']").val(ovalData.description);
		
		$("body").spin("modal");
	});//btnAddDef
	
	
	$(".chk-platform-all").change(function(){
		if(this.checked) {
	        $(".chk-platform").prop('checked',true);
	    }else{
	    	$(".chk-platform").prop('checked',false);
	    }

	});
	
	
	//***************Search Oval ID
	$("#btnSearchOval").click(function(){
		$("body").spin("modal");
		var ovalPram=new Object();
		ovalPram.definition_id=$("#definition_id").val();
		ovalPram.title=$("#title").val();
		ovalPram.description=$("#description").val();
		ovalPram.reference_id=$("#reference_id").val();
		
		ovalPram.platform=$("#platform").val();
		ovalPram.product=$("#product").val();
		ovalPram.aclass=$("#class").val();
		ovalPram.family=$("#family").val();
		ovalPram.status=$("#status").val();
		console.log(ovalPram);
		var cveId=$("#modalDownloadableArticles").attr("cveId");
		
		$.ajax({
			  url:"/OvalDefTool/jsonrest/ovaldefids.do",
			  type: "post",
			  dataType:'json',
			  contentType: 'application/json',
			  data:JSON.stringify(ovalPram),
			  success: function(definitions){
					dtOvalDefs.clear().draw(false);
					$.each(definitions,function(idx, def){
						dtOvalDefs.row.add( [
						                     def.definitionUrl,
						                     def.definitionClass,
						                     def.title,
						                     def.lastModified,
						                     "<span cveId="+cveId+" oval-ref-id='"+def.definitionId+"' onclick='Gen.addExtendedDefinition(this)' title='add as extended' class='btn btn-default fa fa-hand-o-up'></span>"
						                     +"<span cveId="+cveId+" oval-ref-id='"+def.definitionId+"' onclick='Gen.addCriterion(this)' title='add as criterion' class='btn btn-default fa fa-thumbs-up'></span>"
						                     ]).draw( false );
					})
					$("body").spin("modal");
			  },
			  error: function(data, status, err){
				console.log("error");		
				$("body").spin("modal");
			  }
		});		
	});	//btnSearchOval
	

	
	//Generate Oval XML
	var success = false;  //NOTE THIS
	$("#btnGenerateOvalDefinition").click(function(){
		$("body").spin("modal");
		var generator=new Object();
		generator.product_name=$("input[name='productName']").val();
		generator.product_version=$("input[name='productVersion']").val();
		generator.schema_version=$("input[name='schemaVersion']").val();
		generator.timestamp=$("input[name='productTimestamp']").val();
		
		generator.title=$("input[name='ovalTitle']").val();
		generator.family=$("select[name='family']").val();
		
		//definition
		var definition=new Object();
		definition.definitionId=$("input[name='ovalId']").val();
		definition.definitionClass=$("select[name='ovalClass']").val();
		definition.title=$("input[name='ovalTitle']").val();
		definition.version=$("input[name='ovalVersion']").val();
		definition.description=$("textarea[name='ovalDescription']").val();
		generator.definition=definition;
		
		//affected
		var affected=[];
		$.each($("select[name='cplatform']").val(),function(idx,content){
			var platform=new Object();
			platform.name=content;
			affected.push(platform);
		})
		generator.affected=affected;
		
		var reference=new Object();
		reference.ref_id=$("input[name='referenceId']").val();
		reference.ref_url=$("input[name='referenceUrl']").val();
		reference.source=$("input[name='referenceSource']").val();
		definition.reference=reference;
		
		//criteria
		generator.criterias=Gen.compressNode("1");
/*		
		test.check=$("select[name='testCheck']").val();
		test.checkExistence=$("select[name='testCheckExistence']").val();
		test.comment=$("input[name='testComment']").val();
		test.referenceId=$("input[name='tetRefId']").val();
		test.objectReference=$("input[name='tetObjectReference']").val();
		test.stateReference=$("input[name='testStateReference']").val();
*/
		
		var tests=[];
		$.each($("#tblTest tbody tr"),function(idx,elem){
			var test=new Object();
			var tds=$(elem).find("td");
			test.comment=tds[0].innerHTML;			
			test.check=tds[1].innerHTML;			
			test.checkExistence=tds[2].innerHTML;			
			test.referenceId=tds[3].innerHTML;			
			test.objectReference=tds[4].innerHTML;
			test.stateReference=tds[5].innerHTML;
			tests.push(test);
		});
		generator.tests=tests;
		
		
		$.ajax({
			  url:"/OvalDefTool/cve/generate_oval.do",
			  type: "post",
			  dataType:'html',
			  contentType: 'application/json',
			  data:JSON.stringify(generator),
			  async:false, 
			  success: function(ovalXml){
					success=true;					
					$("body").spin("modal");
			  },
			  error: function(data, status, err){
				console.log("error");		
				$("body").spin("modal");
			  }
		});		
		 if(success){ //AND THIS CHANGED
			 window.open("/OvalDefTool/cve/xmlresult.do","_blank",'toolbar=no, location=no, status=no, menubar=no, scrollbars=yes');
		 }
	});//$("#btnGenerateOvalDefinition")
	
	
};//Gen.click


Gen.select=function(){
	//select family
	$("select[name='family']").change(function(){
		$.getJSON("/OvalDefTool/db/ajaxReadPlatform.do?family="+$(this).val(), function(result){
			$("select[name='cplatform']").empty();
			$("select[name='cplatform']").append("<option value='0'></option>");
			$.each(result,function(idx, plat){
				$("select[name='cplatform']").append("<option>"+plat.name+"</option>");
			});
			$("select[name='cplatform']").selectpicker('refresh');
			
		});		
	});//select[name='family']
	
	
	$("select[name='criteriaType']").change(function(){
		if ($(this).val()==="Criteria"){
			$("#holderRefId").addClass("hide");
			$("#holderOperator").removeClass('hide');
		}else{
			$("#holderRefId").removeClass("hide");
			$("input[name='criteriaRefId']").val("oval:com.xxxx:tst:" + Math.floor((Math.random() * 100) + 100));
			$("#holderOperator").addClass('hide');
		}
	});
	
	$("select[name='criteriaType1']").change(function(){
		if ($(this).val()==="Criteria"){
			$("#holderRefId1").addClass("hide");
			$("#holderOperator1").removeClass('hide');
		}else{
			$("#holderRefId1").removeClass("hide");
			$("input[name='criteriaRefId1']").val("oval:com.xxxx:tst:" + Math.floor((Math.random() * 100) + 100));
			$("#holderOperator1").addClass('hide');
		}
	});
		
};//Gen.select


Gen.showRefreshCriteria=function(tblCriteria){

};



Gen.addCriteria=function(cve){
	var criteria=new Object();
	criteria.tblCriteria="tbl"+cve;
	criteria.type="type";
	criteria.operator="operator";
	criteria.comment="comment";
	criteria.refId="ref id";
	console.log(criteria);
	Gen.plotCriteria(criteria);
}

Gen.addExtendedDefinition=function(elem){
	//oval:org.cisecurity:def:807
	$("#tblCriteria tr:first").addClass("selected");
	var criteria=new Object();
	criteria.tblCriteria="tbl"+$(elem).attr("cveId").replace(/-/g,"");
	criteria.type="extended_definition";
	criteria.operator="";
	criteria.comment=$($(elem).parents("tr").find("td")[2]).html();
	criteria.refId=$(elem).attr("oval-ref-id");
	$("#tblCriteria tr:first").addClass("selected");
	Gen.plotCriteria(criteria);
};

Gen.addCriterion=function(elem){
	//oval:org.cisecurity:def:807
	$("#tblCriteria tr:first").addClass("selected");
	var criteria=new Object();
	criteria.tblCriteria="tbl"+$(elem).attr("cveId").replace(/-/g,"");
	criteria.type="criterion";
	criteria.operator="";
	criteria.comment=$($(elem).parents("tr").find("td")[2]).html();
	criteria.refId=$(elem).attr("oval-ref-id");
	$("#tblCriteria tr:first").addClass("selected");
	Gen.plotCriteria(criteria);	
};

var ste;
Gen.addState=function(elem){
	ste=elem;
}


Gen.deleteNode=function(tblCriteria,node_id){
	//var node=$("#tblCriteria").treetable('node',node_id);
	//$("#tblCriteria").treetable('unloadBranch',node);
	
	$("#"+tblCriteria).treetable('removeNode',node_id);
	parent_node_id=1;
};

Gen.deleteTest=function(rowId){
	$("#"+rowId).remove();
}

Gen.deleteDefinition=function(elem){
	dtDefinition.row($(elem).parents('tr')).remove().draw();
}



Gen.compressNode=function(tblCriteria,ttId){
	var trNodes=$("#"+tblCriteria+" tbody tr[data-tt-parent-id='"+ttId+"']")
	
	var criterias=[];
	$.each(trNodes,function(idx,trNode){
		var nodeId=$(trNode).attr("data-tt-id");
		var tds=$(trNode).find("td");
		//console.log($(tds[0]).html().split("</span>")[1]);	
		var criteria=new Object();
		criteria.comment=$(tds[0]).html().split("</span>")[1]
		criteria.type=$(tds[1]).html();
		criteria.operator=$(tds[2]).html();
		criteria.referenceId=$(tds[3]).html()		
		criteria.nodeId=nodeId;
		criteria.parentNodeId=$(trNode).attr("data-tt-parent-id");
		criterias.push(criteria);
		
		criteria.criterias=Gen.compressNode(tblCriteria,nodeId);
	});
	return criterias;
};

Gen.extractNode=function(strNode){
	
}

Gen.compressTest=function(){
	var tests=[];
	$.each($("#tblTest tbody tr"),function(idx,tr){
		var tds=$(tr).find("td");
		var test=new Object();
		test.check=$(tds[1]).html();
		test.checkExistence=$(tds[2]).html();
		test.comment=$(tds[0]).html();
		test.testId=$(tds[3]).html();
		test.objectReference=$(tds[4]).html();
		test.stateReference=$(tds[5]).html();
		tests.push(test);
	});
	console.log(tests);
	return tests;
};

Gen.compressState=function(){
	var states=[];
	$.each($("#tblState tbody tr"),function(idx,tr){
		var tds=$(tr).find("td");
		var state=new Object();
		state.comment=$(tds[1]).html();
		state.type=$(tds[0]).html();
		state.stateId=$(tds[2]).html();
		state.operation=$(tds[3]).html();
		state.operationValue=$(tds[4]).html();
		state.version=$(tds[5]).html();
		states.push(state);
	});
	console.log(states);
	return states;
};


Gen.processProducts=function(affectedProducts){
	$("body").spin("modal");
	var products=[];
	$.each(affectedProducts,function(idx,p){
//		console.log(p.name);
//		console.log(p.platform);
		var product=new Object();
		product.name=p.name;
		product.platform=p.platform
		products.push(product);
		
	});
	$.ajax({
		  url:"/OvalDefTool/jsonrest/extractproducts.do",
		  type: "post",
		  dataType:'json',
		  contentType: 'application/json',
		  data:JSON.stringify(products),
		  async:false, 
		  success: function(result){
//			console.log(result); 
			$.each(result,function(idx,res){
				$("#tblFamily tbody").append("<tr><td>"+res.name+"</td><td>"+res.type+"</td><td><input item='"+res.name+"' itemtype='"+res.type+"' class='chk-platform' checked=checked type='checkbox'></td></tr>");
			});			
			$("body").spin("modal");			
		  },
		  error: function(data, status, err){
			console.log("error");		
			$("body").spin("modal");
		  }
	});	
		
}

var success=false;
Gen.generateOvalDef=function(){
	$("#btnGenerateOvalDef").click(function(){
		var ovaldefs=[];
		$("body").spin("modal");
		var datas=dtDefinition.rows().data();
		$.each(datas,function(idx,elem){//row
			$($(elem[1])[0]).html();
			var od=new Object();
			od.refId=$($(elem[1])[0]).attr("cveId");//$($(elem[0])[0]).html();		//cveId/refId
			od.refUrl=$($(elem[1])[8]).html();		//refUrl
			od.source=$($(elem[1])[9]).html();		//source
			od.ovalClass=$($(elem[1])[3]).html();	//class
			od.ovalId="oval:com.xxxx:def:389"		//will be replaced in backend
			od.ovalVersion="0";						//will be replaced in backend
			od.productName=$("input[name='productName']").val();
			od.productVersion=$("input[name='productVersion']").val();
			od.schemaVersion=$("input[name='schemaVersion']").val();
			od.timestamp=$("input[name='productTimestamp']").val();
			od.title=$($(elem[1])[4]).html();		//title
			od.description=$($(elem[1])[10]).html();	//description
			od.submitted=$("input[name='productTimestamp']").val();
			od.family=$($(elem[1])[2]).html();		//family
			od.platform=$($(elem[1])[5]).html();		//platform
			od.product=$($(elem[1])[6]).html();		//product
			
//			console.log("TBLE ID: tbl"+od.refId);
			od.criterias=Gen.compressNode("tbl"+od.refId.replace(/-/g,""),"1");
			od.tests=Gen.compressTest();
			od.states=Gen.compressState();
			
			ovaldefs.push(od);
		});
		console.log(ovaldefs);

		$.ajax({
			  url:"/OvalDefTool/cve/generateovaldef.do",
			  type: "post",
			  dataType:'html',
			  contentType: 'application/json',
			  data:JSON.stringify(ovaldefs),
			  async:false, 
			  success: function(ovalXml){
				success=true;					
				$("body").spin("modal");
			  },
			  error: function(data, status, err){
				console.log(err);		
				$("body").spin("modal");
			  }
		});	
		
		 if(success){ //AND THIS CHANGED
			 window.open("/OvalDefTool/cve/xmlresult.do","_blank",'toolbar=no, location=no, status=no, menubar=no, scrollbars=yes');
		 }
	});
};

var apiRes;
Gen.getFromMicrosoftFileInfo=function(kbNumber){
//	$("body").spin("modal");	
	$.ajax({
		  url:"/OvalDefTool/jsonrest/readmicrosofturl.do?kbNumber="+kbNumber,
		  type: "get",
		  dataType:'json',
		  contentType: 'application/json',
		  async:false, 
		  success: function(result){
			  apiRes=result;
//			  $("body").spin("modal");
		  },
		  error: function(data, status, err){
			console.log(err);
//			$("body").spin("modal");
		  }
	});	
	return apiRes;
}

Gen.getKbDownloadUrl=function(bodys){
	  var url="";
	  for(var i=0;i<bodys.length;i++){
		  var b=bodys[i];				  
		  if ($.inArray(b.title,["How to get this update","File information"])>-1){
//			  console.log(b.title + " [index: " + i);
//			  console.log("URL: " + $(b.content[0]).find("a[href$=csv]").attr("href"))
			  url= $(b.content[0]).find("a[href$=csv]").attr("href");
			  break;
		  }
	  }
	  return url;
};

Gen.cleanseArticle=function(articles){
//	$("body").spin("modal");
	var art=new Object();
	$.ajax({
		  url:"/OvalDefTool/jsonrest/cleansearticles.do",
		  type: "post",
		  dataType:'json',
		  contentType: 'application/json',
		  async:false, 
		  data:JSON.stringify(articles),
		  success: function(result){
			  art=result;
//			  $("body").spin("modal");
		  },
		  error: function(data, status, err){
//			  $("body").spin("modal");
		  }
	});	
	
	return art;
	
};

Gen.plotCriteria=function(criteria, pr_node_id){
	var newid=Math.floor(Math.random() * 10000000);
	console.log(criteria.tblCriteria);

	pr_node=$("#"+criteria.tblCriteria).treetable("node",pr_node_id);
	
	$("#"+criteria.tblCriteria).treetable("loadBranch"
			,pr_node
			,"<tr ondragstart=Gen.dragCriteria(event) draggable='true' ondrop=Gen.dropCriteria(event,\'"+criteria.tblCriteria+"\') ondragenter=Gen.dragoverCriteria(event) ondragover=Gen.dragoverCriteria(event) class='branch' data-tt-id='"+newid
				+"' data-tt-parent-id='"+pr_node_id
				+"' ><td>"+criteria.comment+"</td><td>"+criteria.type+"</td><td>"+criteria.operator+"</td>" 
				+"<td>"+criteria.refId+"</td>" +
				"<td>" +
			    "<span onclick=Gen.showModalNewTest(this) title='test' class='ptr-hand fa fa-eyedropper'></span>"+
			    "<span title='state' class='ptr-hand fa fa-thumbs-up'></span>"+
			    "<span title='delete' class='fa fa-trash ptr-hand' onclick=Gen.deleteNode(\'"+criteria.tblCriteria+"\',\'"+newid+"\')></span>"+
						"</td></tr>");


}


Gen.dragCriteria=function(event){//drag start draggable="true"
	console.log("**********************ondragstart********************************");
	console.log("parent-tt-id: " + $(event.target).attr("data-tt-parent-id"));
	console.log("Source tt-id: " + $(event.target).attr("data-tt-id"));
	console.log(event.target.id);
	event.dataTransfer.setData("src-tt-id", $(event.target).attr("data-tt-id"));
};

Gen.dropCriteria=function(event, tblCriteria){// drop

	//console.log("ondrop: " + event);
	console.log("**********************ondrop********************************");
	var src=event.dataTransfer.getData("src-tt-id");
	console.log("Source data-tt-id: " + src);
	
	var dest=$(event.target).parent().attr("data-tt-id");
	console.log("parent-tt-id: " + $(event.target).parent().attr("data-tt-parent-id"));
	console.log("Destination data-tt-id: " + dest);
	if (src!==dest){
		$("tr[data-tt-id='"+src+"']").attr("data-tt-parent-id",dest);
		$("#"+tblCriteria).treetable("move",src,dest);
	}
};

Gen.dragoverCriteria=function(event){//drag over
	event.preventDefault();
};

Gen.clickCveOption=function(elem){
	console.log("Focus: " + $(elem).attr("cveId"));
	$("#newCriteria").html("New Criteria for " + $(elem).attr("cveId"));
}

Gen.clickLostOption=function(elem){
	console.log("Lost : " + $(elem).attr("cveId"));
	
}

Gen.CveTableDetail=function(criteriaHolder) {
    // `d` is the original data object for the row
/*    
 	return '<table class="col-md-12" cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
        '<tr>'+
            '<td>Full name:</td>'+
            '<td>Hello</td>'+
        '</tr>'+
        '<tr>'+
            '<td>Extension number:</td>'+
            '<td>World</td>'+
        '</tr>'+
        '<tr>'+
            '<td>Extra info:</td>'+
            '<td>And any further details here (images etc)...</td>'+
        '</tr>'+
    '</table>';
*/
	var strTable="tbl" + criteriaHolder;
 	return '<table id="'+strTable+'" class="col-md-12" cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
			    '<tr data-tt-id="1"  ondrop="Gen.dropCriteria(event)" ondragenter="Gen.dragoverCriteria(event)" ondragover="Gen.dragoverCriteria(event)"><th>'+
			    '<span onclick=Gen.showModalNewCriteria("'+strTable+'")  class="ptr-hand fa fa-cubes fa-2"></span>'+
			    '<span onclick=Gen.showRefreshCriteria("'+strTable+'")  class="ptr-hand fa fa-refresh fa-2"></span>'+
			    '<span>Level/Comment</th><th>Type</th><th>Operator</span></th><th>Reference ID</th><th>Action</th></tr>' +
			'</table>';
    
}

$(document).ready(function(){
	Gen.init();
	Gen.showModalReadDefintion();
	Gen.readCveData();
	Gen.click();
	Gen.select();
	Gen.generateOvalDef();	
});