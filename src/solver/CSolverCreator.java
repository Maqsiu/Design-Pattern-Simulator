package solver;

public class CSolverCreator implements ISolverCreator {

    @Override
    public CSolver getSolver(ESolverType type, CStepData init) {
        switch (type) {
            case FIRST_ORDER:
                return new CSolver1st(init.T, init.Alpha, init.Omega).getSolver();
            case SECOND_ORDER:
                return new CSolver2nd(init.T, init.Alpha, init.Omega).getSolver();
            case FOURTH_ORDER:
                return new CSolver4th(init.T, init.Alpha, init.Omega).getSolver();
            default:
                return null;
        }
    }
}
