package metacode.api.lexer.sources

data class CharToken(
	val char: Char,
	val line: Int,
	val column: Int
)
