<%@include file="/template/header.jsp" %>

</head>
<body style="background-color:seagreen">
	<%@include file="/template/navigation.jsp" %>
	<div class="container-fluid">
		<div class="start-container">
			<form>	
				<!-- Generation -->
				<fieldset class="scheduler-border">
					<legend class="scheduler-border">Generation</legend>	
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
						<div class="input-group just-float-left col-sm-6 col-md-6">
							<span class="input-group-addon" id="ovalSchemaDef">Oval Schema Def</span>
							<select name="ovalSchemaDef" class="form-control selectpicker" placeholder="ovalSchemaDef" value="0.1" aria-describedby="basic-addon1">
								<option>http://oval.mitre.org/XMLSchema/oval-definitions-4</option>
								<option>http://oval.mitre.org/XMLSchema/oval-definitions-5</option>
							</select>
							<span class="input-group-addon " ><i class="ptr-hand fa fa-plus"></i></span>
						</div>
						<div class="input-group just-float-left col-sm-2 col-md-2">
							<span class="input-group-addon" id="classDef">Class</span>
							<select name="classDef" class="form-control selectpicker" placeholder="classDef"  aria-describedby="basic-addon1">
								<option value="compliance">Compliance</option>
								<option value="inventory">Inventory</option>
								<option value="miscellaneous">Miscellaneous</option>
								<option value="patch">Patch</option>
								<option value="vulnerability">Vulnerability</option>
							</select>
							<span class="input-group-addon " ><i class="ptr-hand fa fa-plus"></i></span>						
						</div>		
						<div class="input-group just-float-left col-sm-2 col-md-2">
							<span class="input-group-addon" id="idDef">ID</span>
							<input type="text" name="idDef" class="form-control" placeholder="idDef" value="oval:com.dtcc:def" aria-describedby="basic-addon1">
						</div>	
						<div class="input-group col-sm-2 col-md-2">
							<span class="input-group-addon" id="versionDef">Version</span>
							<input type="text" name="versionDef" class="form-control" placeholder="versionDef" value="0" aria-describedby="basic-addon1">
						</div>
					</div>
					<fieldset class="scheduler-border">
						<legend class="scheduler-border">Metadata <i class="add-definition fa fa-plus-square-o ptr-hand" aria-hidden="true"></i></legend>	
						<div class="input-group just-float-left col-sm-3 col-md-3">
							<span class="input-group-addon" id="cveMeta">CVE</span>
							<select name="cveMetaId" class="form-control"  placeholder="cveMeta"  aria-describedby="basic-addon1">
								
							</select>
							<span class="input-group-addon " ><i class="ptr-hand fa fa-plus"></i></span>	
							<span class="input-group-addon " >
								<i  title="process" class="process-cve ptr-hand fa fa-cog"></i>
								<!-- <a href="/OvalDefTool/cve/read_cve_meta.do" title="process" class="process-cve ptr-hand fa fa-cog"></a>  -->
							</span>						
						</div>	
						<div class="input-group just-float-left col-sm-2 col-md-2">
							<span class="input-group-addon" id="cveAffectedFamily">Affected Family</span>
							<input type="text" name="cveAffectedFamily" class="form-control" placeholder="cveAffectedFamily" aria-describedby="basic-addon1">
							<span class="input-group-addon " >
								<i  title="process" class="process-aff-family ptr-hand fa fa-cog"></i>
							</span>		
						</div>		
						<div class="input-group just-float-left col-sm-7 col-md-7">
							<span class="input-group-addon" id="cveTitle">Title</span>
							<input type="text" name="cveTitle" class="form-control" placeholder=""cveTitle"" aria-describedby="basic-addon1">
						</div>								
					</fieldset><!-- Meta -->
					<fieldset class="scheduler-border">
						<legend class="scheduler-border">Reference <i class="fa fa-plus-square-o ptr-hand" aria-hidden="true"></i></legend>	
						<table id="tblReference" class="table table-stripped">
							<thead><th>Source</th><th>Id</th><th>Url</th><th>Description</th></tr></thead>
							<tbody>
							
							</tbody>
						</table>
					</fieldset>	
				</fieldset><!-- Definitions -->	
				<fieldset class="scheduler-border">
					<legend class="scheduler-border">Repository <i class="fa fa-plus-square-o ptr-hand" aria-hidden="true"></i></legend>	
						<div class="input-group just-float-left col-sm-2 col-md-2">
							<span class="input-group-addon" id="repoOrg">Organization</span>
							<input type="text" name="repoOrg" class="form-control" value="DTCC" aria-describedby="basic-addon1">
						</div>	
						<div class="input-group just-float-left col-sm-3 col-md-3  date-submission">
							<span class="input-group-addon" id="repoDateSubmission">Submission Date</span>
							<input type="text" name="repoDateSubmission" class="form-control" aria-describedby="basic-addon1">
							<span class="input-group-addon" ><i class="ptr-hand fa fa-calendar"></i></span>
						</div>
						<div class="input-group just-float-left col-sm-3 col-md-3">
							<span class="input-group-addon" id="repoDateSubmission">Status</span>
							<select name="repoStatus" class="form-control"  placeholder="cveMeta"  aria-describedby="basic-addon1">
								<option>INITIAL SUBMISSION</option>
								<option>ACCEPTED</option>
							</select>
						</div>
				</fieldset>		
			</form>
		</div>
	</div>
	
<!-- modal -->
<%@include file="/modal/read_cve_meta.jsp" %>	
<%@include file="/modal/show_affected_family.jsp" %>

	
	
<%@include file="/template/js.jsp" %>	
<script src="../resources/js/page/welcome.js"></script>
<script src="../resources/js/page/cve.js"></script>
<%@include file="/template/bottom.jsp" %>