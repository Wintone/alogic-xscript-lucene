package com.alogic.xscript.lucene;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import com.alogic.xscript.ExecuteWatcher;
import com.alogic.xscript.Logiclet;
import com.alogic.xscript.LogicletContext;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

public class IndexDelete extends IndexWriterOperation {
	
	protected String field = null;
	//关键字
	protected String q = null;

	public IndexDelete(String tag, Logiclet p) {
		super(tag, p);
		// TODO Auto-generated constructor stub		
	}
	
	@Override
	public void configure(Properties p) {
		super.configure(p);
		
		field = PropertiesConstants.getString(p, "field", field, true);
		q = PropertiesConstants.getString(p, "q", q, true);
	}

	@Override
	protected void onExecute(IndexWriter indexWriter, Map<String, Object> root, Map<String, Object> current,
			LogicletContext ctx, ExecuteWatcher watcher) {
		try {
			indexWriter.deleteDocuments(new Term(field, q));
			indexWriter.commit();
			logger.info("Index delete  Success! ");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
