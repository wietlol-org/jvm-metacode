package metacode.core.parser

import metacode.api.parser.results.ParserResult
import metacode.core.LocalTestModule
import metacode.core.grammar.parseLexer
import metacode.core.grammar.parseParser
import metacode.core.lexer.sources.StreamSource
import metacode.core.lexer.sources.StringSource
import org.junit.Test

class JsonParserTests : LocalTestModule()
{
	@Test
	fun `assert that json can be lexed`() = unitTest {
		val json = """{ "prop1": "str", "prop2": [ 0, 1.0, true, null ] }"""
		
		val lexerDefinition = StreamSource("lexer", javaClass.getResourceAsStream("/examples/Json.lexer.mcg")!!)
		val lexer = parseLexer(lexerDefinition)
		
		val source = StringSource("data", json)
		val results = lexer.lex(source).toList()
		
		assertThat(results)
			.map { it.map { it.type to it.text } }
			.isEqualTo(
				listOf(
					"openBrace" to "{",
					"string" to "\"prop1\"",
					"colon" to ":",
					"string" to "\"str\"",
					"comma" to ",",
					"string" to "\"prop2\"",
					"colon" to ":",
					"openBracket" to "[",
					"number" to "0",
					"comma" to ",",
					"number" to "1.0",
					"comma" to ",",
					"boolean" to "true",
					"comma" to ",",
					"null" to "null",
					"closeBracket" to "]",
					"closeBrace" to "}",
				)
			)
	}
	
	@Test
	fun `assert that json can be parsed`() = unitTest {
		val json = """{ "prop1": "str", "prop2": [ 0, 1.0, true, null ] }"""
		
		val lexerDefinition = StreamSource("lexer", javaClass.getResourceAsStream("/examples/Json.lexer.mcg")!!)
		val lexer = parseLexer(lexerDefinition)
		val parserDefinition = StreamSource("parser", javaClass.getResourceAsStream("/examples/Json.parser.mcg")!!)
		val parser = parseParser(parserDefinition)
		
		val source = StringSource("data", json)
		val results = lexer.lex(source).toList()
		val tree = parser.parse("value", results).toList()
		val model = convertTreeToValue(tree.first())
		
		assertThat(model)
			.isEqualTo(mapOf(
				"prop1" to "str",
				"prop2" to listOf(
					0.0,
					1.0,
					true,
					null,
				),
			))
	}
	
	// returns Map<String, Any>
	private fun convertTreeToValue(result: ParserResult): Any? =
		when (result.option!!.name)
		{
			"object" -> convertTreeToObject(result.members["value"].single())
			"array" -> convertTreeToArray(result.members["value"].single())
			"string" -> convertString(result.members["value"].single())
			"number" -> convertNumber(result.members["value"].single())
			"boolean" -> convertBoolean(result.members["value"].single())
			"null" -> null
			else -> TODO()
		}
	
	private fun convertTreeToObject(result: ParserResult): Map<String, Any?> =
		result.members["properties"].associate {
			val key = convertString(it.members["name"].single())
			val value = convertTreeToValue(it.members["value"].single())
			key to value
		}
	
	private fun convertTreeToArray(result: ParserResult): List<Any?> =
		result.members["elements"]
			.map { convertTreeToValue(it) }
	
	private fun convertNumber(result: ParserResult): Double =
		result.tokens
			.single()
			.text
			.toDouble()
	
	private fun convertBoolean(result: ParserResult): Boolean =
		result.tokens
			.single()
			.text
			.toBoolean()
	
	private fun convertString(result: ParserResult): String =
		result.tokens
			.single()
			.text
			.run { substring(1, length - 1) }
	
	interface JsonValue<T>
	{
		val value: T
	}
	
	@JvmInline
	value class JsonString(override val value: String) : JsonValue<String>
	
	@JvmInline
	value class JsonNumber(override val value: Double) : JsonValue<Double>
	
	@JvmInline
	value class JsonBoolean(override val value: Boolean) : JsonValue<Boolean>
	
	@JvmInline
	value class JsonArray(override val value: List<JsonValue<*>>) : JsonValue<List<JsonValue<*>>>
	
	@JvmInline
	value class JsonObject(override val value: Map<String, JsonValue<*>>) : JsonValue<Map<String, JsonValue<*>>>
	
	@JvmInline
	value class JsonNull(override val value: Nothing? = null) : JsonValue<Nothing?>
}
