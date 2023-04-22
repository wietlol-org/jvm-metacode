package metacode.core.parser.utils

object MetaCodeStringParser
{
	fun parse(code: String, enclosed: Boolean = true): String =
		if (enclosed)
		{
			if (code.startsWith("'").not() || code.endsWith("'").not())
				throw IllegalArgumentException("Invalid string found: '$code'")
			
			val content = code.substring(1, code.length - 1)
			
			parse(content)
		}
		else
		{
			parse(code)
		}
	
	private fun parse(content: String): String
	{
		val builder = StringBuilder(content.length)
		
		val iterator = content.iterator()
		
		while (iterator.hasNext())
		{
			when (val char = iterator.next())
			{
				'\\' -> readEscaped(iterator).also { builder.append(it) }
				'\'' -> throw IllegalArgumentException("Unexpected end of string found.")
				else -> builder.append(char)
			}
		}
		
		return builder.toString()
	}
	
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
				else -> throw IllegalArgumentException("Illegal escape sequence: '\\$next'")
			}
	
	private fun CharIterator.nextCharacter(): Char =
		if (hasNext())
			next()
		else
			throw IllegalArgumentException("Unexpected end of string found.")
}
