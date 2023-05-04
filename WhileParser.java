import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import javax.swing.JFileChooser;

public class WhileParser {
    
    private static final String[] OPERATORS = {"<", ">", "==", ">=", "<=", "!="};
    
    public static void main(String[] args) {
        File file = getFileFromDialog();
        if (file == null) {
            System.out.println("No se ha seleccionado ningún archivo.");
            return;
        }
        
        String input = readFile(file);
        int[] stats = new int[3]; // stats[0] = total de variables, stats[1] = total de operadores de comparación, stats[2] = total de whiles
        
        boolean isValid = validateInput(input, stats);
        System.out.println("Resultado: " + isValid);
        System.out.println("Total de variables: " + stats[0]);
        System.out.println("Total de operadores de comparación: " + stats[1]);
        System.out.println("Total de whiles: " + stats[2]);
    }
    
    private static String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: archivo no encontrado.");
        }
        return sb.toString();
    }
    
    private static boolean validateInput(String input, int[] stats) {
        String[] tokens = input.split("\\s+");
        Stack<String> stack = new Stack<>();
        Set<String> variables = new HashSet<>();
        int comparisons = 0;
        boolean isInWhileBlock = false;
        boolean isValid = true;

        for (String token : tokens) {
            switch (token) {
                case "while":
                    stack.push(token);
                    isInWhileBlock = true;
                    stats[2]++;
                    break;
                case "{":
                    if (!stack.isEmpty() && stack.peek().equals("while")) {
                        stack.push(token);
                    } else {
                        isValid = false;
                    }
                    break;
                case "}":
                    if (!stack.isEmpty() && stack.peek().equals("{")) {
                        stack.pop();
                        isInWhileBlock = false;
                    } else {
                        isValid = false;
                    }
                    break;
                default:
                    if (isInWhileBlock) {
                        if (token.matches("[a-zA-Z]+[0-9]*")) { // variable
                            variables.add(token);
                        } else if (token.matches("[0-9]+") || isComparisonOperator(token)) { // constante o operador de comparación
                            if (isComparisonOperator(token)) {
                                comparisons++;
                            }
                        } else {
                            isValid = false;
                        }
                    }
                    break;
            }
        }

        if (!stack.isEmpty()) {
            isValid = false;
        }

        if (variables.size() == 0) {
            isValid = false;
        }

        stats[0] = variables.size();
        stats[1] = comparisons;

        return isValid;
    }

    private static boolean isComparisonOperator(String token) {
        return token.matches("[><=!]+");
    }

    
    private static File getFileFromDialog() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }
}

