import javax.script.*;
import java.util.Map;

public class LogicalExpressionEvaluator {
    
    public boolean evaluate(Map<String, Boolean> criteriaMatchMap, String expression){
         // Substitute values from the criteria match map into the expression
         for (String criteria : criteriaMatchMap.keySet()) {
            expression = expression.replaceAll("(?<!\\w)" + criteria + "(?!\\w)", String.valueOf(criteriaMatchMap.get(criteria)));
        }

        // Replace logical operators with symbols
        expression = expression.replaceAll("AND", "&&");
        expression = expression.replaceAll("OR", "||");

        System.out.println("Expression: " + expression);

        // Evaluate the expression
        boolean result = evaluateExpression(expression);
        System.out.println("Evaluation Result: " + result);
        return result;
    }

    private static boolean evaluateExpression(String expression) {
        try {
            // Create a new script engine
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            // Evaluate the expression using the engine
            Object result = engine.eval(expression);
            // Convert the result to a boolean value
            return Boolean.parseBoolean(result.toString());
        } catch (ScriptException e) {
            System.out.println("Error evaluating expression: " + e.getMessage());
            return false;
        }
    }
}

//sample inputs
//criteriaMatchMap = {four=true, one=true, two=false, three=true}
//expression = "three OR (one AND two)"
