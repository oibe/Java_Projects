abstract class binaryOperator implements Comparable<binaryOperator>
{
	protected int precedence = 2;

	abstract public int op(int a, int b);

	public int compareTo(binaryOperator b)
	{
		return this.precedence - b.precedence;
	}
}
