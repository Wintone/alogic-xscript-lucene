lucene
======

lucene用于创建或获取一个lucene索引配置上下文。

lucene是一个segment，支持子语句，所有子语句将可以通过上下文对象使用该索引配置，该索引配置也只在子语句范围内有效。


### 实现类

com.alogic.xscript.lucene.IndexConf


### 配置参数

| 编号 | 代码 | 说明 |
| ---- | ---- | ---- |
| 1 | cid | lucene的上下文对象id,缺省为$lucene | 
| 2 | indexDir | 确定索引文件存储的位置 |
| 3 | analyzer | 使用词法分析器Analyzer |



### 案例

下面是一个创建lucene的案例：

```xml
<script>
    <using xmlTag="lucene" module="com.alogic.xscript.lucene.IndexConf"/>

    <lucene indexDir="D:\\luceneIndex" analyzer="StandardAnalyzer">
    	<!-- create为false即批量增加索引,默认为false;
    		true即新建索引，如果是第一次用lucene建立索引，则必须设置为true -->
    	<idx-writer create="true">
	        <!-- 在这里你可以使用alogic-xcript-lucene提供的语句
	           type有text和Blob两种类型
	        -->        
	        <idx-new type="text" id="text1" value="hello,man!"/>  
        </idx-writer>   
    	 <idx-reader>
	        <!-- 在这里你可以使用alogic-xcript-lucene提供的语句
	        	第一种情况，没有过滤器的简单查询
	        	第二种情况，过滤器查询
	        -->
       		<!-- <idx-query type="TermQuery" field="content" q="man"/> -->
        	<idx-query>
        		<filter module="Multi">
        			<filter module="QueryParse" occur="MUST" field="content" q="woman"/>
        			<filter module="QueryParse" occur="MUST_NOT" field="content" q="man"/>
        		</filter>
        	</idx-query>
        </idx-reader>
    </lucene>
</script>
```
