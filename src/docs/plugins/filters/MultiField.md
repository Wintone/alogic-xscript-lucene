MultiField
===========

MultiField用于用于多域查询,每个域之间用‘|’分隔。。。

### 实现类

com.alogic.xscript.lucene.util.filter.MultiField

### 配置参数

| 编号 | 代码 | 说明 |
| ---- | ---- | ---- |
| 1 | field | 域名（可以为content、fileName等）,必要|
| 2 | q | 关键字,必要 |
| 3 | analyzer | 词法分析器Analyzer类型,必要| 
| 4 | occur | 组合查询中的条件之间的关系，可以是BooleanClause.Occur三个枚举值MUST、MUST_NOT、SHOULD中的任意一个.,必要 | 

### 配置样例

例如：下面是一个MultiFieldQueryParser类型的查询过滤器。

```xml
<filter module="MultiField" analyzer="StandardAnalyzer" occur="MUST" field="content" q="woman"/>

```