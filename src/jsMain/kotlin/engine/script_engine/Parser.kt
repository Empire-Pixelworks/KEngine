package engine.script_engine

class Parser(private val tokens: MutableList<Token>)  {
    fun start(): List<Element> {
        val elements = elementList()

        if (tokens.isNotEmpty()) error("Unexpected tokens present: $tokens")
        else return elements
    }

    private fun elementList(): List<Element> {
        tailrec fun elements(elements: List<Element> = emptyList()): List<Element> =
            if (tokens.isEmpty()) elements
            else when (tokens.peek()) {
                is NewLine -> {
                    consume<NewLine>()
                    elements(elements)
                }
                else -> when (val maybeElement = element()){
                    null -> elements(elements)
                    else -> elements(elements + maybeElement)
                }
            }

        return elements()
    }

    private fun element(): Element? = when (tokens.peek()) {
        is Identifier -> item()
        is LBracket -> obj()
        else -> null
    }

    private fun obj(): Obj {
        consume<LBracket>()
        val identity = keyList()
        consume<RBracket>()

        tailrec fun getItems(items: List<Item> = emptyList()): List<Item> =
            if (tokens.isNotEmpty() && tokens.peek() is NewLine) {
                consume<NewLine>()
                if (tokens.peek() is Identifier) {
                    getItems(items + item())
                } else {
                    items
                }
            } else {
                items
            }

        return Obj(identity, getItems())
    }

    private fun keyList(): ObjIdentity {
        val identity = consume<Identifier>()
        return if (tokens.peek() is Dot) {
            consume<Dot>()
            val childIdentity = consume<Identifier>()
            ObjIdentity(childIdentity.text, identity.text)
        } else {
            ObjIdentity(identity.text)
        }
    }

    private fun item(): Item {
        val identity = consume<Identifier>()
        consume<Equals>()
        val value = elementValue()

        return Item(identity.text, value)
    }

    private fun elementValue(): Value = when (val token = tokens.pop()) {
            is Bool -> BoolVal(token.bool)
            is IntLiteral -> IntVal(token.int)
            is FloatLiteral -> FloatVal(token.float)
            is StringLiteral -> StringVal(token.text)
            is Identifier -> IdentityVal(token.text)
            is LBracket -> array()
            else -> error("Invalid token: $token")
        }

    private fun array(): Value {
        tailrec fun getElements(values: List<Value> = emptyList()): List<Value> =
            if (tokens.peek() is RBracket) {
                tokens.pop()
                values
            } else {
                val element = elementValue()
                if (tokens.peek() is Comma) {
                    consume<Comma>()
                }
                getElements(values + element)
            }

        return ArrayVal(getElements())
    }

    private inline fun <reified T: Token> consume(): T =
        if (tokens.peek() is T) tokens.pop() as T
        else error("unexpected Type: ${tokens.first()} (Expected ${T::class}")
}

fun MutableList<Token>.pop() = this.removeFirstOrNull() ?: error("Token list is empty")
fun MutableList<Token>.peek() = this.firstOrNull() ?: error("Token list is empty")

sealed class Value
data class BoolVal(val value: Boolean) : Value()
data class StringVal(val value: String) : Value()
data class IntVal(val value: Int) : Value()
data class FloatVal(val value: Float) : Value()
data class IdentityVal(val value: String): Value()
data class ArrayVal(val values: List<Value>) : Value()

sealed class Element
data class Item(val identity: String, val value: Value): Element()
data class Obj(val identity: ObjIdentity, val items: List<Item>, val children: List<Obj> = emptyList()): Element()
data class ObjIdentity(val identity: String, val parent: String? = null)

data class Script(val elements: List<Element>)