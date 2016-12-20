package com.alogic.xscript.lucene;

import java.util.Map;

import com.alogic.lucene.core.Indexer;
import com.alogic.xscript.ExecuteWatcher;
import com.alogic.xscript.Logiclet;
import com.alogic.xscript.LogicletContext;
import com.alogic.xscript.plugins.Segment;
import com.anysoft.util.BaseException;
import com.anysoft.util.IOTools;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

import org.apache.lucene.index.IndexWriter;

public class IdxWriter extends Segment {
	
	protected String pid = "$lucene";
	protected String cid = "$idx-writer";
	protected boolean create = false;

	public IdxWriter(String tag, Logiclet p) {
		super(tag, p);

		registerModule("idx-new", IndexNew.class);
		registerModule("idx-update", IndexUpdate.class);
		registerModule("idx-delete", IndexDelete.class);
	}
	
	
	public void configure(Properties p) {
		super.configure(p);
		
		pid = PropertiesConstants.getString(p, "pid", pid, true);
		cid = PropertiesConstants.getString(p, "cid", cid, true);
		create = PropertiesConstants.getBoolean(p, "create", create, true);
	}
	
	@Override
	protected void onExecute(Map<String, Object> root, 
			Map<String, Object> current, LogicletContext ctx, ExecuteWatcher watcher) {
		Indexer.Abstract indexer = ctx.getObject(pid);
		if(indexer == null) {
			throw new BaseException("core.no_luceneIndexer", "It must in a lucene context, check your script.");
		}
		//false为批量增加索引;true为覆盖
		IndexWriter indexWriter = indexer.newWriter(create);
		
		try {
			ctx.setObject(cid, indexWriter);
			super.onExecute(root, current, ctx, watcher);
		} finally {
			ctx.removeObject(cid);
			IOTools.close(indexWriter);
		}
	}
}
