import java.io.*;
import java.util.Random;
//B1上修改
//B2上修改
//B2上修改
public class garph {
	int  MAX_WEIGHT = Integer.MAX_VALUE/2;
	static String[] words = new String[100];
	static int edge[][] = new int[100][100];
	int  reallen;
	int weight_index;
	public garph() throws IOException
	{
		String[] temp;
		String[] txt = new String[100];
		System.out.println("make some differrance");
		FileReader file = new FileReader("C:\\Users\\Administrator\\Desktop\\sentence.txt");
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
			if(match(words,txt[i]) == -1)   //无重复出现
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
	//返回字符串在字符串数组的位置
	static int match(String[] Template, String Substr) throws IOException
	{
		int i;
		boolean find = false;
		for (i = 0 ; i < Template.length ; i++)
		{
			if((Template[i] != null)&&(Template[i].equals(Substr)))
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
		String Bridge_word = "";
		int i, j, k; //i,j分别表示word1，word2在图中的位置
		boolean result = false , find1 , find2 ; //标志在图中能否找到输入单词
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
				Bridge_word = Bridge_word + words[k] + " ,";
				result = true;
			}
		if (result == false)
			return "error4";
		else
			return Bridge_word; 
	}
	String generateNewText(String inputText) throws IOException
	{
		String[] temp, Bridge_word;
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
			Bridge_word = result.split(reg);
			if (result.equals("error1")||result.equals("error2")||result.equals("error3")||result.equals("error4"))
				newText = newText + " " + temp[i];
			else if(Bridge_word.length == 1)
				newText = newText + " " + Bridge_word[0] + " " + temp[i];
			else
			{
				ran = new Random().nextInt(Bridge_word.length);
				newText = newText + " " + Bridge_word[ran] + " " + temp[i];
			}
		}
		return newText;
	}

	
	public String Dijkstra(String word1) throws IOException
	{  
		   int n = reallen, degree, degree2;  
		   int minweight = MAX_WEIGHT;  
		   int minUn = 0; 
		   int [][] edge2 = new int[reallen][reallen];
		   int[] minmatrix = new int[reallen];// 存放当前起始点0到其余各个顶点的距离；  
		   boolean[] isS = new boolean[n];// 判断各个是否被访问过  
		   String[] route = new String[n];// 每个字符串是显示对应终止顶点最短距离的路径；
		   int word1_index = match(words,word1);
		   String sentence = "";
		   if (word1_index == -1)
			   return "ERROR";
		   for (int i = 0 ; i < reallen ;i++) 
		   {
				for (int j = 0 ; j < reallen ; j++)
				{
					edge2[i][j]=edge[i][j];
					if((i!=j)&&(edge2[i][j]==0))
						edge2[i][j]=MAX_WEIGHT;
				}		
				
			}
		   for (int i = 0; i < n; i++) 
		   if(i!=word1_index){// 初始化  
			   minmatrix[i] = edge2[word1_index][i];  
			   isS[i] = false;  
			   route[i] = words[word1_index] +"->" + words[i];    
		   }  
		   degree2 = word1_index;
		   for (int i = 0; i < n; i++) 
		   {  		     
		   // 选择 当前 和起点 连通的，且值最小的顶点；  
			   degree = word1_index;
			   for (int k = 0; k < n; k++) 
			   {  			     
				   if ((!isS[degree]) &&(degree != word1_index))
				   {  			     
					   if (minmatrix[degree] < minweight) 
					   {  
						   minweight = minmatrix[degree];  						     
						   minUn = degree;  			     
					   }  		     
				   }  
				   degree = (degree + 1)%reallen;
			   }  		     
			   isS[minUn] = true;// 将该点设置为已访问；  	
			   degree2 = word1_index;
			   for (int j = 0; j < n; j++) 
			   {     
				   if (!isS[degree2]) 
				   {// 判断：该顶点还没加入到S中/属于U-S；  				     
					   if (minweight + edge2[minUn][degree2] < minmatrix[degree2]) 
					   {  				     
					   // 通过当下最小值 访问到得其他顶点的距离小于原先的最小值 则进行交换值  				     
						   minmatrix[degree2] = minweight + edge2[minUn][degree2];  				     
						   route[degree2] = route[minUn] + "->" + words[degree2];  		     
					   }  		     
				   }  
				   degree2 = (degree2 + 1)%reallen;
			   }  
			   minweight = MAX_WEIGHT;// 因为要放到下一个循环中，所以一定要重设置一下，回到最大值  		     
		   }  	     
		   for (int m = 0; m < n; m++) 
		   {  
			   if(m!=word1_index)			   
			   {
					if (minmatrix[m] == MAX_WEIGHT) 
					{     
						System.out.println("没有到达该点的路径");  		     
					} 
					else 
					{  		     
					   		sentence=sentence+route[m]+",";     
					   //System.out.println(route[m]);  		   		   
					}
			   }
				}  
		     
		//   }  
		   return sentence;
		     
	}
	public String Dijkstra(String word1,String word2) throws IOException
	{  
		   int n = reallen, degree, degree2;  
		   int minweight = MAX_WEIGHT;  
		   int minUn = 0; 
		   int [][] edge2 = new int[reallen][reallen];
		   int[] minmatrix = new int[reallen];// 存放当前起始点0到其余各个顶点的距离；  
		   boolean[] isS = new boolean[n];// 判断各个是否被访问过  
		   String[] route = new String[n];// 每个字符串是显示对应终止顶点最短距离的路径；
		   int word1_index = match(words,word1);
		   if (word1_index == -1)
			   return "ERROR";
		   int word2_index = match(words,word2);
		   if (word2_index == -1)
			   return "ERROR";
		   for (int i = 0 ; i < reallen ;i++) 
		   {
				for (int j = 0 ; j < reallen ; j++)
				{
					edge2[i][j]=edge[i][j];
					if((i!=j)&&(edge2[i][j]==0))
						edge2[i][j]=MAX_WEIGHT;
				}		
					
			}
		   for (int i = 0; i < n; i++) 
		   if(i!=word1_index){// 初始化  
			   minmatrix[i] = edge2[word1_index][i];  
			   isS[i] = false;  
			   route[i] = words[word1_index] +"->" + words[i];    
		   }  
		   degree2 = word1_index;
		   for (int i = 0; i < n; i++) 
		   {  		     
		   // 选择 当前 和起点 连通的，且值最小的顶点；  
			   degree = word1_index;
			   for (int k = 0; k < n; k++) 
			   {  			     
				   if ((!isS[degree]) &&(degree != word1_index))
				   {  			     
					   if (minmatrix[degree] < minweight) 
					   {  
						   minweight = minmatrix[degree];  						     
						   minUn = degree;  			     
					   }  		     
				   }  
				   degree = (degree + 1)%reallen;
			   }  		     
			   isS[minUn] = true;// 将该点设置为已访问；  	
			   degree2 = word1_index;
			   for (int j = 0; j < n; j++) 
			   {     
				   if (!isS[degree2]) 
				   {// 判断：该顶点还没加入到S中/属于U-S；  				     
					   if (minweight + edge2[minUn][degree2] < minmatrix[degree2]) 
					   {  				     
					   // 通过当下最小值 访问到得其他顶点的距离小于原先的最小值 则进行交换值  				     
						   minmatrix[degree2] = minweight + edge2[minUn][degree2];  				     
						   route[degree2] = route[minUn] + "->" + words[degree2];  		     
					   }  		     
				   }  
				   degree2 = (degree2 + 1)%reallen;
			   }  
			   minweight = MAX_WEIGHT;// 因为要放到下一个循环中，所以一定要重设置一下，回到最大值
			   
		   }
		   weight_index=minmatrix[word2_index];
		 return  route[word2_index];
		 
		     
	}  

	String randomWalk()
	{
		int[] adj = new int[reallen]; //储存点的出点
		int [][] mark_edge = new int[reallen][reallen];//标志边是否被访问
		String text = new String();
		int num_adj, ran, i, j, k;
		ran = new Random().nextInt(reallen);
		text = words[ran];
		while (true) {
			j = 0;
			for (i = 0 ; i < reallen ; i++)
			{
				if (edge[ran][i]!=0) //words[ran]->words[i]存在且未被访问
				{
					adj[j] = i;
					j++;
				}
			}
			num_adj = j;
			if (num_adj == 0)
				
				break;
			else
			{
				k = new Random().nextInt(num_adj);
				if (mark_edge[ran][adj[k]] == 0)
				{
					text = text + " " + words[adj[k]];
					mark_edge[ran][adj[k]] = 1;
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
	public void noteDirectedGraph(garph G,String wor1,String word2,String word3) throws IOException
	   {
	     String [] notepath; 
		GraphViz gv1 = new GraphViz();
	      gv1.addln(gv1.start_graph());
	      int a,b;
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
	      
	      gv1.addln(gv1.end_graph());
	      System.out.println(gv1.getDotSource());
	     
	      
	 //     String type = "gif";
//	      String type = "dot";
//	      String type = "fig";    // open with xfig
	     // String type = "pdf";
//	      String type = "ps";
//	      String type = "svg";    // open with inkscape
	      String type = "png";
//	      String type = "plain";
	 //     File out = new File("/tmp/out." + type);   // Linux
	      File out = new File("E://graph2." + type);    // Windows
	      gv1.writeGraphToFile( gv1.getGraph( gv1.getDotSource(), type ), out );
	   }
}

