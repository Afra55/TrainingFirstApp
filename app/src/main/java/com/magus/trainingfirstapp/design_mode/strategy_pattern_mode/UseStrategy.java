package com.magus.trainingfirstapp.design_mode.strategy_pattern_mode;

/**
 * Created by yangshuai in the 11:39 of 2016.01.29 .
 */
public class UseStrategy {

    private Strategy strategy = new StrategyOne();

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int strategy() {
        return strategy.strategy();
    }
}
