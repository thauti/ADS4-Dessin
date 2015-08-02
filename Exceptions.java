class LexerException extends Exception {
	public LexerException(int line, int column) {
		super("Unexpected character at line " + line + " column " + column + ".");
	}
}

class ParserException extends Exception {
	public ParserException(String s) {
		super("'" + s + "' not expected.");
	}
}

class VariableNotDefinedException extends Exception {
	public VariableNotDefinedException(String name) {
		super("Variable '" + name + "' called before being defined.");
	}
}

class VariableNotInitializedException extends Exception {
	public VariableNotInitializedException(String name) {
		super("Variable '" + name + "' called before being initialized.");
	}
}

class VariableTwiceDefinedException extends Exception {
	public VariableTwiceDefinedException(String name) {
		super("Variable '" + name + "' defined twice.");
	}
}
