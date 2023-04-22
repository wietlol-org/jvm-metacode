package metacode.core.parser.utils

object MetaCodeCharParser
{
	fun parse(code: String): Char =
		if (code.first() != '\\')
			code.single()
		else
			readEscaped(code.substring(1).iterator())
	
	private fun readEscaped(iterator: CharIterator): Char =
		if (iterator.hasNext().not())
			throw IllegalArgumentException("Incomplete escape sequence found.")
		else
			when (val next = iterator.next())
			{
				'\'' -> '\''
				'\\' -> '\\'
				't' -> '\t'
				'r' -> '\r'
				'n' -> '\n'
				'b' -> '\b'
				'u' ->
				{
					Integer.parseInt(String(charArrayOf(
						iterator.nextCharacter(),
						iterator.nextCharacter(),
						iterator.nextCharacter(),
						iterator.nextCharacter()
					)), 16).toChar()
				}
				else -> next
			}
	
	private fun CharIterator.nextCharacter(): Char =
		if (hasNext())
			next()
		else
			throw IllegalArgumentException("Unexpected end of string found.")
	
	private fun CharIterator.assertEnd()
	{
		if (hasNext())
			throw IllegalArgumentException("Unexpected character in string found (expected end of string).")
	}
}
