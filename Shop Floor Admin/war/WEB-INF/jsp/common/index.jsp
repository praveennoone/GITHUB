<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<title>EL Expression Examples</title>
</head>
<body>
<h1>EL Expression Examples</h1>
${1}
<h2>Arithmetic Operators in Expressions</h2>
<c:set var="appleCount" value="${1 + 2 * 4 - 6 / 2}"/>
<b>There are ${1 + 2 * 4 - 6 / 2} apples on the table.</b><br/>
<b>There are 
<fmt:formatNumber pattern="#####"> ${1 + 2 * 4 - 6 / 2}</fmt:formatNumber>
apples on the table.</b><br/>


<b>It feels like ${-4 - 8} degree today.</b><br/>
<c:set var="myGrade" value="11"/><br/>
<b>The average grade is ${(myGrade == 10) ? "perfect" : "good"}. </b><br/>
<b>There are ${23/54} remaining. </b><br/>
<b>There are ${6 div 2} apples on the table.</b><br/>
<b>There are ${2003 div 8} apples on the table.</b><br/>
<b>There are ${2003 mod 8} apples on the table.</b><br/>
<b>There are ${2003 % 8} apples on the table.</b><br/>

<h2>Logical Operators</h2>
<c:set var="guess" value="12"/>
<b>Your guess is ${guess}.</b><br/>

<c:if test="${(guess >= 10)  && (guess <= 20)}">
   <b>You're in range!</b><br/>
</c:if>
<c:if test="${(guess < 10)  || (guess > 20)}">
   <b>Try again!</b><br/>
</c:if>

<c:set var="guess" value="1"/>
<b>Your guess is ${guess}.</b><br/>

<c:if test="${(guess >= 10)  and (guess <= 20)}">
   <b>You're in range!</b><br/>
</c:if>
<c:if test="${(guess < 10)  or (guess > 20)}">
   <b>Try again!</b><br/>
</c:if>



<h2>Comparison Operators</h2>

4 > '3'   ${4 > '3'}<br/>
'4' > 3   ${'4' > 3}<br/>
'4' > '3' ${'4' > '3'} <br/>
4 >= 3    ${4 >= 3}<br/>
4 <= 3     ${4 < 3}<br/>
4 == '4'   ${4 == 4}<br/>

<h2>empty Operator</h2>
empty "" ${empty ""}<br/>
empty "sometext" ${empty "sometext"}<br/>
empty Junk ${empty Junk}<br/>
empty guess ${empty guess}<br/> 


<h2>Boolean and Null Values</h2>

<c:set var="StrVar" value="true"/>
<c:if test="${StrVar}">
  equal!
</c:if><br/>

null == null  ${null == null}<br/>
"null" == null ${"null" == null}<br/>


</body>
</html>
