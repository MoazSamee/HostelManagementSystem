import javafx.stage.Stage;
import view.Administrator.AdminPage;
import view.MaintenanceStaff.MaintenanceStaffView;
import view.Student.StudentPage;
import view.comman.signup_page;
import view.comman.AddStaffDialog;
import view.comman.signin_page;;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        // signup_page.main(args);
        signin_page.main(args);
        // AdminPage.main(args);
        // StudentPage.main(args);
        // testmaintenanceStaff();

        // AddStaffDialog.main(args);
    } 
}

// import model.Database.database;

// public class App {
//     public static void main(String[] args) throws Exception {
//         System.out.println("Hello, World!");
//         database db = new database();
//     } 
// }