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

public class IndexUpdate extends IndexWriterOperation {
	
	protected String file = null;
	
	protected String contentValue = null;

	public IndexUpdate(String tag, Logiclet p) {
		super(tag, p);
	}

	@Override
	public void configure(Properties p) {
		super.configure(p);
		
		file = p.GetValue("id", file, false, true);
		contentValue = p.GetValue("value", contentValue, false, true);
	}
	@Override
	protected void onExecute(IndexWriter indexWriter, Map<String, Object> root, Map<String, Object> current,
			LogicletContext ctx, ExecuteWatcher watcher) {
		try {
			String fileName = ctx.transform(file);
			String content = ctx.transform(contentValue);
			Document doc = new Document();
			doc.add(new TextField("filename", fileName, Store.YES));
			doc.add(new TextField("content", content, Store.YES));			
			indexWriter.updateDocument(new Term("filename", fileName), doc);
			indexWriter.commit();
			logger.info("Index update Success! ");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
