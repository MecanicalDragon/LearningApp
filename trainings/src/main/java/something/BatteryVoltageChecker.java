package something;

/**
 * @author Stanislav Tretyakov
 * 24.03.2021
 */
public class BatteryVoltageChecker {

    private static class Battery {
        final Double voltage;

        private Battery(Double voltage) {
            this.voltage = voltage;
        }
    }

    private static class VoltageChecker {
        boolean checkVoltage(Battery a, Battery b) {
            return a.voltage.equals(b.voltage);
        }
    }

    private static class VoltageCheckerPlus extends VoltageChecker {
        final double epsilon;

        VoltageCheckerPlus(double epsilon) {
            this.epsilon = epsilon;
        }

        boolean checkVoltage(Battery a, Battery b) {
            return Math.abs(a.voltage - b.voltage) < epsilon;
        }
    }

    public static void main(String[] args) {
        double d = 0;
        for (int i = 0; i < 15; i++) d += 0.1;

        Battery a = new Battery(1.5);
        Battery b = new Battery(d);

        VoltageChecker voltageChecker = new VoltageChecker();
        boolean match = voltageChecker.checkVoltage(a, b);
        System.out.println(match);

        voltageChecker = new VoltageCheckerPlus(0.000001);
        match = voltageChecker.checkVoltage(a, b);
        System.out.println(match);
    }
}
