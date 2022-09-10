package duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the loading and saving of data in file,
 * where data represents the list of tasks
 * to be done
 */


public class Storage {
    private static String filePath;
    private static ArrayList<Task> tasks;
    private Scanner reader;
    private File file;


    Storage(String filePath) {
        
        this.filePath = filePath;
        this.file = new File(filePath);
        this.tasks = new ArrayList<>();

    }

    /**
     * Checks and loads the tasks in file
     * @return ArrayList of tasks already in file
     * @throws DukeException Thrown if file is empty
     */
    public ArrayList<Task> load() throws DukeException {
        try {
            reader = new Scanner(file);
            if (file.length() != 0) {
                while (reader.hasNextLine()) {
                    String task = reader.nextLine();
                    String type = task.split("")[3];
                    if (type.equals("E")) {
                        String currentTask = task.substring(9, task.indexOf(" (at: "));
                        String eventTime = task.substring(task.indexOf(" (at: ") + 6, task.indexOf(")"));
                        Task curr = new Events(currentTask, eventTime);
                        tasks.add(curr);
                    } else if (type.equals("T")) {
                        String currentTask = task.substring(9);
                        Task curr = new ToDos(currentTask);
                        tasks.add(curr);
                    } else if (type.equals("D")) {
                        String currentTask = task.substring(9, task.indexOf(" (by: "));
                        String eventTime = task.substring(task.indexOf(" (by: "));
                        Task curr = new Deadline(currentTask, eventTime);
                        tasks.add(curr);
                    } else {
                        break;
                    }
                }
            } else {
                throw new DukeException("File empty");
            }
        } catch (FileNotFoundException e) {
            this.file = new File(filePath);
        } catch (DukeException e) {
           throw new DukeException("File empty");
        }
        return tasks;
    }

    /**
     * Saves tasks in file
     * @param tasks
     * @throws IOException
     */
    public static void writeToFile(ArrayList<Task> tasks) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            throw new IOException("File does not exist");
        }
        FileWriter fw = new FileWriter(filePath);
        String textToAdd = "";
        for (Task item : tasks) {
            if (item != null)
                textToAdd += tasks.indexOf(item) + 1 + "." + item + "\n";
        }
        fw.write(textToAdd);
        fw.close();
    }
}
