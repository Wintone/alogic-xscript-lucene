Multi
===

Multi用于连接多个Filter。
```
Filter应用BooleanQuery进行组合查询,条件之间的关系是BooleanClause.Occur三个枚举值MUST、MUST_NOT、SHOULD.
它们的组合关系代表的意思如下: 
1、MUST和MUST表示“与”的关系，即“并集”。 
2、MUST和MUST_NOT前者包含后者不包含。 
3、MUST_NOT和MUST_NOT没意义 
4、SHOULD与MUST表示MUST，SHOULD失去意义； 
5、SHOUlD与MUST_NOT相当于MUST与MUST_NOT。 
6、SHOULD与SHOULD表示“或”的概念。 
```
### 实现类

com.alogic.xscript.lucene.util.filter.Multi

### 配置参数

无

### 配置样例

例如：选取content域中含有“woman”关键字且不含有“man”关键字的文档

```xml

<idx-query>
	<filter module="Multi">
		<filter module="QueryParse" occur="MUST" field="content" q="woman"/>
		<filter module="QueryParse" occur="MUST_NOT" field="content" q="man"/>
	</filter>
</idx-query>
	
```