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
