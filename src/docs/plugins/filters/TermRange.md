TermrRange
===========

TermrRange用于字符串范围搜索。

### 实现类

com.alogic.xscript.lucene.util.filter.TermrRange

### 配置参数

| 编号 | 代码 | 说明 |
| ---- | ---- | ---- |
| 1 | field | 域名（可以为content、fileName等）,必要|
| 2 | lowerTerm | 关键字字符串下界 |
| 3 | upperTerm | 关键字字符串上界 | 
| 4 | includeLower | 是否包含下界（true,false） | 
| 5 | includeUpper | 是否包含下界（true,false） | 
| 6 | occur | 组合查询中的条件之间的关系，可以是BooleanClause.Occur三个枚举值MUST、MUST_NOT、SHOULD中的任意一个,必要  |

### 配置样例

例如：下面是一个TermRangeQuery类型的查询过滤器。

```xml
<filter module="TermrRange" occur="MUST" field="content" includeUpper="woman"/>

```