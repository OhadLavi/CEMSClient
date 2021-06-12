package teacherDashboard;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import client.ClientController;
import common.Course;
import common.Question;
import common.Teacher;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import util.GeneralUIMethods;
import util.Navigator;
import util.PopUp;

public class AddingNewTestUIController implements Initializable {

	private String isEditingTest = null;
	private static int Screen = 0;
	private Node QuestionForm, testBank;
	private QuestionFormUIController questionFormUIController;
	private String testTitle, duration, course, studentInst, teacherInst, field;
	private Set<Question> pickedQuestions;
	boolean flag = false;
	ArrayList<Question> questions;
	ObservableList<String> fields = FXCollections.observableArrayList();
	ObservableList<String> courses = FXCollections.observableArrayList();
	ObservableList<String> durationList = FXCollections.observableArrayList();

	@FXML
	private AnchorPane contentPaneAnchor;

	@FXML
	private AnchorPane setParametersAnchor;

	@FXML
	private AnchorPane insidesetParametersAnchor;

	@FXML
	private AnchorPane parameterTitleAnchor;

	@FXML
	private AnchorPane insideparameterTitleAnchor;

	@FXML
	private TableView<QuestionRow> questionTable;

	@FXML
	private TableColumn<?, ?> selectCol;

	@FXML
	private TableColumn<?, ?> idCol;

	@FXML
	private TableColumn<?, ?> authorCol;

	@FXML
	private TableColumn<?, ?> textCol;

	@FXML
	private TableColumn<?, ?> viewCol;

	@FXML
	private VBox parametersVBox;

	@FXML
	private JFXComboBox<String> selectFieldCBox;

	@FXML
	private JFXComboBox<String> selectCourseCBox;

	@FXML
	private JFXTextField titleTxt;

	@FXML
	private JFXComboBox<String> durationCbox;


	@FXML
	private JFXTextArea teacherInstructionsTxtArea;

	@FXML
	private JFXTextArea studentInstructionsTxtArea;

	@FXML
	private AnchorPane testAnchor;

	@FXML
	private ScrollPane testScrollPane;

	@FXML
	private Label headTitleLbl;

	@FXML
	private JFXButton backBtn;

	@FXML
	private JFXButton finishBtn;

	@FXML
	private JFXButton previewTestBtn;

	@FXML
	private JFXButton continueWithParametersBtn;

	@FXML
	private StackPane popUpWindow;

	public TableView<QuestionRow> getQuestionTable() {
		return questionTable;
	}

	public JFXComboBox<String> getSelectFieldCBox() {
		return selectFieldCBox;
	}

	public JFXComboBox<String> getSelectCourseCBox() {
		return selectCourseCBox;
	}

	public JFXTextField getTitleTxt() {
		return titleTxt;
	}


	public JFXTextArea getTeacherInstructionsTxtArea() {
		return teacherInstructionsTxtArea;
	}

	public Set<Question> getPickedQuestions() {
		return pickedQuestions;
	}

	public void setPickedQuestions(Set<Question> pickedQuestions) {
		this.pickedQuestions = pickedQuestions;
	}

	public JFXTextArea getStudentInstructionsTxtArea() {
		return studentInstructionsTxtArea;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setEditingTest(String isEditingTest) {
		this.isEditingTest = isEditingTest;
	}

	public class QuestionRow {
		private String id;
		private String author;
		private String text;
		private JFXButton viewBtn;
		private CheckBox checkBox;

		public QuestionRow(Question question) {
			id = question.getID();
			author = question.getAuthorID();
			text = question.getQuestionText();
			viewBtn = new JFXButton();
			viewBtn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EYE));
			checkBox = new CheckBox();
		}

		public JFXButton getViewBtn() {
			return viewBtn;
		}

		public CheckBox getCheckBox() {
			return checkBox;
		}

		public String getID() {
			return id;
		}

		public String getAuthor() {
			return author;
		}

		public String getText() {
			return text;
		}

	}

	/**
	 * initialize a combo box with the relevant fields
	 *
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (int i = 30; i <= 180; i += 5)
			durationList.add(String.valueOf(i));
		durationCbox.setItems(durationList);
		popUpWindow.toBack();
		pickedQuestions = new HashSet<>();
		if (ClientController.getRoleFrame().equals("Teacher")) {
			Teacher teacher = (Teacher) ClientController.getActiveUser();
			String[] fieldsSplit = teacher.getFields().split("~");
			for (String oneField : fieldsSplit)
				fields.add(oneField);
		}

		selectFieldCBox.setItems(fields);
		selectFieldCBox.setOnAction(event -> {
			questionTable.getItems().clear();
			ClientController.accept("QUESTION_BANK-" + selectFieldCBox.getValue());
			questions = ClientController.getQuestions();
			idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
			textCol.setCellValueFactory(new PropertyValueFactory<>("text"));
			authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
			viewCol.setCellValueFactory(new PropertyValueFactory<>("viewBtn"));
			selectCol.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
			ClientController.accept("GET_COURSES_BY_FIELD-" + selectFieldCBox.getValue());
			courses.clear();
			if (ClientController.getCourses() == null || ClientController.getCourses().isEmpty()) {
				System.out.println("Error: no courses in the field " + selectFieldCBox.getValue());
				return;
			}
			for (Course course : ClientController.getCourses())
				courses.add(course.getName());
			selectCourseCBox.setItems(courses);
			for (Question q : questions) {
				QuestionRow qr = new QuestionRow(q);
				questionTable.getItems().add(qr);
				if (pickedQuestions.contains(q))
					flag = true;
				qr.getViewBtn().setOnAction(e -> {
					FXMLLoader questionFormPageLoader = new FXMLLoader(
							getClass().getResource(Navigator.QUESTION_FORM.getVal()));
					try {
						QuestionForm = questionFormPageLoader.load();
						QuestionForm.toFront();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					{
						questionFormUIController = questionFormPageLoader.getController();
						questionFormUIController.getQuestionContentTxt().setText(q.getQuestionText());
						// questionFormUIController.getAnswerBtns().get(q.getCorrectAnswer()).setSelected(true);
						questionFormUIController.getNewQuestionFormLbl()
								.setText("Viewing question " + qr.getID() + " by " + qr.getAuthor());
						questionFormUIController.getQuestionContentTxt().setEditable(false);
						questionFormUIController.getFieldCBox().setDisable(true);
						for (int p = 0; p < 4; p++) {
							questionFormUIController.getAnswerTextFields().get(p).setText(q.getAnswers().get(p));
							questionFormUIController.getAnswerTextFields().get(p).setEditable(false);
							questionFormUIController.getAnswerBtns().get(p).setDisable(true);
							questionFormUIController.getSaveBtn().setVisible(false);
						}
					}
					;
					GeneralUIMethods.getPopupPane().setTranslateX(-208);
					GeneralUIMethods.getPopupPane().setTranslateY(-280);
					questionFormUIController.getQuestionsTxtAnchor().setTranslateY(-1
							* (questionFormUIController.getQuestionsTxtAnchor().getLayoutY()
									- questionFormUIController.getQuestionContentTxt().getLayoutY())
							- 1 * GeneralUIMethods.resizeTxtArea(questionFormUIController.getQuestionContentTxt())
							+ 80);
					questionFormUIController.getContentPaneAnchor().prefHeightProperty()
							.bind(testScrollPane.heightProperty().add(20));
					questionFormUIController.getContentPaneAnchor().prefWidthProperty()
							.bind(testScrollPane.widthProperty());
					questionFormUIController.getInsideContentAnchor().prefHeightProperty()
							.bind(testScrollPane.heightProperty().subtract(100));
					questionFormUIController.getQuestionAnchor().prefHeightProperty()
							.bind(testScrollPane.heightProperty().subtract(100));
					new PopUp(PopUp.TYPE.INFORM, "VIEW_QUESTION", "", contentPaneAnchor,
							Arrays.asList(new JFXButton("Cancel")), questionFormPageLoader);
				});

				if (pickedQuestions.size() == 0)
					previewTestBtn.setDisable(true);
				if (pickedQuestions.contains(q))
					qr.getCheckBox().setSelected(true);
				qr.getCheckBox().setOnAction(eventCheck -> {
					if (qr.getCheckBox().isSelected()) {
						pickedQuestions.add(q);
						previewTestBtn.setDisable(false);
					} else {
						pickedQuestions.remove(q);
						if (pickedQuestions.size() == 0)
							previewTestBtn.setDisable(true);
					}
					System.out.print("[");
					for (Question qe : pickedQuestions) {
						System.out.print(qe.getID());
						System.out.print(",");
					}
					System.out.print("]\n");
				});
			}
			if (flag)
				pickedQuestions.clear();
		});
	}

	/**
	 * clicking back will go back to the test bank screen
	 * 
	 * @param event
	 */
	@FXML
	void clickBack(MouseEvent event) {
		switch (Screen) {
		case 0:
			try {
				testBank = FXMLLoader.load(getClass().getResource(Navigator.TEST_BANK.getVal()));
				contentPaneAnchor.getChildren().setAll(testBank);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			continueWithParametersBtn.setVisible(true);
			previewTestBtn.setVisible(false);
			parametersVBox.setVisible(true);
			questionTable.setVisible(false);
			headTitleLbl.setText("Set parameters");
			break;
		case 2:
			previewTestBtn.setVisible(true);
			finishBtn.setVisible(false);
			questionTable.setVisible(true);
			headTitleLbl.setText("Choose questions to add to the test");
			testAnchor.setVisible(false);
			break;
		}
		if (--Screen == -1)
			Screen = 0;
	}

	/*
	 * testTitle, duration, course, studentInst, teacherInst//
	 */
	@FXML
	void clickContinueWithParameters(MouseEvent event) {
		Border border = new Border(
				new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
		if (titleTxt.getText().isEmpty() || durationCbox.getSelectionModel().isEmpty()
				|| selectFieldCBox.getSelectionModel().isEmpty() || selectCourseCBox.getSelectionModel().isEmpty()) {
			if ((titleTxt.getText().isEmpty()))
				titleTxt.setBorder(border);
			if (durationCbox.getSelectionModel().isEmpty())
				durationCbox.setBorder(border);
			if (selectFieldCBox.getSelectionModel().isEmpty())
				selectFieldCBox.setBorder(border);
			if (selectCourseCBox.getSelectionModel().isEmpty())
				selectCourseCBox.setBorder(border);
		} else {
			Screen++;
			testTitle = titleTxt.getText();
			duration = durationCbox.getValue().toString();
			field = selectFieldCBox.getValue().toString();
			course = selectCourseCBox.getValue().toString();
			studentInst = (studentInstructionsTxtArea.getText() == null) ? "null"
					: studentInstructionsTxtArea.getText();
			teacherInst = (teacherInstructionsTxtArea.getText() == null) ? "null"
					: teacherInstructionsTxtArea.getText();
			continueWithParametersBtn.setVisible(false);
			previewTestBtn.setVisible(true);
			parametersVBox.setVisible(false);
			questionTable.setVisible(true);
			parametersVBox.setVisible(false);
			headTitleLbl.setText("Choose questions to add to the test");
		}
		// If editing disable the possibility to change field and course
	}

	public JFXComboBox<String> getDurationCbox() {
		return durationCbox;
	}

	public void setDurationCbox(JFXComboBox<String> durationCbox) {
		this.durationCbox = durationCbox;
	}

	@FXML
	void clickPreviewTest(MouseEvent event) {
		Screen++;
		previewTestBtn.setVisible(false);
		finishBtn.setVisible(true);
		questionTable.setVisible(false);
		headTitleLbl.setText("Preview the test");
		testAnchor.setVisible(true);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigator.TEST_FORM.getVal()));
			Region test = loader.load();
			test.prefWidthProperty().bind(testScrollPane.widthProperty());
			test.prefHeightProperty().bind(testScrollPane.heightProperty().add(28));
			TestFormController controller = loader.getController();
//			controller.getScrollPane().setLayoutX(testScrollPane.layoutXProperty().doubleValue());
//			controller.getScrollPane().setLayoutY(testScrollPane.layoutYProperty().doubleValue());
			controller.getScrollPane().prefHeightProperty().bind(testScrollPane.heightProperty().add(20));
			controller.getScrollPane().prefWidthProperty().bind(testScrollPane.widthProperty());
			controller.getScrollPane().setTranslateX(10);
			controller.getScrollPane().setTranslateY(11);
			controller.getEditBtn().setVisible(false);
			controller.addTitleAndInstructionsToTest(testTitle, teacherInst, studentInst);
			int i = 1;
			for (Question q : pickedQuestions) {
				controller.addQuestionToTestForm(q, i, 100 / pickedQuestions.size()); // adding questions to preview
				i++;
			}
			GeneralUIMethods.loadPage(testAnchor, test);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// -------------need to implement an if statement that will block passage if no
		// questions were selected--------------
	}

	/**
	 * clicking continue will move to blank test form only if at least one question
	 * was chosen.
	 * 
	 * @param event
	 */
	@FXML
	void clickFinish(MouseEvent event) {
		Screen++;
		StringBuilder sb = new StringBuilder(); // changing the set to and array like : 12~1~5~5
		for (Question q : pickedQuestions) {
			sb.append(q.getID());
			sb.append("~");
		}
		System.out.println("ok: " + sb.toString());
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb.toString());
		String popupMsg = "";
		// Edit question query
		if (isEditingTest != null) {
			ClientController.accept("EDIT_TEST-" + isEditingTest + "," + testTitle + "," + duration + ","
					+ 100 / pickedQuestions.size() + "," + studentInst + "," + teacherInst + "," + sb.toString());
			popupMsg = "The test (ID: " + isEditingTest + ") has been updated!";
		} else { // Add new test query
			ClientController.accept("ADD_TEST-" + ClientController.getActiveUser().getSSN() + "," + testTitle + ","
					+ course + "," + duration + "," + 100 / pickedQuestions.size() + "," + studentInst + ","
					+ teacherInst + "," + sb.toString() + "," + field);
			popupMsg = "The test " + testTitle + " was added to " + course + " in " + field;
		}
		isEditingTest = null;
		// Show popup window
		JFXButton okayBtn = new JFXButton("Okay");
		okayBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			try {
				testBank = FXMLLoader.load(getClass().getResource(Navigator.TEST_BANK.getVal()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			GeneralUIMethods.loadPage(contentPaneAnchor, testBank);
		});
		GeneralUIMethods.setPopupPane(popUpWindow);
		new PopUp(PopUp.TYPE.INFORM, "Information", popupMsg, setParametersAnchor, Arrays.asList(okayBtn), null);
		GeneralUIMethods.setPopupPane(ClientController.getTeacherDashboardUIController().getPopUpWindow());
	}

}