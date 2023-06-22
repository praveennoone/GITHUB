<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page trimDirectiveWhitespaces="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
<meta charset="utf-8" />
<title>Document Viewer</title>
<meta http-equiv="refresh" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&amp;subset=all"
	rel="stylesheet" type="text/css" />
<link href="assets/global/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link href="assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="assets/global/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<link
	href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
	rel="stylesheet" type="text/css" />
<link href="assets/global/plugins/select2/css/select2.min.css"
	rel="stylesheet" type="text/css" />
<link href="assets/global/plugins/select2/css/select2-bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.css"
	rel="stylesheet" type="text/css" />
<link
	href="assets/global/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css"
	rel="stylesheet" type="text/css" />
<link href="assets/global/css/components.min.css" rel="stylesheet"
	id="style_components" type="text/css" />
<link href="assets/global/css/plugins.min.css" rel="stylesheet"
	type="text/css" />
<link href="assets/layouts/layout/css/layout.min.css" rel="stylesheet"
	type="text/css" />
<link href="assets/layouts/layout/css/themes/darkblue.min.css"
	rel="stylesheet" type="text/css" id="style_color" />
<link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet"
	type="text/css" />
<link rel="shortcut icon" href="favicon.ico" />
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>

<style>
.button {
	display: block;
	width: 105px;
	height: 35px;
	background: #4E9CAF;
	padding: 10px;
	text-align: center;
	border-radius: 5px;
	color: white;
	font-weight: bold;
}

p.test {
    width: 11em; 
    
    word-wrap: break-word;
}
  
   
 
</style>
<script>
	var app = angular.module('docViewerApp', []);

	app.controller('docViewerController', function($scope) {

		$scope.docViewer = {

			submit : function() {
			
				var searchResult = $scope.txtSearchId;
				
				alert(searchResult.value);
				if(searchResult==' ' ||searchResult==null ){
				
			
					return false;
				}else{
					
				
				}

				 if ($scope.searchResult.$invalid) {

					return false;
				} 
			}

		}

	});
	
	function generateLabel(id,filepath){
		alert("test");
		alert(id);
		alert(filepath);
		
		document.getElementById("id").value = id;

		document.getElementById("filePath").value = filepath;

		

		document.formSubmission.action = "/DocViewer/generateLabel.html";
		document.formSubmission.submit();
		
	}
	
</script>
</head>
<!-- END HEAD -->

<body ng-app="docViewerApp" ng-controller="docViewerController">
	<div class="page-content">
		<img src="./images/logo.jpg" height="10%" width="100%" />
	</div>


	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">

		<!-- BEGIN CONTENT BODY -->
		<div class="page-content">
			<!-- BEGIN PAGE HEADER-->


			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN VALIDATION STATES-->
					<div class="portlet light portlet-fit portlet-form bordered">

						<div class="portlet-body">
							<div class="row">
								<div class="col-md-12">

									<!-- BEGIN Employee Details -->
									<h2>DocViewer</h2>
									<div class="margin-top-10 margin-bottom-10 clearfix">
										<form:form id="searchResult" name="searchResult" method="POST"
											action="/DocViewer/searchResults.html"
											ng-submit="docViewer.submit()">
											<table class="table table-bordered table-striped">

												<tr>
													<td width="20%"><b>Enter Search Text:</b></td>
													<td colspan="3">
													<input type="text" name="txtSearch"
														id="txtSearchId" value="${lastSearchText}" size="100"
														required /> &nbsp;&nbsp; <input type="submit"
														value="Search" disallow-spaces/></td>
												</tr>

											</table>
										</form:form>
									</div>
									<div class="portlet light ">
										<div class="portlet-body">
											<c:choose>
												<c:when test="${!empty lisiIndexer}">
													<table
														class="table table-striped table-bordered table-hover table-checkable order-column"
														id="sample_1">
														<thead>
															<tr>
																<th width="7%">Location</th>
																<th width="35%">File Name</th>
																<th width="35%">MetadataValue</th>
																<th width="13%">Index Date</th>
																<th width="13%">Merged Date</th>
																<th width="13%">Append Document</th>
															</tr>
														</thead>

														<c:forEach items="${lisiIndexer}" var="lisiIndex">
															<tr>
																<td><c:out value="${lisiIndex.location}" /></td>
																<td style="width: 15px !important; -ms-word-break: break-all;
     word-break: break-all;

     /* Non standard for webkit */
     word-break: break-word;

-webkit-hyphens: auto;
   -moz-hyphens: auto;
    -ms-hyphens: auto;
        hyphens: auto;"><a
																	href="downloadFile.html?id=${lisiIndex.indexId}"
																	target="_blank"><c:out
																			value="${lisiIndex.filePath}" /></a></td>
																<td style="width: 15px;  -ms-word-break: break-all;
     word-break: break-all;

     /* Non standard for webkit */
     word-break: break-word;

-webkit-hyphens: auto;
   -moz-hyphens: auto;
    -ms-hyphens: auto;
        hyphens: auto;"><c:out value="${lisiIndex.metadataValue}" /></td>
																<td><c:out value="${lisiIndex.indexDate}" /></td>
																<td><c:out value="${lisiIndex.mergedDate}" /></td>																
																<%-- <td><a
																	href="generateLabel.html?id=${lisiIndex.indexId}&filePath=${lisiIndex.filePath}"
																	target="_blank" class="button"><c:out
																			value="Coversheet" /></a></td> --%>
																			<td><a
																	 onclick="generateLabel('${lisiIndex.indexId}','${lisiIndex.filePath}');"
																	class="button" href="#"><c:out
																			value="Coversheet" /></a></td>

															</tr>
														</c:forEach>
													</table>
												</c:when>
												<c:when test="${!empty param.txtSearch}">
													<table>
														<tr>
															<td><b><font color="#FF0000">Document Not
																		Found.....</font></b></td>
														</tr>
													</table>
												</c:when>
											</c:choose>
										</div>
									</div>
									<!-- END EXAMPLE TABLE PORTLET-->
								</div>
							</div>
						</div>
						<!-- END VALIDATION STATES-->
					</div>
				</div>
			</div>
		</div>
		<!-- END CONTENT BODY -->
		
	</div>
	<form method="post" name="formSubmission">
		<input type="hidden" name="id" id="id"> <input
			type="hidden" name="filePath" id="filePath"> 
			 <input type="submit"
			style="display: none">
	</form>
	<!-- END CONTENT -->
	<!-- END CONTAINER -->

	<!--[if lt IE 9]>
<script src="assets/global/plugins/respond.min.js"></script>
<script src="assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
	<!-- BEGIN CORE PLUGINS -->
	<script src="assets/global/plugins/jquery.min.js"
		type="text/javascript"></script>
	<script src="assets/global/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script src="assets/global/plugins/js.cookie.min.js"
		type="text/javascript"></script>
	<script
		src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
		type="text/javascript"></script>
	<script
		src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
		type="text/javascript"></script>
	<script src="assets/global/plugins/jquery.blockui.min.js"
		type="text/javascript"></script>
	<script src="assets/global/plugins/uniform/jquery.uniform.min.js"
		type="text/javascript"></script>
	<script
		src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
	<script src="assets/global/plugins/datatables/datatables.min.js"
		type="text/javascript"></script>
	<script
		src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN THEME GLOBAL SCRIPTS -->
	<script src="assets/global/scripts/app.min.js" type="text/javascript"></script>
	<!-- END THEME GLOBAL SCRIPTS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="assets/pages/scripts/table-datatables-managed.min.js"
		type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN THEME LAYOUT SCRIPTS -->
	<script src="assets/layouts/layout2/scripts/layout.min.js"
		type="text/javascript"></script>
	<script src="assets/layouts/layout2/scripts/demo.min.js"
		type="text/javascript"></script>
	<script src="assets/layouts/global/scripts/quick-sidebar.min.js"
		type="text/javascript"></script>
	<!-- END THEME LAYOUT SCRIPTS -->

</body>
</html>