%%
%public
%class Lexer
%unicode
%type Token
%line
%column

%{
	
%}

%yylexthrow{
	LexerException
%yylexthrow}

blank = "\n" | "\r" | " " | "\t"
nombre = [1-9][0-9]* | 0
ident = [a-z][a-zA-Z0-9]*
%%

<YYINITIAL> {

   {nombre}+ { return new IntToken(Sym.INT,Integer.parseInt(yytext())); }
   {ident}+ { return new IdentToken(Sym.IDENT,yytext()); }
   {blank}	{}
   "+"		{return new Token(Sym.PLUS);}
   "-"		{return new Token(Sym.MINUS);}
   "*"		{return new Token(Sym.TIMES);}
   "/"		{return new Token(Sym.DIV);}
   "="		{return new Token(Sym.EQ);}
   ";"		{return new Token(Sym.CONCAT);}
   "("		{return new Token(Sym.PARG);}
   ")"		{return new Token(Sym.PARD);}
   "Var"	{return new Token(Sym.VAR);}
   "HautPinceau"	{return new Token(Sym.HautPinceau);}
   "BasPinceau"	{return new Token(Sym.BasPinceau);}
   "Debut"	{return new Token(Sym.DEBUT);}
   "Fin"	{return new Token(Sym.FIN);}
   "Avance"	{return new Token(Sym.AVANCE);}
   "Tourne"	{return new Token(Sym.TOURNE);}
   "COLOR"  {return new Token(Sym.COLOR);}
   "SI"		{return new Token(Sym.SI);}
   "ALORS"		{return new Token(Sym.ALORS);}
   "SINON"		{return new Token(Sym.SINON);}
   "TANT QUE"  {return new Token(Sym.TANTQUE);}
   "FAIRE"     {return new Token(Sym.FAIRE);}
   <<EOF>>	{return new Token(Sym.EOF);}
   "Taille" {return new Token(Sym.TAILLE);}
   [^]		{throw new LexerException(yyline, yycolumn);}
}

