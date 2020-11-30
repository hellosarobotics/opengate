var apri = function(){
	$.ajax({
			url: "apricancello",
			type: "GET",
			success: function (response, textStatus, xhr) {
				console.log("Apertura cancello")
			},
			error: function (XMLHttpRequest, textStatus, error) {
				console.log(error)
				//toastr.error("ERROR GETTING data", "Parameter ID Descriptions", { "positionClass": "toast-top-right", "timeOut": "-1" });
			}
		})
}

var aprieblocca = function(){
	$.ajax({
			url: "apricancelloeblocca",
			type: "GET",
			success: function (response, textStatus, xhr) {
				console.log("Apertura cancello")
			},
			error: function (XMLHttpRequest, textStatus, error) {
				console.log(error)
				//toastr.error("ERROR GETTING data", "Parameter ID Descriptions", { "positionClass": "toast-top-right", "timeOut": "-1" });
			}
		})
}