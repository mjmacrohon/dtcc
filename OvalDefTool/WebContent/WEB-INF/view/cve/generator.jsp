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
					<legend class="scheduler-border">Definitions <i class="add-definition fa fa-plus-square-o ptr-hand" aria-hidden="true"></i></legend>	
					<table id="tblSelectedDef" class="table table-stripped">
						<thead class="bg-info">
							<tr><th colspan="2">Title</th></tr>
						</thead>
						<tbody>
							<tr class="tr-1"><td colspan="2"><i rowid="tr-1" class="ptr-hand parent fa fa-arrow-right"></i> A</td></tr>
							<tr class="tr-1-child" style="display:none"><td>A-1</td><td>A-2</td></tr>
							
							<tr class="tr-2"><td colspan="2"><i rowid="tr-2" class="ptr-hand parent fa fa-arrow-right"></i> B</td></tr>
							<tr class="tr-2-child" style="display:none"><td>B-1</td><td>B-2</td></tr>
						</tbody>
					</table>
				</fieldset><!-- Definitions -->	

			</form>
		</div>
	</div>
	
<!-- modal -->
<%@include file="/modal/new_definition.jsp" %>	
<%@include file="/modal/show_affected_family.jsp" %>

	
	
<%@include file="/template/js.jsp" %>	
<script src="../resources/js/page/welcome.js"></script>
<script src="../resources/js/page/cve.js"></script>
<%@include file="/template/bottom.jsp" %>