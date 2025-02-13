package engine.script_engine

sealed class Token
data object NewLine: Token()
data object LBracket: Token()
data object RBracket: Token()
data object Equals: Token()
data object Comma: Token()
data object Dot: Token()

data class Identifier(val text: String): Token()
data class Bool(val bool: Boolean): Token()
data class IntLiteral(val int: Int): Token()
data class FloatLiteral(val float: Float): Token()
data class StringLiteral(val text: String): Token()
