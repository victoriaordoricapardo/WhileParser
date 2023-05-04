import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import java.io.File;

public class WhileRecognizer {

    public static void main(String[] args) throws IOException {
        String filename;
        if (args.length > 0) {
            filename = args[0];
        } else {
            filename = getFileFromDialog();
        }
        String input = readInputFromFile(filename);
        int[] results = parseInput(input);
        System.out.println("Numero total de variables (diferentes) usadas en todos los while encontrados: " + results[0]);
        System.out.println("Numero total de operadores de comparación encontrados (con repeticiones): " + results[1]);
        System.out.println("Numero total de while's que contienen los bloques parseados: " + results[2]);
    }
    
    
    
    

    private static String getFileFromDialog() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null); 
        if (result == JFileChooser.APPROVE_OPTION) { 
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        } else {
            return null; 
        }
    }

    
    private static String readInputFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder inputBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            inputBuilder.append(line);
        }
        reader.close();
        return inputBuilder.toString();
    }
    
    
    

    private static int[] parseInput(String input) {
        int numVariables = 0;
        int numComparators = 0;
        int numBlocks = 0;

        String variablePattern = "[a-z]";
        String constantPattern = "[0-9]";
        String comparatorPattern = "<|>|==|>=|<=|!=";
        String whilePattern = "\\bwhile\\b";
        String leftBracePattern = "\\{";
        String rightBracePattern = "\\}";

        Stack<String> stack = new Stack<String>();

        Pattern whileBlockPattern = Pattern.compile(whilePattern + "(.*?)" + leftBracePattern + "(.*?)" + rightBracePattern);
        Matcher matcher = whileBlockPattern.matcher(input);

        while (matcher.find()) {
            numBlocks++;

            String condition = matcher.group(1).trim();

            Set<String> variables = new HashSet<String>();
            Pattern variableMatcher = Pattern.compile(variablePattern);
            Matcher varMatcher = variableMatcher.matcher(condition);
            while (varMatcher.find()) {
                variables.add(varMatcher.group());
                numVariables += variables.size();

                Pattern comparatorMatcher = Pattern.compile(comparatorPattern);
                Matcher compMatcher = comparatorMatcher.matcher(condition);
                while (compMatcher.find()) {
                    numComparators++;
                }
                String block = matcher.group(2).trim();
                String[] tokens = block.split("\\s+");
                for (String token : tokens) {
                    if (token.equals("{")) {
                        stack.push(token);
                    } else if (token.equals("}")) {
                        if (stack.empty()) {
                            System.out.println("Error: más llaves de cierre que de apertura en el bloque while");
                            System.exit(1);
                        } else {
                            stack.pop();
                        }
                    }
                }
                if (!stack.empty()) {
                    System.out.println("Error: más llaves de apertura que de cierre en el bloque while");
                    System.exit(1);
                }
            }

            if (!stack.empty()) {
                System.out.println("Error: más llaves de apertura que de cierre en el archivo completo");
                System.exit(1);
            }
        }
        return new int[] {numVariables, numComparators, numBlocks};
    }
    
}


