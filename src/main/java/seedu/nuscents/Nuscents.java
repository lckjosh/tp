package seedu.nuscents;

import seedu.nuscents.commands.Command;
import seedu.nuscents.commands.ExitCommand;
import seedu.nuscents.data.exception.NuscentsException;
import seedu.nuscents.parser.Parser;
import seedu.nuscents.storage.Storage;
import seedu.nuscents.ui.Ui;
import seedu.nuscents.data.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Nuscents {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    /**
     * Sets up the required objects and loads up the data from the storage file.
     * @param filePath path of the file used to store data
     */
    public Nuscents (String filePath) throws IOException {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.readDataFromFile());
        } catch (FileNotFoundException e) {
            Ui.showReadDataError();
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            file.createNewFile();
            tasks = new TaskList(storage.readDataFromFile());
        }
    }

    /**
     * Runs the program until termination.
     */
    public void run() {
        Ui.showWelcomeMessage();
        runProgramUntilTermination();
    }

    /**
     * Reads the user command and executes it, until the user issues the "bye" command.
     * This method continuously prompts the user for commands and executes them until the user enters "bye".
     */
    private void runProgramUntilTermination() {
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.getUserCommand();
                Command command = Parser.parseCommand(fullCommand, tasks);
                command.execute(tasks);
                isExit = ExitCommand.isExit(command);
                storage.writeToFile(tasks);
            } catch (NuscentsException e) {
                ui.showException(e);
            } catch (IOException e) {
                Ui.showLine();
                System.out.println("Something went wrong: " + e.getMessage());
                Ui.showLine();
            } catch (IndexOutOfBoundsException e) {
                ui.showException(e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Nuscents("./data/nuscents.txt").run();
    }
}