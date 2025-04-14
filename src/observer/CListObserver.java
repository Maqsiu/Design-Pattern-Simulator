package observer;

import solver.CStepData;
import javax.swing.*;

public class CListObserver implements IObserver {

    private final DefaultListModel<Object> model;

    public CListObserver(DefaultListModel<Object> model) {
        model.clear();
        model.addElement("Time [s]   Alpha [rad]   Omega [rad/s]");
        this.model = model;
    }

    @Override
    public void update(CStepData data) {
        String formatted = String.format("%-8.4f   %-12.7f   %-12.7f",
                data.T, data.Alpha, data.Omega);
        model.addElement(formatted);
    }
}
