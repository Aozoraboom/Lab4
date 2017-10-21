import java.io.*;
import java.util.Random;
/**
* 
*
* @author George Bush
*/
public class Garph {
	/**
	* �˴�Ӧ��ע��
	*/
	int maxWeight = Integer.MAX_VALUE/2;
	/**
	* �˴�Ӧ��ע��
	*/
	static String[] words = new String[100];
	/**
	* �˴�Ӧ��ע��
	*/
	static int edge[][] = new int[100][100];
	/**
	* �˴�Ӧ��ע��
	*/
	static int  reallen;
	/**
	* �˴�Ӧ��ע��
	*/
	int weightIndex;
	public char[] weight_index;
	/**
	* 
	*
	* ע��
	*/
	public Garph() throws IOException
	{
		String[] temp;
		String[] txt = new String[100];
		System.out.println("make some differrance");
		FileReader file = new FileReader("C:\\Users\\zy\\Desktop\\dd.txt");
		BufferedReader input = new BufferedReader(file);
		String line = null;
		int index = 0, i, j, k, len;
		String reg = "[^\\p{Alpha}]+";
		while((line=input.readLine())!=null)
		{
			if (!line.equals("")){
				temp = line.split(reg);
				for (i = 0 ; i < temp.length ; i++)
				{
					txt[index] = temp[i].toLowerCase();
					index++;
				}
			}
		}
		reallen = index;
		len = index;
		index = 0;
		words[index] = txt[0];
		index++;
		for(i = 1 ; i < len ; i++)
		{
			if(match(words,txt[i]) == -1)   //���ظ�����
			{
				words[index] = txt[i];
				j = match(words,txt[i-1])==-1?index - 1:match(words,txt[i-1]);
				edge[j][index] = 1;
				index++;
			}
			else
			{
				reallen--;
				j = match(words,txt[i-1])==-1?index - 1:match(words,txt[i-1]);
				k = match(words,txt[i]);
				edge[j][k]++;
			}
		}
		for (i = 0 ; i < reallen ;i++) {
			System.out.printf("%15s",words[i]+"  ");
			for (j = 0 ; j < reallen ; j++)
				System.out.print(edge[i][j]+"   ");
			System.out.print("\n");
		}
		input.close();
	}
	//�����ַ������ַ��������λ��
	static int match(String[] template, String substr) throws IOException
	{
		int i;
		boolean find = false;
		for (i = 0 ; i < template.length ; i++)
		{
			if((template[i] != null)&&(template[i].equals(substr)))
			{
				find = true;
				break;
			}
		}
		if (find)
			return i;
		else
			return -1;
	}
	String queryBridgeWords(String word1, String word2) throws IOException
	{
		String bridgeWord = "";
		int i, j, k; //i,j�ֱ��ʾword1��word2��ͼ�е�λ��
		boolean result = false , find1 , find2 ; //��־��ͼ���ܷ��ҵ����뵥��
		find1 = false;
		find2 = false;
		for (i = 0 ; i < reallen ; i++)
			if (word1.equals(words[i]))
			{
				find1 = true;
				break;
			}
		for (j = 0 ; j < reallen ; j++)
			if (word2.equals(words[j]))
			{
				find2 = true;
				break;
			}
		if (find1 == false && find2 == false)
			return "error1";
		else if (find1 == false)
			return "error2";
		else if(find2 == false)
			return "error3";
    	for (k = 0 ; k < reallen ; k++)
			if ((k!=i)&&(k!=j)&&(edge[i][k]>0)&&(edge[k][j]>0))
			{
				bridgeWord = bridgeWord + words[k] + " ,";
				result = true;
			}
		if (result == false)
			return "error4";
		else
			return bridgeWord; 
	}
	String generateNewText(String inputText) throws IOException
	{
		String[] temp, bridgeWord;
		String newText,  result;
		int i, ran;
		String reg = "[^\\p{Alpha}]+";
		temp = inputText.split(reg);
		if (temp.length == 0)
			return "error!";
		newText = temp[0];
		for (i = 1 ; i < temp.length ; i++)
		{
			result = queryBridgeWords(temp[i-1], temp[i]);
			bridgeWord = result.split(reg);
			if (result.equals("error1")||result.equals("error2")||result.equals("error3")||result.equals("error4"))
				newText = newText + " " + temp[i];
			else if(bridgeWord.length == 1)
				newText = newText + " " + bridgeWord[0] + " " + temp[i];
			else
			{
				ran = new Random().nextInt(bridgeWord.length);
				newText = newText + " " + bridgeWord[ran] + " " + temp[i];
			}
		}
		return newText;
	}

	/**
	* 
	*
	* ע��
	*/
	public String dijkstra(String word1) throws IOException
	{  
		   int n = reallen, degree, degree2;  
		   int minweight =maxWeight;  
		   int minUn = 0; 
		   int [][] edge2 = new int[reallen][reallen];
		   int[] minmatrix = new int[reallen];// ��ŵ�ǰ��ʼ��0�������������ľ��룻  
		   boolean[] isS = new boolean[n];// �жϸ����Ƿ񱻷��ʹ�  
		   String[] route = new String[n];// ÿ���ַ�������ʾ��Ӧ��ֹ������̾����·����
		   int word1Index = match(words,word1);
		   String sentence = "";
		   if (word1Index == -1)
			   return "ERROR";
		   for (int i = 0 ; i < reallen ;i++) 
		   {
				for (int j = 0 ; j < reallen ; j++)
				{
					edge2[i][j]=edge[i][j];
					if((i!=j)&&(edge2[i][j]==0))
						edge2[i][j]=maxWeight;
				}		
				
			}
		   for (int i = 0; i < n; i++) 
		   if(i!=word1Index){// ��ʼ��  
			   minmatrix[i] = edge2[word1Index][i];  
			   isS[i] = false;  
			   route[i] = words[word1Index] +"->" + words[i];    
		   }  
		   degree2 = word1Index;
		   for (int i = 0; i < n; i++) 
		   {  		     
		   // ѡ�� ��ǰ ����� ��ͨ�ģ���ֵ��С�Ķ��㣻  
			   degree = word1Index;
			   for (int k = 0; k < n; k++) 
			   {  			     
				   if ((!isS[degree]) &&(degree != word1Index))
				   {  			     
					   if (minmatrix[degree] < minweight) 
					   {  
						   minweight = minmatrix[degree];  						     
						   minUn = degree;  			     
					   }  		     
				   }  
				   degree = (degree + 1)%reallen;
			   }  		     
			   isS[minUn] = true;// ���õ�����Ϊ�ѷ��ʣ�  	
			   degree2 = word1Index;
			   for (int j = 0; j < n; j++) 
			   {     
				   if (!isS[degree2]) 
				   {// �жϣ��ö��㻹û���뵽S��/����U-S��  				     
					   if (minweight + edge2[minUn][degree2] < minmatrix[degree2]) 
					   {  				     
					   // ͨ��������Сֵ ���ʵ�����������ľ���С��ԭ�ȵ���Сֵ ����н���ֵ  				     
						   minmatrix[degree2] = minweight + edge2[minUn][degree2];  				     
						   route[degree2] = route[minUn] + "->" + words[degree2];  		     
					   }  		     
				   }  
				   degree2 = (degree2 + 1)%reallen;
			   }  
			   minweight =maxWeight;// ��ΪҪ�ŵ���һ��ѭ���У�����һ��Ҫ������һ�£��ص����ֵ  		     
		   }  	     
		   for (int m = 0; m < n; m++) 
		   {  
			   if(m!=word1Index)			   
		   
					if (minmatrix[m] ==maxWeight) 
					{     
						System.out.println("û�е���õ��·��");  		     
					} 
					else 
					{  		     
					   		sentence=sentence+route[m]+",";     
					   //System.out.println(route[m]);  		   		   
					} 
				}  
		     
		//   }  
		   return sentence;
		     
	}
	/**
	* 
	*
	* ע��
	*/
	public String dijkstra(String word1,String word2) throws IOException
	{  
		   int n = reallen, degree, degree2;  
		   int minweight =maxWeight;  
		   int minUn = 0; 
		   int [][] edge2 = new int[reallen][reallen];
		   int[] minmatrix = new int[reallen];// ��ŵ�ǰ��ʼ��0�������������ľ��룻  
		   boolean[] isS = new boolean[n];// �жϸ����Ƿ񱻷��ʹ�  
		   String[] route = new String[n];// ÿ���ַ�������ʾ��Ӧ��ֹ������̾����·����
		   int word1Index = match(words,word1);
		   if (word1Index == -1)
			   return "ERROR";
		   int word2Index = match(words,word2);
		   if (word1Index == -1)
			   return "ERROR";
		   for (int i = 0 ; i < reallen ;i++) 
		   {
				for (int j = 0 ; j < reallen ; j++)
				{
					edge2[i][j]=edge[i][j];
					if((i!=j)&&(edge2[i][j]==0))
						edge2[i][j]=maxWeight;
				}		
					
			}
		   for (int i = 0; i < n; i++) 
		   if(i!=word1Index){// ��ʼ��  
			   minmatrix[i] = edge2[word1Index][i];  
			   isS[i] = false;  
			   route[i] = words[word1Index] +"->" + words[i];    
		   }  
		   degree2 = word1Index;
		   for (int i = 0; i < n; i++) 
		   {  		     
		   // ѡ�� ��ǰ ����� ��ͨ�ģ���ֵ��С�Ķ��㣻  
			   degree = word1Index;
			   for (int k = 0; k < n; k++) 
			   {  			     
				   if ((!isS[degree]) &&(degree != word1Index))
				   {  			     
					   if (minmatrix[degree] < minweight) 
					   {  
						   minweight = minmatrix[degree];  						     
						   minUn = degree;  			     
					   }  		     
				   }  
				   degree = (degree + 1)%reallen;
			   }  		     
			   isS[minUn] = true;// ���õ�����Ϊ�ѷ��ʣ�  	
			   degree2 = word1Index;
			   for (int j = 0; j < n; j++) 
			   {     
				   if (!isS[degree2]) 
				   {// �жϣ��ö��㻹û���뵽S��/����U-S��  				     
					   if (minweight + edge2[minUn][degree2] < minmatrix[degree2]) 
					   {  				     
					   // ͨ��������Сֵ ���ʵ�����������ľ���С��ԭ�ȵ���Сֵ ����н���ֵ  				     
						   minmatrix[degree2] = minweight + edge2[minUn][degree2];  				     
						   route[degree2] = route[minUn] + "->" + words[degree2];  		     
					   }  		     
				   }  
				   degree2 = (degree2 + 1)%reallen;
			   }  
			   minweight =maxWeight;// ��ΪҪ�ŵ���һ��ѭ���У�����һ��Ҫ������һ�£��ص����ֵ
			   
		   }
		   weightIndex=minmatrix[word2Index];
		 return  route[word2Index];
		 
		     
	}  

	String randomWalk()
	{
		int[] adj = new int[reallen]; //�����ĳ���
		int [][] markEdge = new int[reallen][reallen];//��־���Ƿ񱻷��� // NOPMD by zy on 17-10-19 ����8:08
		//String text = new String();
		int numAdj, ran, i, j, k;
		ran = new Random().nextInt(reallen);
		String text = words[ran];
		while (true) {
			j = 0;
			for (i = 0 ; i < reallen ; i++)
			{
				if (edge[ran][i]!=0) //words[ran]->words[i]������δ������
				{
					adj[j] = i;
					j++;
				}
			}
			numAdj = j;
			if (numAdj == 0)
				
				break;
			else
			{
				k = new Random().nextInt(numAdj);
				if (markEdge[ran][adj[k]] == 0)
				{
					text = text + " " + words[adj[k]];
					markEdge[ran][adj[k]] = 1;
					ran = adj[k];
				}
			
				
				else
				{
					text = text + " " + words[adj[k]];
					break;
				}
			}
		}
		return text;
	}
	/**
	* 
	*
	* ע��
	*/
	public void noteDirectedGraph(Garph g,String wor1,String word2,String word3) throws IOException
	   {
	     String [] notepath; 
		GraphViz gv1 = new GraphViz();
	      gv1.addln(gv1.startGraph());
	      int a,b,len;
	      notepath = word3.split("->");
	      for(int i=0;i<reallen;i++)
	      {
	    	  for(int j=0;j<reallen;j++)
	    	  {
	    		  
	    		  if(edge[i][j]>0) 
	    		  {
	    			  a=match(notepath,words[i]);
	    			  b=match(notepath,words[j]);
	    			  if((b - a == 1)&&(a != -1))
	    			  {
	    				  gv1.addlncolor(words[i]+"->"+words[j],""+edge[i][j]);
	    			  }
	    			  else
	    				  gv1.addlnlabel(words[i]+"->"+words[j],""+edge[i][j]);
	    		  }
	    	  }
	      }
	      
	      gv1.addln(gv1.endGraph());
	      System.out.println(gv1.getdotSource());
	     
	      
	 //     String type = "gif";
//	      String type = "dot";
//	      String type = "fig";    // open with xfig
	     // String type = "pdf";
//	      String type = "ps";
//	      String type = "svg";    // open with inkscape
	      String type = "png";
//	      String type = "plain";
	 //     File out = new File("/tmp/out." + type);   // Linux
	      File out = new File("E://graph2" + type);    // Windows
	      gv1.writeGraphToFile( gv1.getGraph( gv1.getdotSource(), type ), out );
	   }
}