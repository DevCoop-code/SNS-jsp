package expertisesns.KMP;

public class PrefixTable 
{
	private char[] P = null;
	private int[] F = null;
	
	private int P_length = 0;
	
	public PrefixTable(String P)
	{
		this.P = P.toCharArray();
		this.P_length = this.P.length;
		
		this.F = new int[P_length];
	}
	
	public void makePrefixTable()
	{
		int i=1;
		int j=0;
		F[0]=0;
		
		while(i < P_length)
		{
			if(P[i] == P[j])
			{
				F[i] = j + 1;
				i++;
				j++;
			}else if(j > 0)
			{
				j = F[j - 1];
			}else
			{
				F[i] = 0;
				i++;
			}
		}
		
		//for(int temp=0; temp<P_length; temp++)
		//{
			//System.out.print(F[temp] + ", ");
		//}
	}
	
	public int GetPrefixTable(int z)
	{
		return F[z];
	}
}
