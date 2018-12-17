# SpringBootMongoDb

> In this tutorial you will know how to create Spring boot 1.4.0.RELEASE MVC  example in simplest way.
### Project Structure

![](extra/projectStructure.png)


#### Step 1 - Let’s Setup Environment

1. Spring 1.4.0.RELEASE and any latest version
2. Maven 3 and any latest version
3. JDK 1.6 / JDK 1.7 / JDK 1.8 / JDK 1.9
4. Eclipse Kepler / Eclipse Juno / Eclipse Neon
5. Tomcat 6 / Tomcat 7 / Tomcat 8 / Tomcat 9


#### Step 2 - Add Dipendency in ``pom.xml`` file

```XML
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.dineshonjava.sbmdb</groupId>
	<artifactId>SpringBootMongoDB</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>SpringBootMongoDB</name>
	<description>SpringBootMongoDB project for Spring Boot with MongoDB providing APIs</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.4.0.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.apache.tomcat.embed</groupId>
			<artifactId>tomcat-embed-jasper</artifactId>
		</dependency>
		<dependency>
		   <groupId>com.googlecode.json-simple</groupId>
		   <artifactId>json-simple</artifactId>
		   <version>1.1.1</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/commons-configuration/commons-configuration -->
		<dependency>
		    <groupId>commons-io</groupId>
		    <artifactId>commons-io</artifactId>
		    <version>2.5</version>
		</dependency>
		<dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-databind</artifactId>
		 </dependency>
		 <dependency>
			 <groupId>commons-beanutils</groupId>
			 <artifactId>commons-beanutils</artifactId>
			 <version>1.8.3</version>
		</dependency>
		<dependency>
			 <groupId>commons-collections</groupId>
			 <artifactId>commons-collections</artifactId>
			 <version>3.2.1</version>
		</dependency>
		 
		 <!-- <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>jquery</artifactId>
            <version>2.2.4</version>
        </dependency> -->
		<!-- <dependency>
		    <groupId>de.flapdoodle.embed</groupId>
		    <artifactId>de.flapdoodle.embed.mongo</artifactId>
		</dependency> -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>


</project>



```


#### Step 3 - Add all package to above structure
first i add com.dineshonjava.sbmdb.
```JAVA
package com.dineshonjava.sbmdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootMongoDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMongoDbApplication.class, args);
	}
}



<!-- End Here -->
```

second one is spring configuration class.
```JAVA
package com.dineshonjava.sbmdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter{
	
	/*@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/path").setViewName("emp");
	}*/
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean
	public InternalResourceViewResolver ViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}



<!-- End Here -->
```


And third one is controller
```JAVA
/**
 * 
 */
package com.dineshonjava.sbmdb.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dineshonjava.sbmdb.models.Booking;
import com.dineshonjava.sbmdb.models.BookingRepository;
import com.dineshonjava.sbmdb.models.FileObject;
import com.dineshonjava.sbmdb.models.JsonObject;
import com.dineshonjava.sbmdb.models.UniqueWord;
import com.dineshonjava.sbmdb.models.UniqueWordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Dinesh.Rajput
 *
 */
//@RestController
//@RequestMapping("/booking")
@Controller
public class BookingController {
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	UniqueWordRepository uniqueWordRepository;
	/**
	 * GET /create  --> Create a new booking and save it in the database.
	 */
	
	@RequestMapping(value="/",method = RequestMethod.GET)
	public String home(Model model) {
		System.out.println("starting page load :::: ");
		
		return "emp";
	}
	
	@RequestMapping(value="/viewUniqueKey",method = RequestMethod.GET)
	public String viewUniqueKey(Model model) {
		System.out.println("starting page load :::: ");
		
		return "emp";
	}
	
	@RequestMapping(value="/viewSerachUniqueKey",method = RequestMethod.GET)
	public String viewSerachUniqueKey(Model model) {
		System.out.println("starting page load :::: ");
		
		return "emp1";
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST,produces="application/json")
	public @ResponseBody Integer insert(Model model) {
		System.out.println("starting page load :::: ");
		
       try {
    	   File folder = new File("D:\\Allswffile\\");
        	//File folder = new File("D:\\myswf\\myfile");
			
			// retrieve file listing
		    File[] fileList = folder.listFiles();

		    if (fileList == null) {
		        // throw an exception, return or do any other error handling here
		    	System.out.println("file list is null");
		    }

		    //allObjData.setSyllabusYear("2018-19");
	       // List<FileObject> fileObjects = new ArrayList<FileObject>();
	      
		    // path is correct
		    for (final File file : fileList ) {
		    	if(file != null) {
		    		JSONParser parser = new JSONParser();
		    		System.out.println("file ::::: "+file.getAbsolutePath());
		    		 Object obj = parser.parse(new FileReader(file));
		                
		                JSONObject jsonObject =  (JSONObject) obj;
		                
		                String status = (String) jsonObject.get("status");
		                String objectId = (String) jsonObject.get("object_id");
		                String guj = (String) jsonObject.get("guj");
		                String hid = (String) jsonObject.get("hid");
		                String eng = (String) jsonObject.get("eng");
		                //String author = (String) jsonObject.get("Author");
		                //JSONArray companyList = (JSONArray) jsonObject.get("Company List");
		     
		                /*System.out.println("status: " + status);
		                System.out.println("objectId: " + objectId);
		                System.out.println("guj: " + guj);
		                System.out.println("hid: " + hid);
		                System.out.println("eng: " + eng);
		                System.out.println("\nCompany List:");*/
		                if(guj.length()>0) {
		                	if(!guj.equalsIgnoreCase("") && !guj.equalsIgnoreCase(null)) {
		                		 Set<String> uniqueWords = new HashSet<String>();

		             			String[] words = guj.split("[ \\[“”\\-–_+\'\"\n\\t\\r.,;:!«÷+?(){}]");
		             			for (String word : words)
		             			{
		             				if (!word.equals("") && !word.equals(" "))
		             					uniqueWords.add(word.toLowerCase());
		             			}
		             			for (String s : uniqueWords)
		             			{
		             				Pattern p1 = Pattern.compile(s);
		             				Matcher m = p1.matcher(guj.toLowerCase());
		             				int count = 0;
		             				while (m.find())
		             				{
		             					count++;
		             				}
		             				//UniqueWord uw1 =uniqueWordRepository.findUnqueKeyByString(s,"Gujarati");
		             				UniqueWord uw =uniqueWordRepository.findUnqueKeyByWord(s);
		             				//Set<FileObject> fOGList = new HashSet<FileObject>();
		             				List<FileObject> fOGList = new ArrayList<FileObject>();
		             				if(uw != null) {
		             					uw.setUnique_word_name(s);
		             					uw.setUnique_word_language("Gujarati");
		             					FileObject fo = new FileObject();
		             					fo.setObject_name(objectId);
		             					fo.setCount_word(count);
		             					int match=0;
	             						for (FileObject fbject : uw.getFile_objects()) {
											if(fbject.getObject_name().equalsIgnoreCase(objectId)) {
												match=1;
											}
										}
	             						if(match == 0) {
	             							fOGList.add(fo);
	             						}
                     					fOGList.addAll(uw.getFile_objects());
                     					uw.setFile_objects(fOGList);
                     					UniqueWord afterUpdateUW=uniqueWordRepository.save(uw);
		             				}else {
		             					UniqueWord uws = new UniqueWord();
		             					uws.setUnique_word_name(s);
		             					uws.setUnique_word_language("Gujarati");
		             					FileObject foAdd = new FileObject();
		             					foAdd.setObject_name(objectId);
		             					foAdd.setCount_word(count);
		             					fOGList.add(foAdd);
		             					uws.setFile_objects(fOGList);
		             					UniqueWord afterAddUW=uniqueWordRepository.save(uws);
		             				}
		             			}
		                    }
		                }
		                
		                if(hid.length()>0) {
		                	if(!hid.equalsIgnoreCase("") && !hid.equalsIgnoreCase(null)) {
		                		Set<String> uniqueWords = new HashSet<String>();

		             			String[] words = hid.split("[ \\[“”\\-–_\'\"\n\\t\\r.,;«÷:!+?(){}]");
		             			for (String word : words)
		             			{
		             				if (!word.equals("") && !word.equals(" "))
		             					uniqueWords.add(word.toLowerCase());
		             			}
		             			for (String s : uniqueWords)
		             			{
		             				Pattern p1 = Pattern.compile(s);
		             				Matcher m = p1.matcher(hid.toLowerCase());
		             				int count = 0;
		             				while (m.find())
		             				{
		             					count++;
		             				}
		             				//UniqueWord uw1 =uniqueWordRepository.findUnqueKeyByString(s,"Hindi");
		             				UniqueWord uw =uniqueWordRepository.findUnqueKeyByWord(s);
		             				//Set<FileObject> fOHList = new HashSet<FileObject>();
		             				List<FileObject> fOHList = new ArrayList<FileObject>();
		             				if(uw != null) {
		             					
		             					uw.setUnique_word_name(s);
		             					uw.setUnique_word_language("Hindi");
		             					FileObject fo = new FileObject();
		             					fo.setObject_name(objectId);
		             					fo.setCount_word(count);
		             					int match=0;
	             						for (FileObject fbject : uw.getFile_objects()) {
											if(fbject.getObject_name().equalsIgnoreCase(objectId)) {
												match=1;
											}
										}
	             						if(match == 0) {
	             							fOHList.add(fo);
	             						}
		                     					fOHList.addAll(uw.getFile_objects());
		                     					uw.setFile_objects(fOHList);
		                     					UniqueWord afterUpdateUW=uniqueWordRepository.save(uw);
		             				}else {
		             					UniqueWord uws = new UniqueWord();
		             					uws.setUnique_word_name(s);
		             					uws.setUnique_word_language("Hindi");
		             					FileObject foAdd = new FileObject();
		             					foAdd.setObject_name(objectId);
		             					foAdd.setCount_word(count);
		             					fOHList.add(foAdd);
		             					uws.setFile_objects(fOHList);
		             					UniqueWord afterAddUW=uniqueWordRepository.save(uws);
		             				}
		             			}
		             			
		                		
		                	}
		                }
		                
		                if(eng.length()>0) {
		                	if(!eng.equalsIgnoreCase("") && !eng.equalsIgnoreCase(null)) {
		                		Set<String> uniqueWords = new HashSet<String>();

		             			String[] words = eng.split("[ \\[“”\\-–_\'\"\n\\t\\r.,;«:÷!+?(){}]");
		             			for (String word : words)
		             			{
		             				if (!word.equals("") && !word.equals(" "))
		             					uniqueWords.add(word.toLowerCase());
		             			}
		             			for (String s : uniqueWords)
		             			{
		             				Pattern p1 = Pattern.compile(s);
		             				Matcher m = p1.matcher(eng.toLowerCase());
		             				int count = 0;
		             				while (m.find())
		             				{
		             					count++;
		             				}
		             				//UniqueWord uw1 =uniqueWordRepository.findUnqueKeyByString(s,"English");
		             				UniqueWord uw =uniqueWordRepository.findUnqueKeyByWord(s);
		             				//Set<FileObject> fOEList = new HashSet<FileObject>();
		             				List<FileObject> fOEList = new ArrayList<FileObject>();
		             				if(uw != null) {
		             					uw.setUnique_word_name(s);
		             					uw.setUnique_word_language("English");
		             					FileObject fo = new FileObject();
		             					fo.setObject_name(objectId);
		             					fo.setCount_word(count);
		             					int match=0;
	             						for (FileObject fbject : uw.getFile_objects()) {
											if(fbject.getObject_name().equalsIgnoreCase(objectId)) {
												match=1;
											}
										}
	             						if(match == 0) {
	             							fOEList.add(fo);
	             						}
		                     					fOEList.addAll(uw.getFile_objects());
		                     					uw.setFile_objects(fOEList);
		                     					UniqueWord afterUpdateUW=uniqueWordRepository.save(uw);
		             				}else {
		             					UniqueWord uws = new UniqueWord();
		             					uws.setUnique_word_name(s);
		             					uws.setUnique_word_language("English");
		             					FileObject foAdd = new FileObject();
		             					foAdd.setObject_name(objectId);
		             					foAdd.setCount_word(count);
		             					fOEList.add(foAdd);
		             					uws.setFile_objects(fOEList);
		             					UniqueWord afterAddUW=uniqueWordRepository.save(uws);
		             				}
		             			}
		                		
		                	}
		                }
		    	}
               
			}
        	
		    return 1;

        } catch (IOException e) {
        	System.out.println("IOException load :::: ");
        	e.printStackTrace();
        	return 0;
        } catch (Exception e) {
        	System.out.println("Exception load :::: ");
            e.printStackTrace();
            return 0;
        }
		
	}


	@RequestMapping(value="/getUniqueWord",method = RequestMethod.POST,produces="application/json")
	public @ResponseBody List<UniqueWord> getUniqueWord(Model model,@RequestParam("filesData") MultipartFile uploadfiles){
		System.out.println("controoller call :::: ");
			 File f =null;
			
			try {
				f = convert(uploadfiles);
				 System.out.println(f.getName());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        ObjectMapper mapper = new ObjectMapper();
	       
	        JsonObject value = null;
	        try {
	            value = mapper.readValue(f, JsonObject.class);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }  
	        
	        if(value.getGuj().length()>0) {
            	if(!value.getGuj().equalsIgnoreCase("") && !value.getGuj().equalsIgnoreCase(null)) {
            		 Set<String> uniqueWords = new HashSet<String>();

         			String[] words = value.getGuj().split("[ \\[“”\\-'\"\n\\t\\r.,;:!?(){}]");
         			for (String word : words)
         			{
         				if (!word.equals("") && !word.equals(" "))
         					uniqueWords.add(word.toLowerCase());
         			}
         			for (String s : uniqueWords)
         			{
         				Pattern p1 = Pattern.compile(s);
         				Matcher m = p1.matcher(value.getGuj().toLowerCase());
         				int count = 0;
         				while (m.find())
         				{
         					count++;
         				}
         				// statement.executeUpdate("insert into counttable (word,count) values('" + s +"','" + count + "')");
         				System.out.println("guj  ::: "+s + " = " + count);
         				/*UniqueWord uw = uniqueWordRepository.findOne(
         						new Query(Criteria.where("unique_word_name").is(s)),
         						UniqueWord.class,);*/
         				UniqueWord uw1 =uniqueWordRepository.findUnqueKeyByString(s,"Gujarati");
         				UniqueWord uw =uniqueWordRepository.findUnqueKeyByWord(s);
         				if(uw1 != null) {
         					System.out.println("uw1.getUnique_word_name() Gujarati:::"+uw1.getUnique_word_name());
         				}
         				//Set<FileObject> fOGList = new HashSet<FileObject>();
         				List<FileObject> fOGList = new ArrayList<FileObject>();
         				//if(uw.getUnique_word_name().equals(s)) {
         				if(uw != null) {
         					System.out.println("uw.getUnique_word_name() Gujarati:::"+uw.getUnique_word_name());
         					//if(uw.getUnique_word_name().equals(s)) {
         					//if(uw.getUnique_word_name().equals(s)) {
	         					uw.setUnique_word_name(s);
	         					uw.setUnique_word_language("Gujarati");
	         					FileObject fo = new FileObject();
	         					/*for (FileObject fobject : uw.getFile_objects()) {
	         						fo.setObject_name(fobject.getObject_name());
	         						fOList.add(fo);
								}*/
	         					fo.setObject_name(f.getName());
	         					fo.setCount_word(count);
	         					System.out.println("out :::: "+fOGList.size());
	         							fOGList.add(fo);
	                 					fOGList.addAll(uw.getFile_objects());
	                 					uw.setFile_objects(fOGList);
	                 					UniqueWord afterUpdateUW=uniqueWordRepository.save(uw);
	                 					System.out.println("afterUpdateUW  ::: "+afterUpdateUW.getUnique_id());
	         					//UniqueWord afterUpdateUW=uniqueWordRepository.save(uw);
	         					/*for (FileObject fileObject : fOList) {
	         						fileObject.set
								}*/
	         					System.out.println("guj update ::: "+s + " = " + count);
         					//}
         				}else {
         					UniqueWord uws = new UniqueWord();
         					uws.setUnique_word_name(s);
         					uws.setUnique_word_language("Gujarati");
         					FileObject foAdd = new FileObject();
         					foAdd.setObject_name(f.getName());
         					foAdd.setCount_word(count);
         					fOGList.add(foAdd);
         					uws.setFile_objects(fOGList);
         					UniqueWord afterAddUW=uniqueWordRepository.save(uws);
         					System.out.println("afterAddUW  ::: "+afterAddUW.getUnique_id());
         					System.out.println("guj add ::: "+s + " = " + count);
         				}
         			}
                }
            }
            
            if(value.getHid().length()>0) {
            	if(!value.getHid().equalsIgnoreCase("") && !value.getHid().equalsIgnoreCase(null)) {
            		Set<String> uniqueWords = new HashSet<String>();

         			String[] words = value.getHid().split("[ \\[“”\\-'\"\n\\t\\r.,;:!?(){}]");
         			for (String word : words)
         			{
         				if (!word.equals("") && !word.equals(" "))
         					uniqueWords.add(word.toLowerCase());
         			}
         			for (String s : uniqueWords)
         			{
         				Pattern p1 = Pattern.compile(s);
         				System.out.println("s hid :::::: is :::: "+s);
         				Matcher m = p1.matcher(value.getHid().toLowerCase());
         				int count = 0;
         				while (m.find())
         				{
         					count++;
         				}
         				// statement.executeUpdate("insert into counttable (word,count) values('" + s +"','" + count + "')");
         				System.out.println("hid :::: "+s + " = " + count);
         				UniqueWord uw1 =uniqueWordRepository.findUnqueKeyByString(s,"Hindi");
         				UniqueWord uw =uniqueWordRepository.findUnqueKeyByWord(s);
         				if(uw1 != null) {
         					System.out.println("uw1.getUnique_word_name() Hindi:::"+uw1.getUnique_word_name());
         				}
         				//Set<FileObject> fOHList = new HashSet<FileObject>();
         				List<FileObject> fOHList = new ArrayList<FileObject>();
         				if(uw != null) {
         					System.out.println("uw.getUnique_word_name() Hindi:::"+uw.getUnique_word_name());
         					uw.setUnique_word_name(s);
         					uw.setUnique_word_language("Hindi");
         					FileObject fo = new FileObject();
         					fo.setObject_name(f.getName());
         					fo.setCount_word(count);
 							fOHList.add(fo);
         					fOHList.addAll(uw.getFile_objects());
         					uw.setFile_objects(fOHList);
         					UniqueWord afterUpdateUW=uniqueWordRepository.save(uw);
         					System.out.println("hid update ::: "+s + " = " + count);
         				}else {
         					UniqueWord uws = new UniqueWord();
         					uws.setUnique_word_name(s);
         					uws.setUnique_word_language("Hindi");
         					FileObject foAdd = new FileObject();
         					foAdd.setObject_name(f.getName());
         					foAdd.setCount_word(count);
         					fOHList.add(foAdd);
         					uws.setFile_objects(fOHList);
         					UniqueWord afterAddUW=uniqueWordRepository.save(uws);
         					System.out.println("hid add ::: "+s + " = " + count);
         				}
         			}
         			
            		
            	}
            }
            
            if(value.getEng().length()>0) {
            	if(!value.getEng().equalsIgnoreCase("") && !value.getEng().equalsIgnoreCase(null)) {
            		Set<String> uniqueWords = new HashSet<String>();

         			String[] words = value.getEng().split("[ \\[“”\\-'\"\n\\t\\r.,;:!?(){}]");
         			for (String word : words)
         			{
         				if (!word.equals("") && !word.equals(" "))
         					uniqueWords.add(word.toLowerCase());
         			}
         			for (String s : uniqueWords)
         			{
         				Pattern p1 = Pattern.compile(s);
         				System.out.println("s English :::::: is :::: "+s);
         				Matcher m = p1.matcher(value.getEng().toLowerCase());
         				int count = 0;
         				while (m.find())
         				{
         					count++;
         				}
         				// statement.executeUpdate("insert into counttable (word,count) values('" + s +"','" + count + "')");
         				System.out.println("eng  :::: "+s + " = " + count);
         				UniqueWord uw1 =uniqueWordRepository.findUnqueKeyByString(s,"English");
         				UniqueWord uw =uniqueWordRepository.findUnqueKeyByWord(s);
         				if(uw1 != null) {
         					System.out.println("uw1.getUnique_word_name() English:::"+uw1.getUnique_word_name());
         				}
         				//Set<FileObject> fOEList = new HashSet<FileObject>();
         				List<FileObject> fOEList = new ArrayList<FileObject>();
         				//if(uw.getUnique_word_name().equals(s)) {
         				if(uw != null) {
         					System.out.println("uw.getUnique_word_name() English:::"+uw.getUnique_word_name());
         					uw.setUnique_word_name(s);
         					uw.setUnique_word_language("English");
         					FileObject fo = new FileObject();
         					/*for (FileObject fobject : uw.getFile_objects()) {
         						fo.setObject_name(fobject.getObject_name());
         						fOList.add(fo);
							}*/
         					/*String match="";
         					for (FileObject fileObject : uw.getFile_objects()) {
								
							}
         					if(uw.getFile_objects())*/
         					fo.setObject_name(f.getName());
         					fo.setCount_word(count);
 							fOEList.add(fo);
         					fOEList.addAll(uw.getFile_objects());
         					uw.setFile_objects(fOEList);
         					UniqueWord afterUpdateUW=uniqueWordRepository.save(uw);
         					
         					/*for (FileObject fileObject : fOList) {
         						fileObject.set
							}*/
         					System.out.println("eng update ::: "+s + " = " + count);
         				}else {
         					UniqueWord uws = new UniqueWord();
         					uws.setUnique_word_name(s);
         					uws.setUnique_word_language("English");
         					FileObject foAdd = new FileObject();
         					foAdd.setObject_name(f.getName());
         					foAdd.setCount_word(count);
         					fOEList.add(foAdd);
         					uws.setFile_objects(fOEList);
         					UniqueWord afterAddUW=uniqueWordRepository.save(uws);
         					System.out.println("eng add ::: "+s + " = " + count);
         				}
         			}
            		
            	}
            }
            List<UniqueWord> um = uniqueWordRepository.findAll();
    		System.out.println("controoller call :::: ");
    		//start 
    		
    		System.out.println("showAll ::: ");
    		//return emplist;
    		return um;
		
		//return emplist;
	}
	
	
	private File convert(MultipartFile uploadfiles) throws IOException {
		File convFile = new File(uploadfiles.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(uploadfiles.getBytes());
        fos.close();
        return convFile;
	}
	
	@RequestMapping(value="/showAll",method = RequestMethod.GET,produces="application/json")
	public @ResponseBody List<UniqueWord> showAll(Model model){
		//List<employee> emplist =crudeService.findAll();
		List<UniqueWord> um = uniqueWordRepository.findAll();
		System.out.println("controoller call :::: ");
		//start 
		
		System.out.println("showAll ::: ");
		//return emplist;
		return um;
	}
	
	@RequestMapping(value="/searchName",method = RequestMethod.GET,produces="application/json")
	public @ResponseBody List<UniqueWord> searchName(Model model,@RequestParam("firstname")String firstname){
		//List<employee> emplist =crudeService.findAll();
		//List<UniqueWord> um = uniqueWordRepository.findAll();
		Date d = new Date();
		long s1=d.getTime();
		System.out.println("start time :::: "+d.getTime());
		List<UniqueWord> uw2 =uniqueWordRepository.findByUniqueWordNameLike(firstname);
		//List<UniqueWord> uw2 =uniqueWordRepository.findUnqueKeyByWordUsingregex(firstname);
		System.out.println("uw2 size :::: "+uw2.size());
		List<UniqueWord> ulist = new ArrayList<UniqueWord>();
		//Set<FileObject> Flist = new HashSet<FileObject>();
		for (UniqueWord uniqueWord : uw2) {
			UniqueWord unw =new UniqueWord();
			unw.setUnique_id(uniqueWord.getUnique_id());
			//System.out.println("uniqueWord "+uniqueWord.getUnique_word_name());
			unw.setUnique_word_name(uniqueWord.getUnique_word_name());
			unw.setUnique_word_language(uniqueWord.getUnique_word_language());
			unw.setFile_objects(uniqueWord.getFile_objects());
			Collections.sort(uniqueWord.getFile_objects(), new Comparator<FileObject>() {

				@Override
				public int compare(FileObject o1, FileObject o2) {
					// TODO Auto-generated method stub
					return o1.getCount_word().compareTo(o2.getCount_word());
				}
			});
			Collections.reverse(uniqueWord.getFile_objects());
			ulist.add(unw);
			//Flist.addAll(uniqueWord.getFile_objects());
		}
		//List<UniqueWord> uw =uniqueWordRepository.findUnqueKeyByWordUsingregexSecondQuery(firstname,Flist);//,uw2,Flist
		//System.out.println("uw  size :::: "+uw.size());
		Date d1 = new Date();
		long s2=d1.getTime();
		long s3 =(s2 - s1);
		System.out.println("end time :::: "+d1.getTime());
		System.out.println(" ms :::: "+s3);
		//start 
		
		System.out.println("searchName ::: ");
		//return emplist;
		return ulist;
	}
	
	@RequestMapping("/create")
	public Map<String, Object> create(Booking booking) {
		booking.setTravelDate(new Date());
		booking = bookingRepository.save(booking);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Booking created successfully");
		dataMap.put("status", "1");
		dataMap.put("booking", booking);
	    return dataMap;
	}
	
	/**
	 * GET /read  --> Read a booking by booking id from the database.
	 */
	@RequestMapping("/read")
	public Map<String, Object> read(@RequestParam String bookingId) {
		Booking booking = bookingRepository.findOne(bookingId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Booking found successfully");
		dataMap.put("status", "1");
		dataMap.put("booking", booking);
	    return dataMap;
	}
	
	/**
	 * GET /update  --> Update a booking record and save it in the database.
	 */
	@RequestMapping("/update")
	public Map<String, Object> update(@RequestParam String bookingId, @RequestParam String psngrName) {
		Booking booking = bookingRepository.findOne(bookingId);
		booking.setPsngrName(psngrName);
		booking = bookingRepository.save(booking);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Booking updated successfully");
		dataMap.put("status", "1");
		dataMap.put("booking", booking);
	    return dataMap;
	}
	
	/**
	 * GET /delete  --> Delete a booking from the database.
	 */
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam String bookingId) {
		bookingRepository.delete(bookingId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Booking deleted successfully");
		dataMap.put("status", "1");
	    return dataMap;
	}
	
	/**
	 * GET /read  --> Read all booking from the database.
	 */
	@RequestMapping("/read-all")
	public Map<String, Object> readAll() {
		List<Booking> bookings = bookingRepository.findAll();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Booking found successfully");
		dataMap.put("totalBooking", bookings.size());
		dataMap.put("status", "1");
		dataMap.put("bookings", bookings);
	    return dataMap;
	}
}


<!-- End Here -->
```

```JAVA
package com.dineshonjava.sbmdb.models;


import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UniqueWordRepository extends MongoRepository<UniqueWord, String> {
	
	String unique_word = null;

	//@Query("{ 'unique_word_name' : ?0 }")
	@Query("{ 'unique_word_name' : ?0 ,'unique_word_language' : ?0 }")
	public UniqueWord findUnqueKeyByString(String unique_word_name, String language);
	
	@Query("{ 'unique_word_name' : ?0 }")
	public UniqueWord findUnqueKeyByWord(String unique_word_name);
	
	@Query("{ 'unique_word_name' : { $regex: ?0 } }")//, 'file_objects.count_word' :{ $group: ?0 }
	public List<UniqueWord> findUnqueKeyByWordUsingregex(String unique_word_name);
	/*or this is or query*/
	@Query("{ '$or' :[{'unique_word_name' : ?0 },{'unique_word_name' : { $regex: ?0 }}]}")
	//@Query("{ 'unique_word_name' : { '$regex': 'unique_word_name' }}")//, 'file_objects.count_word' :{ $group: ?0 }
	public List<UniqueWord> findByUniqueWordNameLike(String unique_word_name);
	
	//@Query("{ 'unique_word_name' : { $regex: ?0  } ,'uniqueword.file_objects.count_word' :{ $order: ?0 } }")
	//@Query("{ 'unique_word_name': {$regex: ?0 } ,field={'file_objects' :{ 'count_word': {$sort:1}}}")
	@Query("{'uniqueword.file_objects.count_word': ?0}} ")//{$sort:1}//{ 'unique_word_name': {$regex: ?0 }, 
	public List<UniqueWord> findUnqueKeyByWordUsingregexSecondQuery(String unique_word_name, Set<FileObject> file_objects);//, List<UniqueWord> uniqueWord

	//public UniqueWord findOne(Query query);
	
	/**
	   * This method will find an Boooking instance in the database by its departure.
	   * Note that this method is not implemented and its working code will be
	   * automatically generated from its signature by Spring Data JPA.
	   */
	  //public Booking findByDeparture(String departure);
}


<!-- End Here -->
```
com.spring.model

```JAVA
package com.spring.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class CrunchifyGetPropertyValues {
	String result = "";
	InputStream inputStream;
 
	public String getPropValues() throws IOException {
 
		try {
			Properties prop = new Properties();
			String propFileName = "database.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			Date time = new Date(System.currentTimeMillis());
 
			// get the property value and print it out
			String driverClassName = prop.getProperty("driverClassName");
			String url = prop.getProperty("url");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			//String consolePath= prop.getProperty("spring.h2.console.path");
			
			result = "database path = " + driverClassName + ", " + url + ", " + username+","+ password;
			//result = "database path = " + driverClassName + ", " + url + ", " + username+","+ password + ","+consolePath;
			System.out.println(result + "\nProgram Ran on " + time);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
		
	}
}


<!-- End Here -->
```

```JAVA
package com.dineshonjava.sbmdb.models;

import java.util.List;
import java.util.Set;

//import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Order;

/**
 * @author Dinesh.Rajput
 *
 */
@Document(collection ="uniqueword")
public class UniqueWord {
	
	@Id
	String unique_id;
	String unique_word_name;
	String unique_word_language;
	//String syllabus_year;
	//@OrderBy(value = "count_word", order = Order.DESCENDING)
	List<FileObject> file_objects;
	
	public String getUnique_id() {
		return unique_id;
	}
	public void setUnique_id(String unique_id) {
		this.unique_id = unique_id;
	}
	public String getUnique_word_name() {
		return unique_word_name;
	}
	public void setUnique_word_name(String unique_word_name) {
		this.unique_word_name = unique_word_name;
	}
	public String getUnique_word_language() {
		return unique_word_language;
	}
	public void setUnique_word_language(String unique_word_language) {
		this.unique_word_language = unique_word_language;
	}
	public List<FileObject> getFile_objects() {
		return file_objects;
	}
	public void setFile_objects(List<FileObject> file_objects) {
		this.file_objects = file_objects;
	}
	
	
	

}



<!-- End Here -->
```

com.dineshonjava.sbmdb.models
```JAVA
package com.dineshonjava.sbmdb.models;

public class FileObject {
	
	String object_name;
	Integer count_word;

	public Integer getCount_word() {
		return count_word;
	}

	public void setCount_word(Integer count_word) {
		this.count_word = count_word;
	}

	public String getObject_name() {
		return object_name;
	}

	public void setObject_name(String object_name) {
		this.object_name = object_name;
	}
	
}


<!-- End Here -->
```

src/main/resource

```database.properties
spring.data.mongodb.database=myFinalFindUniqueKey2
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
#server.context-path=/rest
server.port=8099


<!-- End Here -->
```



#### Step 4 - Add View to project ``Webapp\WEB-INF\view\emp.jsp`` file

```JSP
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet"> 
	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
	<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<!-- CSS -->
	<style>
	Table.GridOne 
		{
		 padding: 3px;
		 margin: 0;
		 background: lightyellow;
		 border-collapse: collapse; 
		 width:35%;
		}
		Table.GridTwo 
		{
		 padding: 3px;
		 margin: 0;
		 background: lightyellow;
		 border-collapse: collapse; 
		 width:35%;
		}
	Table.GridOne Td 
		{ 
		 padding:2px;
		 border: 1px solid #ff9900;
		 border-collapse: collapse;
		} 
		Table.GridTwo Td 
		{ 
		 padding:2px;
		 border: 1px solid #ff9900;
		 border-collapse: collapse;
		} 
	</style>
	<script type="text/javascript">
	
	jQuery(document).ready(function() {
		$('#insertBut').hide();
		$('#updateBut').hide();
		
		showAll();
		
	    jQuery("#confirmationDialog").dialog({
	        autoOpen: false,
	        modal: true,
	        title:'insert Data',
	        width:600,
	        height:600,
	        open: function() {
	        	var id = $("#id").val();
	        	if(id == '' ){
	        		$('#insertBut').show();
	        		$('#updateBut').hide();
	        	}else{
	        		$('#insertBut').hide();
	        		$('#updateBut').show();
	        	}
	        },
	        close :function() {
	        	$("#id").val('');
    	    	$("#fname").val('');
    	    	$("#lname").val('');
    	    	$("#gen").val('');
    	    	$("#address").val('');
    	    	
	        	showAll();
	        } 
	    });
	});
	
	function insert(){
		$("#confirmationDialog").dialog("open");
	}
	
	function showAll(){
		alert('shioall');
	      $.ajax({
	              type:"GET",
	              url:"showAll",
	              dataType: "json",
	              success:function(data)
	              { 
	            	  $('#tblProducts').html('');
	            	  var rows = '';
	                  $.each( data , function( index, item ) {
	                	rows += '<tr><td>' + (index+1) + '</td>';
	          	  	  	rows += '<td>' + item.unique_word_name + '</td>';
	          	  	  	//rows += '<td>' + item.unique_word_language + '</td>';
	          	  	  	rows += '<td>';
	          	  	  	if(item.file_objects.length >0 ){
	          	  	  		$.each( item.file_objects , function( oIndex, oItem ) {
	          	  	  			rows += '<table class="GridTwo"><tr>';
	          	  	  			rows += '<td>' + oItem.object_name + '</td>';//object_name,count_word
	          	  	  			rows += '<td>&nbsp;&nbsp;&nbsp;count is:' + oItem.count_word + '</td>';
	          	  				rows += '</tr></table>';
	          	  	  		});
	          	  	  	}else{
	          	  	  		//rows += '</td></tr>';
	          	  	  	}
	          	  		rows +='</td></tr>';
	          	  	  	//rows += '<td>' + item.gender + '</td>';
	          	  		//rows += '<td>' + item.address + '</td>';
	          	  		//rows += '<td onclick="editAjaxData('+item.id+');" >edit</td>';
	          	  		//rows += '<td onclick="deleteAjaxData('+item.id+');" >Delete</td></tr>';
	          	  	  	
	          	  	  });
	          	  	  $('#tblProducts').html(rows);
	              },
	              error:function(xmlHttpRequest, textStatus, errorThrown)
	              {
	                     alert("error");
	              }
	      });
	}
	
	function insertAjaxData(){
    	//var fn = $("#fname").val();
  		//var ln = $("#lname").val();
  		//var gn = $("#gen").val();
  		//var add = $("#address").val();
  		//console.log("fn ::: "+fn+" ln ::: "+ln+" gn ::: "+gn+" add ::: "+add);
    	$.ajax({
	    	   type: "post",
	    	   url: "save",
	    	   dataType: "json",  
	    	   data:{},//fname: fn , lname: ln , gen: gn , address: add
	    	   success: function(data){
	    		  alert('success :::: '+data);
	    	    if(data == 1){
	    	    	
	    	    	alert('inserted');
	    	    	$('#confirmationDialog').dialog('close');
	    	    	showAll();
	    	    }else{
	    	    	alert('inserting fail');
	    	    	$('#confirmationDialog').dialog('close');
	    	    	showAll();
	    	    }
	    	   },
	    	   error: function(){      
	    	    alert('Error while request..');
	    	   }
	    });
    }
	function updateAjaxData(){
		var id = $("#id").val();
    	var fn = $("#fname").val();
  		var ln = $("#lname").val();
  		var gn = $("#gen").val();
  		var add = $("#address").val();
    	$.ajax({
	    	   type: "post",
	    	   url: "updateEmp",
	    	   dataType: "json",  
	    	   data:{id: id, fname: fn , lname: ln , gen: gn , address: add},
	    	   success: function(response){
	    	    if(response == 1){
	    	    	alert('updated');
	    	    	$('#confirmationDialog').dialog('close');
	    	    	showAll();
	    	    }else{
	    	    	alert('updated fail ');
	    	    	$('#confirmationDialog').dialog('close');
	    	    	showAll();
	    	    }
	    	   },
	    	   error: function(){      
	    	    alert('Error while request..');
	    	   }
	    });
    }
	
	function editAjaxData(id){
		alert('editAjaxData()'+id);
		
  	  $.ajax({
  	   type: "get",
  	   url: "editData",
  	   dataType: "json",  
  	   data:{id: id },
  	   success: function(response){
  		    alert(response);
  		    $("#id").val(response.id);
    		$("#fname").val(response.firstName);
    		$("#lname").val(response.lastName);
    		$("#gen").val(response.gender);
    		$("#address").val(response.address);
  	    	$("#confirmationDialog").dialog("open");
  	   },
  	   error: function(){      
  	    alert('Error while request..');
  	   }
  	  });
  	}
	
	function deleteAjaxData(id){
  	  $.ajax({
  	   type: "post",
  	   url: "deleteEmp",
  	   dataType: "json",  
  	   data:{id: id },
  	   success: function(response){
	  	   if(response == 1){
	  		   alert('data deleted success');
	  		   showAll();
	  	   }else{
	  		 alert('data deleted fail');
	  		   showAll();
	  	   }
  	   },
  	   error: function(){      
  	   		alert('Error while request..');
  	   }
  	  });
  	 }
	
	function searchName(){
		var firstname = $("#search").val();
	      $.ajax({
	              type:"GET",
	              url:"searchName",
	              dataType: "json",
	              data:{firstname: firstname },
	              success:function(data)
	              { 
	            	  var rows = '';
	            	  $('#tblProducts').html('');
	                  //$.each( data , function( index, item ) {
	                		rows += '<tr><td></td>';
	          	  	  	rows += '<td>' + data.unique_word_name + '</td>';
	          	  	  	//rows += '<td>' + item.unique_word_language + '</td>';
	          	  	  	rows += '<td>';
	          	  	  	if(data.file_objects.length >0 ){
	          	  	  		$.each( data.file_objects , function( oIndex, oItem ) {
	          	  	  			rows += '<table class="GridTwo"><tr>';
	          	  	  			rows += '<td>' + oItem.object_name + '</td>';//object_name,count_word
	          	  	  			rows += '<td>&nbsp;&nbsp;&nbsp;count is:' + oItem.count_word + '</td>';
	          	  				rows += '</tr></table>';
	          	  	  		});
	          	  	  	}else{
	          	  	  		//rows += '</td></tr>';
	          	  	  	}
	          	  		rows +='</td></tr>';

	                  //});
	          	  	  $('#tblProducts').html(rows);
	              },
	              error:function(xmlHttpRequest, textStatus, errorThrown)
	              {
	                     alert("error");
	              }
	      });
	}
	/* $("#btnSubmit").click(function(event){
		alert('hie ');
		event.preventDefault();
		submitClick();
	}); */
	function submitClicked(){
		alert('hie ');
		var form =$('#fileUploadForm')[0];
		var filename= $('#filesEditData').val();
		var ext = filename.split('.').pop();
		var data = new FormData(form);
		if(filename.length<0){
			alert('Plase choose file');
			return;
		}
		 $.ajax({
             type:"POST",
             url:"getUniqueWord",
             encrype:"multipart/form-data",
           //  dataType: "json",
             data:data,
             processData:false,
             contentType:false,
             cache:false,
             success:function(data)
             { 
            	 $('#filesEditData').val('');
            	 var rows = '';
                 $.each( data , function( index, item ) {
               		rows += '<tr><td>' + (index+1) + '</td>';
         	  	  	rows += '<td>' + item.unique_word_name + '</td>';
         	  	  	//rows += '<td>' + item.unique_word_language + '</td>';
         	  	  	rows += '<td>';
         	  	  	if(item.file_objects.length >0 ){
         	  	  		$.each( item.file_objects , function( oIndex, oItem ) {
         	  	  			rows += '<table class="GridTwo"><tr>';
         	  	  			rows += '<td>' + oItem.object_name + '</td>';//object_name,count_word
         	  	  			rows += '<td>&nbsp;&nbsp;&nbsp;count is:' + oItem.count_word + '</td>';
         	  				rows += '</tr></table>';
         	  	  		});
         	  	  	}else{
         	  	  		//rows += '</td></tr>';
         	  	  	}
         	  		rows +='</td></tr>';
         	  	  	//rows += '<td>' + item.gender + '</td>';
         	  		//rows += '<td>' + item.address + '</td>';
         	  		//rows += '<td onclick="editAjaxData('+item.id+');" >edit</td>';
         	  		//rows += '<td onclick="deleteAjaxData('+item.id+');" >Delete</td></tr>';
         	  	  	
         	  	  });
         	  	  $('#tblProducts').html(rows);
             },
             error:function(xmlHttpRequest, textStatus, errorThrown)
             {
            	 $('#filesEditData').val('');
                    alert("error");
             }
     });
	}
	</script>
</head>
<body>
	<!-- <button id="click" onclick="insert();"> Add New</button> -->
	<!-- <form method="POST" enctype="multipart/form-data" id="fileUploadForm">
	    <input type="file" name="filesData" id="filesEditData"/><br/><br/>
	    <input type="button"  value="send" id="btnSubmit" onclick="submitClicked()"/>
	</form>viewSerachUniqueKey,viewUniqueKey -->
	<a href="viewSerachUniqueKey">Search View</a>
	<button id="click" onclick="insertAjaxData();" style="display: none;"> insert</button>
	<!-- <input id="search" type="type" name="search"  value=""/>
	<button id="click" onclick="searchName();"> SEARCH</button> -->
				
	<table class="GridOne">
	  <thead>
	  	<tr>
		  <th> Id </th>
		  <th> word </th>
		  <!-- <th> lang </th> -->
		  <th> object </th>
		  <!-- <th> address </th> -->
		  <!-- <th>Action</th> -->
		</tr>
	  </thead>
	  <tbody id="tblProducts">
	  
	  </tbody>
	</table>

</body>
</html>

```
After load above page and click on search link then open under page

```JSP
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet"> 
	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
	<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<!-- CSS -->
	<style>
	Table.GridOne 
		{
		 padding: 3px;
		 margin: 0;
		 background: lightyellow;
		 border-collapse: collapse; 
		 width:35%;
		}
		Table.GridTwo 
		{
		 padding: 3px;
		 margin: 0;
		 background: lightyellow;
		 border-collapse: collapse; 
		 width:35%;
		}
	Table.GridOne Td 
		{ 
		 padding:2px;
		 border: 1px solid #ff9900;
		 border-collapse: collapse;
		} 
		Table.GridTwo Td 
		{ 
		 padding:2px;
		 border: 1px solid #ff9900;
		 border-collapse: collapse;
		} 
	</style>
	<script type="text/javascript">
	
	function searchName(){
		var firstname = $("#search").val();
	      $.ajax({
	              type:"GET",
	              url:"searchName",
	              dataType: "json",
	              data:{firstname: firstname },
	              success:function(data)
	              { 
	            	  var rows = '';
	            	  $('#tblProducts').html('');
	            	  if(data.length > 0 && data !=null){
	            		  $.each( data , function( index, item ) {
	  	                	rows += '<tr><td>' + (index+1) + '</td>';
	  	          	  	  	rows += '<td>' + item.unique_word_name + '</td>';
	  	          	  	  	rows += '<td>' + item.unique_word_language + '</td>';
	  	          	  	  	rows += '<td>';
	  	          	  	  	if(item.file_objects.length >0 ){
	  	          	  	  		//var count='';
	  	          	  	  		$.each( item.file_objects , function( oIndex, oItem ) {
	  	          	  	  			rows += '<table class="GridTwo"><tr>';
	  	          	  	  			rows += '<td>' + oItem.object_name + '</td>';//object_name,count_word
	  	          	  	  			rows += '<td>&nbsp;&nbsp;&nbsp;count is:' + oItem.count_word + '</td>';
	  	          	  				rows += '</tr></table>';
	  	          	  				//count =oItem.count_word+count;
	  	          	  	  		});
	  	          	  	  	}else{
	  	          	  	  		//rows += '</td></tr>';
	  	          	  	  	}
	  	          	  		rows +='</td></tr>';
	  	          	  	  	//rows += '<td>' + item.gender + '</td>';
	  	          	  		//rows += '<td>' + item.address + '</td>';
	  	          	  		//rows += '<td onclick="editAjaxData('+item.id+');" >edit</td>';
	  	          	  		//rows += '<td onclick="deleteAjaxData('+item.id+');" >Delete</td></tr>';
	  	          	  	  	
	  	          	  	  });
	            	  }else{
	            		  rows +='<tr></td colspan="3"><td></tr>';
	            	  }
	                  
	          	  	  $('#tblProducts').html(rows);
	              },
	              error:function(xmlHttpRequest, textStatus, errorThrown)
	              {
	                     alert("error");
	              }
	      });
	}
	</script>
</head>
<body>
	
	<a href="viewUniqueKey">back</a>
	<input id="search" type="type" name="search"  value=""/>
	<button id="click" onclick="searchName();"> SEARCH</button>
				
	<table class="GridOne">
	  <thead>
	  	<tr>
		  <th> Id </th>
		  <th> word </th>
		  <th> lang </th>
		  <th> object </th>
		  <!-- <th> address </th> -->
		  <!-- <th>Action</th> -->
		</tr>
	  </thead>
	  <tbody id="tblProducts">
	  
	  </tbody>
	</table>

</body>
</html>

```

# That's it... you are ready to run

> Right Click on Project > Run As > click on spring boot app > Ater that run the project and go browser and type in url ``http://localhost:8099/``


## Meta

Pratik Joshi - pratik.joshi7859@gmail.com

Distributed under the GPL V3.0 license. See ``LICENSE`` for more information.
