/**
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a>
 * 
 */
package prop.assignment0.nodes;

import java.io.IOException;
import prop.assignment0.*;

/**
 * <strong>Term Node Class</strong><br/>
 * Class that represents a term node in the grammar
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a><br/>
 * Personnummer: 701204-0619
 */
public class TermNode implements INode {
	
	/*
	 * term = factor , [ ( ’*’ | ’/’ ) , term ] ;
	 */
	private FactorNode factor;
	private Lexeme op = null;
	private TermNode term = null;

	public TermNode(ITokenizer tokenizer) throws IOException, TokenizerException, ParserException {
		
		factor = new FactorNode(tokenizer);
		Token optok = tokenizer.current().token();
		if (optok == Token.MULT_OP || optok == Token.DIV_OP) {
			op = tokenizer.current();
			tokenizer.moveNext();
			term = new TermNode(tokenizer);
		}
		
		if (factor == null) {
			throw new ParserException("Syntax error. ParserException in TermNode.");
		}
	}

	/* (non-Javadoc)
	 * @see prop.assignment0.INode#evaluate(java.lang.Object[])
	 */
	@Override
	public Object evaluate(Object[] args) throws Exception {

		Double result = (Double) args[1];
		Token optkn = (Token) args[2];
		Double val = Double.valueOf(factor.evaluate(args).toString());
		
		// Multiply or divide with the previous result
		if (result != null && optkn != null) {
			args[1] = (optkn == Token.MULT_OP ? result * val : result / val);
		} else {
			args[1] = val;
		}
		if (op != null) {
			args[2] = op.token(); // Remember the new operator
			term.evaluate(args);
		}
		return args[1];
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
		builder.append(sbindent + "TermNode\n");
		factor.buildString(builder, tabs + 1);
		if (op != null) {
			builder.append(sbindent + "\t" + op.token() + " " + op.value() + "\n");
			term.buildString(builder, tabs + 1);
		}
	}

}
