package com.alogic.xscript.lucene;

import java.util.Map;

import org.apache.lucene.index.IndexWriter;

import com.alogic.xscript.ExecuteWatcher;
import com.alogic.xscript.Logiclet;
import com.alogic.xscript.LogicletContext;
import com.alogic.xscript.lucene.util.BlobIndexBuilder;
import com.alogic.xscript.lucene.util.TextIndexBuilder;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

public class IndexNew extends IndexWriterOperation {
	
	protected String type = "";
	protected String id = "";
	protected String value = "";

	public IndexNew(String tag, Logiclet p) {
		super(tag, p);
	}

	@Override
	public void configure(Properties p) {
		super.configure(p);

		type = PropertiesConstants.getString(p, "type", type, true);
		id = PropertiesConstants.getString(p, "id", id, true);
		value = PropertiesConstants.getString(p, "value", value, true);
	}
	
	@Override
	protected void onExecute(IndexWriter indexWriter, Map<String, Object> root, Map<String, Object> current,
			LogicletContext ctx, ExecuteWatcher watcher) {	
		if(type.equals("text")) {
			new TextIndexBuilder(id, value).addDocument(indexWriter);
		} else if(type.equals("Blob")) {
			new BlobIndexBuilder(id,value).addDocument(indexWriter);
		}	
	}

}
