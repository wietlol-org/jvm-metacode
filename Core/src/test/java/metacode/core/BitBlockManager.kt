package metacode.core

import bitblock.codegenerator.BitModuleProcessor
import bitblock.codegenerator.generators.kotlin.BitModuleKotlinGenerator
import bitblock.core.BitSchemaBuilder
import metacode.core.lexer.data.MetaCodeLexer
import metacode.core.parser.data.MetaCodeParser
import org.junit.Test
import java.io.File

class BitBlockManager
{
	@Test
	fun processBitModule()
	{
		BitModuleProcessor.processBitModule(
			File("../Api/src/main/resources/metacode/api/lexer/MetaCodeLexer.bitmodule"),
			BitModuleKotlinGenerator()
		)
		BitModuleProcessor.processBitModule(
			File("../Api/src/main/resources/metacode/api/parser/MetaCodeParser.bitmodule"),
			BitModuleKotlinGenerator()
		)
	}

	@Test
	fun generateBitSchema()
	{
		BitSchemaBuilder.buildSchema(
			File("../Api/src/main/resources/metacode/api/lexer/MetaCodeLexer.bitschema"),
			listOf(MetaCodeLexer),
		)
		BitSchemaBuilder.buildSchema(
			File("../Api/src/main/resources/metacode/api/parser/MetaCodeParser.bitschema"),
			listOf(MetaCodeParser),
		)
	}
}
