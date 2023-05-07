
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import gen.*;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CharStream cs = CharStreams.fromString("""
                module space{
                	struct A{
                		short i1=10;
                	};
                };
                """);

        MIDLGrammarLexer lexer = new MIDLGrammarLexer(cs);
        Token token = lexer.getToken();
        while (!(token = lexer.nextToken()).equals(Token.EOF)) {
            System.out.println(token.getText());
            Thread.sleep(100);
        }

//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        MIDLGrammarParser parser = new MIDLGrammarParser(tokens);
//        MIDLGrammarParser.SpecificationContext ctx = parser.specification();
//        MIDLGrammarBaseVisitor visitor = new MIDLGrammarBaseVisitor();
//        System.out.println(visitor.visit(ctx));
    }
}
