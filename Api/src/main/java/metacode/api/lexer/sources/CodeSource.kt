package metacode.api.lexer.sources

import utils.common.Jsonable
import java.io.InputStream

interface CodeSource : Jsonable
{
	fun read(): InputStream
}
