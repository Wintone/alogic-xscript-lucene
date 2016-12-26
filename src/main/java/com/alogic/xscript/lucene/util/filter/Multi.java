package com.alogic.xscript.lucene.util.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.BooleanClause.Occur;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alogic.xscript.lucene.util.FilterBuilder;
import com.alogic.xscript.lucene.util.FilterBuilder.Abstract;
import com.anysoft.util.Properties;
import com.anysoft.util.XmlElementProperties;
import com.anysoft.util.XmlTools;

/**
 * Filter列表实现
 * 
 *
 */
public class Multi extends Abstract{
	/**
	 * 子FilterBuidler
	 */
	protected List<FilterBuilder> children = new ArrayList<FilterBuilder>();
	
	protected static HashMap<String, Occur> occurMap = new HashMap<String, BooleanClause.Occur> ();
	static {
		occurMap.put("MUST", BooleanClause.Occur.MUST);
		occurMap.put("SHOULD", BooleanClause.Occur.SHOULD);
		occurMap.put("MUST_NOT", BooleanClause.Occur.MUST_NOT);
	}
	
	@Override
	public Query getFilter(Properties p) {
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		for (FilterBuilder fb:children){
			Query filter = fb.getFilter(p);
			String occur = fb.getOccur();
			booleanQuery.add(filter, occurMap.get(occur));
		}
		return booleanQuery.build();
	}

	@Override
	public void configure(Properties p) {

	}
	
	@Override
	public void configure(Element e, Properties p) {
		Properties props = new XmlElementProperties(e,p);
		
		NodeList nodeList = XmlTools.getNodeListByPath(e, "filter");
		
		TheFactory factory = new TheFactory();
		
		for (int i = 0 ;i < nodeList.getLength() ; i ++){
			Node n = nodeList.item(i);
			if (n.getNodeType() != Node.ELEMENT_NODE){
				continue;
			}
			
			Element elem = (Element)n;
			
			try {
				FilterBuilder fb = factory.newInstance(elem, p, "module");
				if (fb != null){
					children.add(fb);
				}
			}catch (Exception ex){
				LOG.error("Can not create instance of FilterBuilder,xml:" + XmlTools.node2String(elem));
			}
		}
		
		configure(props);
	}

	@Override
	public String getOccur() {
		// TODO Auto-generated method stub
		return null;
	}		
}
