$(document).ready(function() {
	
	$('#wheel').attr('src', '/img/wheel/' + nix() + ".jpg"); 
	
	$('body').addClass('loaded');
	$('h1').css('color','#222222');
	
	window.onclick = function(e) {
		if(e.startsWith("http")) {
			$('body').removeClass('loaded');
		}

	};
});

/**
 * Next integer index for image wheel
 */
function nix() {
	return Math.floor(Math.random() * 0xF + 1).toString(16).toUpperCase();
}