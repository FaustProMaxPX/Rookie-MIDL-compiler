import org.junit.Assert;
import org.junit.Test;
import rookie.midl.ast.RookieMIDLVisitor;
import rookie.midl.ast.TreeNode;
import rookie.midl.gen.MIDLGrammarLexer;
import rookie.midl.gen.MIDLGrammarParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import rookie.midl.semantic.exception.DuplicateDefinitionException;
import rookie.midl.semantic.exception.MisMatchException;
import rookie.midl.semantic.exception.UndefinedException;
import static rookie.midl.utils.TypeCheckUtil.*;

import java.io.*;
import java.nio.file.*;
import java.util.regex.Pattern;

public class Main {

    public static void genAST(String input, String output) {
        try (Writer out = new BufferedWriter(new FileWriter(output))) {
            CharStream cs = CharStreams.fromFileName(input);
            MIDLGrammarLexer lexer = new MIDLGrammarLexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MIDLGrammarParser parser = new MIDLGrammarParser(tokens);
            MIDLGrammarParser.SpecificationContext ctx = parser.specification();
            RookieMIDLVisitor visitor = new RookieMIDLVisitor();
            TreeNode root = visitor.visit(ctx);
            out.write(root.display());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void semanticCheck(String input) throws IOException {
        CharStream cs = CharStreams.fromFileName(input);
        MIDLGrammarLexer lexer = new MIDLGrammarLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MIDLGrammarParser parser = new MIDLGrammarParser(tokens);
        MIDLGrammarParser.SpecificationContext ctx = parser.specification();
        RookieMIDLVisitor visitor = new RookieMIDLVisitor();
        TreeNode root = visitor.visit(ctx);
    }

    @Test
    public void exp1Check() throws IOException {
        Path path = Paths.get("test", "exp1");
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.{idl}");

        Files.walk(path)
                .filter(pathMatcher::matches)
                .forEach(p -> {
                    try {
                        String in = p.toString();
                        String out = in.replace(".idl", ".txt");
                        genAST(in, out);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.printf("error in file: %s\n", p.toString());
                    }
                });
    }

    @Test
    public void exp2Check() throws IOException {
        Assert.assertThrows(DuplicateDefinitionException.class, () -> semanticCheck("test/exp2/test1.idl"));
        semanticCheck("test/exp2/test2.idl");
        Assert.assertThrows(DuplicateDefinitionException.class, () -> semanticCheck("test/exp2/test3.idl"));
        Assert.assertThrows(UndefinedException.class, () -> semanticCheck("test/exp2/test4.idl"));
        semanticCheck("test/exp2/test5.idl");
        Assert.assertThrows(UndefinedException.class, () -> semanticCheck("test/exp2/test6.idl"));
        semanticCheck("test/exp2/test7.idl");
        Assert.assertThrows(MisMatchException.class, () -> semanticCheck("test/exp2/test8.idl"));
        semanticCheck("test/exp2/test9.idl");
        semanticCheck("test/exp2/test10.idl");
        Assert.assertThrows(MisMatchException.class, () -> semanticCheck("test/exp2/test11.idl"));
        Assert.assertThrows(MisMatchException.class, () -> semanticCheck("test/exp2/test12.idl"));
        Assert.assertThrows(MisMatchException.class, () -> semanticCheck("test/exp2/test13.idl"));
        Assert.assertThrows(MisMatchException.class, () -> semanticCheck("test/exp2/test14.idl"));
    }

    @Test
    public void typeCheckTest() {
        Assert.assertTrue(checkDeclarator("int32", "2147"));
        Assert.assertTrue(checkDeclarator("unsigned long long", "2147483649"));
        Assert.assertFalse(checkDeclarator("int32", "2147483649"));
        Assert.assertTrue(checkDeclarator("uint32", "2147483648"));
        Assert.assertTrue(checkDeclarator("boolean", "true"));
        Assert.assertTrue(checkDeclarator("char", "c"));
        Assert.assertTrue(checkDeclarator("float", "1.23f"));
        Assert.assertTrue(checkDeclarator("double", "1.23d"));
        Assert.assertTrue(checkDeclarator("double", "1.23f"));
    }

    public static void main(String[] args) throws IOException {

    }

    @Test
    public void checkTest() throws IOException {
        semanticCheck("test/exp2/test8.idl");
    }
}
