public class HighRollerGame {

    public static void main(String[] args) {
        var console = System.console();
        DiceSet diceSet = null;
        var highest = 0;
        System.out.println("Welcome " + Die.SIX_SIDED_DIE_EMOJI.repeat(5));
        while (true) {
            System.out.println();
            try {
                var command = console.readLine("Enter a command (h for help): ").trim();

                if (command.matches("h(elp)?")) {
                    showHelp();

                } else if (command.matches("q(uit)?")) {
                    System.out.println("I'm glad you played today. You look great!");
                    break;

                } else if (command.matches("use\\s+\\d\\d?\\s+\\d\\d?")) {
                    var tokens = command.split("\\s+");
                    var sides = Integer.parseInt(tokens[2].trim());
                    var number = Integer.parseInt(tokens[1].trim());
                    diceSet = new DiceSet(sides, number);
                    highest = Math.max(highest, diceSet.sum());
                    System.out.println("You are now using a " + diceSet.descriptor());
                    System.out.println(diceSet);

                } else if (command.matches("roll\\s+all")) {
                    if (diceSet == null) {
                        throw new IllegalStateException("You don't have any dice yet");
                    }
                    diceSet.rollAll();
                    highest = Math.max(highest, diceSet.sum());
                    System.out.println(diceSet);

                } else if (command.matches("roll\\s+\\d+")) {
                    if (diceSet == null) {
                        throw new IllegalStateException("You don't have any dice yet");
                    }
                    diceSet.rollIndividual(Integer.parseInt(command.substring(4).trim()));
                    highest = Math.max(highest, diceSet.sum());
                    System.out.println(diceSet);

                } else if (command.matches("high(est)?")) {

                    if (highest == 0) {
                          System.out.println("No highest score yet.");

                    } else {
                          System.out.println(highest);
                    }

                } else {
                    System.out.println("I don't understand");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void showHelp() {
        System.out.println("Enter 'h or help': Prints all commands and what they do.");
        System.out.println("Enter 'q or quit': Quits the program.");
        System.out.println("Enter 'use <s><n>': Rolls a dice set of n number of dice and s sides on each die.");
        System.out.println("Enter 'roll all': Rolls all dice in the current set.");
        System.out.println("Enter 'roll <i>': Rolls the ith die in the set.");
        System.out.println("Enter 'high or highest': Prints the current high score.");
    }
}
