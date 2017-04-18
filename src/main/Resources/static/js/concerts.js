/**
 * Created by Bert on 15/04/2017.
 */
window.onscroll = function() { myFunction() };
function myFunction() {
    var navbar = document.getElementById("nav");
    if (window.pageYOffset > 0) {
        navbar.className = navbar.className.replace("on-top", "scrolling");
    } else {
        navbar.className = navbar.className.replace("scrolling", "on-top");
    }
}