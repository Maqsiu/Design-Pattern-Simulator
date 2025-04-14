package solver;

import observer.IObservedObject;
import observer.IObserver;

import java.util.ArrayList;

public abstract class CSolver implements IObservedObject {

    protected double T0, Tk, dt;
    protected double L, m, M, kt, g;
    protected double Alpha0, Omega0;
    protected ArrayList<IObserver> observers;

    protected ESolverType type = null;

    public CSolver(double Tk, double Alpha0, double Omega0) {
        T0 = 0.0;
        this.Tk = Tk;
        dt = 0.01;
        L = 1.0;
        m = 0.1;
        M = 0.0;
        kt = 1.0;
        g = 9.81;
        this.Alpha0 = Alpha0;
        this.Omega0 = Omega0;
        observers = new ArrayList<>();
    }

    protected void pendulum(double[] aa, double[] dd) {
        dd[1] = M / (m * L * L) - kt * aa[1] + g / L * Math.sin(aa[0]);
        dd[0] = aa[1];
    }

    public void solve() {
        double[] Y0 = new double[2];
        double[] Y1;
        Y0[0] = Alpha0;
        Y0[1] = Omega0;
        notifyObservers(new CStepData(0.0, Alpha0, Omega0));
        for (double dd = T0 + dt; dd <= Tk; dd += dt) {
            Y1 = doStep(Y0);
            Y0[0] = Y1[0];
            Y0[1] = Y1[1];
            notifyObservers(new CStepData(dd, Y1[0], Y1[1]));
        }
    }

    abstract public CSolver getSolver();
    abstract protected double[] doStep(double[] Y);

    @Override
    public void addObserver(IObserver obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(IObserver obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers(CStepData data) {
        for (IObserver obs : observers) {
            obs.update(data);
        }
    }
}
