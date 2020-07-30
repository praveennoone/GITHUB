function fnShift(frm, shiftTime){
	// Receiving the dates from the form
	var formFromDateTime = frm.fromDate.value;
	var formToDateTime = frm.toDate.value;
	
	// dont know why this is used
	var shiftdate = shiftTime;
	 
	// This will strip the date from the date-time string from the FORM
	var fDateTime_array=formFromDateTime.split(" ");
	var tDateTime_array=formToDateTime.split(" ");
	
	// This variable will hold data from the radio button
	var shiftDateTime_array=shiftdate.split("-");
	
	// This varaible is used to store the FROM time stamp
	sfdTime = shiftDateTime_array[0];
	
	// This varaible is used to store the TO time stamp
	stdTime = shiftDateTime_array[1];
	
	// This varaible is used to store the date shift flag
	shiftRequired = shiftDateTime_array[2];
	
	
	objFromDate = Date.parse(fDateTime_array[0]);
	objToDate = Date.parse(tDateTime_array[0]);
	
	dateFlag = objFromDate.compareTo(objToDate);
	
	if (shiftRequired == 'true') {
		if (dateFlag == '0' || dateFlag == '-1') {
			newToDate = Date.parse(tDateTime_array[0]).add({days : 1}).toString('MM/dd/yyyy');
		} else {
			newToDate = Date.parse(fDateTime_array[0]).add({days : 1}).toString('MM/dd/yyyy');
		}
	} else {
		if (dateFlag == '0' || dateFlag == '-1') {
			newToDate = tDateTime_array[0];
		} else {
			newToDate = fDateTime_array[0];
		}
	}
	
	//alert("sdate :"+shiftDateTime_array[0]+"  End Date "+shiftDateTime_array[1]);
	formFromDateTime = fDateTime_array[0]+" "+shiftDateTime_array[0];
	formToDateTime = newToDate+" "+shiftDateTime_array[1];
	
	
	frm.fromDate.value = formFromDateTime;
	frm.toDate.value = formToDateTime;
}