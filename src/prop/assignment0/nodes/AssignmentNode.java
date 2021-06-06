/**
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a>
 */

package prop.assignment0.nodes;

import java.io.IOException;
import java.util.HashMap;
import prop.assignment0.*;

/**
 * <strong>Assignment Node Class</strong><br/>
 * Class that represents a single assignment statement
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a><br/>
 * Personnummer: 701204-0619
 */
public class AssignmentNode implements INode {
	
	/*
	 * assign = id , ’=’ , expr , ’;’ ;
	 */
	private Lexeme id;
	private ExpressionNode expr;

	public AssignmentNode(ITokenizer tokenizer)  throws IOException, TokenizerException, ParserException {
		String errorMsg = null;
		if (tokenizer.current().token() == Token.IDENT) {
			id = tokenizer.current();
			tokenizer.moveNext();
			if (tokenizer.current().token() == Token.ASSIGN_OP) {
				tokenizer.moveNext();
				expr = new ExpressionNode(tokenizer);
				if (tokenizer.current().token() == Token.SEMICOLON) {
					tokenizer.moveNext();
				} else {
					errorMsg = "Semicolon is missing";
				}
			} else {
				errorMsg = "Assignment operator is missing.";
			}
		} else {
			errorMsg = "Variable_ID is missing in Assignment.";
		}
		if (errorMsg != null) {
//			System.out.println(" " + tokenizer.current().token());
			throw new ParserException("AssignmentNode: " + errorMsg);
		}
	}

	/* (non-Javadoc)
	 * @see prop.assignment0.INode#evaluate(java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object evaluate(Object[] args) throws Exception {
		
		String idstr = id.value().toString();
		// Reset the current value and operation
		args[1] = null;
		args[2] = null;
		expr.evaluate(args);
		Double result = Double.valueOf(args[1].toString());
		// Put the value of the identifier into the symbol table
		((HashMap<String, Double>) args[0]).put(idstr, result);
		
		return idstr + " = " + result + "\n";
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
		builder.append(sbindent + "AssignmentNode\n");
		builder.append(sbindent + "\t" + Token.IDENT + " " + id.value() + "\n");
		builder.append(sbindent + "\t" + Token.ASSIGN_OP + " " + TokenSgn.EQ_SIGN + "\n");
		expr.buildString(builder, tabs + 1);
		builder.append(sbindent + "\t" + Token.SEMICOLON + " " + TokenSgn.SEMICOL_SIGN + "\n");
	}

}
