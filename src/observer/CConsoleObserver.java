package observer;

import solver.CStepData;

import java.util.Observable;
import java.util.Observer;

public class CConsoleObserver implements IObserver {
    @Override
    public void update(CStepData data) {
        System.out.printf("%-6.4f.  %-6.4f  %-6.4f\n", data.T, data.Alpha, data.Omega);
    }
}
