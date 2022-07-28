/**
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a>
 */

package prop.assignment0;

import java.io.IOException;

/**
 * <strong>Tokenizer class </strong><br/>
 * Takes a stream of characters and turns it into a stream of lexems/tokens
 * @author Victor Sago, <a href="mailto:VictorSago01@gmail.com">VictorSago01@gmail.com</a>
 *
 */
public class Tokenizer implements ITokenizer {

    private IScanner scanner;
    private Lexeme current = null;

    /**
     *
     */
    public Tokenizer() {
        scanner = new Scanner();
    }

    /* (non-Javadoc)
     * @see prop.assignment0.ITokenizer#open(java.lang.String)
     */
    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        scanner.open(fileName);
        scanner.moveNext();
        this.moveNext();
    }

    /* (non-Javadoc)
     * @see prop.assignment0.ITokenizer#current()
     */
    @Override
    public Lexeme current() {
        return this.current;
    }

    /* (non-Javadoc)
     * @see prop.assignment0.ITokenizer#moveNext()
     */
    @Override
    public void moveNext() throws IOException, TokenizerException {

//		if (scanner == null)
//			throw new IOException("No open file!");
        char c = scanner.current();
        while (Character.isWhitespace(c)) {				// Ignore whitespaces
            scanner.moveNext();
            c = scanner.current();
        }

        if (c != Scanner.NULL && c != Scanner.EOF) {
            if (Character.isDigit(c)) {					// Is this he start of an integer literal?
                StringBuilder lit = new StringBuilder();
                while (Character.isDigit(c)) {			// Integer literal defined by RE [0-9]+
                    lit.append(c);
                    try {
                        scanner.moveNext();
                        c = scanner.current();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
                this.current = new Lexeme(Integer.parseInt(lit.toString()), Token.INT_LIT);
            } else if (Character.isLetter(c)) {			// Is this the start of an identifier?
                StringBuilder ident = new StringBuilder();
                while ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {		// Identifiers defined by RE [a-zA-Z]+
                    ident.append(c);
                    try {
                        scanner.moveNext();
                        c = scanner.current();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
                this.current = new Lexeme(ident.toString(), Token.IDENT);
            } else if (Character.isWhitespace(c)) {
                do {
                    scanner.moveNext();
                    c = scanner.current();
                } while (Character.isWhitespace(c));
            } else {
                switch (c) {
                    case TokenSgn.ADD_SIGN		: this.current = new Lexeme(c, Token.ADD_OP); break;
                    case TokenSgn.SUB_SIGN 		: this.current = new Lexeme(c, Token.SUB_OP); break;
                    case TokenSgn.MULT_SIGN 	: this.current = new Lexeme(c, Token.MULT_OP); break;
                    case TokenSgn.DIV_SIGN 		: this.current = new Lexeme(c, Token.DIV_OP); break;
                    case TokenSgn.SEMICOL_SIGN	: this.current = new Lexeme(c, Token.SEMICOLON); break;
                    case TokenSgn.LPAREN_SIGN 	: this.current = new Lexeme(c, Token.LEFT_PAREN); break;
                    case TokenSgn.RPAREN_SIGN 	: this.current = new Lexeme(c, Token.RIGHT_PAREN); break;
                    case TokenSgn.LCURLY_SIGN	: this.current = new Lexeme(c, Token.LEFT_CURLY); break;
                    case TokenSgn.RCURLY_SIGN	: this.current = new Lexeme(c, Token.RIGHT_CURLY); break;
                    case TokenSgn.EQ_SIGN 		: this.current = new Lexeme(c, Token.ASSIGN_OP); break;
                    default	: throw new TokenizerException("Tokenizer.moveNext() : Unknown token");
                }
                scanner.moveNext();
            }
        } else {
            this.current = new Lexeme("", Token.EOF);
        }
    }

    /* (non-Javadoc)
     * @see prop.assignment0.ITokenizer#close()
     */
    @Override
    public void close() throws IOException {
        scanner.close();
    }

}
