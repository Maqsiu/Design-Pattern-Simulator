package solver;

import observer.IObserver;

public class CSolver2nd extends CSolver{

    public CSolver2nd(double Tk, double Alfa0, double Omega0) {
        super(Tk, Alfa0, Omega0);
        type = ESolverType.SECOND_ORDER;
    }

    private final double [] ax = new double[2];
    private final double [] dx = new double[2];
    private final double [] tx = new double[2];
    private final double [] k1 = new double[2];
    private final double [] k2 = new double[2];

    @Override
    protected double[] doStep(double[] Y) {
        int i;
        double [] YK = new double[2];
        ax[0] = Y[0];
        ax[1] = Y[1];
        pendulum(ax, dx);
        for (i = 0; i < 2; i++) {
            k1[i] = dx[i] * dt;
            tx[i] = ax[i] + k1[i] * 0.5;
        }
        pendulum(tx, dx);
        for (i = 0; i < 2; i++) {
            k2[i] = dx[i] * dt;
            ax[i] = ax[i]+0.5*(k1[i]+k2[i]);
        }
        YK[0] = ax[0];
        YK[1] = ax[1];
        return YK;
    }

    @Override
    public CSolver getSolver() {

        return this;
    }

}
