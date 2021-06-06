/**
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a>
 */

package prop.assignment0.nodes;

import java.io.IOException;
import prop.assignment0.*;

/**
 * <strong>Block Node Class</strong><br/>
 * Class that represents a block of statements
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a><br/>
 * Personnummer: 701204-0619
 */
public class BlockNode implements INode {
	
	/*
	 * block = ’{’ , stmts , ’}’ ;
	 */
	private StatementsNode stmts;

	public BlockNode(ITokenizer tokenizer) throws IOException, TokenizerException, ParserException {
		String errorMsg = null;
		tokenizer.moveNext();
		stmts = new StatementsNode(tokenizer);
		if (tokenizer.current().token() == Token.RIGHT_CURLY) {
			tokenizer.moveNext();
			if (tokenizer.current().token() != Token.EOF) {
				errorMsg = "EOF must follow right curly brace.";
			}
		} else {
			errorMsg = "The right brace is missing.";
		}
		if (errorMsg != null) {
			throw new ParserException("Blocknode: " + errorMsg);
		}
	}

	/* (non-Javadoc)
	 * @see prop.assignment0.INode#evaluate(java.lang.Object[])
	 */
	@Override
	public Object evaluate(Object[] args) throws Exception {
		String ret = stmts.evaluate(args).toString();
		return ret;
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
		builder.append(sbindent + "BlockNode\n");
		builder.append(sbindent + "\t" + Token.LEFT_CURLY + " " + TokenSgn.LCURLY_SIGN + "\n");
		stmts.buildString(builder, tabs+1);
		builder.append(sbindent + "\t" + Token.RIGHT_CURLY + " " + TokenSgn.RCURLY_SIGN + "\n");
	}

}
