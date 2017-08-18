<div class="modal fade" id="modalCveMeta" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
    	<div class="modal-header">
        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        	<h4 class="modal-title" id="modalCveID">Modal title</h4>
      	</div>
      	<div class="modal-body">
      	<span hidden id="msrcTitle"></span>
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
      	</div> 
		<div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	    </div>
    </div>
  </div>
</div>
