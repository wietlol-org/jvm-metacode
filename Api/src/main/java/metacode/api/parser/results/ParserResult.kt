package metacode.api.parser.results

import metacode.api.lexer.MetaCodeToken
import metacode.api.parser.data.models.RuleOption
import utils.common.Jsonable
import utils.common.collections.ManyValuesMap
import utils.common.toJsonString

interface ParserResult : Jsonable
{
	val startIndex: Int
	val endIndex: Int
	val tokens: List<MetaCodeToken>
	val option: RuleOption?
	val members: ManyValuesMap<CharSequence, ExpressionResult>
	
	val text: String
		get() = tokens.joinToString("") { it.text }
	
	fun toSimplifiedString(): String
	{
		val values = mutableListOf<String>()
		
		if (option != null)
			values.add(""""option": ${option?.name.toJsonString()}""")
		
		if (members.isEmpty())
			values.add(""""expression": ${tokens.joinToString("") { it.text }.toJsonString()}""")
		else
			values.add(""""members": {${members.entries.joinToString(", ") {
				"${it.key.toJsonString()}: [${it.value.joinToString(", ") { it.toSimplifiedString() }}]"
			}}}""")
		
		return """{${values.joinToString(", ")}}"""
	}
}
