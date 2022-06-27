import java.util.ArrayList;
import java.util.Scanner;

public class Mastermind {

    public static void main(String[] args) {

        // Scanner needed to collect player inputs.
        Scanner sc = new Scanner(System.in);

        // Creation of an array with 4 colors
        ArrayList<String> mastermind = generateGameColors();

        // Initialisation of turn count.
        int turn = 1;

        // ASCII Art for game main title
        System.out.println("___________________________________________________________________________");
        System.out.println("  __  __     _      _____ _______ ______ _____  __  __ _____ _   _ _____");
        System.out.println(" |  \\/  |   / \\    / ____|__   __|  ____|  __ \\|  \\/  |_   _| \\ | |  __ \\ ");
        System.out.println(" | \\  / |  /   \\  | (___    | |  | |__  | |__) | \\  / | | | |  \\| | |  | |");
        System.out.println(" | |\\/| | / /_\\ \\  \\___ \\   | |  |  __| |  _  /| |\\/| | | | | . ` | |  | |");
        System.out.println(" | |  | |/ _____ \\ ____) |  | |  | |____| | \\ \\| |  | |_| |_| |\\  | |__| |");
        System.out.println(" |_|  |_/_/     \\_\\_____/   |_|  |______|_|  \\_\\_|  |_|_____|_| \\_|_____/");
        System.out.println("___________________________________________________________________________");
        System.out.println();


        System.out.println("The game is ready!");
        System.out.println("Input 4 different colors, separated by spaces ' '.");
        System.out.println("Colors are: red, yellow, green, blue, orange, white, purple et fuchsia");

        // Game Loop. Will run until player wins or uses all turns.
        while(turn <= 12) {

            System.out.println(mastermind); // line to uncomment to show the answer.

            System.out.println("Turn " + turn + "/12 - Quelle est votre proposition ?");
            System.out.println("e.g.: 'red yellow green blue'");
            System.out.print("#==> ");

            String proposition = sc.nextLine();
            String[] playerProposition = proposition.split(" ");


            boolean validProposition = isPropositionValid(playerProposition);

            // WHILE loop to ensure the player proposition meets the game criteria.
            while (!validProposition) {
                System.out.println("Incorrect proposition.");
                System.out.println("Input 4 different colors, separated by spaces ' '.");
                System.out.println("Colors are: red, yellow, green, blue, orange, white, purple et fuchsia");
                System.out.print("#==> ");

                proposition = sc.nextLine();
                playerProposition = proposition.split(" ");

                validProposition = isPropositionValid(playerProposition);

            }


            int[] correction = checkPlayerAnswer(mastermind, playerProposition);

            if (correction[0] == 4) {
                break;
            } else {
                System.out.println("There are " + correction[0] + " well-placed colors.");
                System.out.println("There are " + correction[1] + " correct but misplaced colors.");
            }

            // Incrementing turn variable to meet while loop stop condition.
            turn++;

        }

        // Based on the number of loops in the WHILE above win or lose message.
        if (turn > 12) {
            System.out.println("Sorry, you ran out of trials!" +
                    " Better luck next time!");
        } else {
            System.out.println("Congratulations! You solved this MasterMind!");
            System.out.println("Trials needed: " + (turn));
        }

        // Closing Scanner instance.
        sc.close();
    }

    /* Method generateGameColors():
    This method returns an ArrayList of 4 Strings for the game.
    This ArrayList will be returned by the method after being randomly populated.
     */
    static ArrayList<String> generateGameColors() {

        // Creation of the ArrayList returned for the game.
        ArrayList<String> gameColors = new ArrayList<>();

        // Creation of an ArrayList containing all the available colors.
        ArrayList<String> colors = new ArrayList<String>();
        colors.add("red");
        colors.add("yellow");
        colors.add("green");
        colors.add("blue");
        colors.add("orange");
        colors.add("white");
        colors.add("purple");
        colors.add("fuchsia");


        // This FOR loop is used to populate the ArrayList gameColors.
        for (int i = 0; i < 4; i++) {

            /* On each turn, a random integer is picked to get a color.
            The range of the randomisation is based on the number of
            colors left in the ArrayList.
             */
            int index = getRandomIndex(colors.size() - 0.1);

            // The final ArrayList is populated with the color.
            gameColors.add(colors.get(index));


            /* The color picked is removed from the list, making it unavailable.
            It ensures that a color is only picked once. */
            colors.remove(index);
        }

        return gameColors;
    }

    /* Method getRandomIndex(range):
    This method pick a random integer between 0 and a given range (a double included).
    Multiplication by range minus 0.1 gives a probability of roughly 12.9% for each integer
    to be drawn by the method.
     */
    static int getRandomIndex(double range) {

        return (int) Math.floor(Math.random() * range);

    }

    /* Method isPropositionValid(tab):
    This method returns a boolean value based on several checks that ensure the proposition of
    the player match the game requirements.
     */
    static boolean isPropositionValid(String[] tab) {

        // Check 1: The proposition must be 4 Strings.
        if (tab.length != 4) {
            return false;
        }

        // Check 2: The proposition must be free of duplicates.
        for (int i = 0; i < tab.length; i++) {
            int count = 0;
            for (int j = 0; j < tab.length; j++) {
                if (tab[i].equals(tab[j])) {
                    count++;
                }
            }
            if (count > 1) {
                return false;
            }
        }

        // ArrayList of available-playable colors.
        ArrayList<String> colors = new ArrayList<String>();
        colors.add("red");
        colors.add("yellow");
        colors.add("green");
        colors.add("blue");
        colors.add("orange");
        colors.add("white");
        colors.add("purple");
        colors.add("fuchsia");

        // Check 3: The proposition must match the available colors.
        for (int i = 0; i < tab.length; i++) {
            if (!colors.contains(tab[i])) {
                return false;
            }
        }

        // If all checks are passed, the proposition is considered valid => return true.
        return true;

    }

    /* Method checkPlayerAnswer(answer, proposition):
    This method checks the player proposition to check how many colors are well-placed
    and how many are misplaced.
    It returns an Array of integers of size 2. Index 0 is the number of well-placed colors.
    Index 1 is the number of misplaced colors.
     */
    static int[] checkPlayerAnswer(ArrayList<String> answer, String[] proposition) {
        // Initialisation of the int[] with a size of 2.
        int[] colorCheck = new int[2];

        // Initialisation of the variable storing the number of well-placed colors.
        int match = 0;

        // Initialisation of the variable storing the number of correct colors found.
        int inside = 0;

        // FOR loop to check the value of each item in 'answer' vs player 'proposition'.
        for (int i = 0; i < answer.size(); i++) {

            // IF block incrementing the variable 'match' when a color is well-placed.
            if (proposition[i].equals(answer.get(i))) {
                match++;
            }

            // IF block incrementing the variable 'inside' when a color proposed is in the ArrayList 'answer'.
            if (answer.contains(proposition[i])) {
                inside++;
            }
        }
        // Value of 'match' (well-placed colors) stored inside index 0 of the returned Array.
        colorCheck[0] = match;

        // Value of 'inside' minus 'match' (all good colors minus the well-placed) to give the misplaced ones.
        colorCheck[1] = inside - match;


        return colorCheck;
    }


}
