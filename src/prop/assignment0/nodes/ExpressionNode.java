/**
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a>
 */

package prop.assignment0.nodes;

import java.io.IOException;

import prop.assignment0.*;


/**
 * <strong>Expression Node Class</strong><br/>
 * Class that represents an expression
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a><br/>
 * Personnummer: 701204-0619
 */
public class ExpressionNode implements INode {
	
	/*
	 * expr = term , [ ( ’+’ | ’-’ ) , expr ] ;
	 */
	private TermNode term;
	private Lexeme op = null;
	private ExpressionNode expr = null;

	public ExpressionNode(ITokenizer tokenizer) throws IOException, TokenizerException, ParserException {
		
		term = new TermNode(tokenizer);
		Token optok = tokenizer.current().token();
		if (optok == Token.ADD_OP || optok == Token.SUB_OP) {
			op = tokenizer.current();
			tokenizer.moveNext();
			expr = new ExpressionNode(tokenizer);
		}
	}

	/* (non-Javadoc)
	 * @see prop.assignment0.INode#evaluate(java.lang.Object[])
	 */
	@Override
	public Object evaluate(Object[] args) throws Exception {
		// Save the current value and operation
		Double result = (Double) args[1];
		Token optkn = (Token) args[2];
		
		if (result != null && optkn != null) {
			// Reset the current value and operation
			args[1] = null;
			args[2] = null;
			term.evaluate(args);
			// Combine the previous calculation with the result from term.eval() (for left associativity)
			Double val = Double.valueOf(args[1].toString());
			args[1] = (optkn == Token.ADD_OP ? result + val : result - val);
		} else {
			term.evaluate(args);
		}
		if (op != null) {
			args[2] = op.token(); // We stock the new operator
			expr.evaluate(args);
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
		builder.append(sbindent + "ExpressionNode\n");
		term.buildString(builder, tabs + 1);
		if (op != null) {
			builder.append(sbindent + "\t" + op.token() + " " + op.value() + "\n");
			expr.buildString(builder, tabs + 1);
		}
	}

}
