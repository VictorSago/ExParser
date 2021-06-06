/**
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a>
 */

package prop.assignment0.nodes;

import java.io.IOException;
import java.util.HashMap;

import prop.assignment0.*;

/**
 * <strong>Factor Node Class</strong><br/>
 * Class that represents a factor node in the grammar
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a><br/>
 * Personnummer: 701204-0619
 */
public class FactorNode implements INode {
	
	/*
	 * factor = int | id | ’(’ , expr , ’)’ ;
	 */
	private Lexeme lexeme = null;
	private ExpressionNode expr = null;

	public FactorNode(ITokenizer tokenizer) throws IOException, TokenizerException, ParserException {
		Lexeme lex = tokenizer.current();
		switch (lex.token()) {
			case IDENT		:
			case INT_LIT	: this.lexeme = lex;						// This factor is an identity or a literal
							  break;
			case LEFT_PAREN	: tokenizer.moveNext();						// This factor is a parenthesised expression
							  this.expr = new ExpressionNode(tokenizer);
							  if (tokenizer.current().token() != Token.RIGHT_PAREN) {
								  throw new ParserException("FactorNode: Right parenthesis missing");
							  }
							  break;
			default			: throw new ParserException("Syntax error. ParserException in FactorNode: Misformed"); 
		}
		tokenizer.moveNext();
	}

	/* (non-Javadoc)
	 * @see prop.assignment0.INode#evaluate(java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object evaluate(Object[] args) throws Exception {
		
		if (lexeme != null) {						// Identity or literal
			if (lexeme.token() == Token.INT_LIT) {
				return Double.valueOf(lexeme.value().toString());
			} else {
				HashMap<String, Double> hm = (HashMap<String, Double>) args[0];
				return hm.containsKey(lexeme.value()) ? hm.get(lexeme.value()) : new Double(0);
			}
		} else { 									// ( ExpressionNode )
			Object[] retar = {args[0], null, null}; //reset the result and operator
			expr.evaluate(retar);
			return retar[1];
		}
	}

	/* (non-Javadoc)
	 * @see prop.assignment0.INode#buildString(java.lang.StringBuilder, int)
	 */
	@Override
	public void buildString(StringBuilder builder, int tabs) {
		StringBuilder sbindent = new StringBuilder();
		for (int i = 0; i < tabs; i++) {
			sbindent.append("\t");
		}
		builder.append(sbindent + "FactorNode\n");
		if (lexeme != null) { 	// INT_LIT or IDENT
			if (lexeme.token() == Token.INT_LIT) {
				// TODO - find a better way then casting Object to double
				builder.append(sbindent + "\t" + lexeme.token() + " " + Double.valueOf(lexeme.value().toString()) + "\n");
			} else {
				builder.append(sbindent + "\t" + lexeme.token() + " " + lexeme.value() + "\n");
			}
		} else {				// ( ExpressionNode )
			builder.append(sbindent + "\t" + Token.LEFT_PAREN + " " + TokenSgn.LPAREN_SIGN + "\n");
			expr.buildString(builder, tabs + 1);
			builder.append(sbindent + "\t" + Token.RIGHT_PAREN + " " + TokenSgn.RPAREN_SIGN + "\n");
		}
	}

}
