package com.alogic.xscript.lucene.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.lucene.search.Query;
import org.w3c.dom.Element;

import com.anysoft.util.Configurable;
import com.anysoft.util.Factory;
import com.anysoft.util.Properties;
import com.anysoft.util.XMLConfigurable;
import com.anysoft.util.XmlElementProperties;

/**
 * Filter Builder
 * 过滤器生成接口
 *
 */
public interface FilterBuilder extends XMLConfigurable,Configurable{
	
	/**
	 * 获取Filter实例
	 * 
	 * @return Filter实例
	 */
	public Query getFilter(Properties p);
	
	/**
	 * 获取过滤器中的组合查询参数值,BooleanClause.Occur三个枚举值MUST、MUST_NOT、SHOULD.
	 */
	public String getOccur();
	
	/**
	 * 虚基类
	 * 
	 *
	 */
	public abstract static class Abstract implements FilterBuilder{
		/**
		 * a logger of log4j
		 */
		protected static final Logger LOG = LoggerFactory.getLogger(FilterBuilder.class);
		
		@Override
		public void configure(Element e, Properties p) {
			Properties props = new XmlElementProperties(e,p);
            configure(props);
		}
		
		
	}
	
	/**
	 * 工厂类
	 * 
	 * @author duanyy
	 *
	 */
	public static class TheFactory extends Factory<FilterBuilder>{
		@Override
        public String getClassName(String module){
			if (module.indexOf(".") < 0){
				return "com.alogic.xscript.lucene.util.filter." + module;
			}
			return module;
		}		
	}
}
