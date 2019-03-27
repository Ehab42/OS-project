//The code below assumes that the input is in ascending order regarding the arrival time.
//The code below is just non-preemptive priority scheduling, but if a process with higher priority arrives it will take the CPU.
//Please Just run the program and open the file with the algorithm name to see the results.

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.attribute.standard.Sides;

public class Priority {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(new File("src/Input.txt"));

		File fileOut = new File("Priority.txt");
		PrintWriter outputStream = new PrintWriter(fileOut);

		ArrayList<String> Names = new ArrayList<>();
		ArrayList<String> Names1 = new ArrayList<>();
		ArrayList<String> Names2 = new ArrayList<>();
		ArrayList<Integer> arrivalTime = new ArrayList<>();
		ArrayList<Integer> arrivalTime1 = new ArrayList<>();
		ArrayList<Integer> arrivalTime2 = new ArrayList<>();
		ArrayList<Integer> runTime = new ArrayList<>();
		ArrayList<Integer> runTime1 = new ArrayList<>();
		ArrayList<Integer> runTime2 = new ArrayList<>();
		ArrayList<Integer> priority = new ArrayList<>();
		ArrayList<Integer> priority1 = new ArrayList<>();
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
				Names1.add(value[0]);
				Names2.add(value[0]);
				arrivalTime1.add(Integer.parseInt(value[1]));
				arrivalTime2.add(Integer.parseInt(value[1]));
				runTime1.add(Integer.parseInt(value[2]));
				runTime2.add(Integer.parseInt(value[2]));
				priority1.add(Integer.parseInt(value[3]));
			}
		}

		int count = 0;
		outputStream.println("Time->Name");
		int [] startTime = new int[Names.size()];
		int [] finsihTime = new int[Names.size()];
		 
		while (!(Names.isEmpty())) {
			int min = priority.get(0);
			int index = 0;
			for (int i = 0; i < priority.size(); i++) {
				if (priority.get(i) < min) {
					min = priority.get(i);
					index = i;
				}
			}
			if (count >= arrivalTime.get(index)) {
				startTime[index] = arrivalTime2.get(index);
				for (int j = 0; j < runTime.get(index); j++) {
					outputStream.println("T" + count + "->" + Names.get(index));
					count++;
				}
				finsihTime[index] = count;

				Names.remove(index);
				arrivalTime.remove(index);
				runTime.remove(index);
				priority.remove(index);
			} else {

				Names1.remove(index);
				arrivalTime1.remove(index);
				runTime1.remove(index);
				priority1.remove(index);

				int min1 = priority1.get(0);
				int index1 = 0;
				for (int i = 0; i < priority1.size(); i++) {
					if (priority1.get(i) < min) {
						min1 = priority1.get(i);
						index1 = i;
					}
				}

				int x = 0;
				for (int j = 0; j < runTime1.get(index1); j++) {
					outputStream.println("T" + count + "->" + Names1.get(index1));
					count++;
					for (int i = 0; i < Names.size(); i++) {
						if (Names1.get(index1) == Names.get(i)) {
							x = i;
							runTime.set(i, (runTime.get(i) - 1));
						}
					}

					if (count >= arrivalTime.get(index)) {
						break;
					}
				}
			}

		}
		
		for (int i = 0; i < finsihTime.length; i++) {
			int Ttime = finsihTime[i] - startTime[i];
			turnaroundTime.add(Ttime);
		}
		
		for (int i = 0; i < finsihTime.length; i++) {
			int Wtime = turnaroundTime.get(i) - runTime2.get(i);
			waitingTime.add(Wtime);
		}
		
		outputStream.println("Name,Waiting Time,Turnaround Time");
		for (int i = 0; i < runTime2.size(); i++) {
			outputStream.println(Names2.get(i) + "," + waitingTime.get(i) + "," + turnaroundTime.get(i));
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
