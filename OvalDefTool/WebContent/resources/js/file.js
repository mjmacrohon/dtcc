var File = File || {};

var header;
var content;

File.fileUpload=function(){
	var flag=true;
	$('input.rk-agenda-file').change(function(e){
		//var fileName = $(this).val().split('/').pop().split('\\').pop();
		$(".rk-loading-spinner").show();

		if(flag){
			$('#formUploader').ajaxSubmit({
				/*
				 beforeSend: function(xhr){
					  xhr.setRequestHeader(header,content);
				  },
				 */
				 dataType:'json',
				 data: {restorationId: $('#restorationId').val(), restorationHasMime: restorationHasMime},
				 success:  function(uploadedFile){
					location.reload();
				 },
				 error: function(result){
					 location.reload();
				 }
			});
		}
	});
};

File.showUploader=function() {
	$('.rk-file-wrapper').show();
}

File.resetForm=function() {
	$("option:selected").prop("selected", false);
	$('.rk-file-wrapper').hide();
}

File.chooseRestoration = function(){
  $('#restorationId').change(function(e){
    var restorationIdDOM = $('.restoration-id');
    var chosenRestoration = $(this).val();
    restorationHasMime = File.checkIfRestorationExisting(restorationIdDOM, chosenRestoration);
    //console.log(restorationHasMime);
    if(restorationHasMime) {
    	$("#modal-message").modal('show');
		$("#modal-message .modal-body").html('');
		$("#modal-message .modal-body").html("Restoration already have and MIME file, Would you like to replace it?");
		$("#modal-message .modal-footer").show();
    }else{
    	File.showUploader();
    }
  });
}

File.checkIfRestorationExisting=function(restorationIdDOM, chosenRestoration) {
	var existingRestoration = true;
	var finalRestoration = false;
	console.log("Choosen :" + chosenRestoration);
	restorationIdDOM.each(function (index) {
		if(chosenRestoration==$(this).text()){
			//console.log("Nakita");
			finalRestoration = true;
		}else{
			existingRestoration = false;
		}
	});	
	
	
	if(finalRestoration){
		return finalRestoration;
	}else{
		return existingRestoration;
	}
	
	//return false;
}


File.fileDownload=function(elem){
	//console.log($(elem).attr("fileName"));
	//console.log($(elem).attr("fileId"));
	window.open("download.do?fileId=" + $(elem).attr("fileId").replace ( /[^\d.]/g, '' ) +"&fileName=" + $(elem).attr("fileName"), "_blank", "toolbar=no");
};


$(document).ready(function(){
	header=$("meta[name='_csrf_header']").attr("content");
	content=$("meta[name='_csrf']").attr("content");
	File.fileUpload();
	File.chooseRestoration();
    var restorationHasMime = false;
});