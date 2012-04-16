import static java.lang.System.out;
import static java.lang.System.err;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BooleanEvaluator 
{
	static HashMap<String,binaryOperator> opMap = new HashMap<String,binaryOperator>();
	static String[] opList = {"^","&","|"};
	
	private static boolean isOperator(String input)
	{
		for(int i = 0; i < opList.length;i++)
		{
			if(opList[i].equals(input))
			{
				return true;
			}
		}
		return false;
	}
	
	private static String[] parseExpression(String input)
	{
		input = input.trim();
		input = input.replaceAll("\t", " ");
		input = input.replace("\n", " ");
		String[] array = input.split(" ");
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < array.length;i++)
		{
			if(!array[i].equals(""))
			{
				list.add(array[i]);
			}
		}
		return list.toArray(new String[list.size()]);
	}
	
	private static Queue<String> shuntingYard(String[] input)
	{
		Queue<String> output = new LinkedList<String>();
		Stack<String> opStack = new Stack<String>();
		
		for(int i = 0; i < input.length; i++)
		{
			String token = input[i];
			if(isOperator(token))
			{
				binaryOperator operator = opMap.get(token);
				if(!opStack.isEmpty())
				{
					
					binaryOperator topStack = opMap.get(opStack.peek());
					int val = operator.compareTo(topStack);
					if(val > 0 || val < 0)
					{
						opStack.push(token);
					}
					else if (val == 0)
					{
						output.add(opStack.pop());
						opStack.push(token);
					}
				}
				else
				{
					opStack.push(token);
				}
			}
			else
			{
				if(token.equals(")"))
				{
					if(opStack.isEmpty())
					{
						err.println("Error unmatched parenthesis");
						System.exit(1);
					}
					while(!(opStack.peek().equals("(")))
					{
						
						output.add(opStack.pop());
						if(opStack.isEmpty())
						{
							err.println("Error unmatched parenthesis");
							System.exit(1);
						}
					}
					opStack.pop();
				}
				else if(token.equals("("))
				{
					opStack.push(token);
				}
				else
				{
					output.add(token);
				}
			}
		}
		while(!opStack.empty())
			output.add(opStack.pop());
		return output;
	}
	
	public static void main(String[] args)
	{
		//String[] expression = parseExpression(args[0]);
		
		String[] expression = parseExpression("3 + 4 * 2 / ( 1 - 5 )");
		for(int i = 0; i < expression.length; i++)
		{
			out.print(expression[i]+", ");
		}
		out.println();
		//initialize operator map
		opMap.put("^",new binaryOperator() { public int op(int a, int b) { return (int)Math.pow(a,b); }});
		opMap.put("&",new binaryOperator() { public int op(int a, int b) { return a & b; }});
		opMap.put("|",new binaryOperator() { public int op(int a, int b) { return a | b; }});
		opMap.put("(",new binaryOperator() { public int op(int a, int b) { return a / b; }});
		opMap.get("(").precedence = 0;
		Queue<String> a = shuntingYard(expression);
		for(String s: a)
		{
			out.print(s+", ");
		}
	}
}
