/*
 * Project11.java
 * 
 *   A program that reads in a text file that uses a specific input format and uses it to
 *   produce a formatted report for output.
 *   
 *   See the write-up for Homework Lab 13 for more details.
 *   
 *   This skeleton is more "skeletal" than previous labs.  You MUST break your code up into
 *   more methods than what you see here.  Consider methods to display the formatted report
 *   as well as more methods to compute values for the report.
 * 
 * @author Paul M Onderisin
 * @id Onderisin.1
 * 
 */

/*
 * I did not use the method readNextSeries() method, could not think of a way
 * to implement it. I created my own.
 * 
 * Also the getAverage(), i created my own called getMean(). I forgot that the former option
 * was there.
 */
package osu.cse1223;

import java.io.*;
import java.util.*;

public class Project11 {

	public static void main(String[] args) throws FileNotFoundException {

		// Sets the Scanner
		Scanner scan = new Scanner(System.in);

		/*
		 * Place holder variables for the input and output file
		 */
		String fileName = "";
		String outputFile = "";

		while (true) {
			/*
			 * Gets the Input FileName
			 */
			System.out.print("Enter an input file name: ");
			fileName = getFileName(scan);

			/*
			 * Gets the Output FileName
			 */
			System.out.print("Enter an output file name: ");
			outputFile = getFileName(scan);
			
			System.out.println(); // Prints open line

			if (!fileName.equalsIgnoreCase(outputFile))
				break;
		}

		/*
		 * This Try/catch will open the file and if unable will print a reason
		 * why it can not.
		 */
		try (Scanner in = new Scanner(new File(fileName))) {

			PrintWriter pw = null; 		//Initializes the varible so I can access it in the finally block of the try catch to close the PrintWriter.
			
			/*
			 * This try and catch will attemp to write to the file. If unable
			 * too, it will catch the error and tell the user
			 */
			try {

				pw = new PrintWriter(outputFile); //Initializes the PrintWriter
				
				printReport(pw, "", 0, 0, 0, 0, 1); // Prints the Initial text
													// format to
													// the file

				readFile(pw, in); // Send the File to be read and processed

			} catch (IOException e) {
				System.out.println("Could not write file: " + outputFile);
			} finally {
				pw.close(); // Closes the PrintWriter the file may be written.
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not open file: " + fileName);
		}

	}

	/*
	 * This Method takes Strings and numbers and formats them into their proper
	 * place
	 */
	private static void printReport(PrintWriter pw, String string, int i, int j, int k, int l, int m) {
		switch (m) {
		case 1:
			pw.printf("%-20s    %6s  %6s  %4s  %4s", "Name", "Mean", "Median", "Max", "Min");
			pw.println();
			pw.printf("%-20s    %6s  %6s  %4s  %4s", "--------------------", "------", "------", "----", "----");
			pw.println();
			break;
		case 0:
			pw.printf("%-20s    %6s  %6s  %4s  %4s", string, i, j, k, l);
			pw.println();
			break;
		}
	}

	/*
	 * Reads from the file top to bottom and prints the formated information The
	 * method then processes the information and passes them onto different
	 * methods depending on what needs to be done. This can handle more users
	 * that get added into the inputfile
	 */
	private static void readFile(PrintWriter pw, Scanner in) {
		int count = 0;
		boolean isString;
		int people = 0;
		String name = "";

		// The Highesy and lowest values
		String Highest = "";
		String Lowest = "";
		int Highest_high = 0;
		int Lowest_low = 0;

		ArrayList<Integer> ix = new ArrayList<Integer>();
		while (in.hasNext()) {

			String s = in.nextLine();

			try {
				int i = Integer.valueOf(s);
				if (i != -1) {
					ix.add(i);
					count = count + i;
				} else {
					Collections.sort(ix); // Sorts the ArrayList

					int mean = getMean(ix); // gets the mean value of the
											// ArrayList
					int median = getMedian(ix);
					int max = getMax(ix);
					int min = getMin(ix);

					if (Highest_high == 0) {
						Highest_high = mean;
						Lowest_low = mean;
						Highest = name;
						Lowest = name;
					} else if (mean > Highest_high) {
						Highest_high = mean;
						Highest = name;
					} else if (mean < Lowest_low) {
						Lowest_low = mean;
						Lowest = name;
					}

					printReport(pw, name, mean, median, max, min, 0);

					ix.clear();
				}

				isString = false;
			} catch (NumberFormatException NFE) {
				isString = true;
			}

			if (isString) {
				people++;
				name = s;
			}
		}

		pw.println();
		pw.println("Total number of participants: " + people);
		pw.println("Highest average score: " + Highest + " (" + Highest_high + ")");
		pw.println("Lowest average score: " + Lowest + "(" + Lowest_low + ")");

	}

	/*
	 * Does the opposite of getMax(), this method will get the minimun value of
	 * a Given ArrayList
	 */
	private static int getMin(ArrayList<Integer> ix) {
		return ix.get(0);
	}

	/*
	 * Given an arraylist of Integers, this method will find the median value of
	 * the list
	 */
	private static int getMedian(ArrayList<Integer> ix) {

		if (ix.size() % 2 == 0) {
			int i = ix.get(ix.size() / 2);
			int z = ix.get((ix.size() / 2) - 1);

			int y = (z + i) / 2;

			return y;
		} else {
			return ix.get(ix.size() / 2);
		}
	}

	/*
	 * Given an ArrayList the method will sort the list and return the max
	 * integer in the list
	 */
	private static int getMax(ArrayList<Integer> in) {

		return in.get(in.size() - 1);
	}

	// Given a Scanner as input read in a list of integers one at a time until a
	// negative
	// value is read from the Scanner. Store these integers in an
	// ArrayList<Integer> and
	// return the ArrayList<Integer> to the calling program.
	private static ArrayList<Integer> readNextSeries(Scanner inScanner) {
		return null;

	}

	// Given a ArrayList<Integer> of integers, compute the median of the list
	// and return it to
	// the calling program.
	private static int getMean(ArrayList<Integer> inList) {
		int i = 0;

		for (int x : inList) {
			i = i + x;
		}

		i = i / inList.size();

		return i;
	}

	// Given a ArrayList<Integer> of integers, compute the average of the list
	// and return it to
	// the calling program.
	private static int getAverage(ArrayList<Integer> inList) {
		return 0;
	}

	/*
	 * This Method just prompts the user for a filename
	 */
	private static String getFileName(Scanner in) {
		return in.nextLine();
	}

}
