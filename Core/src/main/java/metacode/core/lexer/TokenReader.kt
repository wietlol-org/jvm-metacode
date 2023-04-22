package metacode.core.lexer

import metacode.api.lexer.sources.CharToken
import metacode.api.lexer.sources.CodeSource
import java.io.Closeable
import java.util.*

class TokenReader(
	val source: CodeSource
) : Closeable
{
	private val lineFeedChar = 0x0A
	private val tabChar = 0x09
	private val tabSize = 4
	
	private val reader = source.read().reader()
	private var line = 1 // 1 based lines
	private var column = 1 // 1 based columns
	private val head: Queue<CharToken> = ArrayDeque()
	
	fun peek(): CharToken? =
		head.peek()
			?: readNext()?.also { head.add(it) }
	
	fun read(): CharToken? =
		head.poll()
			?: readNext()
	
	private fun readNext(): CharToken?
	{
		val code = reader.read()
		return if (code == -1)
			null
		else
			CharToken(
				code.toChar(),
				line,
				column++
			)
				.also {
					if (code == lineFeedChar)
					{
						line++
						column = 1 // 1 based columns
					}
					else if (code == tabChar)
					{
						column = column.ceil(tabSize) + 1 // 1 based columns
					}
				}
	}
	
	// https://stackoverflow.com/a/9303678/2764866
	private fun Int.ceil(steps: Int): Int =
		(this + steps - 1) / steps * steps
	
	fun readToList(): List<CharToken> =
		generateSequence { read() }
			.toList()
	
	override fun close() =
		reader.close()
}
