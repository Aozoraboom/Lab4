import java.io.File;
public class Proba
{
	//B1涓婁慨鏀�
	//B2涓婁慨鏀�
  // private static final int MAX_WEIGHT = 0;
public static void main(String[] args)
   {
 
      //p.showDirectedGraph();
//      p.start2();
   }
/**
    * Construct a DOT graph in memory, convert it
    * to image and store the image in the file system.
    */
   @SuppressWarnings({ "static-access" })
public void showDirectedGraph(garph G)
   {
      GraphViz gv = new GraphViz();
      gv.addln(gv.start_graph());
      for(int i=0;i<G.reallen;i++)
      {
    	  for(int j=0;j<G.reallen;j++)
    	  {
    		  if(G.edge[i][j]>0)
    		  {
    		  gv.addlnlabel(G.words[i]+"->"+G.words[j],""+G.edge[i][j]);
    		  }
    	  }
      }
   
      
      //gv.addln("A -> C;");
      gv.addln(gv.end_graph());
      System.out.println(gv.getDotSource());
     
      
 //     String type = "gif";
//      String type = "dot";
//      String type = "fig";    // open with xfig
     // String type = "pdf";
//      String type = "ps";
//      String type = "svg";    // open with inkscape
      String type = "png";
//      String type = "plain";
 //     File out = new File("/tmp/out." + type);   // Linux
      File out = new File("E://graph1" + type);    // Windows
      gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
   }
   
   /**
    * Read the DOT source from a file,
    * convert to image and store the image in the file system.
    */
   private void start2()
   {
 //     String dir = "/home/jabba/eclipse2/laszlo.sajat/graphviz-java-api";     // Linux
 //     String input = dir + "/sample/simple.dot";
    String input = "c:/eclipse.ws/graphviz-java-api/sample/simple.dot";    // Windows
    
    GraphViz gv = new GraphViz();
    gv.readSource(input);
    System.out.println(gv.getDotSource());
     
      String type = "gif";
//    String type = "dot";
//    String type = "fig";    // open with xfig
//    String type = "pdf";
//    String type = "ps";
//    String type = "svg";    // open with inkscape
//    String type = "png";
//      String type = "plain";
    File out = new File("/tmp/simple." + type);   // Linux
//    File out = new File("c:/eclipse.ws/graphviz-java-api/tmp/simple." + type);   // Windows
    gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
   }
   
}
