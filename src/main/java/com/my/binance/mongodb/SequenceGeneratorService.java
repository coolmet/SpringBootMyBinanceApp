package com.my.binance.mongodb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService
{
	
	private MongoOperations mongoOperations;
	
	@Autowired
	public SequenceGeneratorService(MongoOperations mongoOperations)
	{
		this.mongoOperations=mongoOperations;
	}
	
	public long generateSequence(String seqName)
	{
		if(seqName.equals(Characters.SEQUENCE_NAME))
		{
			DatabaseSequenceID1 counter=mongoOperations.findAndModify(new Query(Criteria.where("_id").is(seqName)),
			                                                          new Update().inc("seq",1),
			                                                          new FindAndModifyOptions().returnNew(true).upsert(true),
			                                                          DatabaseSequenceID1.class);
			return !Objects.isNull(counter)?counter.getSeq():1; 
		}
		else
			return 0;
		
	}
}
