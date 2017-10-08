import expression.Expression;
import parser.ExpressionBuilder;

public class Main {

	public static void main(String[] args) {
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder.build("sin(x)^2");
	}

}
