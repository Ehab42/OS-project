//The code below assumes that the input is in ascending order regarding the arrival time.
//Please Just run the program and open the file with the algorithm name to see the results.


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ShortestJobFirst {
	
	public static void main(String[] args) throws FileNotFoundException {
			Scanner input = new Scanner(new File("src/Input.txt"));

			File fileOut = new File("ShortestJobFirst.txt");
			PrintWriter outputStream = new PrintWriter(fileOut);

			ArrayList<String> Names = new ArrayList<>();
			ArrayList<String> Names1 = new ArrayList<>();
			ArrayList<Integer> arrivalTime = new ArrayList<>();
			ArrayList<Integer> runTime = new ArrayList<>();
			ArrayList<Integer> runTime1 = new ArrayList<>();
			ArrayList<Integer> priority = new ArrayList<>();
			ArrayList<Integer> waitingTime = new ArrayList<>();
			ArrayList<Integer> turnaroundTime = new ArrayList<>();

			while (input.hasNext()) {
				String data = input.nextLine();
				if (data.startsWith("Name")) {
					continue;
				} else {
					String[] value = data.split(",");
					Names.add(value[0]);
					arrivalTime.add(Integer.parseInt(value[1]));
					runTime.add(Integer.parseInt(value[2]));
					priority.add(Integer.parseInt(value[3]));
				}

			}

			try {

				int index = 0;
				int count = 0;
				outputStream.println("Time->Name");
				while (!(Names.isEmpty())) {
					int min = runTime.get(0);
					for (int i = 0; i < runTime.size(); i++) {
						if (runTime.get(i) < min) {
							min = runTime.get(i);
							index = i;
						}
					}

					Names1.add(Names.get(index));
					waitingTime.add(count);
					for (int i = 0; i < min; i++) {
						outputStream.println("T" + count + "->" + Names.get(index));
						count++;
					}
					runTime1.add(runTime.get(index));

					Names.remove(index);
					arrivalTime.remove(index);
					runTime.remove(index);

					if (runTime.size() == 1) {
						Names1.add(Names.get(0));
						waitingTime.add(count);
						for (int i = 0; i < runTime.get(0); i++) {
							outputStream.println("T" + count + "->" + Names.get(0));
							count++;
						}
						runTime1.add(runTime.get(0));
					}
				}
			} catch (IndexOutOfBoundsException e) {
				outputStream.println(" ");
			}

			for (int i = 0; i < Names1.size(); i++) {
				int Ttime = waitingTime.get(i) + runTime1.get(i);
				turnaroundTime.add(Ttime);
			}

			outputStream.println("Name,Waiting Time,Turnaround Time");
			for (int i = 0; i < Names1.size(); i++) {
				outputStream.println(Names1.get(i) + "," + waitingTime.get(i) + "," + turnaroundTime.get(i));
			}

			int Sum = 0;
			for (int i = 0; i < waitingTime.size(); i++) {
				Sum = Sum + waitingTime.get(i);
			}
			double averageWaiting = ((double) Sum / (double) (waitingTime.size()));

			int Sum1 = 0;
			for (int i = 0; i < turnaroundTime.size(); i++) {
				Sum1 = Sum1 + turnaroundTime.get(i);
			}

			double averageTurnaround = ((double) Sum1 / ((double) turnaroundTime.size()));

			outputStream.println("Average Turnaround,Average Waiting");
			outputStream.println(averageTurnaround + "," + averageWaiting);

			outputStream.flush();
			outputStream.close();

		
	}
}
