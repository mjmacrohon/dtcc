var DownloadCSV = DownloadCSV || {};

var header;
var content;


DownloadCSV.init = function(){
	var checkBoxes = $('input[name="databaseTable"]');
	checkBoxes.change(function () {
	    $('.download-button').prop('disabled', checkBoxes.filter(':checked').length < 1);
	});
}

DownloadCSV.selected = function(){
    
    var tables = "";
	$('input[name="databaseTable"]:checked').each(function() {
		popup_window = window.open("downloadTableToCSV.do?tables=" + this.value + "&text=" + $('#textSeparator').val() + "&field=" + $('#fieldSeparator').val(), "_blank", "toolbar=no");
		$(this).prop('checked', false);
	});
	
};



$(document).ready(function(){
	header=$("meta[name='_csrf_header']").attr("content");
	content=$("meta[name='_csrf']").attr("content");
	DownloadCSV.init();
});