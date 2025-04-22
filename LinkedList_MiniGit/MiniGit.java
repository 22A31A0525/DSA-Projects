import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Node class representing each commit
class CommitNode {
    int commitId;
    CommitNode prev;
    CommitNode next;
    String commitMsg;
    String fileContent;
    String timestamp;

    // Constructor to initialize a commit
    public CommitNode(int id, String commitMsg, String fileContent, String timeStamp) {
        this.commitId = id;
        this.commitMsg = commitMsg;
        this.next = null;
        this.prev = null;
        this.fileContent = fileContent;
        this.timestamp = timeStamp;
    }

    // Prints file content (private)
    private void getFileContent() {
        System.out.println(this.fileContent);
    }

    // Public method to expose file content
    public void getFileData() {
        this.getFileContent();
    }
}

// Main version control class using doubly linked list
class VersionControlSystem {
    private Map<String, Branch> branches = new HashMap<>();
    private Branch currentBranch;
    private int commitCounter = 0;  // Unique ID for each commit

    public VersionControlSystem(Scanner sc) {
        // Create the initial commit and branch
        CommitNode initialCommit = null;
        Branch mainBranch = new Branch("main", initialCommit);
        branches.put("main", mainBranch);
        currentBranch = mainBranch;
    }

    // Set the current branch to the one with the given name
    public void setCurrBranch(String name) {
        currentBranch = branches.get(name);
        if (currentBranch == null) {
            System.out.println("Branch not found.");
        } else {
            System.out.println("Switched to branch: " + name);
        }
    }

    // Create a new branch from the current branch
    public void createBranch(String branchName, Scanner sc) {
        Branch newBranch = new Branch(branchName, null);
        branches.put(branchName, newBranch);
        this.currentBranch = newBranch;
        System.out.println("Branch '" + branchName + "' created successfully.");
        
        // Prompt for commit message and file content after branch creation
        System.out.print("Enter commit message: ");
        String msg = sc.nextLine();
        System.out.print("Enter file content: ");
        String content = sc.nextLine();
        
        // Commit the changes to the new branch
        this.commit(msg, content);
    }

    // Commit a new change
    public void commit(String commitMsg, String fileContent) {
        // Generate current timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time_stamp = LocalDateTime.now().format(formatter);

        // Create a new commit node
        CommitNode new_node = new CommitNode(commitCounter++, commitMsg, fileContent, time_stamp);

        // If this is the first commit for the branch
        if (currentBranch.head == null) {
            currentBranch.head = new_node;
            currentBranch.tail = new_node;
        } else {
            // Append to the end of the linked list
            currentBranch.tail.next = new_node;
            new_node.prev = currentBranch.tail;
            currentBranch.tail = new_node;
        }

        System.out.println("Successfully Committed at " + time_stamp);
    }

    // Delete a specific commit by ID
    public void delete(int commitId) {
        if (currentBranch.head == null) {
            System.out.println("No commits to delete.");
            return;
        }

        CommitNode temp = currentBranch.head;

        while (temp != null) {
            if (temp.commitId == commitId) {
                // If there's only one commit
                if (temp == currentBranch.head && temp == currentBranch.tail) {
                    currentBranch.head = null;
                    currentBranch.tail = null;
                }
                // If deleting the first commit
                else if (temp == currentBranch.head) {
                    currentBranch.head = currentBranch.head.next;
                    if (currentBranch.head != null) currentBranch.head.prev = null;
                }
                // If deleting the last commit
                else if (temp == currentBranch.tail) {
                    currentBranch.tail = currentBranch.tail.prev;
                    if (currentBranch.tail != null) currentBranch.tail.next = null;
                }
                // If deleting a middle commit
                else {
                    temp.prev.next = temp.next;
                    temp.next.prev = temp.prev;
                }

                System.out.println("Commit ID " + commitId + " deleted successfully.");
                return;
            }
            temp = temp.next;
        }

        System.out.println("Commit ID not found.");
    }

    // Revert to a specific commit
    public void revertToCommit(int commitID) {
        CommitNode current = currentBranch.tail;

        while (current != null) {
            if (current.commitId == commitID) {
                System.out.println("Reverting to commit ID: " + commitID);
                System.out.println("File Content Restored: " + current.fileContent);

                // Create a new commit with reverted content
                String commitMsg = "Revert to commit ID " + commitID;
                commit(commitMsg, current.fileContent);
                return;
            }
            current = current.prev;
        }

        System.out.println("Commit ID not found.");
    }

    // Checkout the latest commit
    public void checkout() {
        if (currentBranch.tail != null) {
            currentBranch.head = currentBranch.tail;
            currentBranch.head.prev = null;
            currentBranch.tail = currentBranch.head;
            System.out.println("Checked out to the latest commit: #" + currentBranch.tail.commitId);
        } else {
            System.out.println("No commits to checkout.");
        }
    }

    // Display all commit history
    public void historyOfCommits() {
        
        Branch branch = this.currentBranch;
        if (branch == null) {
            System.out.println("Branch not found.");
            return;
        }

        CommitNode temp = branch.head;
        System.out.println("\nCommit History for Branch '" + currentBranch.name + "':");
        while (temp != null) {
            System.out.println(" ID: " + temp.commitId +
                               " | Msg: " + temp.commitMsg +
                               " | Time: " + temp.timestamp +
                               " | Content: " + temp.fileContent);
            temp = temp.next;
        }
    }

    // Show full details about each branch
    public void BranchDetails() {
        for (Branch branch : branches.values()) {
            this.currentBranch = branch;
            this.historyOfCommits();
        }
    }
}

// Branch class to handle multiple branches
class Branch {
    String name;
    CommitNode head;
    CommitNode tail;

    public Branch(String name, CommitNode baseCommit) {
        this.name = name;
        this.head = baseCommit;
        this.tail = baseCommit;
    }
}

// Main class to interact with the version control system
public class MiniGit {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  // Only one Scanner instance
        VersionControlSystem vcs = new VersionControlSystem(sc);

        while (true) {
            System.out.println("\n==== Mini Git Version Control ====");
            System.out.println("1. Commit a Change");
            System.out.println("2. View Commit History");
            System.out.println("3. Revert to Commit");
            System.out.println("4. Delete a Commit");
            System.out.println("5. Checkout Latest Commit");
            System.out.println("6. Create a New Branch");
            System.out.println("7. Switch to a Branch");
            System.out.println("8. Full Details About Each Branch");
            System.out.println("9. Exit");
            System.out.print("Choose an operation: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter commit message: ");
                    String msg = sc.nextLine();
                    System.out.print("Enter file content: ");
                    String content = sc.nextLine();
                    vcs.commit(msg, content);
                    break;
                case 2:
                    vcs.historyOfCommits();
                    break;
                case 3:
                    System.out.print("Enter commit ID to revert to: ");
                    int revertId = sc.nextInt();
                    vcs.revertToCommit(revertId);
                    break;
                case 4:
                    System.out.print("Enter commit ID to delete: ");
                    int deleteId = sc.nextInt();
                    vcs.delete(deleteId);
                    break;
                case 5:
                    vcs.checkout();
                    break;
                case 6:
                    System.out.print("Enter new branch name: ");
                    String branchName = sc.nextLine();
                    vcs.createBranch(branchName, sc);  // Pass the Scanner for input
                    break;
                case 7:
                    System.out.print("Enter branch name to switch to: ");
                    String branchToSwitch = sc.nextLine();
                    vcs.setCurrBranch(branchToSwitch);
                    break;
                case 8:
                    vcs.BranchDetails();
                    break;
                case 9:
                    System.out.println("Exiting Mini Git. Bye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }
}
