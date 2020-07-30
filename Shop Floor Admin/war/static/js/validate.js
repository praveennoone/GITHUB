
// Function to validate strings not to include special characters 
function isProper(string) {
   if (!string) return false;
   var iChars = "~!^-.?_*|,\"/:<>[]{}`\';()@&$#%' '";
   for (var i = 0; i < string.length; i++) {
      if (iChars.indexOf(string.charAt(i)) != -1)
         return false;
   }
   return true;
} 

function trim(str) {
	return str.replace(/^\s+|\s+$/g,"");
}