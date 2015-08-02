import java.util.*;

abstract class Expression {
    abstract int eval(ValueEnvironment env)
    throws Exception;
}
class Int extends Expression {
	private int value;
	public Int(int i) {
		value = i;
		
	}
	public int eval(ValueEnvironment env)
	throws Exception {
		
		return value;
	}
	
}
class Var extends Expression {
	private String name;
	public Var(String s) {
		name = s;
	}
	public int eval(ValueEnvironment env)
	throws Exception {
		
		return env.getValue(name);
	}
	
}
class Sum extends Expression {
	private Expression left, right;
	public Sum(Expression l, Expression r) {
		left = l;
		right = r;
	}
	public int eval(ValueEnvironment env)
	throws Exception {
		return left.eval(env) + right.eval(env);
	}
	
}
class Difference extends Expression {
	private Expression left, right;
	public Difference(Expression l, Expression r) {
		left = l;
		right = r;
	}
	public int eval(ValueEnvironment env)
	throws Exception {
		return left.eval(env) - right.eval(env);
	}
	
}
class Product extends Expression {
	private Expression left, right;
	public Product(Expression l, Expression r) {
		left = l;
		right = r;
	}
	public int eval(ValueEnvironment env)
	throws Exception {
		return left.eval(env) * right.eval(env);
	}
	
}
class Division extends Expression {
	private Expression left, right;
	public Division(Expression l, Expression r) {
		left = l;
		right = r;
	}
	public int eval(ValueEnvironment env)
	throws Exception {
		int r = right.eval(env);
		if(r==0)
		{
			throw new ArithmeticException("Division par 0");
		}
		return left.eval(env) / right.eval(env);
	}
	
}

class Program {
	private Declaration first;
	private Instruction rest;
	public Program(Declaration i, Instruction p) {
		first = i;
		rest = p;
	}
	public void run(ValueEnvironment env) 
	throws Exception {
		
		if (first != null) {
			first.exec(env);
		}
		if(rest != null)
		{
		
			rest.exec(env);
		}
		else
		{
		
		}
	}
	
}

abstract class Instruction {
	abstract void exec(ValueEnvironment env)
	throws Exception;
}
class Declaration extends Instruction {
	private String varName;
	private Declaration next;
	public Declaration(String s) {
		varName = s;
	}
	public void exec(ValueEnvironment env) 
	throws Exception {
		Main.fen.log(">> Variable "+ varName);
		env.addVariable(varName);
		if(next != null)
		{
			next.exec(env);		
		}
	}
	public void setSuivant(Declaration d)
	{
		this.next = d;
	}
	public Declaration getSuivant()
	{
		return this.next;
	}
	
}
class Tourne extends Instruction {
	Expression e;
	public Tourne(Expression e)
	{
		this.e = e;

	}
	public void exec(ValueEnvironment env)
			throws Exception
	{
		if(this.e != null)
			Main.fen.log(">> Rotation de "+this.e.eval(env));
			Main.fen.canvas.addangle(this.e.eval(env));
	}
	
}

class BasPinceau extends Instruction {
	public BasPinceau()
	{

	}
	public void exec(ValueEnvironment env)
			throws Exception
	{
		
		Main.fen.canvas.activerDessin();
		Main.fen.log(">> Activation Dessin");
	}
	
}
class HautPinceau extends Instruction {
	public HautPinceau()
	{
	}
	public void exec(ValueEnvironment env)
			throws Exception
	{
		
		Main.fen.canvas.desactiverDessin();
		Main.fen.log(">> Desactivation Dessin");
	}
	
}
class Assignment extends Instruction {
	private String varName;
	private Expression exp;
	public Assignment(String s, Expression e) {
		varName = s;
		exp = e;
	}
	public void exec(ValueEnvironment env)
	throws Exception {
		Main.fen.log(" >> "+this.varName +" = " + exp.eval(env));
		
		env.setVariable(varName, exp.eval(env));
	}
	
}
class InstructionChain extends Instruction
{
	InstructionList next;
	Instruction curr;
	public InstructionChain(Instruction curr, InstructionList next) {
		this.curr = curr;
		this.next = next;
	}
	public void exec(ValueEnvironment env)
			throws Exception {
	
		if(curr != null)
			curr.exec(env);
		if(next != null)
			next.exec(env);
	}
	
}
class ColorInstr extends Instruction
{
	public String color;
	public ColorInstr(String c)
	{
		
		this.color = c;
	}
	public void exec(ValueEnvironment env)
			throws Exception {
		
		Main.fen.canvas.setcolor(color.toLowerCase());
	}
	
}
class SizeInstr extends Instruction
{
	public Expression t;
	public SizeInstr(Expression c)
	{
		
		this.t = c;
	}
	public void exec(ValueEnvironment env)
			throws Exception {
		
		Main.fen.canvas.size = this.t.eval(env);
	}
	
}
class Condition extends Instruction
{
	private Expression e;
	private InstructionList alorsi;
	private InstructionList sinoni;

	public Condition(Expression e, InstructionList i, InstructionList is)
	{
		this.e = e;
		this.alorsi = i;
		this.sinoni = is;
	}
	public void exec(ValueEnvironment env)
			throws Exception {
		if(this.e.eval(env) != 0)
		{
			this.alorsi.exec(env);
		}
		else{
			this.sinoni.exec(env);	
		}
	}
}
class Boucle extends Instruction
{
	private Expression e;
	private InstructionList fairei;


	public Boucle(Expression e, InstructionList i)
	{
		this.e = e;
		this.fairei = i;
	}
	public void exec(ValueEnvironment env)
			throws Exception {
		while(this.e.eval(env) != 0)
		{
			this.fairei.exec(env);
		}
	}
}
class ValueEnvironment extends HashMap<String, Integer> {
	public ValueEnvironment() {
		super();
	}
	public void addVariable(String name) 
	throws Exception {
		if (this.containsKey(name)) 
			throw new VariableTwiceDefinedException(name);
		this.put(name, 0);
	}
	public void setVariable(String name, int value) 
	throws Exception {
		if (!this.containsKey(name))
			throw new VariableNotDefinedException(name);
		this.put(name, value);
	}
	public int getValue(String name) 
	throws Exception {
		if (!this.containsKey(name))
			throw new VariableNotDefinedException(name);
		Integer value = this.get(name);
		if (value == null)
			throw new VariableNotInitializedException(name);
		return value;
	}
}
abstract class InstructionList {
	abstract void exec(ValueEnvironment env) throws Exception;
	abstract Instruction first();
	abstract InstructionList next();
}

class Nil extends InstructionList {
    public Nil() {
    }
	void exec(ValueEnvironment env) throws Exception
	{

	}
	public Instruction first()
	{
		return null;
	}
	public InstructionList next()
	{
		return null;
	}

}

class Seq extends InstructionList {
    public Instruction head;
    private InstructionList rest;
    public Seq(Instruction i, InstructionList il) {
	head=i;
	rest=il;
    }
	public void exec(ValueEnvironment env)
			throws Exception
	{
		if(head != null)
			head.exec(env);
		if(rest != null)
			rest.exec(env);
	}
	public Instruction first()
	{
		if(head != null)
			return head;
		return null;
	}
	public InstructionList next()
	{
		if(rest != null)
			return rest;
		return null;
	}
  

}

class Avance extends Instruction{
	private Expression e;
	public Avance(Expression e)
	{
	
	this.e=e;
	}
	public void exec(ValueEnvironment env)throws Exception {
		Main.fen.log(">> Avance de "+ this.e.eval(env));
		Main.fen.ajouterDroite(this.e.eval(env));
	}
	

}



