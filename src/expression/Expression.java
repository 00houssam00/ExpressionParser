package expression;


import java.util.ArrayList;

import scanner.Token;
import scanner.TokenID;

public class Expression {
	
	// ============================================================
	// Variables
	// ============================================================
	private Token token;
	private Expression parent;
	private ArrayList<Expression> children;
	
    // ============================================================
    // Constructors
    // ============================================================
	public Expression(Token token){
		this.token = token;
		this.children = new ArrayList<Expression>();
	}
	
	// ============================================================
    // Methods
    // ============================================================
	public void addChild(Expression operand){
		operand.parent = this;
		children.add(operand);
	}
	
	public void addChild(int index, Expression operand) {
		operand.parent = this;
        children.add(index, operand);
    }
	
	public Expression removeChild(int index){
        return children.remove(index);
	}
	
	public Expression clone(){
		Expression expression = new Expression(getToken().clone());
        clone(this, expression);
        return expression;
	}
	
	public void clone(Expression tree, Expression clone){
        for(int i = 0; i < tree.getOperandSize(); i++){
            Expression child = tree.getChild(i);
            Expression copy = new Expression(child.getToken().clone());
            clone.addChild(copy);
            clone(tree.getChild(i), clone.getChild(i));
        }
    }
	
	// ============================================================
    // GETTERS / SETTERS
    // ============================================================	
	public ArrayList<Expression> getChildren(){
	    return children;
	}
	
	public int getOperandSize(){
	    return children.size();
	}
	  
	public Expression getChild(int i){
	     return children.get(i);
	}
	  
	public Token getToken(){
		return token;
	}
	
	public Expression getParent(){
		return parent;
	}
}
