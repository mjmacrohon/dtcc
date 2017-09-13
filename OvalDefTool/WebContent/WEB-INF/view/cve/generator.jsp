<%@include file="/template/header.jsp" %>
<!--Page CSS-->
<link rel="stylesheet" href="../resources/css/page/cve.css">

</head>
<body style="background-color:seagreen">
	<%@include file="/template/navigation.jsp" %>
	<div class="container-fluid">
		<div class="start-container">
			<form>	
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
					<div class="row"><!-- oval-def -->
						<div class="input-group just-float-left col-sm-4 col-md-6">
							<span class="input-group-addon" >Oval Title</span>
							<input type="text" name="ovalTitle" class="form-control" placeholder="title"  aria-describedby="basic-addon1">
						</div>
						<div class="input-group just-float-left col-sm-2 col-md-2">
							<span class="input-group-addon" >Oval Class</span>
							<select name="ovalClass" class="form-control"   placeholder="select"  aria-describedby="basic-addon1">
								<option>vulnerability</option>
								<option>miscellaneous</option>
								<option>inventory</option>
								<option>compliance</option>
								<option>patch</option>
							</select>
						</div>
						<div class="input-group just-float-left col-sm-2 col-md-3">
							<span class="input-group-addon" >Oval ID</span>
							<span onclick='Cve.showModalOvalId()' class="input-group-addon"><i class="ptr-hand fa fa-asterisk"></i></span>
							<input type="text" name="ovalId" class="form-control" placeholder="oval id"  aria-describedby="basic-addon1">
						</div>
						<div class="input-group just-float-left col-sm-2 col-md-1">
							<span class="input-group-addon" >Version</span>
							<input type="text" name="ovalVersion" class="form-control" value="0"  aria-describedby="basic-addon1">
						</div>
					</div><!-- row -->
					<!-- reference -->
					<div class="row">
						<div class="input-group just-float-left col-sm-2 col-md-2">
							<span class="input-group-addon" >Reference ID</span>
							<input type="text" name="referenceId" class="form-control" placeholder="ref id"  aria-describedby="basic-addon1">
						</div>
						<div class="input-group just-float-left col-sm-8 col-md-8">
							<span class="input-group-addon" >URL</span>
							<input type="text" name="referenceUrl" class="form-control" placeholder="url"  aria-describedby="basic-addon1">
						</div>
						<div class="input-group just-float-left col-sm-2 col-md-2">
							<span class="input-group-addon">Source</span>
							<input type="text" name="referenceSource" class="form-control" placeholder="source"  aria-describedby="basic-addon1">
						</div>					
					</div>
					<div class="row">
						<textarea name="ovalDescription" class="form-control"></textarea>
					</div>
					<br>
					<legend class="scheduler-border">Definitions <i class="add-definition fa fa-plus-square-o ptr-hand" aria-hidden="true"></i></legend>	
					<table id="tblSelectedDef"  class="table table-striped table-bordered">
						<thead class="bg-success">
							<tr><th>Title</th><th>Family</th><th>Platform</th><th>CPE</th><th>Description</th><th>Criteria</th><th>Action</th></tr>
						</thead>
						<tbody>
						
						</tbody>
					</table>
				</fieldset><!-- Definitions -->	

			</form>
		</div>
	</div>
	
<!-- modal -->
<%@include file="/modal/new_definition.jsp" %>	
<%@include file="/modal/show_affected_family.jsp" %>
<%@include file="/modal/search_oval.jsp" %>	
	
	
<%@include file="/template/js.jsp" %>	
<script src="../resources/js/page/welcome.js"></script>
<script src="../resources/js/page/cve.js"></script>
<%@include file="/template/bottom.jsp" %>