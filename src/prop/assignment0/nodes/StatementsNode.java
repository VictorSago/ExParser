/**
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a>
 */

package prop.assignment0.nodes;

import java.io.IOException;
import prop.assignment0.*;

/**
 * <strong>Statements Node Class</strong><br/>
 * Class that represents a sequence of statements where each  
 * statement is either an assignment or is empty
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a><br/>
 * Personnummer: 701204-0619
 */
public class StatementsNode implements INode {
	
	/*
	 * stmts = [ assign , stmts ] ;
	 */
	private AssignmentNode assign = null;
	private StatementsNode stmts = null;

	public StatementsNode(ITokenizer tokenizer) throws IOException, TokenizerException, ParserException {
		if (tokenizer.current().token() != Token.RIGHT_CURLY) {
			assign = new AssignmentNode(tokenizer);
			stmts = new StatementsNode(tokenizer);
		}
	}

	/* (non-Javadoc)
	 * @see prop.assignment0.INode#evaluate(java.lang.Object[])
	 */
	@Override
	public Object evaluate(Object[] args) throws Exception {
		String ret;
		if (assign != null) {
			StringBuilder bld = new StringBuilder(assign.evaluate(args).toString());
			if (stmts != null) {
				bld.append(stmts.evaluate(args));
			}
			ret = bld.toString();
		} else
			ret = "";				
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
		builder.append(sbindent + "StatementsNode\n");
		if (assign != null) {
			assign.buildString(builder, tabs + 1);
		}
		if (stmts != null) {
			stmts.buildString(builder, tabs + 1);
		}
	}

}
