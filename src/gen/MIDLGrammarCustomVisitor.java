package gen;

import java.util.Objects;

public class MIDLGrammarCustomVisitor<T> extends MIDLGrammarBaseVisitor<T> {
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitSpecification(MIDLGrammarParser.SpecificationContext ctx) {
		T t = null;
		for (MIDLGrammarParser.DefinitionContext definitionContext : ctx.definition()) {
			t = visitDefinition(definitionContext);
		}
		return t;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitDefinition(MIDLGrammarParser.DefinitionContext ctx) {
		if (Objects.nonNull(ctx.type_decl())) {
			return visitType_decl(ctx.type_decl());
		}
		return visitModule(ctx.module());
    }
}
