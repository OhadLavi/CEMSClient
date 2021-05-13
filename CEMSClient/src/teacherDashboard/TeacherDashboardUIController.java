package teacherDashboard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.GeneralUIMethods;
import util.Navigator;

public class TeacherDashboardUIController implements Initializable {

	@FXML
	private VBox menuVBox;

	@FXML
	private JFXToggleButton darkModeToggleBtn;

	@FXML
	private ImageView logoImg;

	@FXML
	private Label teacherDashboardLbl;

	@FXML
	private JFXButton testBankBtn;

	@FXML
	private JFXButton questionBankBtn;

	@FXML
	private JFXButton viewActiveTestsBtn;

	@FXML
	private JFXButton scheduledTestsBtn;

	@FXML
	private JFXButton testReportsBtn;

	@FXML
	private JFXButton checkTestsBtn;

	@FXML
	private JFXButton signOutBtn;

	@FXML
	private AnchorPane contentPaneAnchor;

	@FXML
	private AnchorPane anchorLogin;

	@FXML
	private StackPane popUpWindow;

	private Node testBank;
	private Node questionBank;
	private Node activeTests;
	private Node login;
	private Node checkTests;
	private Node scheduledTests;

	/**
	 * clicking test bank will open the test bank page.
	 * 
	 * @param event
	 */
	@FXML
	void testBankClicked(MouseEvent event) {
		GeneralUIMethods.loadPage(contentPaneAnchor, testBank);
		GeneralUIMethods.setMenuStyle(testBankBtn, menuVBox);
	}

	/**
	 * clicking question bank will open the question bank page.
	 *
	 * @param event
	 */
	@FXML
	void questionBankClicked(MouseEvent event) {
		GeneralUIMethods.loadPage(contentPaneAnchor, questionBank);
		GeneralUIMethods.setMenuStyle(questionBankBtn, menuVBox);
	}

	/**
	 * clicking view active test will open the active test page.
	 *
	 * @param event
	 */
	@FXML
	void viewActiveTestClicked(MouseEvent event) {
		GeneralUIMethods.loadPage(contentPaneAnchor, activeTests);
		GeneralUIMethods.setMenuStyle(viewActiveTestsBtn, menuVBox);
	}

	/**
	 * clicking check tests will go to the check tests screen
	 * 
	 * @param event
	 */
	@FXML
	void checkTestsClicked(MouseEvent event) {
		GeneralUIMethods.loadPage(contentPaneAnchor, checkTests);
		GeneralUIMethods.setMenuStyle(checkTestsBtn, menuVBox);

	}
	
    @FXML
    void scheduledTestsClicked(MouseEvent event) {
    	GeneralUIMethods.loadPage(contentPaneAnchor, scheduledTests);
		GeneralUIMethods.setMenuStyle(scheduledTestsBtn, menuVBox);
    }

	/**
	 * clicking sign out will go back to the login screen
	 * 
	 * @param event
	 */
	@FXML
	void clickSignOut(MouseEvent event) {
		GeneralUIMethods.signOut(contentPaneAnchor, anchorLogin, menuVBox, login);
	}

	/**
	 * initializes all the FXML files for easier access.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		GeneralUIMethods.setPopupPane(popUpWindow);
		GeneralUIMethods.setSideBar(menuVBox);
		try {
			testBank = FXMLLoader.load(getClass().getResource(Navigator.TEST_BANK.getVal()));
			questionBank = FXMLLoader.load(getClass().getResource(Navigator.QUESTION_BANK.getVal()));
			activeTests = FXMLLoader.load(getClass().getResource(Navigator.VIEW_ACTIVE_TESTS.getVal()));
			login = FXMLLoader.load(getClass().getResource(Navigator.LOGIN.getVal()));
			checkTests = FXMLLoader.load(getClass().getResource(Navigator.CHECK_TESTS.getVal()));
			scheduledTests = FXMLLoader.load(getClass().getResource(Navigator.SCHEDULED_TESTS.getVal()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
