package metacode.core.parser

import metacode.core.data.Grammar
import metacode.api.lexer.MetaCodeToken
import metacode.api.parser.MetaCodeParser
import metacode.api.parser.results.ParserResult
import utils.common.Jsonable

class HeadFirstRecursiveParser(
	val grammar: Grammar
) : MetaCodeParser, Jsonable
{
	override fun parse(name: String, tokens: List<MetaCodeToken>): Sequence<ParserResult> =
		grammar.findMatches(name, tokens)

	override fun parseExhaustive(name: String, tokens: List<MetaCodeToken>): Sequence<ParserResult> =
		grammar
			.findMatches(name, tokens)
			.filter { it.endIndex == tokens.size }

	override fun toJson(): String =
		"""{"grammar":${grammar.toJson()}}"""

	override fun toString(): String =
		toJson()
}
