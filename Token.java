class Token{

    protected Sym symbol;

    public Token(Sym s){
	this.symbol=s;
    }

    public Sym getType(){
	return this.symbol;
    }
}

class IntToken extends Token{
    
    private int value;

    public IntToken(Sym symbol,int val){
	super(symbol);
	value = val;
    }

    public int getIntVal(){
	return this.value;
    }

    public String toString(){
	return symbol.toString()+" la valeur est :"+this.value;
    }
}
class VarToken extends Token {
    private String value;
    public VarToken(Sym s, String v) {
        super(s);
        value=v;
    }
    public String getValue(){
        return value;
    }
    public boolean isEqual(Token t){
        return (t.getType() != this.getType() && ((VarToken)t).getValue() == value);
    }
    public String toString(){
        return "Symbol : "+this.symbol+" | Value : "+this.value;
    }
}

class IdentToken extends Token{

    public String value;

    public IdentToken(Sym symbol,String val){
	super(symbol);
	this.value = val;
    }

    public String getStringVal(){
	return this.value;
    }
    
    public String toString(){
	return symbol.toString()+" l'identificateur est : "+this.value;
    }
    
    
}

