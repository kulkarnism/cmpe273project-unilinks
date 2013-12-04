<#include "./header.ftl"> 

<body><div class="background">
 			
           <!--script for slideshow-->
           
			<script>
 var image1=new Image()
image1.src="http://oi44.tinypic.com/2iqj345.jpg";
var image2=new Image()
image2.src="http://oi41.tinypic.com/2n1gshv.jpg";
var image3=new Image()
image3.src="http://oi43.tinypic.com/316s29g.jpg";
      var step=1
function slideit(){
//if browser does not support the image object, exit.
if (!document.images)
return	
document.images.slide1.src=eval("image"+step+".src")
if (step<3)
step++
else
step=1
//call function "slideit()" every 2.5 seconds
setTimeout("slideit()",5000)
}

function slide2it(){
//if browser does not support the image object, exit.
if (!document.images)
return
document.images.slide2.src=eval("image"+step+".src")
if (step<3)
step++
else
step=1

setTimeout("slide2it()",5000)
}
slideit()
slide2it()
			</script>
            
            
     <!--adding some style to tables -->
     <style>
#unilinks
{
font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
width:100%;
border-collapse:collapse;
}
#unilinks td, #unilinks th 
{
font-size:1em;
border:1px solid #000000;
padding:3px 7px 2px 7px;
}
#unilinks th 
{
font-size:1.1em;
text-align:left;
padding-top:5px;
padding-bottom:4px;
background-color:#0066CC;
color:#ffffff;
}
#unilinks tr.alt td 
{
color:#000000;
background-color:#66CCFF;
}
     </style>       
         <!--added CSS-->
            <link rel="stylesheet" href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css" media="screen">
            <link href="//bootswatch.com/cerulean/bootstrap.min.css" rel="stylesheet">
              
            
            
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
<div class="table-responsive" align="center">

<table id="unilinks" class="table" border="1" cellpadding="5">
 <#list universityObject as universityObject>
     <tr>
     <th>School Name</th>
     <th>Contact Info</th>
     <th>Location</th>
     <th>Tuition Fees</th>
     <tr>
     <td>${universityObject.getSchoolName()}</td>
     <td>${universityObject.getContactInfo()}</td>   
     <td>${universityObject.getLocation()}</td>   
     <td>${universityObject.getTutionFees()}</td>       
    </tr>    
  </#list> 
  </table>
  </div>
  <!--Department table-->
  <div class="table-responsive" align="center">
  <table id="unilinks" class="table" border="1" cellpadding="5">
  <tr>
     <th>Department Name</th>
     <th>Toefl Score</th>
     <th>GRE score</th>
     <th>IELTS score</th>
     <tr>
 <#list departmentObject as departmentObject>
     
     <td>${departmentObject.getDepartmentName()}</td>
     <td>${departmentObject.getToeflscore()}</td>   
     <td>${departmentObject.getGrescore()}</td>   
     <td>${departmentObject.getIeltscore()}</td>       
    </tr>    
  </#list> 
  </table>
  </div>
  <div class="table-responsive" align="center">
  <table id="unilinks" class="table" border="1" cellpadding="5">
 
     <tr>
     <th>FirstName</th>
     <th>LastName</th>
     <th>Headline</th>
     <th>ProfileURL</th>
       <tr>  
     <#list careerDetails as careerDetails>
     <td>${careerDetails.getFirstName()}</td>
     <td>${careerDetails.getLastName()}</td>
     <td>${careerDetails.getHeadline()}</td>
     <td>${careerDetails.getProfileURL()}</td>         
     </tr> 
  </#list>  
  </div> 
  <div id="chart_div" style="width: 900px; height: 500px;"></div>
  </table> 
  <table class="datatable" border="1" cellpadding="5" style="display:none">
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
  <h3>Enter Email Address</h3>
  <input type="text" name="sendEmailZ" /> <br/><br/>
  <input type="submit" value="SendDetails" name="details" />
  
</div>
<#else>

<!--this can be removed later if not required-->
<div ="images">
<img id="img1" align="right" name="slide1" src="https://www.realmagnet.com/wp-content/uploads/2011/10/San-Jose-State-University.png" height="37" width="236" border="0px"/>
<img id="img2" align="left" name="slide2" src="https://www.realmagnet.com/wp-content/uploads/2011/10/San-Jose-State-University.png" height="37" width="236" border="0px"/>
</div>


<div align="center"><img src="http://i41.tinypic.com/v5dao7.png"/></div></h1></font></div>
<h3><div align ="center">Your One Stop Destination for University Search</div></h3>
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
</div>
</body>
<#include "./footer.ftl"> 