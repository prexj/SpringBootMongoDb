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
