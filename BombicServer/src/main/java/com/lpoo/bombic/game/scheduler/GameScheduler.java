package com.lpoo.bombic.game.scheduler;

import com.lpoo.bombic.game.GameManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedDeque;


public class GameScheduler
        implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameScheduler.class);

    private static final String SIMULATION_THREAD_NAME = "[GameScheduler - Simulation Thread]";

    private final ConcurrentLinkedDeque<IGameRunnable> mQueuedRunnables = new ConcurrentLinkedDeque<>();

    private final GameManager mGameManager;

    private Thread  mSimulationThread;
    private boolean mSimulationRunning;

    public GameScheduler(final GameManager pGameManager) {
        mGameManager = pGameManager;
    }

    public void startSimulation() {
        LOGGER.info("Starting Simulation...");
        mSimulationRunning = true;
        mSimulationThread = new Thread(this);
        mSimulationThread.setName(SIMULATION_THREAD_NAME);
        mSimulationThread.start();
    }

    public void stopSimulation() {
        mSimulationRunning = false;
        try {
            mSimulationThread.join();
        } catch (InterruptedException pE) {
            pE.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (mSimulationRunning) {
            GameTimer.updateTime();
            try {
                IGameRunnable gameRunnable;
                while ((gameRunnable = mQueuedRunnables.poll()) != null) {
                    gameRunnable.run();
                }
                mGameManager.doTick();
            } catch (Exception pE) {
                pE.printStackTrace();
            }
            final long sleepTime = (long) (GameTimer.TIME_PER_TICK - GameTimer.getNewDelta());
            if (sleepTime < 0) {
                LOGGER.warn("Game Thread couldn't keep up for {} seconds!", -(sleepTime / 1000F));
            }
            try {
                Thread.sleep(sleepTime);
            } catch (Exception ignored) {
            }
        }
    }

    public void runSyncRunnable(final IGameRunnable pIGameRunnable) {
        if (pIGameRunnable != null) {
            mQueuedRunnables.add(pIGameRunnable);
        }
    }

    public Thread runAsyncRunnable(final IGameRunnable pIGameRunnable) {
        final Thread thread = new Thread(pIGameRunnable);
        thread.start();
        return thread;
    }
}
