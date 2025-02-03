package engine.core

import kotlinx.browser.window
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent

object Input {
    enum class Key(val key: String) {
        ZERO("0"),
        ONE("1"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        A("a"),
        B("b"),
        C("c"),
        D("d"),
        E("e"),
        F("f"),
        G("g"),
        H("h"),
        I("i"),
        J("j"),
        K("k"),
        L("l"),
        M("m"),
        N("n"),
        O("o"),
        P("p"),
        Q("q"),
        R("r"),
        S("s"),
        T("t"),
        U("u"),
        V("v"),
        W("w"),
        X("x"),
        Y("y"),
        Z("z"),
        UPPERCASE_A("A"),
        UPPERCASE_B("B"),
        UPPERCASE_C("C"),
        UPPERCASE_D("D"),
        UPPERCASE_E("E"),
        UPPERCASE_F("F"),
        UPPERCASE_G("G"),
        UPPERCASE_H("H"),
        UPPERCASE_I("I"),
        UPPERCASE_J("J"),
        UPPERCASE_K("K"),
        UPPERCASE_L("L"),
        UPPERCASE_M("M"),
        UPPERCASE_N("N"),
        UPPERCASE_O("O"),
        UPPERCASE_P("P"),
        UPPERCASE_Q("Q"),
        UPPERCASE_R("R"),
        UPPERCASE_S("S"),
        UPPERCASE_T("T"),
        UPPERCASE_U("U"),
        UPPERCASE_V("V"),
        UPPERCASE_W("W"),
        UPPERCASE_X("X"),
        UPPERCASE_Y("Y"),
        UPPERCASE_Z("Z"),
        CMD_WINDOW("Meta"),
        ALT_OPTION("Alt"),
        CONTROL("Control"),
        SHIFT("Shift"),
        ESCAPE("Escape"),
        TAB("Tab"),
        BACK_TIC("`"),
        TILDE("~"),
        EXCLAMATION_MARK("!"),
        AT_SYMBOL("@"),
        HASH("#"),
        MONEY("$"),
        PERCENT("%"),
        CARROT("^"),
        AMPERSAND("&"),
        WILD_CARD("*"),
        LEFT_PARENTHESIS("("),
        RIGHT_PARENTHESIS(")"),
        UNDERSCORE("_"),
        DASH("-"),
        PLUS("+"),
        EQUALS("="),
        BACKSPACE("Backspace"),
        F1("F1"),
        F2("F2"),
        F3("F3"),
        F4("F4"),
        F5("F5"),
        F6("F6"),
        F7("F7"),
        F8("F8"),
        F9("F9"),
        F10("F10"),
        F11("F11"),
        F12("F12"),
        ARROW_LEFT("ArrowLeft"),
        ARROW_DOWN("ArrowDown"),
        ARROW_RIGHT("ArrowRight"),
        ARROW_UP("ArrowUp"),
        ENTER("Enter"),
        LEFT_BRACE("{"),
        RIGHT_BRACE("}"),
        LEFT_BRACKET("["),
        RIGHT_BRACKET("]"),
        COLON(":"),
        SEMI_COLON(";"),
        DOUBLE_QUOTE("\""),
        SINGLE_QUOTE("'"),
        LESS_THAN("<"),
        GREATER_THAN(">"),
        COMMA(","),
        PERIOD("."),
        QUESTION("?"),
        FORWARD_SLASH("/"),
        BACK_SLASH("\\"),
        BAR("|"),
        SPACE(" "),
    }
    data class KeyState(
        var previousState: Boolean = false,
        var isKeyPressed: Boolean = false,
        var isKeyClicked: Boolean = false
    )

    private val keys = Key.entries.associate {
        it.key to KeyState()
    }

    init {
        window.addEventListener("keyup", ::onKeyUp)
        window.addEventListener("keydown", ::onKeyDown)
    }

    private fun onKeyDown(event: Event) {
        if (event is KeyboardEvent) {
            keys[event.key]?.isKeyPressed = true
        }
    }

    private fun onKeyUp(event: Event) {
        if (event is KeyboardEvent) {
            keys[event.key]?.isKeyPressed = false
        }
    }

    fun update() {
        keys.forEach { (_, state) ->
            state.isKeyClicked = (!state.previousState) && state.isKeyPressed
            state.previousState = state.isKeyPressed
        }
    }

    fun isKeyPressed(key: Key) = keys[key.key]?.isKeyPressed ?: false
    fun isKeyClicked(key: Key) = keys[key.key]?.isKeyClicked ?: false

}