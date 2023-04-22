package metacode.api.lexer

import metacode.api.lexer.sources.CodeSource

interface MetaCodeLexer
{
	fun lex(source: CodeSource): Sequence<MetaCodeToken>
}
