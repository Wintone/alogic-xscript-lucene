package com.alogic.xscript.lucene;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import com.alogic.xscript.ExecuteWatcher;
import com.alogic.xscript.Logiclet;
import com.alogic.xscript.LogicletContext;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

public class IndexUpdate extends IndexWriterOperation {
	
	protected String fileName = null;
	
	protected String content = null;

	public IndexUpdate(String tag, Logiclet p) {
		super(tag, p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void configure(Properties p) {
		super.configure(p);
		
		fileName = PropertiesConstants.getString(p, "id", fileName, true);
		content = PropertiesConstants.getString(p, "value", content, true);
	}
	@Override
	protected void onExecute(IndexWriter indexWriter, Map<String, Object> root, Map<String, Object> current,
			LogicletContext ctx, ExecuteWatcher watcher) {
		try {
			Document doc = new Document();
			doc.add(new TextField("fileName", fileName, Store.YES));
			doc.add(new TextField("content", content, Store.YES));			
			indexWriter.updateDocument(new Term("fileName", fileName), doc);
			indexWriter.commit();
			logger.info("Index update  Success! ");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
