package com.alogic.xscript.lucene;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import com.alogic.xscript.ExecuteWatcher;
import com.alogic.xscript.Logiclet;
import com.alogic.xscript.LogicletContext;
import com.anysoft.util.Properties;

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
		
		field = p.GetValue("field", field, false, true);
		q = p.GetValue("q", q, false, true);
	}

	@Override
	protected void onExecute(IndexWriter indexWriter, Map<String, Object> root, Map<String, Object> current,
			LogicletContext ctx, ExecuteWatcher watcher) {
		try {
			String fieldValue = ctx.transform(field);
			String qStr = ctx.transform(q);
			indexWriter.deleteDocuments(new Term(fieldValue, qStr));
			indexWriter.commit();
			logger.info("Index delete  Success! ");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
