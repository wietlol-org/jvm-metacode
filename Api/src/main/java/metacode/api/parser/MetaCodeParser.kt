package metacode.api.parser

import metacode.api.lexer.MetaCodeToken
import metacode.api.parser.results.ParserResult

interface MetaCodeParser
{
	fun parse(name: String, tokens: List<MetaCodeToken>): Sequence<ParserResult>
	
	fun parseExhaustive(name: String, tokens: List<MetaCodeToken>): Sequence<ParserResult>
}
