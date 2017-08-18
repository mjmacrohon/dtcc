<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	
<%@include file="/template/header.jsp" %>
<link rel="stylesheet" href="../resources/css/page/login.css">
</head>
  <body onload='document.f.username.focus();'>
  

    <!-- BOOTSTRAP LOGIN FORM -->
		<div class="container ac-login-form-container">
		<c:url var="loginUrl" value="/login"/>
	      <form class="form-signin" name='f' role="form" action="${loginUrl}" method='POST'>
	        <h2 class="form-signin-heading">Application Login</h2>
		        <div class="form-group">
		            <label for="username">Username</label>
		            <input type="text" class="form-control" id="username" required="" autofocus="" name='username' />
		        </div>    
		        <div class="form-group">
		            <label for="password">Password</label>
			        <input type="password" id="password" class="form-control"  required="" name='password' />
		        </div>
		       
	        	<button class="btn btn-lg btn-primary btn-block os-login-button" type="submit" name="submit" >Login</button>
	      </form>
	    </div>
	<!-- END OF LOGIN FORM -->
<%@include file="/template/bottom.jsp" %>
