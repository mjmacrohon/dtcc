<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/template/header.jsp" %>
<!--Page CSS-->
<link rel="stylesheet" href="../resources/css/page/cve.css">

</head>
<body style="background-color:seagreen">
	<%@include file="/template/navigation.jsp" %>
	<div class="container-fluid">
		<div class="start-container">	
				<!-- Generation -->
				<fieldset class="scheduler-border">
					<legend class="scheduler-border">Generation <i title="initialize cves" class="init-cves fa fa-asterisk ptr-hand" aria-hidden="true"></i></legend>	
					<div class="row">
						<div class="input-group just-float-left col-sm-4 col-md-4">
							<span class="input-group-addon" id="productName">Product Name</span>
							<input type="text" name="productName" class="form-control" placeholder="productName" value="CSI OVAL Repository" aria-describedby="basic-addon1">
						</div>
						<div class="input-group just-float-left col-sm-2 col-md-2">
							<span class="input-group-addon" id="productVersion">Product Version</span>
							<input type="text" name="productVersion" class="form-control" placeholder="productVersion" value="0.1" aria-describedby="basic-addon1">
						</div>
						<div class="input-group just-float-left col-sm-2 col-md-2">
							<span class="input-group-addon" id="schemaVersion">Schema Version</span>
							<input type="text" name="schemaVersion" class="form-control" placeholder="schemaVersion" value="5.11.1" aria-describedby="basic-addon1">
						</div>
						<div class="input-group col-sm-4 col-md-4">
							<span class="input-group-addon" id="productTimestamp">Timestamp</span>
							<input type="text" name="productTimestamp" class="form-control" placeholder="productTimestamp" aria-describedby="basic-addon1">
						</div>		
					</div><!-- row -->
				</fieldset>	
				
				<!-- Definitions -->
				<fieldset class="scheduler-border">		
					<legend class="scheduler-border">Definitions <i class="add-definition fa fa-plus-square-o ptr-hand" aria-hidden="true"></i></legend>				
						<div class="row">
							<table id="tblDefinition">
								<thead>
									<tr>
										<th></th>
										<th class="col-md-1">CVE Number</th>
										<th class="col-md-2">Title</th>
										<th class="col-md-3">Description</th>
										<th class="col-md-1">Platform</th>
										<th class="col-md-2">Product</th>
										<th class="col-md-1">ID</th>
										<th class="col-md-1">Articles</th>
										<th class="col-md-1">Action</th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</div>
					
					<div class="hide row spacer"><!-- oval-def -->
						<div class="input-group just-float-left col-sm-4 col-md-6">
							<span class="input-group-addon" >Oval Title</span>
							<input type="text" name="ovalTitle" class="form-control" placeholder="title"  aria-describedby="basic-addon1">
						</div>
						<div class="input-group just-float-left col-sm-2 col-md-2">
							<span class="input-group-addon" >Oval Class</span>
							<select name="ovalClass" class="form-control"    aria-describedby="basic-addon1">
								<option>vulnerability</option>
								<option>miscellaneous</option>
								<option>inventory</option>
								<option>compliance</option>
								<option>patch</option>
							</select>
						</div>
						<div class="input-group just-float-left col-sm-2 col-md-3">
							<span class="input-group-addon" >Oval ID</span>
							<span onclick='Gen.showModalOvalId()' class="input-group-addon"><i class="ptr-hand fa fa-asterisk"></i></span>
							<input type="text" name="ovalId" class="form-control" placeholder="oval id"  aria-describedby="basic-addon1">
						</div>
						<div class="input-group just-float-left col-sm-2 col-md-1">
							<span class="input-group-addon" >Version</span>
							<input type="text" name="ovalVersion" class="form-control" value="0"  aria-describedby="basic-addon1">
						</div>
					</div><!-- row -->
					<!-- reference -->
					<div class="hide row">
						<div class="input-group just-float-left col-sm-2 col-md-2">
							<span class="input-group-addon" >Reference ID</span>
							<input type="text" name="referenceId" class="form-control" placeholder="ref id"  aria-describedby="basic-addon1">
						</div>
						<div class="input-group just-float-left col-sm-3 col-md-3">
							<span class="input-group-addon" >URL</span>
							<input type="text" name="referenceUrl" class="form-control" urltemplate=<spring:eval expression="@appProp.getProperty('ref.url')" /> value=<spring:eval expression="@appProp.getProperty('ref.url')" /> placeholder="url"  aria-describedby="basic-addon1">
						</div>
						<div class="input-group just-float-left col-sm-1 col-md-1">
							<span class="input-group-addon">Source</span>
							<input type="text" name="referenceSource" value=<spring:eval expression="@appProp.getProperty('ref.source')" /> class="form-control" placeholder="source"  aria-describedby="basic-addon1">
						</div>
						<div class="input-group just-float-left col-sm-2 col-md-2">
							<span class="input-group-addon" >Family</span>
							<span onclick='Gen.showModalNewFamily()' class="input-group-addon"><i class="ptr-hand fa fa-asterisk"></i></span>
							<select name="family" class="form-control"   aria-describedby="basic-addon1">
								<c:forEach items="${families}" var="family">
									<option>${family.name}</option>								
								</c:forEach>								
							</select>
						</div>						
						<div class="input-group just-float-left col-sm-4 col-md-4">
							<span class="input-group-addon" >Affected</span>
							<span onclick='Gen.showModalNewPlatform()' class="input-group-addon"><i class="ptr-hand fa fa-asterisk"></i></span>
							<select name="cplatform" class="form-control selectpicker"  multiple data-actions-box="true"  aria-describedby="basic-addon1">
								
							</select>
						</div>								
					</div>
					<div class="hide row spacer">
						<textarea name="ovalDescription" class="form-control"></textarea>
					</div>
					<!-- tests -->
					<div class="row spacer">
					<span id="newTest" class="btn btn-default btn-sm" onclick='Gen.showModalNewTest(this)'>New Test Entry</span>
						<table id="tblTest" class="table table-striped treetable"  style="background-color:#e4e1e1">
							<thead>
								<tr>
									<th>Comment</th><th>Check</th><th>Check Existence</th><th>Test ID</th><th>Object Ref</th><th>State Ref</th><th>Action</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					
					<div class="row spacer">
						<span id="newState" class="btn btn-default btn-sm" onclick='Gen.showModalNewState(this)'>New State Entry</span>
						<table id="tblState" class="table table-striped treetable" style="background-color:#e4e1e1">
							<thead>
								<tr>
									<th>Type</th><th>Comment</th><th>State ID</th><th>Operation</th><th>Value</th><th>Data Type</th>
								</tr>								
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					
					<div class="row">						
						<span  id="btnGenerateOvalDefinition" class="spacer pull-right btn btn-default">Generate Oval Defnition</span>
						<span  id="btnGenerateOvalDef" class="spacer pull-right btn btn-default">Generate Oval Def (use this)</span>
						<form id="uploadForm" target="_blank" action="http://www.subchild.com/liveXmlEdit/index.php" enctype="multipart/form-data" method="post">
							<input class="btn btn-default" name="xmlfile" id="xmlfile" type="file">
						</form>
					</div>
				</fieldset><!-- Definitions -->	

		</div>
	</div>
	
<!-- modal -->
<%@include file="/modal/new_definition.jsp" %>	
<%@include file="/modal/new_platform.jsp" %>
<%@include file="/modal/new_family.jsp" %>
<%@include file="/modal/new_criteria.jsp" %>	
<%@include file="/modal/new_test.jsp" %>	
<%@include file="/modal/new_state.jsp" %>
<%@include file="/modal/downloadable_articles.jsp" %>
<%@include file="/modal/show_affected_family.jsp" %>

	
	
<%@include file="/template/js.jsp" %>	
<script src="../resources/js/page/generator.js"></script>
<%@include file="/template/bottom.jsp" %>