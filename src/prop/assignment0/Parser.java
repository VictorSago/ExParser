/*
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a>
 */

package prop.assignment0;

import java.io.IOException;

import prop.assignment0.nodes.*;

/**
 * <strong>Parser class </strong><br/>
 * Takes a stream of lexems/tokens and creates a parse tree
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a>
 *
 */
public class Parser implements IParser {
	
	private Tokenizer tokenizer;
	private INode root = null;

	/**
	 * 
	 */
	public Parser() {
		tokenizer = new Tokenizer();
	}

	/* (non-Javadoc)
	 * @see prop.assignment0.IParser#open(java.lang.String)
	 */
	@Override
	public void open(String fileName) throws IOException, TokenizerException {
		tokenizer.open(fileName);
	}

	/* (non-Javadoc)
	 * @see prop.assignment0.IParser#parse()
	 */
	@Override
	public INode parse() throws IOException, TokenizerException, ParserException {
		Lexeme firstLex = tokenizer.current();
		if (firstLex.token() == Token.LEFT_CURLY) {		// If the first token is a left brace then it's Grammar2
			root = new BlockNode(tokenizer);			// and we process the rule block = ’{’ , stmts , ’}’ ;
		} else if (firstLex.token() == Token.IDENT) {	// Otherwise it's Grammar1
			root = new AssignmentNode(tokenizer);		// and we process the rule assign = id , ’=’ , expr , ’;’ ;
		} else {
			throw new ParserException("Syntax error: First Token Unknown.");
		}
		return root;
	}

	/* (non-Javadoc)
	 * @see prop.assignment0.IParser#close()
	 */
	@Override
	public void close() throws IOException {
		if (tokenizer != null) {
			tokenizer.close();
		}
	}

}
