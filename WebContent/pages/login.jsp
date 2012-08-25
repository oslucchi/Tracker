<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="pages/style.css"/>
		<link rel="Shortcut Icon" href="images/tracker.ico" type="image/x-icon" />

		<title>Tracker</title>
	</head>
<body>
	<div class="logo" id="logo">
		<img src="images/trackerSmall.bmp"/>
		<BR>
	</div>
	<s:form action="loginCheck">
    <table>
    	<tr>
    		<td style="vertical-align:center;">
    			<s:text name="label.username" />
  			</td>
    		<td style="vertical-align:center;">
    			<s:textfield name="username" maxlength="15" size="10" />
    		</td>
    	</tr>
    	<tr>
    		<td style="width:98px; vertical-align:center;">
    			<s:text name="label.password" />
    		</td>
    		<td style="vertical-align:center;">
    			<s:password name="password" maxlength="15" size="10" />
    		</td>
    	</tr>
    	<s:if test="properties.showPrimAltLoginRadio == true" > 
	    	<tr>
	    		<td style="width:98px; vertical-align:center;">
	    			<s:text name="label.authDomain" />
	    		</td>
	    		<td>
	    			<s:radio name="idType" list="#{'PRI':'PRI','ALT':'ALT'}" value="{'PRI'}" />
	    		</td>
	    	</tr>
    	</s:if>
    	<s:else>
    		<s:hidden name="idType" id="idType" value="PRI" />
    	</s:else>    	
    	<tr>
    		<td colspan="2">
    			&nbsp;
    		</td>
    	</tr>
    	<tr>
    		<td colspan="2" style="text-align:center;">
    			<s:submit align="center" value="Sign In" />
    		</td>    		
    	</tr>
	</table>
    </s:form>
	<s:actionerror />
</body>
</html>