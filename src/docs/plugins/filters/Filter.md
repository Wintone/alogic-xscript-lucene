Filter
======

Filter主要用于对lucene的查询结果进行过滤，主要应用于idx-query查询操作中。

当前支持的Filter包括：

- [Multi](Multi.md) --应用BooleanQuery进行组合查询时,条件之间的关系是BooleanClause.Occur三个枚举值MUST、MUST_NOT、SHOULD
- [QueryParse](QueryParse.md) --对单个域查询时创建查询query
- [TermFilter](TermFilter.md) --单个关键字域查询
- [MultiField](MultiField.md) --多域查询
- [Prefix](Prefix.md) --前缀字符查询,只需要指定前缀若干个字符preStr，以preStr开始的都会被匹配，比如”Lu”可以匹配”Luke” ,”Lucene”.
- [Wildcard](Wildcard.md) --通配符查询
- [NumericRange](NumericRange.md) --数字范围搜索
- [TermRange](TermRange.md) --字符串范围搜索
- [Phrase](Phrase.md) --短语查询

