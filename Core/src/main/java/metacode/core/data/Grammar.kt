package metacode.core.data

import metacode.core.parser.context.MapMatchContext
import metacode.core.parser.data.models.DefaultRuleParserExpression
import metacode.api.lexer.MetaCodeToken
import metacode.api.parser.data.models.ParserRule
import metacode.api.parser.data.models.PriorityTreeRule
import metacode.api.parser.results.ParserResult
import utils.common.Jsonable
import utils.common.toJsonString

class Grammar(
	val name: CharSequence,
	val rules: Collection<ParserRule>
) : Jsonable
{
	private val lookupTable =
		rules
			.asSequence()
			.flatMap {
				if (it is PriorityTreeRule)
					it.compileTree().asSequence()
				else
					sequenceOf(it)
			}
			.map { Pair(it.name.lowercase(), it) }
			.toMap()
	
	fun findMatches(name: CharSequence, tokens: List<MetaCodeToken>): Sequence<ParserResult> =
		DefaultRuleParserExpression(name.toString())
			.findMatches(MapMatchContext(lookupTable), tokens, 0)
	
	override fun toJson(): String =
		"""{"name":${name.toJsonString()},"rules":${rules.toJsonString()}}"""
	
	override fun toString(): String =
		toJson()
}
