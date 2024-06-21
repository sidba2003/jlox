package src.main.java.com.interpreter.jlox;

public class AstReversePolishNotation implements Expr.Visitor<String> {
    public String printRPN(Expr expr){
        return expr.accept(this);
    }
    @Override
    public String visitBinaryExpr(Expr.Binary expr){
        return reversePolishNotation(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override 
    public String visitGroupingExpr(Expr.Grouping expr){
        return reversePolishNotation("", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr){
        if (expr.value == null) return "";
        return reversePolishNotation(expr.value.toString());
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr){
        return "(" + reversePolishNotation(expr.operator.lexeme, expr.right) + ")";
    }

    private String reversePolishNotation(String operator, Expr... exprs){
        StringBuilder rpnString = new StringBuilder();

        for (Expr expr : exprs){
            rpnString.append(expr.accept(this));
        }
        rpnString.append(operator);

        return rpnString.toString();
    }

    public static void main(String[] args){
        Expr expr = new Expr.Binary(
            new Expr.Binary(
                new Expr.Literal(1), new Token(TokenType.PLUS, "+", null, 1), new Expr.Literal(2)
            ),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Binary(
                new Expr.Literal(4), new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(3)
            )
        );

        System.out.println(new AstReversePolishNotation().printRPN(expr));
    }
}
