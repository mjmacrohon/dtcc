<div class="modal fade" id="modalNewDefinition" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
    	<div class="modal-header">
        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        	<h4 class="modal-title">Add New CVE Definition
        	</h4>
      	</div>
      	<div class="modal-body">
      		<div class="container-fluid">
      			<form>
      				<div class="just-float-left input-group col-sm-4 col-md-4">
						<span class="input-group-addon" >CVE</span>
						<select name="cveIds" class="form-control"  placeholder="cveMeta"  aria-describedby="basic-addon1">
							
						</select>
						<span class="input-group-addon " ><i class="ptr-hand fa fa-plus"></i></span>	
						<span class="input-group-addon " >
							<i  title="process" class="process-cve-data ptr-hand fa fa-cog"></i>
						</span>						
					</div>
					<div class="input-group col-sm-8 col-md-8">
							<span class="input-group-addon" id="cveTitle">Description</span>
							<input type="text" name="cveTitle" class="form-control" placeholder=""cveTitle"" aria-describedby="basic-addon1">
					</div>	
					<span hidden id="msrcTitle"></span>
      			</form>
      		
				<ul  class="nav nav-pills">
					<li class="active">
		        		<a  href="#MetaInfo" data-toggle="tab" style="border-radius:0px !important">Meta Info</a>
					</li>
					<li>
						<a href="#MetaDefinition" data-toggle="tab" style="border-radius:0px !important">Definition</a>
					</li>
				</ul>
			
				<div class="tab-content clearfix">
					<!-- MetaInfo Tab -->
					<div class="tab-pane active" id="MetaInfo">
			          		<form>					
						      	<fieldset class="scheduler-border">
									<legend class="scheduler-border">portal.msrc.microsoft.com [<i class="cve-info fa fa-info ptr-hand" cveIdInfo=msrcMetaInfo aria-hidden="true"></i>]</legend>			
									<div id="msrcMetaInfo" class="container-fluid">
									</div>
									<div id="msrcMeta" class="container-fluid">
									</div>			
								</fieldset>      	
						     	<fieldset class="scheduler-border">
									<legend class="scheduler-border">cve.mitre.org  [<i class="cve-info fa fa-info ptr-hand" cveIdInfo=mitreMetaInfo aria-hidden="true"></i>]</legend>
									<div id="mitreMetaInfo" class="container-fluid">
									</div>			
									<div id="mitreMeta" class="container-fluid">
									</div>	
								</fieldset>
								<fieldset class="scheduler-border">
									<legend class="scheduler-border">nvd.nist.gov  [<i class="cve-info fa fa-info ptr-hand" cveIdInfo=nvdMetaInfo aria-hidden="true"></i>]</legend>	
									<div id="nvdMetaInfo" class="container-fluid">
									</div>
									<div id="nvdMeta" class="container-fluid">
									</div>
								</fieldset>
					      	</form>
					</div>
					<!-- MetaDefinition -->
					<div class="tab-pane" id="MetaDefinition">
			           <table id="tblProduct" class="table table-stripped">
			           		<thead>
			           			<tr><th>Entry</th><th>Option</th></tr>
			           		</thead>
			           		<tbody>
			           		
			           		</tbody>
			           </table>
					</div>
				</div>
      		
      		
      		
      		
      		

	      	</div>       	
     	</div> <!-- modal-body -->
		<div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	    </div>
    </div>
  </div>
</div>
      	