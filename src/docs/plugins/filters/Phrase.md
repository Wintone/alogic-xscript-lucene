Phrase
===========

Phrase用于短语查询,关键词值之间用‘|’分隔。

### 实现类

com.alogic.xscript.lucene.util.filter.Phrase

### 配置参数

| 编号 | 代码 | 说明 |
| ---- | ---- | ---- |
| 1 | field | 域名（可以为content、filename等）,必要|
| 2 | q | 关键字,必要 |
| 3 | slot | 关键字之间的间距，默认为0| 
| 4 | occur | 组合查询中的条件之间的关系，可以是BooleanClause.Occur三个枚举值MUST、MUST_NOT、SHOULD中的任意一个,必要 | 

### 配置样例

例如：下面是一个PhraseQuery类型的查询过滤器。

```xml
<filter module="Phrase" occur="MUST" field="content" q="hello|woman" slot="1" />

```