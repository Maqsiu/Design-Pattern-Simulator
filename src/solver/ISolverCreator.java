package solver;

public interface ISolverCreator {
    CSolver getSolver(ESolverType type, CStepData init);
}
