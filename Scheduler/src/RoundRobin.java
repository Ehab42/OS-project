//The code below assumes that the input is in ascending order regarding the arrival time.
//Please Just run the program and open the file with the algorithm name to see the results.
//After running please enter a time quantum for the processes.

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class RoundRobin {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(new File("src/Input.txt"));

		System.out.println("Please enter a suitable time quantum:");
		Scanner sc = new Scanner(System.in);
		int TimeQuantum = sc.nextInt();

		File fileOut = new File("RoundRobin.txt");
		PrintWriter outputStream = new PrintWriter(fileOut);

		ArrayList<String> Names = new ArrayList<>();
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
				runTime1.add(Integer.parseInt(value[2]));
				priority.add(Integer.parseInt(value[3]));
			}

		}

		int Time = 0;
		outputStream.println("Time->Name");
		int s = Names.size();
		int[] finishTime = new int[s];
		int count = 0;

		while (Collections.max(runTime) > 0) {
			for (int i = 0; i < Names.size(); i++) {

				if (Time < arrivalTime.get(i)) {

					i--;
					for (int j = 0; j < TimeQuantum; j++) {
						outputStream.println("T" + Time + "->" + Names.get(i));
						Time++;
						runTime.set(i, (runTime.get(i) - 1));
						if (runTime.get(i) == 0) {
							finishTime[i] = Time;
						}
					}

				} else {
					if (count < 5) {
						waitingTime.add(Time);
					}
					count++;
					for (int j = 0; j < TimeQuantum; j++) {
						if (runTime.get(i) > 0) {
							outputStream.println("T" + Time + "->" + Names.get(i));
							Time++;
							runTime.set(i, (runTime.get(i) - 1));
							if (runTime.get(i) == 0) {
								finishTime[i] = Time;
							}
						}
					}
				}
			}

		}

		for (int i = 0; i < Names.size(); i++) {
			int Ttime = finishTime[i] - arrivalTime.get(i);
			turnaroundTime.add(Ttime);
		}

		outputStream.println("Name,Waiting Time,Turnaround Time");
		for (int i = 0; i < Names.size(); i++) {
			outputStream.println(Names.get(i) + "," + waitingTime.get(i) + "," + turnaroundTime.get(i));
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
