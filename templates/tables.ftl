<#include "./header.ftl"> 
<#if container??>
  <div ="${container}">
<#else>
  <div ="default">
</#if>
<form>
<br/>
<#if careerDetails?has_content>
<h1><div align="center">Details of University</div></h1>
<div ="input page">
<div align="center">
 <table class="datatable" border="1" cellpadding="5">
 <#list careerDetails as careerDetails>
     <tr>
     <th>FirstName</th>
     <th>LastName</th>
     <th>Headline</th>
     <th>ProfileURL</th>
     <tr>
     <td>${careerDetails.getFirstName()}</td>
     <td>${careerDetails.getLastName()}</td>
     <td>${careerDetails.getHeadline()}</td>
     <td>${careerDetails.getProfileURL()}</td>         
    </tr>    
  </#list>   
  <div id="chart_div" style="width: 900px; height: 500px;"></div>
  </table> 
  <table class="datatable" border="1" cellpadding="5">
  <#list salaryDetails as salaryDetails>
  <tr>
  <td id="one">${salaryDetails.getA()}</td>
  <td id="two">${salaryDetails.getB()}</td>
  <td id="three">${salaryDetails.getC()}</td>
  <td id="four">${salaryDetails.getD()}</td>
  <td id="five">${salaryDetails.getE()}</td>
  <td id="six">${salaryDetails.getF()}</td>
  <td id="seven">${salaryDetails.getG()}</td>
  <td id="eight">${salaryDetails.getH()}</td>
  <td id="nine">${salaryDetails.getI()}</td>
  <td id="ten">${salaryDetails.getJ()}</td>
  </tr>
  </#list>
  </table> 
  <br/>
  <br/>
  <input type="text" name="sendEmailZ" /> <br/><br/>
  <input type="submit" value="SendDetails" name="details" />
  
</div>
<#else>
<div align="center"><img src="http://i41.tinypic.com/v5dao7.png"/></div></h1></font></div>
<h3><div align ="center">All University Statics at One Click</div></h3>
<h3><div align ="center">Enter the name or part of the name of the university you are looking for</div></h3>
<div align="center"><input type="text" name="searchText" /> <br/><br/>
<input type="submit" value="Search" name="submit" />
<br/>
<br/>
</div>
</#if>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script>
var v1= parseInt(document.getElementById("one").textContent);
var v2= parseInt(document.getElementById("two").textContent);
var v3= parseInt(document.getElementById("three").textContent);
var v4= parseInt(document.getElementById("four").textContent);
var v5= parseInt(document.getElementById("five").textContent);
var v6= parseInt(document.getElementById("six").textContent);
var v7= parseInt(document.getElementById("seven").textContent);
var v8= parseInt(document.getElementById("eight").textContent);
var v9= parseInt(document.getElementById("nine").textContent);
var vten= parseInt(document.getElementById("ten").textContent);


 
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Department', '2011-12', '2010-11'],
          ['Computer Engineering',  v1,      v2],
          ['Software Engineering',  v3,      v4],
          ['Mechanical and Aerospace Engineering',  v5,       v6],
          ['Chemical and Materials Engineering',  v7,      v8],
          ['Civil Engineering',  v9,   vten]
        ]);

        var options = {
          title: 'University Salary Average',
          vAxis: {title: 'Graduate Department',  titleTextStyle: {color: 'red'}}
        };

        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
      
</script>


</form>
<#include "./footer.ftl"> 