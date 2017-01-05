idx-query
========

idx-query用于执行查询索引。

分别有QueryParser、MultiFieldQueryParser、TermQuery、PrefixQuery、WildcardQuery共5种查询类型。

必须在idx-reader语句内使用。


### 实现类

com.alogic.xscript.lucene.IndexQuery


### 配置参数

| 编号 | 代码 | 说明 |
| ---- | ---- | ---- |
| 1 | pid | idx-reader的上下文对象id,缺省为$idx-reader | 
| 2 | type | 需要采用的查询类型,值为{QueryParser、MultiFieldQueryParser、TermQuery、PrefixQuery、WildcardQuery}中一个，必要 | 
| 3 | field | 域名（可以为content、filename等）,必要| 
| 4 | q | 关键字,必要 | 

备注：
[过滤器Filer使用方法介绍](filters/Filter.md)

### 案例

下面是一个创建idx-query的案例：

```xml
<?xml version="1.0"?>
<script>
    <using xmlTag="lucene" module="com.alogic.xscript.lucene.IndexConf"/>

    <lucene indexDir="D:\\luceneIndex" analyzer="StandardAnalyzer">
    	 <idx-reader>
	        <!-- 在这里你可以使用alogic-xcript-lucene提供的语句
	        	第一种情况，没有过滤器的简单查询
	        	第二种情况，过滤器查询
	        -->
       		<!-- <idx-query type="TermQuery" field="content" q="man"/> -->
        	<idx-query>
        		<filter module="Multi">
        			<filter module="QueryParse"  analyzer="StandardAnalyzer" occur="MUST" field="content" q="woman"/>
        			<filter module="QueryParse"  analyzer="StandardAnalyzer" occur="MUST_NOT" field="content" q="man"/>
        		</filter>
        	</idx-query>
        </idx-reader>
    </lucene>
</script>
```
