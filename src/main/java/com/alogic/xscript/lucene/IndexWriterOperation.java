package com.alogic.xscript.lucene;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.IndexWriter;

import com.alogic.xscript.AbstractLogiclet;
import com.alogic.xscript.ExecuteWatcher;
import com.alogic.xscript.Logiclet;
import com.alogic.xscript.LogicletContext;
import com.anysoft.util.BaseException;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

/*
 * 
 * Lucene操作基类
 * 
 */
public abstract class IndexWriterOperation extends AbstractLogiclet {
	
	protected String pid = "$idx-writer";
	
	/**
	 * 返回结果的id
	 */
	protected String id;
	
	/**
	 * 数据集
	 */
	protected String tag = "data";

	public IndexWriterOperation(String tag, Logiclet p) {
		super(tag, p);
	}
	
	public void configure(Properties p) {
		super.configure(p);
		pid = PropertiesConstants.getString(p, "pid", pid, true);
		id = PropertiesConstants.getString(p, "id", "$" + getXmlTag(), true);
	}
	
	@Override
	protected void onExecute(Map<String, Object> root, 
			Map<String, Object> current, LogicletContext ctx, ExecuteWatcher watcher) {
		
		IndexWriter indexWriter = ctx.getObject(pid);
		if(indexWriter == null) {
			throw new BaseException("core.no_luceneIndexer", "It must in a lucene context, check your script.");
		}
		
		if(StringUtils.isNotEmpty(id)) {
			onExecute(indexWriter, root, current, ctx, watcher);
		}		
	}

	protected abstract void onExecute(IndexWriter indexWriter, Map<String, Object> root,
			Map<String, Object> current, LogicletContext ctx,
			ExecuteWatcher watcher);
}
