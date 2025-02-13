package engine.script_engine

object Lexer {
    tailrec fun tkz(input: String, tokens: List<Token> = emptyList()): List<Token> = if (input.isEmpty()) {
        tokens
    } else if (isWhitespace(input)) {
        tkz(input.drop(1), tokens)
    } else if (isNewLine(input)) {
        tkz(input.drop(1), tokens + NewLine)
    } else when (val result = lexTkn(input)) {
        null -> throw Exception("no match for $input")
        else -> {
            tkz(input.drop(result.second), tokens + result.first)
        }
    }

    private fun lexTkn(input: String): Pair<Token, Int>? {
        singleCharTkn(input)?.let { return it to 1 }
        signedTkn(input)?.let { return it }
        floatTkn(input)?.let { return it }
        intTkn(input)?.let { return it }
        boolTkn(input)?.let { return it }
        identityTkn(input)?.let { return it }
        stringTkn(input)?.let { return it }
        return null
    }

    private fun singleCharTkn(input: String): Token? = when (input[0]) {
        '[' -> LBracket
        ']' -> RBracket
        '=' -> Equals
        ',' -> Comma
        '.' -> Dot
        else -> null
    }

    private fun boolTkn(input: String): Pair<Bool, Int>? = when {
        input.startsWith("true") -> Bool(true) to 4
        input.startsWith("false") -> Bool(false) to 5
        else -> null
    }

    private fun identityTkn(input: String): Pair<Identifier, Int>? {
        tailrec fun go(input: String, cnt: Int): Int = when {
            input.isEmpty() -> cnt
            input[cnt] in 'a'..'z' ||
                input[cnt] in 'A'..'Z' ||
                input[cnt] == '_' ||
                input[cnt] == '-' -> go(input, cnt + 1)
            else -> cnt
        }
        val idCnt = go(input, 0)
        return if (idCnt > 0) Identifier(input.take(idCnt)) to idCnt else null
    }

    private fun stringTkn(input: String): Pair<StringLiteral, Int>? {
        tailrec fun go(input: String, cnt: Int): Int = when {
            input.isEmpty() || input[cnt] == '"' || input[cnt] == '\n' -> cnt
            else -> go(input, cnt + 1)
        }
        return if (input[0] == '"') {
            val stringCnt = go(input, 1)
            if (stringCnt > 0) StringLiteral(input.substring(1, stringCnt)) to stringCnt+1 else null
        } else {
            null
        }
    }

    private fun intTkn(input: String): Pair<IntLiteral, Int>? {
        val i = digit(input)
        return if (i > 0) IntLiteral(input.take(i).toInt()) to i else null
    }

    private fun floatTkn(input: String): Pair<FloatLiteral, Int>? {
        val f = digit(input)
        if (f == 0 || f >= input.length || input[f] != '.') return null
        val frac = digit(input.drop(f+1))

        return if (frac == 0) {
            null
        } else {
            FloatLiteral(input.take(f + 1 + frac).toFloat()) to f + 1 + frac
        }
    }

    private fun signedTkn(input: String): Pair<Token, Int>? {
        if (input[0] != '-') return null
        val nums = input.drop(1)
        floatTkn(nums)?.let { (tkn, cnt) ->
            return tkn.copy(float = -tkn.float) to cnt+1
        }
        intTkn(nums)?.let { (tkn, cnt) ->
            return tkn.copy(int = -tkn.int) to cnt+1
        }
        return null
    }

    private fun digit(input: String): Int {
        tailrec fun go(input: String, cnt: Int): Int = when {
            input.isEmpty() || !input[cnt].isDigit() -> cnt
            else -> go(input, cnt + 1)
        }
        return go(input, 0)
    }

    private fun isNewLine(input: String) = input.singleCharMatch('\n')
    private fun isWhitespace(input: String) = input.singleCharMatch(' ')

    private fun String.singleCharMatch(input: Char) = this[0] == input
}