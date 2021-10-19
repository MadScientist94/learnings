<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Customers</title>
</head>
<body>
<h1>view Customer Details</h1>
<h2>Details as submitted as follows</h2>
<form action="getdetails" method="post">
enter the cid of the customer <input type="number" name ="CustomerId"><br>
<input type="submit" value="Submit">
</form>
<h4>Customer Id : ${cid }</h4>
<h4>Customer Name : ${cname }</h4>
<h4>Customer Email : ${cemail }</h4>
</body>
</html>