import exp1.ASTOutputVisitor;
import exp1.TreeNode;
import gen.MIDLGrammarLexer;
import gen.MIDLGrammarParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.*;
import java.nio.file.*;

public class Main {

    public static void genAST(String input, String output) {
        try (Writer out = new BufferedWriter(new FileWriter(output))) {
            CharStream cs = CharStreams.fromFileName(input);
            MIDLGrammarLexer lexer = new MIDLGrammarLexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MIDLGrammarParser parser = new MIDLGrammarParser(tokens);
            MIDLGrammarParser.SpecificationContext ctx = parser.specification();
            ASTOutputVisitor visitor = new ASTOutputVisitor();
            TreeNode root = visitor.visit(ctx);
            out.write(root.display());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("test");
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.{idl}");

        Files.walk(path)
                .filter(pathMatcher::matches)
                .forEach(p -> {
                    try {
                        String in = p.toString();
                        String out = in.replace(".idl", ".txt");
                        genAST(in, out);
                    } catch (Exception e) {
                        System.out.printf("error in file: %s\n", p.toString());
                    }
                });
    }
}
