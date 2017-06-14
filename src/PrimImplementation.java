//Program to implement Heap data structure and also implement Prim's algorithm using the same heap data structure 

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class PrimImplementation {
	static int[][] adjacencymatrix = new int[10001][10001];
	static int u, v;
	static int[] heap;
	static int[] index;//array to store the index of element in heap
	static int[] key;//array to store value of element
	static int sizeofheap = 0;
	static int ret;

	public static void main(String[] args) throws IOException {
		
		try {
			String opPath="output.txt";  //to direct and write output to output.txt file
			String ipPath="input.txt";  //to retrieve query terms from input.txt file
			
			File file = new File(ipPath);
			Scanner scanner = new Scanner(file);
			PrintStream fw=new PrintStream(opPath);

			String string = scanner.nextLine();
			String[] i = string.split(" ");
			int vertices = Integer.parseInt(i[0]);
			//form Adjacency Matrix to store weight of the edges
			while (scanner.hasNextLine()) {
				String ar = scanner.nextLine();
				String[] f = ar.split(" ");

				int u1 = Integer.parseInt(f[0]);
				int v1 = Integer.parseInt(f[1]);
				adjacencymatrix[u1][v1] = Integer.parseInt(f[2]);
				adjacencymatrix[v1][u1] = Integer.parseInt(f[2]);

			}

			heap = new int[vertices + 1];
			index = new int[vertices + 1];
			key = new int[vertices + 1];
			prim(vertices,fw); //to implement Prim's algorithm

		} catch (Exception e)

		{
			e.printStackTrace();
			System.out.println(e);
		}
	}
    //function to insert elements in heap
	public static void insert(int v, int key_value) {
		sizeofheap = sizeofheap + 1;
		heap[sizeofheap] = v;
		index[v] = sizeofheap;
		key[v] = key_value;
		Heapify_up(sizeofheap);
	}
  
	public static void Heapify_up(int i) {
		int j;
		int temp;
		while (i > 1) {
			j = i / 2;
			j = Math.floorDiv(i, 2);
			if (key[heap[i]] < key[heap[j]]) {
				temp = heap[i];
				heap[i] = heap[j];
				heap[j] = temp;
				index[heap[i]] = i;
				index[heap[j]] = j;
				i = j;
			} else {
				break;
			}
		}
	}

	public static int extractmin() {
		ret = heap[1];
		heap[1] = heap[sizeofheap];
		index[heap[1]] = 1;
		sizeofheap = sizeofheap - 1;
		Heapify_down(1);
		return ret;
	}

	public static void Heapify_down(int l) {
		int j;
		int temp = 0;
		while ((2 * l) <= sizeofheap) {
			if ((2 * l) == sizeofheap || (key[heap[2 * l]] <= key[heap[(2 * l) + 1]])) {
				j = (2 * l);
			} else {
				j = (2 * l) + 1;
			}
			if (key[heap[j]] < key[heap[l]]) {
				temp = heap[l];
				heap[l] = heap[j];
				heap[j] = temp;
				index[heap[l]] = l;
				index[heap[j]] = j;
				l = j;
			} else {
				break;
			}
		}
	}

	public static void decrease_key(int v, int key_value) {
		key[v] = key_value;
		Heapify_up(index[v]);
	}

	public static void prim(int numberofvertices, PrintStream fw)throws IOException
	{
		int total_weight = 0;
		int min;
		HashMap<Integer, Integer> precedingV = new HashMap<Integer, Integer>();
		int[] d = new int[numberofvertices + 1];
		d[1] = 0; // distance
		ArrayList<Integer> visited = new ArrayList<Integer>();

		for (int y = 2; y <= numberofvertices; y++) {
			d[y] = Integer.MAX_VALUE;
		}

		for (int w = 1; w <= numberofvertices; w++) {
			insert(w, d[w]);
		}
		while (visited.size() < numberofvertices) {
			min = extractmin();
			visited.add(min);
			total_weight = total_weight + key[min];
			for (int g = 1; g <= numberofvertices; g++) {
				if (adjacencymatrix[g][min] != 0) {
					if (!visited.contains(g)) {
						if ((adjacencymatrix[min][g]) < d[g]) {
							d[g] = adjacencymatrix[min][g];
							decrease_key(g, d[g]);
							precedingV.put(g, min);//inserting preceding vertex of g
						}
					}
				}
			}
		}
		
		fw.println(total_weight);//prints the total weight of the minimum spanning tree
		for (int m=2;m<=visited.size();m++) {
			 if (m != 1)
			fw.println(precedingV.get(m) + " " + m);
		}

	}
}
