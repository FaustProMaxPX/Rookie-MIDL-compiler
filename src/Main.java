
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import gen.*;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import exp1.*;
public class Main {

    public static void main(String[] args) throws InterruptedException {
        CharStream cs = CharStreams.fromString("""
                module space{
                	struct A{
                		short i1=10 + 2;
                	};
                };
                """);

        MIDLGrammarLexer lexer = new MIDLGrammarLexer(cs);


        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MIDLGrammarParser parser = new MIDLGrammarParser(tokens);
        MIDLGrammarParser.SpecificationContext ctx = parser.specification();
        ASTOutputVisitor visitor = new ASTOutputVisitor();

        TreeNode visit = visitor.visit(ctx);
        System.out.println(visit.display());
    }
}
