package metacode.core.lexer

import metacode.core.LocalTestModule
import metacode.core.lexer.sources.StringSource
import org.junit.Test

class LexerTests : LocalTestModule()
{
	@Test
	fun `assert that an empty lexer can lex an empty input`() = unitTest {
		val input = ""
		val source = StringSource("text", input)
		val lexer = HeadFirstLexer(emptyList())
		
		val result = lexer.lex(source).toList()
		
		assertThat(result)
			.isEqualTo(emptyList())
	}
	
	@Test
	fun `assert that an empty lexer cannot match input`() = unitTest {
		val input = "foo"
		val source = StringSource("text", input)
		val lexer = HeadFirstLexer(emptyList())
		
		assertThrows<IllegalArgumentException> { lexer.lex(source).toList() }
			.property { ::message }
			.isEqualTo("Invalid character f (66) found!")
	}
}
