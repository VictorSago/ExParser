package prop.assignment0;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class Program {

    public static void main(String[] args) {
        String inputFileName = null;
        String outputFileName = null;
        IParser parser = null;
        INode root = null; // Root of the parse tree.
        StringBuilder builder = null;
        FileOutputStream stream = null;
        OutputStreamWriter writer = null;

        try {
            try {
                if (args.length < 2)
                    throw new Exception("Incorrect number of parameters to program.");
                inputFileName = args[0];
                outputFileName = args[1];

                parser = new Parser();
                parser.open(inputFileName); 						// *Exception 2: IOException,
                // TokenizerException
                root = parser.parse(); 								// *Exception 3: IOException,
                // TokenizerException, ParserException
                builder = new StringBuilder();
                builder.append("PARSE TREE:\n");
                root.buildString(builder, 0);
                builder.append("\nEVALUATION:\n");

                // The first member of the object array is a symbol table where variables are saved together with their values
                // The second and third members are for the last calculation and the next operation
                Object[] array = {new HashMap<String, Double>(), null, null};
                builder.append(root.evaluate(array));

                stream = new FileOutputStream(outputFileName); 		// *Exception
                // 1:
                // FileNotFoundException
                writer = new OutputStreamWriter(stream); 			// *Exception 1:
                // IOException
                writer.write(builder.toString());
            }
            catch (Exception exception) {
                System.out.println("EXCEPTION: " + exception);
            }
            finally {
                if (parser != null)
                    parser.close();
                if (writer != null)
                    writer.close();
                if (stream != null)
                    stream.close();
            }
        }
        catch (Exception exception) {
            System.out.println("EXCEPTION: " + exception);
        }
    }
}
