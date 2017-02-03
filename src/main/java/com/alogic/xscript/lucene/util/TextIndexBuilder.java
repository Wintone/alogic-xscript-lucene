package com.alogic.xscript.lucene.util;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.slf4j.LoggerFactory;

public class TextIndexBuilder {
	
	protected static final Logger logger =  (Logger) LoggerFactory.getLogger(TextIndexBuilder.class);

	
	protected String fileName = null;
	protected String content = null;
	
	public TextIndexBuilder(String id, String value) {
		fileName = id;
		content = value;
	}

	protected String getFileName() {
		return fileName;
	}
	
	protected String getContent() {
		return content;
	}
	
	public void addDocument(IndexWriter writer) {
		try {
			Document doc = new Document();			
			doc.add(new StringField("fileName", fileName, Field.Store.YES));
			doc.add(new TextField("content", content, Field.Store.YES));			
			writer.addDocument(doc);
			writer.commit();
//			logger.info("TextIndexBuilder  Success! " + doc);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

}
