package net.medrag.microservices.aspectj.service;

public aspect BalanceAspect {

    pointcut callDecreaseBalance(int delta, BalanceService bc)
            : call(int BalanceService.decreaseBalance(int)) && args(delta) && target(bc);

    before(int delta, BalanceService bc): callDecreaseBalance(delta, bc) {
        System.out.println("Before... delta=" + delta);
    }

    int around(int delta, BalanceService bc): callDecreaseBalance(delta, bc) {
        if (bc.balance.get() < 500) {
            System.out.println("Operation not allowed!");
            return bc.balance.get();
        }
        return proceed(delta, bc);
    }

    after(int delta, BalanceService bc): callDecreaseBalance(delta, bc) {
        System.out.println("After... delta=" + delta);
    }
}