package expertisesns.KMP;

public class MatchingAlgorithm 
{
	private int T_length = 0;
	private int P_length = 0;
	
	private char[] T = null;
	private char[] P = null;
	
	PrefixTable prefix = null;
	public MatchingAlgorithm(String T, String P)
	{
		this.T = T.toCharArray();
		this.P = P.toCharArray();
		
		T_length = this.T.length;
		P_length = this.P.length;
		
		prefix = new PrefixTable(P);
		prefix.makePrefixTable();
	}
	
	/*
	 * Return 값 : -1 ==> 일치하지 않음
	 * Return 값 : > 0 ==> 일치함
	 */
	public int KMP()
	{
		int i=0;
		int j=0;
		
		while(i<T_length)
		{
			if(T[i] == P[j])
			{
				if(j == P_length-1)
					return i-j;
				else
				{
					i++;
					j++;
				}
			}else if(j > 0)
			{
				j = prefix.GetPrefixTable(j-1);
			}
			else
				i++;
		}
		return -1;
	}
}
