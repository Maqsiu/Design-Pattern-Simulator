package Simulator;

import observer.CConsoleObserver;
import observer.CFileObserver;
import observer.CListObserver;
import solver.CSolver;
import solver.CSolverCreator;
import solver.CStepData;
import solver.ESolverType;

import javax.swing.*;
import java.awt.*;

public class CMainForm extends JFrame {
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenu menuOptions;
    private JMenuItem itemExit;
    private JMenuItem itemSolve;
    private JMenuItem itemAbout;
    private JRadioButton rbFirst;
    private JRadioButton rbSecond;
    private JRadioButton rbFourth;
    private JTextField alphaTextField;
    private JTextField tkTextField;
    private JTextField omegaTextField;
    private JCheckBox cbPanel;
    private JCheckBox cbFile;
    private JCheckBox cbConsole;
    private JList<Object> list1;

    private DefaultListModel<Object> model;

    public CMainForm(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        model = new DefaultListModel<>();
        list1.setModel(model);

        itemExit.addActionListener(actionEvent -> CMainForm.this.dispose());
        itemAbout.addActionListener(actionEvent -> JOptionPane.showMessageDialog(CMainForm.this, "This program simulates the motion of a pendulum using numerical methods. \\n\n" +
                "It integrates the equations of motion using a specified solver method, such as the Euler method. \\n\n" +
                "The program takes initial conditions such as the final time, initial angle, and initial angular velocity. \\n\n" +
                "Results are computed at each time step and can be displayed in a graphical interface or written to a text file. \\n\n" +
                "Observers are notified about each step, allowing real-time updates of the system's state.\n", "About program", JOptionPane.INFORMATION_MESSAGE));
        itemSolve.addActionListener(actionEvent -> solveActionPerformed());

    }
    private void solveActionPerformed() {
        CSolverCreator sc = new CSolverCreator();

        ESolverType st = ESolverType.FIRST_ORDER;
        if (rbSecond.isSelected()) st = ESolverType.SECOND_ORDER;
        else if (rbFourth.isSelected()) st = ESolverType.FOURTH_ORDER;


        CStepData init = null;
        try {
            init = new CStepData(
                    Double.parseDouble(tkTextField.getText().trim()),
                    Double.parseDouble(alphaTextField.getText().trim()),
                    Double.parseDouble(omegaTextField.getText().trim())
            );
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(CMainForm.this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        }


        CSolver solverObj = sc.getSolver(st, init);

        if(cbPanel.isSelected()) {
            solverObj.addObserver(new CListObserver(model));
        }
        if(cbConsole.isSelected()) {
            solverObj.addObserver(new CConsoleObserver());
        }
        if(cbFile.isSelected()) {
            solverObj.addObserver(new CFileObserver());
        }

        solverObj.solve();
    }


}
