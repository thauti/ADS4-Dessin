import java.io.*;

class Parser {

    protected LookAhead1 reader;

    public Parser(LookAhead1 r) {
	reader = r;
    }

    public void term(Sym s) throws Exception{
	reader.eat(s);
    }

 //declarations → Var identificateur ; declarations | ε
    public Declaration nontermDecl() throws Exception{
	Declaration d;
	if(reader.check(Sym.VAR)){
	    term(Sym.VAR);
	    String s = reader.getStringValue();
	    term(Sym.IDENT);
	    term(Sym.CONCAT);
	    d = new Declaration(s);
	    d.setSuivant(nontermDecl());
	    return d;
	} else if(reader.check(Sym.AVANCE) || reader.check(Sym.TOURNE)|| reader.check(Sym.BasPinceau) || reader.check(Sym.HautPinceau) || reader.check(Sym.IDENT) || reader.check(Sym.DEBUT)) {
	    return null;
	}
	else{
            Main.fen.log("[Erreur] Can't eat symbol :( " +reader.getNext());
	    throw new Exception("cannot reduce Declarations");
	}
    }
    //programme → declarations instruction
    public Program nontermProg() throws Exception {
	if (reader.check(Sym.VAR) || reader.check(Sym.EOF)){
	    Declaration d = nontermDecl();
	    Instruction inst = nontermInstr();

	    return new Program(d,inst);
	} else {
            Main.fen.log("[Erreur] Can't eat symbol :( " +reader.getNext());
	    throw new Exception("cannot reduce programme");
	}
    }

   
    /* instruction → Avance expression | Tourne expression | BasPinceau | HautPinceau | identificateur = expression | Debut blocInstruction Fin */

    public Instruction nontermInstr() throws Exception,ParserException{
	Expression e = null;
	if(reader.check(Sym.AVANCE)){
	    term(Sym.AVANCE);
	    e = nontermExpr();
	    return new Avance(e);
	} 
	else if (reader.check(Sym.TOURNE)){
	    term(Sym.TOURNE);
	    e = nontermExpr();
	    return new Tourne(e);
	}
	else if (reader.check(Sym.BasPinceau)){
	    term(Sym.BasPinceau);
	    return new BasPinceau();
	}
	else if (reader.check(Sym.HautPinceau)){
	    term(Sym.HautPinceau);
	    return new HautPinceau();
		
	}
	else if (reader.check(Sym.COLOR))
	{
		term(Sym.COLOR);
		String c = reader.getStringValue();
		term(Sym.IDENT);
		return new ColorInstr(c);
	}
	else if (reader.check(Sym.TAILLE))
	{
		term(Sym.TAILLE);
		Expression f = nontermExpr();;
		return new SizeInstr(f);
	}
	else if(reader.check(Sym.SI))
	{
		term(Sym.SI);
		Expression f = nontermExpr();
		term(Sym.ALORS);
		InstructionList i = nontermBlocInstr();
		term(Sym.SINON);
		InstructionList is =nontermBlocInstr();
		return new Condition(f,i,is);	
	}
	else if(reader.check(Sym.TANTQUE))
	{
		term(Sym.TANTQUE);
		Expression f = nontermExpr();
		term(Sym.FAIRE);
		InstructionList i = nontermBlocInstr();
		return new Boucle(f,i);
	}
	else if (reader.check(Sym.IDENT)){
	    String varName = reader.getStringValue();
	    term(Sym.IDENT);
	    term(Sym.EQ);
	    Expression ex = nontermExpr();
	    return new Assignment(varName,ex);
	}
	else if (reader.check(Sym.DEBUT)){
	    term(Sym.DEBUT);
	    InstructionList il = nontermBlocInstr();
	    term(Sym.FIN);
		
	    return new InstructionChain(il.first(), il.next());
	}
	else {
            Main.fen.log("[Erreur] Can't eat symbol : " +reader.getNext());
	    throw new ParserException(reader.getString());	
	}	
    }       
    //blocInstruction → instruction ; blocInstruction | ε
    public InstructionList nontermBlocInstr() throws Exception{
	if(reader.check(Sym.DEBUT) || reader.check(Sym.IDENT) || reader.check(Sym.HautPinceau) || reader.check(Sym.BasPinceau) || reader.check(Sym.COLOR) || reader.check(Sym.TOURNE) || reader.check(Sym.SI) || reader.check(Sym.TANTQUE)|| reader.check(Sym.AVANCE) ||reader.check(Sym.TAILLE)) {
	    Instruction i = nontermInstr();
	    term(Sym.CONCAT);
	    InstructionList il = nontermBlocInstr();
	    return new Seq(i,il);
	}
	else if (reader.check(Sym.FIN) || reader.check(Sym.SINON) ||reader.check(Sym.CONCAT)){
	    return new Nil();
	}
	else {
            Main.fen.log("[Erreur] Can't eat symbol : " +reader.getNext());
	    throw new Exception("Cannot reduce blocInstruction");
	}
    }
    //expression → nombre expressionSuite | identificateur expressionSuite | ( expression ) expressionSuite

    public Expression nontermExpr() throws Exception,ParserException{
	Expression exp;
	if (reader.check(Sym.INT)){
	    int i = reader.getIntValue(); 
	    exp = new Int(i);
	    term(Sym.INT);
	    Expression es = nontermExprSuite(exp);
	    return es;
	}
	else if (reader.check(Sym.IDENT)){
	    String var = reader.getStringValue();
	    exp = new Var(var);
	    this.term(Sym.IDENT);
	    Expression es = nontermExprSuite(exp);
	    return es;
	}
	else if(reader.check(Sym.PARG)){
	    this.term(Sym.PARG);
	    Expression e = nontermExpr();
	    this.term(Sym.PARD);
	    Expression es = nontermExprSuite(e);	
	    return es;
	}
	else {
        Main.fen.log("[Erreur] Can't eat symbol : " +reader.getNext());
	throw new ParserException(reader.getString());
	}

    }

   //expressionSuite → + expression | - expression | * expression | / expression | ε 
    public Expression nontermExprSuite(Expression beginning) throws Exception {
        Expression e = null;
        if (reader.check(Sym.PLUS)) {
            term(Sym.PLUS);
            e = nontermExpr();
            return new Sum(beginning, e);
        } else if (reader.check(Sym.MINUS)) {
            term(Sym.MINUS);
            e = nontermExpr();
            return new Difference(beginning, e);
        } else if (reader.check(Sym.TIMES)) {
            term(Sym.TIMES);
            e = nontermExpr();
            return new Product(beginning, e);
        } else if (reader.check(Sym.DIV)) {
            term(Sym.DIV);
            e = nontermExpr();
            return new Division(beginning, e);
	} else if(reader.check(Sym.AVANCE) ||
 		reader.check(Sym.TOURNE)||
 		reader.check(Sym.BasPinceau) ||
 		reader.check(Sym.HautPinceau) || 
		reader.check(Sym.IDENT) || 
		reader.check(Sym.DEBUT) || reader.check(Sym.SI)|| reader.check(Sym.TANTQUE) || reader.check(Sym.SINON)||reader.check(Sym.ALORS)||reader.check(Sym.FAIRE)|| reader.check(Sym.COLOR) || reader.check(Sym.PARD) ||reader.check(Sym.CONCAT)||reader.check(Sym.TAILLE)) {
	    return beginning;
	} else {
	   Main.fen.log("[Erreur] Can't eat symbol : " +reader.getNext());
            throw new Exception("cannot reduce expressionSuite");
	}
    }
}
