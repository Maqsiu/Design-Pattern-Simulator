package observer;

import solver.CSolver;
import solver.CStepData;

public interface IObserver {
    void update(CStepData data);
}
