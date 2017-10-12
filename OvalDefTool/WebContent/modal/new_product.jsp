<div class="modal fade" id="modalNewProduct" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
    	<div class="modal-header">
        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        	<h4 class="modal-title" >New Product</h4>
      	</div>      	
      	<div class="modal-body">
			<div class="container-fluid">
				<div class="input-group input-group-sm col-md-12">
				  <span class="input-group-addon" id="sizing-addon3">Name</span>
				  <input id="prodName" type="text" class="form-control"  aria-describedby="sizing-addon3">
				</div>
				<div class="spacer input-group input-group-sm col-md-12">
				  <span class="input-group-addon" id="sizing-addon3">Family</span>
		  			<select id="prodFamily" class="form-control"   aria-describedby="basic-addon1">
						<c:forEach items="${families}" var="family">
							<option>${family.name}</option>								
						</c:forEach>								
					</select>				  
				</div>
				<div class="spacer input-group input-group-sm col-md-12">
				  <span class="input-group-addon" id="sizing-addon3">Type</span>
		  			<select id="prodType" class="form-control"   aria-describedby="basic-addon1">
							<option>platform</option>			
							<option>product</option>															
					</select>				  
				</div>
				<div class="spacer input-group input-group-sm col-md-12">
				  <span class="input-group-addon" id="sizing-addon3">Name</span>
				  <input id="prodDescription" type="text" class="form-control"  aria-describedby="sizing-addon3">
				</div>
			</div><!-- container-fluid -->
      	</div>       	
		<div class="modal-footer">
			 <button id="btnSaveNewProduct" type="button" class="btn btn-default" >Save</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	    </div>
    </div>
  </div>
</div>
