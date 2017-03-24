/*
 * Graph.java
 * Huid: 21240443 41240032
 * CS124
 * Feb/26/2017
 */

/*
 *  Vertex class
 *  Vertex is the vertex of the graph
 */
class Vertex {
	// compute edge weight
	public float computeWeight(Vertex v){        
        return 0.f;
    }
}

/*
 * Vertex0 class extends Vertex
 * Vertex0 is the vertex in the first condition:
 * Complete graphs on n vertices, where the weight of each edge is a real number chosen uniformly at random on [0, 1].
 */
class Vertex0 extends Vertex{
	// compute edge weight
    public float computeWeight(Vertex v){        
        return (float)Math.random();
    }
}

/*
 * Vertex2d class extends Vertex
 * Vertex2d is the vertex in the second condition:
 * Complete graphs on n vertices, where the vertices are points chosen uniformly at random inside the unit square. 
 * (That is, the points are (x,y), with x and y each a real number chosen uniformly at random from [0, 1].) 
 * The weight of an edge is just the Euclidean distance between its endpoints.
 */
class Vertex2d extends Vertex{
	// coordinate
    float x,y;
	
    // constructor
    public Vertex2d(){
        this((float)Math.random(),(float)Math.random());
    }
    
	// constructor
    public Vertex2d(float x, float y){
        this.x = x;
        this.y = y;
    }
    
    // compute edge weight
    public float computeWeight(Vertex _v){   
    	Vertex2d v = (Vertex2d)_v;
        float weight = (float)Math.sqrt((this.x-v.x)*(this.x-v.x)+(this.y-v.y)*(this.y-v.y)) ;
        return weight;
    }        
}

/*
 * Vertex3d class extends Vertex
 * Vertex3d is the vertex in the second condition:
 * Complete graphs on n vertices, where the vertices are points chosen uniformly at random inside the unit cube
 *  (3 dimensions). the weight of an edge is just the Euclidean distance between its endpoints.
 */
class Vertex3d extends Vertex{
	// coordinate
	float x,y,z;
	
	// constructor
    public Vertex3d(){
        this((float)Math.random(),(float)Math.random(),(float)Math.random());
    }
    
	// constructor
    public Vertex3d(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
	// compute edge weight
    public float computeWeight(Vertex _v){   
    	Vertex3d v = (Vertex3d)_v;
        float weight = (float)Math.sqrt((this.x-v.x)*(this.x-v.x)+(this.y-v.y)*(this.y-v.y)+(this.z-v.z)*(this.z-v.z)) ;
        return weight;
    }   
}

/*
 * Vertex4d class extends Vertex
 * Vertex4d is the vertex in the second condition:
 * Complete graphs on n vertices, where the vertices are points chosen uniformly at random inside the unit 
 * hypercube (4 dimensions). the weight of an edge is just the Euclidean distance between its endpoints.
 */
class Vertex4d extends Vertex{
	// coordinate
    float x,y,z,t;
    
    // constructor
    public Vertex4d(){
        this((float)Math.random(),(float)Math.random(),(float)Math.random(),(float)Math.random());
    }
    
	// constructor
    public Vertex4d(float x, float y, float z, float t){
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }
    
	// compute edge weight
    public float computeWeight(Vertex _v){     
    	Vertex4d v = (Vertex4d)_v;
        float weight = (float)Math.sqrt((this.x-v.x)*(this.x-v.x)+(this.y-v.y)*(this.y-v.y)
        		+(this.z-v.z)*(this.z-v.z)+(this.t-v.t)*(this.t-v.t)) ;
        return weight;
    }
}

/*
 * Graph class
 */
public class Graph {
	// number of vertices
    private int vertexNum;      
    
    // vertices array
    Vertex v[];       
    
    // constrctor
    public Graph(int dimension, int n){
    	vertexNum = n;
    	
       	//0 graph
    	if(dimension == 0){
    		v = new Vertex0[n];        
            for(int i=0; i< n; i++){
                v[i] = new Vertex0();            
            }     
    	}
    	//2d graph
    	else if(dimension == 2){
            v = new Vertex2d[n];        
            for(int i=0; i< n; i++){
                v[i] = new Vertex2d();            
            }                
    	}
        //3d graph
    	else if(dimension == 3){
            v = new Vertex3d[n];        
            for(int i=0; i< n; i++){
                v[i] = new Vertex3d();            
            }                
    	}
    	//4d graph
    	else if(dimension == 4){
            v = new Vertex4d[n];        
            for(int i=0; i< n; i++){
                v[i] = new Vertex4d();            
            }
    	}else{
    		System.out.println("wrong dimension");
    	}		
    }
    
    // finding mst method, using Prim's algorithm. Returns the sum of weight of mst
    public float findMST(){
        float dist[] = new float[vertexNum];
        int[] prev = new int[vertexNum];
        Heap myHeap = new Heap(vertexNum);
        boolean[] mySet = new boolean[vertexNum];
        float sum = 0.f;
        
        myHeap.insert(0.f,0);
        for(int i=0; i<vertexNum; i++){
            dist[i]=100.f;
            prev[i]=-1; 
        }
        dist[0] = 0.f;
                
        while(myHeap.getSize() != 0){            
            int nodeVertex =  myHeap.extractMin();
            sum += dist[nodeVertex];
            mySet[nodeVertex] = true;            
            
            for (int i = 0; i < vertexNum; i++){                
                if(mySet[i] == false && i != nodeVertex){                    
                    float dist2 = v[nodeVertex].computeWeight(v[i]);
                    if (dist[i]>dist2){
                        dist[i]=dist2;
                        prev[i]=nodeVertex;
                        myHeap.insert(dist[i],i);
                    }
                }
            }
        }
        return sum;    
    }
    
    public static void main(String args[]){
        
    	// calculating the average weight of mst of different dimensions and vertex numbers
        int numpoints = Integer.parseInt(args[1]);
        int trails = Integer.parseInt(args[2]);
        int dimension = Integer.parseInt(args[3]);
        
        float sumMST = 0;
	    for(int i = 0; i < trails; i++){
	        Graph myGraph = new Graph(dimension,numpoints);
	        float mst = myGraph.findMST();
	            sumMST += mst;
        }
        System.out.println(sumMST/trails+" "+numpoints+" "+trails+" "+dimension);
    	
    	// test code for average weights of msts on 128,256,512...131072
        /*
    	int dimension = 0;
        int numpoints = 131072*8;
        int trails = 1;
        int power = 1;
        for(int j = 0; j < power; j++){
	        float sumMST = 0;
	        System.out.println(numpoints);
	        for(int i = 0; i < trails; i++){
		        Graph myGraph = new Graph(dimension,numpoints);
		        float mst = myGraph.findMST();
		        	//System.out.println(mst);
		            sumMST += mst;
	        }
	        System.out.println(sumMST/trails);
	        numpoints *= 2;
        }
    	

    	// test code for running time
        int type = 3;
        int vertNum = 131072;
        int times = 1;
        int power = 1;
        for(int j = 0; j < power; j++){
        	float sumMST = 0;
        	System.out.println(vertNum);
        	
        	//timer starts
        	long startMili=System.currentTimeMillis();
    		System.out.println("start"+startMili);
    		
        	for(int i = 0; i < times; i++){
        		Graph myGraph = new Graph(type,vertNum);
        		float mst = myGraph.findMST();
        		sumMST += mst;        		
        	}
        	System.out.println(sumMST/times);
        	
        	//timer ends
    		long endMili=System.currentTimeMillis();
    		System.out.println("end"+endMili);
    		System.out.println("time: "+(endMili-startMili)+"ms");	
        	vertNum *= 2;
        }  
		*/

    }   
}

