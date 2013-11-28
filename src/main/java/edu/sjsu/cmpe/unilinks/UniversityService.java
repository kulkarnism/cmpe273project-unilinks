package edu.sjsu.cmpe.unilinks;

import java.io.File;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

import spark.Route;
import spark.Request;
import spark.Response;


//import java.io.FileWriter;
//import java.io.FileWriter;
//import java.io.OutputStreamWriter;
import java.io.StringWriter;
//import java.io.Writer;
import java.util.ArrayList;

import static spark.Spark.*;
import spark.*;

import java.util.Date;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import edu.sjsu.cmpe.unilinks.resources.SalaryDetails;
import edu.sjsu.cmpe.unilinks.resources.UniversityObject;
import edu.sjsu.cmpe.unilinks.resources.CareerDetails;
import freemarker.template.Configuration;
import freemarker.template.Template;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AWSJavaMailTransport;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.VerifyEmailAddressRequest;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;
import com.mongodb.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class UniversityService
{
	static String u_name = null;
	public static void main(String[] args) throws Exception
	    { 
		//Amazon SNS Subscription
		final AWSCredentials credentials = new BasicAWSCredentials("AKIAIWJCTBPOCHEYCOYA", "7Qc2uMkRE+gQBt3ujQk/KlyKuUgVoKexzPrb6gFE");
		final AmazonSNS sns = new AmazonSNSClient(credentials);	
		final CareerDetails cd =  new CareerDetails();
        final SalaryDetails sd = new SalaryDetails();
       
	        String nextToken = null;
	        int subscriptions = 0;
	                
	        //creating a topic
	        CreateTopicResult topicResult=sns.createTopic(new CreateTopicRequest("unilinksTopic"));
	        final String topicArn = topicResult.getTopicArn();
	        System.out.println("topic ARN==="+topicResult.getTopicArn());
	        
	        //create a subscription
	        SubscribeResult subRes=sns.subscribe(new SubscribeRequest(topicResult.getTopicArn(),"email","anita.tvl@gmail.com"));
	       		
		try{
			
			get(new Route("/unilinks") {
				Configuration cfg = new Configuration();
		          @Override
		          public Object handle(Request request, Response response){
		        	  
		        	  StringWriter file = null;
		        	  String queryParam = request.queryString();
		        	  String uname;
		        	  try{
		        		  file = new StringWriter();
		            	  cfg.setDirectoryForTemplateLoading(new File("templates"));
		                  // load template
		                  Template template = cfg.getTemplate("tables.ftl");   
		                  	                  
		                  // data-model
		                  Map<String, Object> input = new HashMap<String, Object>(); 		
		                  // create list
		                  List<UniversityObject> universityObject = new ArrayList<UniversityObject>();
		                  List<CareerDetails> careerDetails = new ArrayList<CareerDetails>();
		                  List<SalaryDetails> salaryDetails = new ArrayList<SalaryDetails>();
		                  
		                  
		                  //Initialization
		                  input.put("Hello","Welcome to CareerData");
		                  template.process(input,file);
		                  //Different template needs to be included here
		                  if(queryParam == null)
		            	  {        	  
		            	  System.out.println("null");
		            	  }
		            	 else
		            	 {		            		 
		            		 if(queryParam.contains("SendDetails"))
		            		 {
		            			 System.out.println("firstname::"+cd.getFirstName());
		            			 System.out.println("queryParam:"+queryParam.toString());
		            			 String emailid= queryParam.substring((queryParam.indexOf("Z")+2),queryParam.lastIndexOf("&"));
		            			 emailid=emailid.replace("%40","@");		            			 
		            			 
		            			 
		            			 String TO = emailid;
	            		         String FROM = "anita.tvl@gmail.com";
	            		         String BODY = "This is auto generated email..Please do not reply...Please find the university details attached";
	            		         String SUBJECT = "University Details";
	            		         AmazonSimpleEmailService ses = new AmazonSimpleEmailServiceClient(credentials);
	            		         ses.verifyEmailAddress(new VerifyEmailAddressRequest().withEmailAddress(FROM));
	            		         
	            		        
	            		         
	            		         Properties props = new Properties();
	            		 		 props.setProperty("mail.transport.protocol", "aws");
	            		         props.setProperty("mail.aws.user","AKIAIWJCTBPOCHEYCOYA");
	            		         props.setProperty("mail.aws.password", "7Qc2uMkRE+gQBt3ujQk/KlyKuUgVoKexzPrb6gFE");
	            		         Session session = Session.getInstance(props);
                                 try {
                                	 
                                	 Message msg = new MimeMessage(session);
	            		             msg.setFrom(new InternetAddress(FROM));
	            		             msg.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
	            		             msg.setSubject(SUBJECT);
	            		             //msg.setText(BODY);
	            		            
	            		             MimeBodyPart messageBodyPart = new MimeBodyPart();
	            		             messageBodyPart.setText("Hi There,"+"\n"+"Please find attached the detailed information about your requested university."+"\n"+"Happy University Hunt!!!"+"\n\n"+"Best Regards,"+"\n"+"Team Unilinks");	            		            
	            		             Multipart multipart = new MimeMultipart();
	            		             multipart.addBodyPart(messageBodyPart);
	            		             messageBodyPart = new MimeBodyPart();	            		            
	            		            
	            		             
	            		             File attachmentSource = new File("UniversityInfo.xls");
	            		             attachmentSource.createNewFile();
	            		             HSSFWorkbook tmpWorkbook = new HSSFWorkbook();
	            		             HSSFSheet sheet = tmpWorkbook.createSheet("Sample sheet");
	            		             tmpWorkbook.write(new FileOutputStream(attachmentSource));
	            		             Map<String, Object[]> data = new HashMap<String, Object[]>();
	            		             
	            		             //Get Salary Data From Database
	            		              String textUri = "mongodb://unilinks:unilinks@ds053818.mongolab.com:53818/unilinks";
			            	          MongoClientURI uri = new MongoClientURI(textUri);		                  
			            			  MongoClient m = new MongoClient(uri);		  
			            			  DB db= m.getDB(uri.getDatabase());
			            			  System.out.println("university name::"+u_name);
			            			  BasicDBObject query = new BasicDBObject("SchoolName",u_name);			            			  
			            			  DBCollection collection_two = db.getCollection("salary");
			            			  DBCursor dc_two = collection_two.find(query);
			            			  data.put("1",new Object[]{"Salary Details"});
			            			  while(dc_two.hasNext())
			            			  {
			            				  DBObject secondary = dc_two.next();
			            				  data.put("2",new Object[]{"Computer Engineering(2011-12)::"+secondary.get("CE").toString()});
			            				  data.put("3",new Object[]{"Computer Engineering(2012-13)::"+secondary.get("CE2").toString()});
			            				  data.put("4",new Object[]{"Software Engineering(2011-12)::"+secondary.get("SE").toString()});
			            				  data.put("5",new Object[]{"Software Engineering(2012-13)::"+secondary.get("SE2").toString()});
			            				  data.put("6",new Object[]{"Mechanical Engineering(2011-12)::"+secondary.get("ME").toString()});
			            				  data.put("7",new Object[]{"Mechanical Engineering(2012-13)::"+secondary.get("ME2").toString()});			            				  
			            				  data.put("8",new Object[]{"Chemical Engineering(2011-12)::"+secondary.get("CHE").toString()});
			            				  data.put("9",new Object[]{"Chemical Engineering(2012-13)::"+secondary.get("CHE2").toString()});
			            				  data.put("91",new Object[]{"Civil Engineering(2011-12)::"+secondary.get("CVE").toString()});
			            				  data.put("92",new Object[]{"Civil Engineering(2012-13)::"+secondary.get("CVE2").toString()});
			            			  }
			            			  
	            		             //data.put("1",new Object[]{"hi"});
	            		             //data.put("2",new Object[] {"UnivName"});
	            		             
	            		             Set<String> keyset = data.keySet();
	            		             int rownum = 0;
	            		             for (String key : keyset) {
	            		                 Row row = sheet.createRow(rownum++);
	            		                 Object [] objArr = data.get(key);
	            		                 int cellnum = 0;
	            		                 for (Object obj : objArr) {
	            		                     Cell cell = row.createCell(cellnum++);
	            		                     if(obj instanceof Date) 
	            		                         cell.setCellValue((Date)obj);
	            		                     else if(obj instanceof Boolean)
	            		                         cell.setCellValue((Boolean)obj);
	            		                     else if(obj instanceof String)
	            		                         cell.setCellValue((String)obj);
	            		                     else if(obj instanceof Double)
	            		                         cell.setCellValue((Double)obj);
	            		                 }
	            		             }
	            		             try {
	            		            	    FileOutputStream out = 
	            		            	            new FileOutputStream(attachmentSource);
	            		            	    tmpWorkbook.write(out);
	            		            	    out.close();
	            		            	    System.out.println("Excel written successfully..");
	            		            	     
	            		            	} catch (FileNotFoundException e) {
	            		            	    e.printStackTrace();
	            		            	} catch (IOException e) {
	            		            	    e.printStackTrace();
	            		            	}
	            		             
	            		             
	            		             DataSource source = new FileDataSource(attachmentSource);
	            		             messageBodyPart.setDataHandler(new DataHandler(source));
	            		             messageBodyPart.setFileName(attachmentSource.getName());
	            		             
	            		             multipart.addBodyPart(messageBodyPart);
	            		             msg.setContent(multipart);
	            		             msg.saveChanges();
                                	 
	            		             Transport t = new AWSJavaMailTransport(session, null);
	            		             t.connect();
	            		             t.sendMessage(msg, null);

	            		             // Close your transport when you're completely done sending
	            		             // all your messages
	            		             t.close();
                                 }
                                 catch(Exception e){System.out.println("exception in universityservice "+e.toString());}          			 
		            			 
		            		 }
		            		 System.out.println(queryParam.substring((queryParam.indexOf("=")+1),queryParam.indexOf("&")));
		            		 uname = queryParam.substring((queryParam.indexOf("=")+1),queryParam.indexOf("&"));
		            		 String name = uname.replace('+', ' ');
		            		 //Assign university name for further use
		            		 u_name = name;
		            		 if(queryParam.contains("submit"))
		            		 {
		            			          	
		            			            			  
		            			  String textUri = "mongodb://unilinks:unilinks@ds053818.mongolab.com:53818/unilinks";
		            	          MongoClientURI uri = new MongoClientURI(textUri);		                  
		            			  MongoClient m = new MongoClient(uri);		  
		            			  DB db= m.getDB(uri.getDatabase());
		            			  BasicDBObject query = new BasicDBObject("SchoolName",name);
		            			  DBCollection collection = db.getCollection("university");
		            			  DBCollection collection_two = db.getCollection("salary");
		            			 //Cursor for finding salary
		            			  DBCursor dc_two = collection_two.find(query);
		            			  while(dc_two.hasNext())
		            			  {
		            				 
		            				  DBObject secondary = dc_two.next();
		            				  sd.setA(Integer.parseInt(secondary.get("CE").toString()));
		            				  sd.setB(Integer.parseInt(secondary.get("CE2").toString()));
		            				  sd.setC(Integer.parseInt(secondary.get("SE").toString()));
		            				  sd.setD(Integer.parseInt(secondary.get("SE2").toString()));
		            				  sd.setE(Integer.parseInt(secondary.get("ME").toString()));
		            				  sd.setF(Integer.parseInt(secondary.get("ME2").toString()));
		            				  sd.setG(Integer.parseInt(secondary.get("CHE").toString()));
		            				  sd.setH(Integer.parseInt(secondary.get("CHE2").toString()));
		            				  sd.setI(Integer.parseInt(secondary.get("CVE").toString()));
		            				  sd.setJ(Integer.parseInt(secondary.get("CVE2").toString()));
		            				 
		            				  
		            				  salaryDetails.add(sd);
		            			  }
		            			  
		            			  //Cursor for finding linkedin data
		            			  DBCursor dc = collection.find(query);
		            			  if(dc.count()==0)
		            			  {
		            				  //Publish to administrator that requested uni is not present in database
		            				  PublishResult pubRes=sns.publish(new PublishRequest(topicArn,"Search for "+name+" has returned null: Data base update needed.","Alert:Unilinks"));
		            			  }
		            			  while(dc.hasNext())
		            			  {
		            				 DBObject primary = dc.next();
		            				 
		            				 
	            					 cd.setFirstName(primary.get("firstName").toString());
	            					 
	            					 cd.setLastName(primary.get("lastName").toString());
	            					 
	            					 cd.setHeadline(primary.get("headline").toString());
	            					 
	            					 cd.setProfileURL(primary.get("url").toString());
	            					 
	            					
	            					 careerDetails.add(cd);		            					 
	            				 }
		            				 
		            			  }
		            			 
		            			 
		            			 
		            			 
		            			 
		            				/* Mongo mongo = new Mongo("localhost", 27017);
		            				 DB db = mongo.getDB("test");
		            				 DBCollection collection = db.getCollection("test");		            				
		            				 BasicDBObject query = new BasicDBObject("SchoolName",name);
		            				 DBCursor dc = collection.find(query);
		            				// System.out.println("size of careerDetails object:"+dc.length());
		            				 while(dc.hasNext())
		            				 {
		            					 DBObject primary = dc.next();
		            			
		            					 CareerDetails cd =  new CareerDetails();
		            					 cd.setFirstName(primary.get("firstName").toString());
		            					 System.out.println("firstName:"+cd.getFirstName());
		            					 cd.setLastName(primary.get("lastName").toString());
		            					 System.out.println("lastname:"+cd.getLastName());
		            					 cd.setOrgName(primary.get("headline").toString());
		            					 System.out.println("orgname:"+cd.getOrgName());
		            					 cd.setSchoolName(primary.get("SchoolName").toString());
		            					 //cd.setProfileURL(primary.get("url").toString());
		            					 //System.out.println("SchoolName:"+cd.getSchoolName());
		            					 //add career details object to the list.
		            					 careerDetails.add(cd);		            					 
		            				 }
		            				 
		            				 BasicDBObject key = new BasicDBObject("OrgName", false );
		            				 BasicDBObject initial= new BasicDBObject("total",0);
		            				 BasicDBObject cond = new BasicDBObject();
		            				 String reduce = "function( curr, result ){result.total++}";
		            				 BasicDBList returnList = (BasicDBList) collection.group(key, cond, initial, reduce);
		            				 for (Object o : returnList) {
                                            System.out.println("Inside the database query");		            					 
		            		                System.out.println(o.toString());
		            		            }
		            				 List<DBObject> dbo = dc.toArray();
		            				 System.out.println("dbobject size:"+dbo.size());
		            				 
		            				 UniversityObject uo = new UniversityObject();
		            				 uo.setSchoolName("San Jose State University");
		            				 uo.setDepartment("Computer Science");
		            				 uo.setLocation("San Jose");
		            				 uo.setContact("One Washington Square,San Jose, CA 95192 408-924-1000");
		            				 uo.setGreScore(94);
		            				 uo.setIeltsScore(6.5);
		            				 uo.setToeflScore(110);	
		            				 uo.setTuitionFees("$7500");
		            				 uo.setFallApplnDate("12August2013");
		            				 uo.setSpringApplnDate("12Jan2013");		            				 		            				 
		            				 universityObject.add(uo);
		            				 
		            				 UniversityObject uoone = new UniversityObject();
		            				 uoone.setSchoolName("Harvard University");
		            				 uoone.setDepartment("Computer Science");
		            				 uoone.setLocation("Cambridge, MA");
		            				 uoone.setContact("Massachusetts Ave, Cambridge, MA 02138");
		            				 uoone.setGreScore(321);
		            				 uoone.setIeltsScore(7.8);
		            				 uoone.setToeflScore(80);	
		            				 uoone.setTuitionFees("$42292");
		            				 uoone.setFallApplnDate("15December2013");
		            				 uoone.setSpringApplnDate("NA");
		            				 
		            				 universityObject.add(uoone);}*/	            				 
		            				 
		            			 
		            			
		            			 	            			 
		            			 input.put("salaryDetails",salaryDetails);
		            			 input.put("careerDetails",careerDetails);
		                         template.process(input, file);
		                         
		                         
		            		 }
		            	 
		                  
		                  
		        	  }catch(Exception e){System.out.print(e.getMessage());}
		        	  
		        	  return file;
		          }
				
			});
			
		}catch(Exception e){System.out.println(e.getMessage());}
           
	    }
	
}


