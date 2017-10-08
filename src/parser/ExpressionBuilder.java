package parser;

import java.util.ArrayList;
import java.util.Iterator;

import expression.Expression;
import scanner.Token;
import scanner.TokenID;

public class ExpressionBuilder {
	
	public Expression build(String expression){
		Expression exp = ExpressionParser.parse(expression);
		exp = collapse(TokenID.ADDITION, exp);
		exp = collapse(TokenID.MULTIPLICATION, exp);
		exp = collapse(TokenID.COMMA, exp);
		remove(TokenID.COMMA, exp);
		return exp;
	}

	private static void remove(TokenID tokenID, Expression expression){
		Iterator<Expression> operands = expression.getChildren().iterator();
		ArrayList<Expression> childrenToAdd = new ArrayList<Expression>();
		
		//
		while(operands.hasNext()){
			Expression exp = operands.next();
			
			if(exp.getToken().getTokenID() == TokenID.COMMA){				
				operands.remove();
				
				//
				childrenToAdd.addAll(exp.getChildren());
			} else {
				remove(tokenID, exp);
			}
		}
			
		//
		for(Expression exp : childrenToAdd){
			remove(tokenID, exp);
			expression.getChildren().add(exp);
		}
	}
	
	/**
     * Collapse the levels of the tree on a specific tokens.
     *
     * Example:-
     *
     *           +                  +
     *          / \          	  / | \
     *         2   +      => 	 2  3  4 
     *            3 4
     *
     * @param tokenID
     * @param expression
     * @return
     */
	private static Expression collapse(TokenID tokenID, Expression expression){
        Expression tree = new Expression(new Token(expression.getToken().getTokenID(), expression.getToken().getSequence()));

        for(int i = 0; i < expression.getOperandSize(); i++)
            tree.addChild(collapse(tokenID, expression.getChild(i)));

        if (tree.getToken().getTokenID() == tokenID) {
            ArrayList<Expression> u = new ArrayList<>();

            for(int i = 0; i < tree.getOperandSize(); i++){
                Expression child = tree.getChild(i);

                if (child.getToken().getTokenID() == tokenID) {
                    u.addAll(child.getChildren());
                }else{
                    u.add(child);
                }
            }

            Expression opr = new Expression(new Token(tokenID, tree.getToken().getSequence()));
            return construct(opr, u);
        }

        return tree;
    }
    
    public static Expression construct(Expression operator, ArrayList<Expression> operands) {
        for (Expression opr : operands)
            operator.addChild(opr.clone());

        return operator;
    }
}
