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
