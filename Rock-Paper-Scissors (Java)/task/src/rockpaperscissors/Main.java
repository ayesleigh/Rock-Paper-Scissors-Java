package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    static List<String> options = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int userScore = 0;

        System.out.print("Enter your name: ");
        String name = capitalize(scanner.nextLine());
        System.out.printf("Hello, %s%n", name);

        File file = new File(".\\rating.txt");
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNext()) {
                String line = reader.nextLine();
                String[] lineAsArray = line.split(" ");
                if (lineAsArray[0].equalsIgnoreCase(name)) {
                    userScore = Integer.parseInt(lineAsArray[1]);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String userOptions = scanner.nextLine();
        if (userOptions.isEmpty()) {
            options.add("rock");
            options.add("paper");
            options.add("scissors");
        } else {
            options = List.of(userOptions.split(","));
        }

        System.out.println("Okay, let's start");

        while (true) {
            String userChoiceString = scanner.nextLine().toLowerCase();
            
            if ("!exit".equals(userChoiceString)) {
                System.out.println("Bye!");
                break;
            }

            if ("!rating".equals(userChoiceString)) {
                System.out.printf("Your rating: %d%n", userScore);
                continue;
            }

            int userChoiceIndex = options.indexOf(userChoiceString);
            if (userChoiceIndex == -1) {
                System.out.println("Invalid input");
                continue;
            }

            int computerChoiceIndex = random.nextInt(options.size());
            String computerChoiceString = options.get(computerChoiceIndex);

            if (userChoiceString.equalsIgnoreCase(computerChoiceString)) {
                userScore += 50;
                System.out.printf("There is a draw (%s)%n",
                        computerChoiceString);
                continue;
            }

            List<String> losingOptions = new LinkedList<>();

            for (int i = 0, count = userChoiceIndex + 1; i < options.size() / 2; i++, count++) {
                if (count > options.size() - 1) {
                    count = 0;
                }
                losingOptions.add(options.get(count));
            }

            if (losingOptions.contains(computerChoiceString)) {
                System.out.printf("Sorry, but the computer chose " +
                        "%s%n", computerChoiceString);
            } else {
                userScore += 100;
                System.out.printf(
                        "Well done. The computer chose " + "%s and" +
                                " failed%n", computerChoiceString);
            }
        }
    }

    public static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
