import java.io.*;

class Main {
    static UI_Frame fen;
    public static void main(String[] args) 
      throws Exception {
        Main.fen = new UI_Frame();
        if(args.length ==1)
        {
            System.out.println(args.length);
            File input = new File(args[0]);
            Main.executer(input);
        }
    }
    public static void executer(String t) throws Exception
    {
        Reader reader = new StringReader(t);
        Lexer lexer = new Lexer(reader);
        LookAhead1 look = new LookAhead1(lexer);
        Parser parser = new Parser(look);
        try {
            Program prog = parser.nontermProg();
            System.out.println("The code is correct");
        }
        catch (Exception e){
            System.out.println("The code is not correct.");
            System.out.println(e);
        }
        
    }
    public static void executer(File f) throws Exception
    {
        File input = f;
        Reader reader = new FileReader(input);
        Lexer lexer = new Lexer(reader);
        LookAhead1 look = new LookAhead1(lexer);
        Parser parser = new Parser(look);
        ValueEnvironment env = new ValueEnvironment();
        try {
            Program prog = parser.nontermProg();
            System.out.println("The code is correct");
            prog.run(env);
            fen.repaint();
        }
        catch (Exception e){
            Main.fen.log("[Erreur] The code is not correct.");
            Main.fen.log("[Erreur]" +e);
        }
        
    }
}
