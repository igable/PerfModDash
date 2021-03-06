<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"
	import="java.util.Calendar"
	import="java.text.SimpleDateFormat"
	import="java.sql.*"
	import="java.io.*"
	import="java.util.Date"

	import="java.net.*"

        import="gov.bnl.racf.exda.*"

	import="java.io.FileOutputStream"
	import="java.io.IOException"
	import="java.util.Properties"
	import="java.util.Iterator"
	import="java.util.List"
	import="java.util.ArrayList"


%>

<head>
<META HTTP-EQUIV="REFRESH" CONTENT="600">

<style type="text/css">
<%@include file="Forest.css" %>
</style>
</head>



<html>
<body>



<%

String pageAddress="";
String schema="";
String hostname="";
try {
    InetAddress addr = InetAddress.getLocalHost();

    // Get IP Address
    byte[] ipAddr = addr.getAddress();

    // Get hostname
    hostname = addr.getHostName().trim();
    
    if (hostname.equals("grid21.racf.bnl.gov")){
       pageAddress="130.199.185.78:28080";
       pageAddress="130.199.185.78";
       pageAddress="perfsonar.usatlas.bnl.gov";	
     }else{
	if (hostname.equals("grid09.racf.bnl.gov")){
      	    //pageAddress="130.199.185.78:28080";
       	    //pageAddress="130.199.185.78";
       	    pageAddress="perfsonar.racf.bnl.gov";	
     	}else{
       	//pageAddress="localhost:8080";
            pageAddress="localhost";
    	}	
    }


    // get port
    int portNumber=request.getLocalPort();
    pageAddress=pageAddress+":"+Integer.toString(portNumber);				
    
    //get schema
    schema=	request.getScheme() ;
    
} catch (UnknownHostException e) {
  out.println("failed to get hostname");
  out.println(e);
}


%>

<div id="title">
<h1><span>RACF</span></h1>
<h3><span>Grid Group</h3>

<%
if (hostname.equals("grid09.racf.bnl.gov")){
out.println("<h2><span>The Production Instance of perfSONAR Dashboard</span></h2>");
}else{
out.println("<h2><span>The Development Instance of perfSONAR Dashboard</span></h2>");
}
%>
</div>



<div id="sideNav">
<ul>


<%

ParameterBag pb=new ParameterBag();

String link="<li><a href=\""+schema+"://"+pageAddress+"/exda/\">Main Page</a></li>";
out.println(link);

link="<li><a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("All Clouds")+"\">All Clouds</a></li>";
out.println(link);

out.println("<li>Individual Clouds:</li>");

link="<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("Cloud Overview")+"&cloudName=USATLAS\">USATLAS</a></li>";
out.println(link);

link="<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("Cloud Overview")+"&cloudName=USCMS\">USCMS</a></li>";
out.println(link);

link="<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("Cloud Overview")+"&cloudName=IT\">IT</a></li>";
out.println(link);

link="<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("Cloud Overview")+"&cloudName=LHCOPN\">LHCOPN</a></li>";
out.println(link);

link="<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("Cloud Overview")+"&cloudName=LHCONE\">LHCONE</a></li>";
out.println(link);

link="<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("Cloud Overview")+"&cloudName=CA-ATLAS\">CA-ATLAS</a></li>";
out.println(link);

link="<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("Cloud Overview")+"&cloudName=UK\">UK</a></li>";
out.println(link);


link="<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("Cloud Overview")+"&cloudName=LHC-FR\">LHC-FR</a></li>";
out.println(link);


out.println("<li>Inter Cloud Tests:</li>");
link="<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("Cloud Overview")+"&cloudName=AGLT2-IT\">AGLT2-IT</a></li>";
out.println(link);
link="<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("Cloud Overview")+"&cloudName=FR-US\">FR-US</a></li>";
out.println(link);

link="<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("Cloud Overview")+"&cloudName=ATLAS-UK\">ATLAS-UK</a></li>";
out.println(link);



String link2="<li><a href=\""+schema+"://"+pageAddress+"/exda/?page="+pb.pageAddress("List of perfSonar Primitive Services")+"\">Primitive Services</a></li>";
out.println(link2);

String link3="<li><a href=\""+schema+"://"+pageAddress+"/exda?page="+pb.pageAddress("List of Sites")+"\">perfSonar Sites</a></li>";
out.println(link3);

String link4="<li><a href=\""+schema+"://"+pageAddress+"/exda?page="+pb.pageAddress("List of Hosts")+"\">List of Hosts</a></li>";
out.println(link4);

String link5="<li><a href=\""+schema+"://"+pageAddress+"/exda?page="+pb.pageAddress("List of Matrices")+"\">List of Matrices</a></li>";
out.println(link5);

link="<li><a href=\""+schema+"://"+pageAddress+"/exda?page="+pb.pageAddress("Overview of Alarms")+"\">List of Alarms</a></li>";
out.println(link);

String link6="<li><a href=\""+schema+"://"+pageAddress+"/exda?page="+pb.pageAddress("Manipulate Clouds")+"\">List of Clouds</a></li>";
out.println(link6);

String link7="<li><a href=\""+schema+"://"+pageAddress+"/exda?page="+pb.pageAddress("Manipulate Sites")+"\">List of Sites</a></li>";
out.println(link7);

String link8="<li><a href=\""+schema+"://"+pageAddress+"/exda?page="+pb.pageAddress("Manipulate Schedulers")+"\">List of Schedulers</a></li>";
out.println(link8);

String link9="<li><a href=\""+schema+"://"+pageAddress+"/exda?page="+pb.pageAddress("Admin Page")+"\">Probes</a></li>";
out.println(link9);

String link10="<li><a href=\""+schema+"://"+pageAddress+"/exda?page="+pb.pageAddress("List Registered Users")+"\">Manage Users</a></li>";
out.println(link10);

String link11="<li><a href=\""+schema+"://"+pageAddress+"/exda?page="+pb.pageAddress("List of Alarms")+"\">Define or Edit Alarms</a></li>";
out.println(link11);

%>

  <li><a href="https://nagios.racf.bnl.gov/nagios/cgi-bin/prod/dashboard.php">RACF dashboard</a></li>
  <li><a href="https://nagios.racf.bnl.gov/nagios/cgi-bin/prod/perfSonar.php">perfSONAR dashboard (old)</a></li>

  <li><a href="https://nagios.racf.bnl.gov/nagios/cgi-bin/test/dashboard.php">RACF dashboard (test)</a></li>
  <li><a href="https://nagios.racf.bnl.gov/nagios/cgi-bin/test/perfSonar.php">perfSONAR dashboard (old,test)</a></li>
  <li><a href="https://www.racf.bnl.gov/experiments/usatlas/griddev/perfsonar-dashboard">Dashboard documentation</a></li>

</div>



<div id="body">



<%


out.flush();

Date date = new Date();

out.println("<strong>Status as of: "+date+"</strong>");
out.flush();

RequestDispatcher rd
            = getServletContext().getRequestDispatcher("/DriverServlet");
            rd.include(request, response);

out.flush();



%>


</div>


<div id="tail">
<p><b>(c) 2010 Brookhaven National Laboratory </b>- send suggestions
and comments to <a href="mailto:tomw@bnl.gov">tomw@bnl.gov</a><br>
</p>
</div>

</body>
</html>
